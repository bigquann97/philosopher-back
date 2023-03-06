package gladiator.philosopher.rank.service;

import gladiator.philosopher.rank.dto.UserRankingResponseDto;
import gladiator.philosopher.rank.entity.Rank;
import gladiator.philosopher.rank.repository.RankRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RankServiceImpl implements RankService {

  private final RankRepository rankRepository;

  @Override
  @Transactional
  public void startRankCount(final String nickname) {
    if (rankRepository.existsByNickname(nickname)) {
      Rank rank = rankRepository.findbyRank(nickname);
      Rank updateCount = rank.updateCount();
      rankRepository.saveAndFlush(updateCount);
    } else {
      Rank rank = Rank.builder()
          .nickname(nickname)
          .count(0L)
          .build();
      rankRepository.save(rank);
    }
  }

  @Override
  @Transactional(readOnly = true)
  public List<UserRankingResponseDto> getNowRankings() {
    PageRequest pageRequest = PageRequest.of(0, 5);
    List<UserRankingResponseDto> userRankingResponseDtos = rankRepository.searchRankByCount(
        pageRequest);
    return userRankingResponseDtos;
  }

  @Override
  @Transactional
  public void deleteRankingAll() {
    List<Long> getIds = rankRepository.getAllById();
    rankRepository.deleteAllById(getIds);
  }

}
