package shopping.product.dto;

import jakarta.validation.constraints.Size;

public record ProductRequest(
        @Size(max = 15, message = "상품 이름은 15자를 초과할 수 없습니다.")
        String name,
        Long price,
        String imageUrl
) {
}
