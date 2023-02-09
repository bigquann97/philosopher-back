package gladiator.philosopher.report.repository;

import gladiator.philosopher.report.entity.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {

}
