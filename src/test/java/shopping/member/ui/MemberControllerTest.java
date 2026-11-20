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
import org.springframework.test.web.servlet.assertj.MvcTestResult;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
    @DisplayName("POST /api/members/register 로 회원 가입하면 201과 토큰을 응답하고 회원이 저장된다")
    void register() {
        String body = """
                {"email": "user@test.com", "password": "password"}
                """;

        MvcTestResult result = mockMvc.post().uri("/api/members/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
                .exchange();

        assertThat(result).hasStatus(HttpStatus.CREATED);
        assertThat(result).bodyJson().extractingPath("$.token").asString().isNotBlank();
        assertThat(memberRepository.count()).isEqualTo(1);
    }

    @Test
    @DisplayName("이메일 형식이 올바르지 않으면 400을 응답하고 회원이 저장되지 않는다")
    void registerWithInvalidEmail() {
        String body = """
                {"email": "not-email", "password": "password"}
                """;

        MvcTestResult result = mockMvc.post().uri("/api/members/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
                .exchange();

        assertThat(result).hasStatus(HttpStatus.BAD_REQUEST);
        assertThat(result).failure().isInstanceOf(MethodArgumentNotValidException.class);
        assertThat(memberRepository.count()).isZero();
    }
}
