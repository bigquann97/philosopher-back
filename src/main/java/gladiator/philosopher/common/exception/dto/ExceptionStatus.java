package gladiator.philosopher.common.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ExceptionStatus {

  // 400 ->  BAD _ REQUEST : 잘못된 요청 (ex. 파라미터 값을 확인해주세요 )
  IMAGE_UPLOAD_FAILED(400, "이미지 정상 업로드 실패"),
  UNSUPPORTED_IMAGE_TYPE(400, "지원하지 않는 이미지 형식"),
  EXCEEDED_INPUT_DATAS(400, " 최대 5개의 유형만 설정 할 수 있습니다."),
  TO_MUCH_FILES(400, " 이미지는 최대 3개만 업로드 할 수 있습니다."),
  NOT_APPROACH_POST(404, "게시물에 접근할 수 없습니다."),

  // 404 ->  NOT _ FOUND : 잘못된 리소스 접근 (ex. 존재하지 않는 값)
  NOT_FOUND_POST(404, "게시물이 존재하지 않습니다."),
  NOT_FOUND_ACCOUNT(404, "사용자가 존재하지 않습니다."),
  NOT_FOUND_COMMENT(404, "댓글이 존재하지 않습니다."),
  NOT_FOUND_THREAD(404, "쓰레드가 존재하지 않습니다."),
  NOT_FOUND_CATEGORY(404, "카테고리가 존재하지 않습니다."),

  NOT_FOUND_RCMND_POST(404, "해당 게시글 추천 이력이 존재하지 않습니다."),
  NOT_FOUND_RCMND_THREAD(404, "해당 쓰레드 추천 이력이 존재하지 않습니다."),
  NOT_FOUND_RCMND_COMMENT(404, "해당 댓글 추천 이력이 존재하지 않습니다."),

  ARCHIVED_THREAD(404, "이미 토론이 종료된 쓰레드입니다."),
  NOT_FOUND_OPINION(404, "논제에 등록된 의견이 아닙니다."),
  INVALID_ACCESS(404, "잘못된 자원에 대한 접근입니다."),

  // 409 ->  CONFLICT : 중복 데이터 (ex. 이미 중복된 값)
  DUPLICATED_ACCOUNT(409, "이미 등록된 사용자 입니다."),
  DUPLICATED_NICKNAME(409, "이미 사용중인 닉네임 입니다."),

  DUPLICATED_REPORT_POST(409, "이미 해당 게시글 신고 이력이 존재합니다."),
  DUPLICATED_REPORT_THREAD(409, "이미 해당 쓰레드 신고 이력이 존재합니다."),
  DUPLICATED_REPORT_COMMENT(409, "이미 해당 댓글 신고 이력이 존재합니다."),

  DUPLICATED_RCMND_POST(409, "이미 해당 게시글 추천 이력이 존재합니다."),
  DUPLICATED_RCMND_THREAD(409, "이미 해당 쓰레드 추천 이력이 존재합니다."),
  DUPLICATED_RCMND_COMMENT(409, "이미 해당 댓글 추천 이력이 존재합니다."),

  // 500 -> INTERNAL SERVER ERROR : 서버에러
  FAIL_TO_POSTING(500, "게시글 작성을 잠시 후 시도해주세요"),
  FAIL_TO_SEND_EMAIL(500, " 이메일 전송 실패 "),
  FAIL_TO_RESIZE_FILE(500," 파일 리사이징 실패"),

  // 401 -> AUTH
  INVALID_REFRESH_TOKEN(401, "유효하지 않은 리프레시 토큰입니다."),
  INVALID_ACCESS_TOKEN(401, "유효하지 않은 액세스 토큰입니다."),
  INVALID_EMAIL_OR_PW(400, "잘못된 아이디 혹은 비밀번호 입력입니다."),
  NOT_VERIFIED_EMAIL(401, "인증되지 않은 이메일입니다."),
  INVALID_CODE(401, "인증번호가 일치하지 않습니다."),

  NOT_AUTHORIZED_COMMENT(401, "해당 댓글의 소유자가 아닙니다."),
  NOT_AUTHORIZED_POST(401, "해당 게시글의 소유자가 아닙니다.");
  
  private final int statusCode;
  private final String message;

}

