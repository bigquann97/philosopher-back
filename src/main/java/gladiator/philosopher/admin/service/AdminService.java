package gladiator.philosopher.admin.service;

import gladiator.philosopher.admin.dto.UserInfoResponseDto;
import gladiator.philosopher.report.dto.ReportResponseDto;
import java.util.List;

public interface AdminService {

  List<UserInfoResponseDto> getUsersInfoList();
  List<ReportResponseDto> getReports();


}
