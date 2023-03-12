package gladiator.philosopher.thread.controller;


import gladiator.philosopher.common.dto.MyPage;
import gladiator.philosopher.thread.dto.ThreadResponseDto;
import gladiator.philosopher.thread.dto.ThreadSearchCond;
import gladiator.philosopher.thread.dto.ThreadSimpleResponseDto;
import gladiator.philosopher.thread.enums.Sort;
import gladiator.philosopher.thread.service.ThreadService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
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
   * 쓰레드 단건 조회
   * @param threadId
   * @return
   */
  @GetMapping("/{threadId}")
  @ResponseStatus(HttpStatus.OK)
  public ThreadResponseDto selectThread(
      final @PathVariable Long threadId
  ) {
    return threadService.selectThread(threadId);
  }

  /**
   * 현재 활성화 된 쓰레드 다건 조회
   * @param category
   * @param page
   * @param sort
   * @param word
   * @return
   */
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public MyPage<ThreadSimpleResponseDto> selectActiveThreads(
      final @RequestParam(required = false) Long category,
      final @RequestParam(required = false) Integer page,
      final @RequestParam(required = false) Sort sort,
      final @RequestParam(required = false) String word
  ) {
    return threadService.selectActiveThreads(ThreadSearchCond.of(page, sort, word, category));
  }

  /**
   * 아카이브 된 쓰레드 단건 조회 ( 종료된 쓰레드 )
   * @param threadId
   * @return
   */
  @GetMapping("/archived/{threadId}")
  @ResponseStatus(HttpStatus.OK)
  public ThreadResponseDto selectArchivedThread(
      final @PathVariable Long threadId
  ) {
    return threadService.selectArchivedThread(threadId);
  }

  /**
   * 아카이브된 쓰레드 다건 조회 ( 종료된 쓰레드 )
   * @param category
   * @param page
   * @param sort
   * @param word
   * @return
   */
  @GetMapping("/archived")
  @ResponseStatus(HttpStatus.OK)
  public MyPage<ThreadSimpleResponseDto> selectArchivedThreads(
      final @RequestParam(required = false) Long category,
      final @RequestParam(required = false) Integer page,
      final @RequestParam(required = false) Sort sort,
      final @RequestParam(required = false) String word
  ) {
    return threadService.selectArchivedThreads(ThreadSearchCond.of(page, sort, word, category));
  }

}
