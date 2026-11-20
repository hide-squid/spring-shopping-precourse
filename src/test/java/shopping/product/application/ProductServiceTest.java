package shopping.product.application;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import shopping.product.domain.Product;
import shopping.product.domain.ProductRepository;
import shopping.product.dto.ProductRequest;
import shopping.product.dto.ProductResponse;
import shopping.product.exception.ProductNotFoundException;

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

    @Test
    @DisplayName("상품 ID로 특정 상품을 조회한다")
    void findById() {
        Product product = productRepository.save(
                new Product("캐러멜 팝콘", 5_000L, "https://example.com/popcorn.jpg"));

        ProductResponse response = productService.findById(product.getId());

        assertThat(response.id()).isEqualTo(product.getId());
        assertThat(response.name()).isEqualTo("캐러멜 팝콘");
        assertThat(response.price()).isEqualTo(5_000L);
        assertThat(response.imageUrl()).isEqualTo("https://example.com/popcorn.jpg");
    }

    @Test
    @DisplayName("존재하지 않는 상품을 조회하면 예외가 발생한다")
    void findByUnknownId() {
        assertThatThrownBy(() -> productService.findById(999L))
                .isInstanceOf(ProductNotFoundException.class)
                .hasMessage("존재하지 않는 상품입니다.");
    }
}
