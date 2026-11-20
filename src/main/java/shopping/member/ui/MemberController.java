package shopping.member.ui;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shopping.member.application.MemberService;
import shopping.member.dto.MemberRequest;
import shopping.member.dto.TokenResponse;

/**
 * 회원 API.
 */
@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    /**
     * POST /api/members/register : 회원 가입
     * 가입에 성공하면 201 Created와 함께 인증 토큰을 응답 바디에 담아 준다.
     */
    @PostMapping("/register")
    public ResponseEntity<TokenResponse> register(@RequestBody MemberRequest request) {
        TokenResponse response = memberService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
