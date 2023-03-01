package gladiator.philosopher.rank.service;

import gladiator.philosopher.rank.dto.UserRankingResponseDto;
import java.util.List;

public interface RankService {

  void startRankCount(String nickname);

  List<UserRankingResponseDto> getNowRankings();

  void deleteRankingAll();
}
