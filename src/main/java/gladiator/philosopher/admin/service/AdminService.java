package gladiator.philosopher.admin.service;

import gladiator.philosopher.admin.dto.UserInfoResponseDto;
import java.util.List;

public interface AdminService {

  List<UserInfoResponseDto> getUsersInfoList();

}
