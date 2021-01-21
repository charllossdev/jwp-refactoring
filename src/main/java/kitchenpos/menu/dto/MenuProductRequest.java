package kitchenpos.menu.dto;

public class MenuProductRequest {
	private final Long productId;
	private final int quantity;

	private MenuProductRequest(Long productId, int quantity) {
		this.productId = productId;
		this.quantity = quantity;
	}

	public Long getProductId() {
		return productId;
	}

	public int getQuantity() {
		return quantity;
	}
}