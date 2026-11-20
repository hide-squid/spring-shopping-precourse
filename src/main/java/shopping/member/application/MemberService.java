package shopping.member.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.member.domain.Member;
import shopping.member.domain.MemberRepository;
import shopping.member.dto.MemberRequest;

/**
 * 회원 관련 비즈니스 로직을 처리하는 서비스.
 */
@Service
public class MemberService {

    private final MemberRepository memberRepository;

    // 생성자 주입: 스프링이 MemberRepository 구현체를 넣어 준다.
    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원 가입: 요청받은 이메일과 비밀번호로 회원을 만들어 저장한다.
     * @return 저장된 회원의 ID
     */
    @Transactional
    public Long register(MemberRequest request) {
        Member member = new Member(request.email(), request.password());
        return memberRepository.save(member).getId();
    }
}
