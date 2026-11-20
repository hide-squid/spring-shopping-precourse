package shopping.member.domain;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 회원 저장소.
 * JpaRepository를 상속하면 save, findById 같은 기본 메서드를 Spring Data JPA가 만들어 준다.
 */
public interface MemberRepository extends JpaRepository<Member, Long> {

    // 해당 이메일로 가입한 회원이 있는지 확인한다.
    // 메서드 이름(existsBy + Email)을 보고 Spring Data JPA가 쿼리를 자동으로 만들어 준다.
    boolean existsByEmail(String email);
}
