package shopping.member.ui;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import shopping.member.application.MemberService;
import shopping.member.dto.MemberRequest;

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
     */
    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody MemberRequest request) {
        memberService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
