package shopping.product.application;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import shopping.product.domain.Product;
import shopping.product.domain.ProductRepository;
import shopping.product.dto.ProductRequest;
import shopping.product.dto.ProductResponse;

@SpringBootTest
@Transactional
class ProductServiceTest {

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("상품을 생성하면 저장되고 생성된 상품을 반환한다")
    void create() {
        ProductRequest request =
                new ProductRequest("캐러멜 팝콘", 5_000L, "https://example.com/popcorn.jpg");

        ProductResponse response = productService.create(request);

        Product product = productRepository.findById(response.id()).orElseThrow();
        assertThat(product.getName()).isEqualTo("캐러멜 팝콘");
        assertThat(product.getPrice()).isEqualTo(5_000L);
        assertThat(product.getImageUrl()).isEqualTo("https://example.com/popcorn.jpg");
    }
}
