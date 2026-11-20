package shopping.product.dto;

public record ProductRequest(
        String name,
        Long price,
        String imageUrl
) {
}
