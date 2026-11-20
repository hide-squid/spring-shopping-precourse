package shopping.product.dto;

import static org.assertj.core.api.Assertions.assertThat;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductRequestTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    @DisplayName("이미지는 파일이 아닌 URL 문자열로 입력받는다")
    void inputImageUrl() {
        ProductRequest request =
                new ProductRequest("캐러멜 팝콘", 5_000L, "https://example.com/popcorn.jpg");

        assertThat(request.imageUrl()).isEqualTo("https://example.com/popcorn.jpg");
    }

    @Test
    @DisplayName("상품 이름은 공백을 포함해 15자까지 입력할 수 있다")
    void nameWithinMaxLength() {
        ProductRequest request =
                new ProductRequest("1234567890 1234", 5_000L, "https://example.com/product.jpg");

        Set<ConstraintViolation<ProductRequest>> violations = validator.validate(request);

        assertThat(violations).isEmpty();
    }
}
