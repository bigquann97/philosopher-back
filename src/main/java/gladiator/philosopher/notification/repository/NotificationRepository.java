package gladiator.philosopher.notification.repository;

import gladiator.philosopher.account.entity.Account;
import gladiator.philosopher.notification.entity.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

  Page<Notification> findByAccountOrderByIdDesc(@NonNull Account account, Pageable pageable);

}
