package gladiator.philosopher.report.service;

import gladiator.philosopher.report.repository.ReportRepository;
import gladiator.philosopher.thread.entity.Thread;
import gladiator.philosopher.thread.service.ThreadService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

  private final static int COUNT_FOR_AUTO_BLIND = 10;
  private final ReportRepository reportRepository;

  private final PostService postService;
  private final ThreadService threadService;
  private final CommentService commentService;

  @Override
  public void reportPost(final Long id, ReportRequestDto dto, Account reporter) {
    Post post = postService.getPostEntity(id);

    Report report = Report.postReport()
        .reporter(reporter)
        .reported(post.getAccount())
        .category(dto.getReportCategory())
        .post(post)
        .build();
    reportRepository.save(report);
    if (reportRepository.countByPost(post) >= COUNT_FOR_AUTO_BLIND) {
      post.blind();
    }
  }

  @Override
  public void reportComment(Long id, ReportRequestDto dto, Account reporter) {
    Comment comment = commentService.getCommentEntity(id);

    Report report = Report.commentReport()
        .reporter(reporter)
        .reported(comment.getAccount())
        .category(dto.getReportCategory())
        .comment(comment)
        .build();

    reportRepository.save(report);
    if (reportRepository.countByComment(comment) >= COUNT_FOR_AUTO_BLIND) {
      comment.blind();
    }
  }

  @Override
  public void reportThread(Long id, ReportRequestDto dto, Account reporter) {
    Thread thread = threadService.getThreadEntity(id);

    Report report = Report.threadReport()
        .reporter(reporter)
        .reported(thread.getAccount())
        .category(dto.getReportCategory())
        .thread(thread)
        .build();

    reportRepository.save(report);

    if (reportRepository.countByThread(thread) >= COUNT_FOR_AUTO_BLIND) {
      thread.blind();
    }
  }

}
