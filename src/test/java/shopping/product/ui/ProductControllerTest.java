package shopping.product.ui;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.servlet.ServletException;
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
import shopping.product.domain.Product;
import shopping.product.domain.ProductRepository;
import shopping.product.exception.ProductNotFoundException;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class ProductControllerTest {

    @Autowired
    private MockMvcTester mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("POST /api/products로 상품을 생성하면 201과 생성된 상품을 반환한다")
    void create() {
        String body = """
                {"name": "캐러멜 팝콘", "price": 5000,
                 "imageUrl": "https://example.com/popcorn.jpg"}
                """;

        MvcTestResult result = mockMvc.post().uri("/api/products")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
                .exchange();

        assertThat(result).hasStatus(HttpStatus.CREATED);
        assertThat(result).bodyJson().extractingPath("$.name").isEqualTo("캐러멜 팝콘");
        assertThat(result).bodyJson().extractingPath("$.price").isEqualTo(5_000);
        assertThat(result).bodyJson().extractingPath("$.imageUrl")
                .isEqualTo("https://example.com/popcorn.jpg");
        assertThat(productRepository.count()).isEqualTo(1);
    }

    @Test
    @DisplayName("GET /api/products/{productId}로 특정 상품을 조회한다")
    void findById() {
        Product product = productRepository.save(
                new Product("캐러멜 팝콘", 5_000L, "https://example.com/popcorn.jpg"));

        MvcTestResult result = mockMvc.get().uri("/api/products/{productId}", product.getId())
                .exchange();

        assertThat(result).hasStatusOk();
        assertThat(result).bodyJson().extractingPath("$.id").isEqualTo(product.getId().intValue());
        assertThat(result).bodyJson().extractingPath("$.name").isEqualTo("캐러멜 팝콘");
        assertThat(result).bodyJson().extractingPath("$.price").isEqualTo(5_000);
        assertThat(result).bodyJson().extractingPath("$.imageUrl")
                .isEqualTo("https://example.com/popcorn.jpg");
    }

    @Test
    @DisplayName("존재하지 않는 상품을 GET /api/products/{productId}로 조회하면 예외가 발생한다")
    void findByUnknownId() {
        MvcTestResult result = mockMvc.get().uri("/api/products/{productId}", 999L)
                .exchange();

        assertThat(result).failure()
                .isInstanceOf(ServletException.class)
                .hasCauseInstanceOf(ProductNotFoundException.class);
    }
}
