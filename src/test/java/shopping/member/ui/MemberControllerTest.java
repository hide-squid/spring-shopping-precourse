package shopping.member.ui;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.transaction.annotation.Transactional;
import shopping.member.domain.MemberRepository;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class MemberControllerTest {

    @Autowired
    private MockMvcTester mockMvc;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("POST /api/members/register 로 회원 가입하면 201을 응답하고 회원이 저장된다")
    void register() {
        String body = """
                {"email": "user@test.com", "password": "password"}
                """;

        assertThat(mockMvc.post().uri("/api/members/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body))
                .hasStatus(HttpStatus.CREATED);
        assertThat(memberRepository.count()).isEqualTo(1);
    }
}
