package gladiator.philosopher.report.service;

import gladiator.philosopher.Account.entity.Account;
import gladiator.philosopher.comment.entity.Comment;
import gladiator.philosopher.post.entity.Post;
import gladiator.philosopher.report.dto.ReportRequestDto;
import gladiator.philosopher.report.dto.ReportResponseDto;
import gladiator.philosopher.report.repository.ReportRepository;
import gladiator.philosopher.thread.entity.Thread;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

  private final static int COUNT_FOR_AUTO_BLIND = 3;
  private final ReportRepository reportRepository;

  @Override
  public void reportPost(
      final Post post,
      final ReportRequestDto dto,
      final Account reporter
  ) {
    checkIfReporterAlreadyReportedObject(post, reporter);
    reportRepository.save(dto.toEntity(post, reporter));
    if (reportRepository.countByPostId(post.getId()) >= COUNT_FOR_AUTO_BLIND) {
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
    reportRepository.save(dto.toEntity(comment, reporter));
    if (reportRepository.countByCommentId(comment.getId()) >= COUNT_FOR_AUTO_BLIND) {
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
    reportRepository.save(dto.toEntity(thread, reporter));
    if (reportRepository.countByThreadId(thread.getId()) >= COUNT_FOR_AUTO_BLIND) {
      thread.blind();
    }
  }

  @Override
  public List<ReportResponseDto> getReports() {
    return reportRepository.getReports();
  }
  
  private void checkIfReporterAlreadyReportedObject(Object obj, Account reporter) {
    if (obj instanceof Post) {
      Post post = (Post) obj;
      if (reportRepository.existsByReporterAndPostId(reporter, post.getId())) {
        throw new IllegalArgumentException("이미 신고한 게시물");
      }
    } else if (obj instanceof Thread) {
      Thread thread = (Thread) obj;
      if (reportRepository.existsByReporterAndThreadId(reporter, thread.getId())) {
        throw new IllegalArgumentException("이미 신고한 게시물");
      }
    } else if (obj instanceof Comment) {
      Comment comment = (Comment) obj;
      if (reportRepository.existsByReporterAndCommentId(reporter, comment.getId())) {
        throw new IllegalArgumentException("이미 신고한 게시물");
      }
    }
  }

}