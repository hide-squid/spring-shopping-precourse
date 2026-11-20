package shopping.member.dto;

/**
 * 회원 가입 요청 바디.
 * 예: {"email": "user@test.com", "password": "password"}
 * @param email
 * @param password
 */
public record MemberRequest(String email, String password) {
}
