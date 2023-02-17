package gladiator.philosopher.report.service;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.comment.entity.Comment;
import gladiator.philosopher.post.entity.Post;
import gladiator.philosopher.report.dto.ReportRequestDto;
import gladiator.philosopher.report.dto.ReportResponseDto;
import gladiator.philosopher.report.repository.CommentReportRepository;
import gladiator.philosopher.report.repository.PostReportRepository;
import gladiator.philosopher.report.repository.ThreadReportRepository;
import gladiator.philosopher.thread.entity.Thread;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

  private final static int COUNT_FOR_AUTO_BLIND = 3;
  private final PostReportRepository postReportRepository;
  private final ThreadReportRepository threadReportRepository;
  private final CommentReportRepository commentReportRepository;


  @Override
  public void reportPost(
      final Post post,
      final ReportRequestDto dto,
      final Account reporter
  ) {
    checkIfReporterAlreadyReportedObject(post, reporter);
    postReportRepository.save(dto.toEntity(post, reporter));
    if (postReportRepository.countByPostId(post.getId()) >= COUNT_FOR_AUTO_BLIND) {
      if (post.isBlinded()) {
        return;
      }
      post.blind();
    }
  }

  @Override
  public void reportComment(
      final Comment comment,
      final ReportRequestDto dto,
      final Account reporter
  ) {
    checkIfReporterAlreadyReportedObject(comment, reporter);
    commentReportRepository.save(dto.toEntity(comment, reporter));
    if (commentReportRepository.countByCommentId(comment.getId()) >= COUNT_FOR_AUTO_BLIND) {
      if (comment.isBlinded()) {
        return;
      }
      comment.blind();
    }
  }

  @Override
  public void reportThread(
      final Thread thread,
      final ReportRequestDto dto,
      final Account reporter
  ) {
    checkIfReporterAlreadyReportedObject(thread, reporter);
    threadReportRepository.save(dto.toEntity(thread, reporter));
    if (threadReportRepository.countByThreadId(thread.getId()) >= COUNT_FOR_AUTO_BLIND) {
      if (thread.isBlinded()) {
        return;
      }
      thread.blind();
    }
  }

  @Override
  public List<ReportResponseDto> getReports() {
    // TODO: 2023/02/17 reportRepository삭제 
    return null;
  }

  private void checkIfReporterAlreadyReportedObject(Object obj, Account reporter) {
    if (obj instanceof Post) {
      Post post = (Post) obj;
      if (postReportRepository.existsByReporterAndPostId(reporter, post.getId())) {
        throw new IllegalArgumentException("이미 신고한 게시물");
      }
    } else if (obj instanceof Thread) {
      Thread thread = (Thread) obj;
      if (threadReportRepository.existsByReporterAndThreadId(reporter, thread.getId())) {
        throw new IllegalArgumentException("이미 신고한 게시물");
      }
    } else if (obj instanceof Comment) {
      Comment comment = (Comment) obj;
      if (commentReportRepository.existsByReporterAndCommentId(reporter, comment.getId())) {
        throw new IllegalArgumentException("이미 신고한 게시물");
      }
    }
  }

}