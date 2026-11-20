package shopping.member.domain;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 회원 저장소.
 * JpaRepository를 상속하면 save, findById 같은 기본 메서드를 Spring Data JPA가 만들어 준다.
 */
public interface MemberRepository extends JpaRepository<Member, Long> {
}
