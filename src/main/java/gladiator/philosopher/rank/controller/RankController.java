package gladiator.philosopher.rank.controller;

import gladiator.philosopher.rank.dto.UserRankingResponseDto;
import gladiator.philosopher.rank.repository.RankRepository;
import gladiator.philosopher.rank.service.RankService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/rank")
public class RankController {

  private final RankService rankService;

  /**
   * Top 5 랭킹 조회
   * @return
   */
  @GetMapping()
  @ResponseStatus(HttpStatus.OK)
  public List<UserRankingResponseDto> getRankings() {
    return rankService.getNowRankings();
  }

  /**
   * 랭킹 삭제
   */
  @DeleteMapping
  @ResponseStatus(HttpStatus.OK)
  public void deleteTodayRanks() {
    rankService.deleteRankingAll();
  }
}
