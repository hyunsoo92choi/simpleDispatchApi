package com.hschoi.common.code;

import com.hschoi.common.code.mapper.EnumType;

/**
 * <pre>
 * com.hschoi.common.code_HttpStatusType.java
 * </pre>
 * @date : 2019. 7. 12.
 * @author : hychoi
 */
public enum HttpStatusType implements EnumType {
	/* Admin Code */
	USER_NOT_FOUND("ADMIN_001", "해당 회원을 찾을 수 없습니다.", 404)
  , INVALID_ACCOUNT_FORM("ADMIN_002", "올바른 계정 형식이 아닙니다.", 400)
  , ACCOUNT_DUPLICATION("ADMIN_003", "계정이 중복되었습니다.", 400)  
  , SIGNUP_REQUIRED_PASSWORD("ADMIN_004","등록할 패스워드를 입력해 주세요.", 400)
  , UNAUTHORIZED_REQUEST("ADMIN_005","허가되지않은 요청입니다.", 403)
  , SIGNUP_REQUIRED_USERTYPE("ADMIN_006","유저의 타입(승객/기사)을 입력해 주세요.", 400)
  , INVALID_USERTYPE("ADMIN_007","올바른 유저타입이 아닙니다. (DRIVER/PASSENGER) 중 입력해 주세요.", 401)
  , INVALID_CALLSTATUSTYPE("ADMIN_008", "올바른 호출상태 타입이 아닙니다.", 403)
  , REQUEST_ONLY_DRIVER("ADMIN_009", "기사만 요청할 수 있는 기능 입니다.", 403)
  , REQUEST_ONLY_PASSENGER("ADMIN_010", "승객만 요청할 수 있는 기능 입니다.", 403)
  , ALREADY_ASSIGNED("ADMIN_011","이미 할당된 배차입니다.", 400)
  , INVALID_ADDRESS("ADMIN_012","잘못된 주소 입니다.", 403)

  /* Access Code */
  ,	INVALID_ACCESS("ACCESS_001", "접근권한이 없습니다.", 403)
  , PASSWORD_CONFIRM_NOT_MATCHING("ACCESS_002", "비밀번호가 일치하지 않습니다.", 401)
  , REQUEST_TIMEOUT("ACCESS_003", "요청시간을 초과하였습니다.", 408)
  , SERVICE_UNAVAILABLE("ACCES_004", "서버에 장애가 있습니다.", 503)
  , PASSWORD_FAILED_EXCEEDED("ACCES_005", "비밀번호 실패 횟수가 초과했습니다.", 400)
  , OK("ACCESS_006", "요청이 정상적으로 완료 되었습니다.", 200)
  , FAIL("ACCESS_007","요청에 실패하였습니다.", 400) 
  ;
	private String message;
	private final int status;

	HttpStatusType(String code, String message, int status) {
		this.message = message;
		this.status = status;
	}

	@Override
	public String getCode() {
		// TODO Auto-generated method stub
		return name();
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return message;
	}

	public int getStatus() {
		return status;
	}
}
