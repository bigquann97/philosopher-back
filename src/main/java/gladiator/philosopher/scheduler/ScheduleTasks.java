package gladiator.philosopher.scheduler;

import static gladiator.philosopher.thread.service.ThreadServiceImpl.THREAD_TIME_LIST_KEY;

import gladiator.philosopher.common.util.RedisUtil;
import gladiator.philosopher.rank.service.RankService;
import gladiator.philosopher.thread.entity.Thread;
import gladiator.philosopher.thread.service.ThreadService;
import java.time.LocalDateTime;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduleTasks {

  private final RedisUtil redisUtil;
  private final ThreadService threadService;
  private final RankService rankService;

  // 0 0 0 1/1 * ?- 매일 00:00:01 마다
  @Scheduled(cron = "1 0 0 1/1 * ?")
  @Transactional
  public void cronExpression() {
    log.info("Scheduler 동작 - 진행 중인 쓰레드 총 검사");
    Map<String, String> data = redisUtil.getAllHashData(THREAD_TIME_LIST_KEY);
    data.forEach((id, time) -> {
      LocalDateTime endDate = LocalDateTime.parse(time);
      if (LocalDateTime.now().isAfter(endDate)) {
        Thread thread = threadService.getThreadEntity(Long.parseLong(id));
        threadService.finishThread(thread);
        log.error("{} 번 쓰레드 종료", thread.getId());
        redisUtil.deleteHashData(THREAD_TIME_LIST_KEY, id);
      }
    });

  }

  @Scheduled(cron = "0 1 0 ? * MON *") // 매주 월요일 00시 01분 초기화
  @Transactional
  public void rankingCleaner() {
    log.info("랭킹 서비스 초기화 시작");
    rankService.deleteRankingAll();
    log.info("랭킹 서비스 초기화 종료");
  }

}
