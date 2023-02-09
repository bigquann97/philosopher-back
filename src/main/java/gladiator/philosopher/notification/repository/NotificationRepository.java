package gladiator.philosopher.notification.repository;

import gladiator.philosopher.notification.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

}
