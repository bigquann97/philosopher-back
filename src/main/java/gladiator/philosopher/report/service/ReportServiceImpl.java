package gladiator.philosopher.report.service;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.comment.entity.Comment;
import gladiator.philosopher.comment.service.CommentService;
import gladiator.philosopher.post.entity.Post;
import gladiator.philosopher.post.service.PostService;
import gladiator.philosopher.report.dto.ReportRequestDto;
import gladiator.philosopher.report.dto.ReportResponseDto;
import gladiator.philosopher.report.repository.ReportRepository;
import gladiator.philosopher.thread.entity.Thread;
import gladiator.philosopher.thread.service.ThreadService;
import java.util.List;
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
    if (reportRepository.countByPost(post) >= COUNT_FOR_AUTO_BLIND) {post.blind();}
    reportRepository.save(dto.toEntity(post, reporter));}

  @Override
  public void reportComment(Long id, ReportRequestDto dto, Account reporter) {
    Comment comment = commentService.getCommentEntity(id);
    if (reportRepository.countByComment(comment) >= COUNT_FOR_AUTO_BLIND) {comment.blind();}
    reportRepository.save(dto.toEntity(comment, reporter));}
  @Override
  public void reportThread(Long id, ReportRequestDto dto, Account reporter) {
    Thread thread = threadService.getThreadEntity(id);
    if (reportRepository.countByThread(thread) >= COUNT_FOR_AUTO_BLIND) {thread.blind();}
    reportRepository.save(dto.toEntity(thread, reporter));}
  @Override
  public void reportPostTest(Long id) {
    Post post = postService.getPostEntity(id);
    System.out.println("post data is : " + post.getTitle());
  }
  @Override
  public void reportPostTest2(Long id, ReportRequestDto dto, Account member) {
    System.out.println("dto data is : " + dto.getContent());
    System.out.println("dto data is T : " + dto.getCategory());
    System.out.println("member data is : " + member.getEmail());
  }

  @Override
  public List<ReportResponseDto> getReports() {
    return reportRepository.getReports();
  }
}