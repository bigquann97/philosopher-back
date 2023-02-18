package gladiator.philosopher.thread.controller;


import gladiator.philosopher.thread.dto.ThreadResponseDto;
import gladiator.philosopher.thread.dto.ThreadSearchCond;
import gladiator.philosopher.thread.dto.ThreadSimpleResponseDto;
import gladiator.philosopher.thread.entity.Sort;
import gladiator.philosopher.thread.service.ThreadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/threads")
public class ThreadController {

  private final ThreadService threadService;

  /**
   * 30초마다 Thread 제어(시간 수정 필요)
   */
  @Scheduled(fixedDelay = 30000)
  public void scheduledThreadStop() {
    log.info("Thread Active 상태에서 Archived 상태로 변경 시작");
    threadService.controllActiveThreads();
    log.info("Thread Active 상태에서 Archived 상태로 변경 종료");
  }

  // 쓰레드 단건 조회
  @GetMapping("/{threadId}")
  @ResponseStatus(HttpStatus.OK)
  public ThreadResponseDto selectThread(
      @PathVariable final Long threadId
  ) {
    return threadService.selectThread(threadId);
  }

  // 쓰레드 객체 페이징 조회
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public Page<ThreadSimpleResponseDto> selectActiveThreads(
      final @RequestParam(required = false) Long category,
      final @RequestParam(required = false) Integer page,
      final @RequestParam(required = false) Sort sort,
      final @RequestParam(required = false) String word
  ) {
    return threadService.selectActiveThreads(ThreadSearchCond.of(page, sort, word, category));
  }

  // 아카이빙 된 쓰레드 단건 조회
  @GetMapping("/archived/{threadId}")
  @ResponseStatus(HttpStatus.OK)
  public ThreadResponseDto selectArchivedThread(
      @PathVariable final Long threadId
  ) {
    return threadService.selectArchivedThread(threadId);
  }

  // 아카이빙 된 쓰레드 조회
  @GetMapping("/archived")
  @ResponseStatus(HttpStatus.OK)
  public Page<ThreadSimpleResponseDto> selectArchivedThreads(
      final @RequestParam(required = false) Long category,
      final @RequestParam(required = false) Integer page,
      final @RequestParam(required = false) Sort sort,
      final @RequestParam(required = false) String word
  ) {
    return threadService.selectArchivedThreads(ThreadSearchCond.of(page, sort, word, category));
  }

}
