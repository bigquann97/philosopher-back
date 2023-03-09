package gladiator.philosopher.rank.service;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import gladiator.philosopher.rank.dto.UserRankingResponseDto;
import gladiator.philosopher.rank.entity.Philosopher;
import gladiator.philosopher.rank.entity.Rank;
import gladiator.philosopher.rank.repository.RankRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;
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
          .count(1L)
          .build();
      rankRepository.save(rank);
    }
  }

  @Override
  @Transactional(readOnly = true)
  public List<UserRankingResponseDto> getNowRankings() {

    List<Philosopher> list = Arrays.asList(Philosopher.values());
    PageRequest pageRequest = PageRequest.of(0, 5);
    List<UserRankingResponseDto> userRankingResponseDtos = rankRepository.searchRankByCount(
        pageRequest);
    int i=0;
    for (UserRankingResponseDto userRankingResponseDto : userRankingResponseDtos) {
      userRankingResponseDto.setPhilosopher(list.get(i));
      i = i+1;
    }
    return userRankingResponseDtos;
  }

  @Override
  @Transactional
  public void deleteRankingAll() {
    List<Long> getIds = rankRepository.getAllById();
    rankRepository.deleteAllById(getIds);
  }

}
