package gladiator.philosopher.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExceptionStatus {

  // 400 ->  BAD _ REQUEST : 잘못된 요청 (ex. 파라미터 값을 확인해주세요 )
  NOT_MATCH_INFORMATION(400, " 회원정보가 일치하지 않습니다. 다시 확인해주세요."),
  IMAGE_UPLOAD_FAILED(400, "이미지 정상 업로드 실패"),
  UNSUPPORTED_IMAGE_TYPE(400, "지원하지 않는 이미지 형식"),
  UNMATCHED_USER(400, "게시물 작성자가 아닙니다."),
  // 404 ->  NOT _ FOUND : 잘못된 리소스 접근 (ex. 존재하지 않는 값)
  POST_IS_NOT_EXIST(404, "존재하지 않는 게시물입니다."),
  // 409 ->  CONFLICT : 중복 데이터 (ex. 이미 중복된 값)
  ACCOUNT_IS_EXIST(409, " 이미 등록된 사용자 입니다. "),
  ACCOUNT_NICKNAME_IS_EXIST(409, " 이미 사용중인 닉네임 입니다. ");
  // 500 -> INTERNAL SERVER ERROR : 서버에러
  private final int statusCode;
  private final String message;

}

