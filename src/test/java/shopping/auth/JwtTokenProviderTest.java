package shopping.auth;

import static org.assertj.core.api.Assertions.assertThat;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import javax.crypto.SecretKey;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class JwtTokenProviderTest {

    private static final String SECRET_KEY = "test-secret-key-test-secret-key-test-secret-key";

    private final JwtTokenProvider jwtTokenProvider = new JwtTokenProvider(SECRET_KEY, 3_600_000L);

    @Test
    @DisplayName("회원 ID를 subject로 담은 토큰을 만든다")
    void createToken() {
        String token = jwtTokenProvider.createToken(1L);

        // 같은 비밀 키로 토큰을 직접 풀어서 subject에 회원 ID가 들어 있는지 확인한다.
        SecretKey key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
        String subject = Jwts.parser().verifyWith(key).build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
        assertThat(subject).isEqualTo("1");
    }
}
