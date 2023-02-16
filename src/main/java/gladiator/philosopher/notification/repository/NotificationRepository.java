package gladiator.philosopher.notification.repository;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.notification.entity.Notification;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

  List<Notification> findByAccount(@NonNull Account account);

}
