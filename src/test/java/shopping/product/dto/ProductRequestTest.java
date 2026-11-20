package shopping.product.dto;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductRequestTest {

    @Test
    @DisplayName("이미지는 파일이 아닌 URL 문자열로 입력받는다")
    void inputImageUrl() {
        ProductRequest request =
                new ProductRequest("캐러멜 팝콘", 5_000L, "https://example.com/popcorn.jpg");

        assertThat(request.imageUrl()).isEqualTo("https://example.com/popcorn.jpg");
    }
}
