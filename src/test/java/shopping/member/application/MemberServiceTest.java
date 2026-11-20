package shopping.member.application;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import shopping.member.domain.Member;
import shopping.member.domain.MemberRepository;
import shopping.member.dto.MemberRequest;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("이메일과 비밀번호로 회원 가입하면 회원이 저장된다")
    void register() {
        MemberRequest request = new MemberRequest("user@test.com", "password");

        Long memberId = memberService.register(request);

        Member member = memberRepository.findById(memberId).orElseThrow();
        assertThat(member.getEmail()).isEqualTo("user@test.com");
        assertThat(member.getPassword()).isEqualTo("password");
    }
}
