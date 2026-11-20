package shopping.member.application;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import shopping.auth.JwtTokenProvider;
import shopping.member.domain.Member;
import shopping.member.domain.MemberRepository;
import shopping.member.dto.MemberRequest;
import shopping.member.dto.TokenResponse;
import shopping.member.exception.DuplicateEmailException;

/**
 * 회원 관련 비즈니스 로직을 처리하는 서비스.
 */
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;

    // 생성자 주입: 스프링이 MemberRepository 구현체와 JwtTokenProvider를 넣어 준다.
    public MemberService(MemberRepository memberRepository, JwtTokenProvider jwtTokenProvider) {
        this.memberRepository = memberRepository;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    /**
     * 회원 가입: 이메일 중복을 확인한 뒤, 요청받은 이메일과 비밀번호로 회원을 만들어 저장하고,
     * 저장된 회원의 ID로 인증 토큰을 발급한다.
     * @return 발급한 인증 토큰
     */
    @Transactional
    public TokenResponse register(MemberRequest request) {
        validateDuplicateEmail(request.email());
        Member member = new Member(request.email(), request.password());
        Member savedMember = memberRepository.save(member);
        return new TokenResponse(jwtTokenProvider.createToken(savedMember.getId()));
    }

    /**
     * 로그인: 요청받은 이메일로 가입한 회원을 찾는다.
     * (없는 이메일/틀린 비밀번호 처리와 토큰 발급은 README의 다음 항목에서 추가한다.)
     * @return 로그인한 회원의 ID
     */
    @Transactional(readOnly = true)
    public Long login(MemberRequest request) {
        Member member = memberRepository.findByEmail(request.email()).orElseThrow();
        return member.getId();
    }

    // 이미 같은 이메일로 가입한 회원이 있으면 예외를 던진다.
    private void validateDuplicateEmail(String email) {
        if (memberRepository.existsByEmail(email)) {
            throw new DuplicateEmailException();
        }
    }
}
