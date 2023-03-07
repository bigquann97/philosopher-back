package gladiator.philosopher.rank.repository;

import gladiator.philosopher.rank.dto.UserRankingResponseDto;
import gladiator.philosopher.rank.entity.Rank;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface RankRepository extends JpaRepository<Rank, Long> {

  boolean existsByNickname(String nickname);

  @Query("select r from Rank r where r.nickname=:nickname")
  Rank findbyRank(@Param("nickname")String nickname);

  @Query("select new gladiator.philosopher.rank.dto.UserRankingResponseDto(r.id, r.nickname, r.count) from Rank r order by r.count DESC ")
  List<UserRankingResponseDto> searchRankByCount(Pageable pageable);

  @Query("select r.id from Rank r")
  List<Long> getAllById();

  @Query("delete from Rank r where r.id in :id")
  @Modifying
  @Transactional
  void deleteAllById(@Param("id")List<Long> id);


}
