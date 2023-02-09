package gladiator.philosopher.report.controller;

import gladiator.philosopher.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReportController {

  private final ReportService reportService;

}
