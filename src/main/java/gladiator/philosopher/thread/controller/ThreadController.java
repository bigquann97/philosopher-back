package gladiator.philosopher.thread.controller;


import gladiator.philosopher.post.service.PostService;
import gladiator.philosopher.thread.dto.Sort;
import gladiator.philosopher.thread.dto.ThreadResponseDto;
import gladiator.philosopher.thread.dto.ThreadSearchCond;
import gladiator.philosopher.thread.dto.ThreadSimpleResponseDto;
import gladiator.philosopher.thread.service.ThreadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/threads")
public class ThreadController {

  private final ThreadService threadService;
  private final PostService postService;

  // 쓰레드 단건 조회
  @GetMapping("/{threadId}")
  @ResponseStatus(HttpStatus.OK)
  public ThreadResponseDto getThread(
      @PathVariable final Long threadId
  ) {
    return threadService.getThread(threadId);
  }

  // 쓰레드 객체 페이징 조회
  @GetMapping
  @ResponseStatus(HttpStatus.OK)
  public Page<ThreadSimpleResponseDto> getThreadItems(
      @RequestParam(required = false) final Integer page,
      @RequestParam(required = false) final Sort sort,
      @RequestParam(required = false) final String word
  ) {
    return threadService.getActiveThreads(ThreadSearchCond.of(page, sort, word));
  }

  // 아카이빙 된 쓰레드 조회
  @GetMapping("/archived")
  @ResponseStatus(HttpStatus.OK)
  public Page<ThreadSimpleResponseDto> getPagingArchivedThread(
      @RequestParam(required = false) final Integer page,
      @RequestParam(required = false) final Sort sort,
      @RequestParam(required = false) final String word
  ) {
    return threadService.getArchivedThreads(ThreadSearchCond.of(page, sort, word));
  }

  @PostMapping("/test/{id}")
  public void startThreadTest(@PathVariable("id")Long id){
    threadService.startThread(postService.getPostEntity(id));
  }

}
