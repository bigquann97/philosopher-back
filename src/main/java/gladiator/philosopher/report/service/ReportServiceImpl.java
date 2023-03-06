package gladiator.philosopher.report.service;

import static gladiator.philosopher.common.exception.dto.ExceptionStatus.DUPLICATED_REPORT_COMMENT;
import static gladiator.philosopher.common.exception.dto.ExceptionStatus.DUPLICATED_REPORT_POST;
import static gladiator.philosopher.common.exception.dto.ExceptionStatus.DUPLICATED_REPORT_THREAD;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.comment.entity.Comment;
import gladiator.philosopher.comment.service.CommentService;
import gladiator.philosopher.common.dto.MyPage;
import gladiator.philosopher.common.exception.DuplicatedException;
import gladiator.philosopher.post.entity.Post;
import gladiator.philosopher.post.service.PostService;
import gladiator.philosopher.recommend.repository.CommentRecommendRepository;
import gladiator.philosopher.report.dto.CommentReportResponseDto;
import gladiator.philosopher.report.dto.CommentReportSearchCondition;
import gladiator.philosopher.report.dto.PostReportResponseDto;
import gladiator.philosopher.report.dto.PostReportSearchCondition;
import gladiator.philosopher.report.dto.ReportRequestDto;
import gladiator.philosopher.report.dto.ThreadReportResponseDto;
import gladiator.philosopher.report.dto.ThreadReportSearchCondition;
import gladiator.philosopher.report.repository.comment.CommentReportRepository;
import gladiator.philosopher.report.repository.post.PostReportRepository;
import gladiator.philosopher.report.repository.thread.ThreadReportRepository;
import gladiator.philosopher.thread.entity.Thread;
import gladiator.philosopher.thread.service.ThreadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {

  private final static int COUNT_FOR_AUTO_BLIND = 1;
  private final PostReportRepository postReportRepository;
  private final ThreadReportRepository threadReportRepository;
  private final CommentReportRepository commentReportRepository;

  private final ThreadService threadService;
  private final PostService postService;
  private final CommentService commentService;
  private final CommentRecommendRepository commentRecommendRepository;

  @Override
  @Transactional
  public void reportPost(
      final Long id,
      final ReportRequestDto dto,
      final Account reporter
  ) {
    Post post = postService.getPostEntity(id);
    checkIfReporterAlreadyReportedObject(post, reporter);
    postReportRepository.save(dto.toEntity(post, reporter));
    if (postReportRepository.countByPostId(id) >= COUNT_FOR_AUTO_BLIND) {
      if (post.isBlinded()) {
        return;
      }
      post.blind();
    }
  }

  @Override
  @Transactional
  public void reportComment(
      final Long id,
      final ReportRequestDto dto,
      final Account reporter
  ) {
    Comment comment = commentService.getCommentEntity(id);
    checkIfReporterAlreadyReportedObject(comment, reporter);
    commentReportRepository.save(dto.toEntity(comment, reporter));
    if (commentReportRepository.countByCommentId(id) >= COUNT_FOR_AUTO_BLIND) {
      if (comment.isBlinded()) {
        return;
      }
      comment.blind();
    }
  }

  @Override
  @Transactional
  public void reportThread(
      final Long id,
      final ReportRequestDto dto,
      final Account reporter
  ) {
    Thread thread = threadService.getThreadEntity(id);
    checkIfReporterAlreadyReportedObject(thread, reporter);
    threadReportRepository.save(dto.toEntity(thread, reporter));
    if (threadReportRepository.countByThreadId(thread.getId()) >= COUNT_FOR_AUTO_BLIND) {
      if (!thread.isBlinded()) {
        thread.blind();
      }
    }
  }

  /**
   * 게시글 신고 목록 조회 (사용처 : 어드민)
   */
  @Override
  @Transactional(readOnly = true)
  public MyPage<PostReportResponseDto> getPostReports(
      final PostReportSearchCondition condition,
      final Pageable pageable
  ) {
    return postReportRepository.getPostReports(condition, pageable);
  }

  /**
   * 댓글 신고 목록 조회 (사용처 : 어드민)
   */
  @Override
  @Transactional(readOnly = true)
  public MyPage<CommentReportResponseDto> getCommentReports(
      final CommentReportSearchCondition condition,
      final Pageable pageable
  ) {
    return commentReportRepository.getCommentReports(condition, pageable);
  }

  /**
   * 쓰레드 신고 목록 조회 (사용처 : 어드민)
   */
  @Override
  @Transactional(readOnly = true)
  public MyPage<ThreadReportResponseDto> getThreadReports(
      final ThreadReportSearchCondition condition,
      final Pageable pageable
  ) {
    return threadReportRepository.getThreadReports(condition, pageable);
  }

  // --- Private Methods ---

  private void checkIfReporterAlreadyReportedObject(Object obj, Account reporter) {
    if (obj instanceof Post) {
      Post post = (Post) obj;
      if (postReportRepository.existsByReporterAndPostId(reporter, post.getId())) {
        throw new DuplicatedException(DUPLICATED_REPORT_POST);
      }
    } else if (obj instanceof Thread) {
      Thread thread = (Thread) obj;
      if (threadReportRepository.existsByReporterAndThreadId(reporter, thread.getId())) {
        throw new DuplicatedException(DUPLICATED_REPORT_THREAD);
      }
    } else if (obj instanceof Comment) {
      Comment comment = (Comment) obj;
      if (commentReportRepository.existsByReporterAndCommentId(reporter, comment.getId())) {
        throw new DuplicatedException(DUPLICATED_REPORT_COMMENT);
      }
    }
  }
  
}