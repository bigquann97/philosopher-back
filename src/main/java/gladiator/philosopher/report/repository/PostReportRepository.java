package gladiator.philosopher.report.repository;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.report.dto.post.PostReportResponseDto;
import gladiator.philosopher.report.entity.PostReport;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

public interface PostReportRepository extends JpaRepository<PostReport, Long> {


  @Query("select new gladiator.philosopher.report.dto.post.PostReportResponseDto(pr.id, pr.content, pr.category, pr.reporter.nickname, pr.postId)  from PostReport  pr order by pr.id desc ")
  List<PostReportResponseDto> getAllPostReportDtosByAdmin();

  boolean existsByReporterAndPostId(@NonNull Account reporter, @NonNull Long postId);

  long countByPostId(@NonNull Long postId);

  @Modifying(clearAutomatically = true)
  @Transactional
  @Query("delete from PostReport p where p.postId =:id")
  void deleteByPostId(@Param("id")Long id);

}
