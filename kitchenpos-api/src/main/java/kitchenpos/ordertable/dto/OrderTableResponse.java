package kitchenpos.ordertable.dto;

import kitchenpos.ordertable.domain.OrderTable;

public class OrderTableResponse {
	private final Long id;
	private final int numberOfGuests;
	private final boolean empty;

	private OrderTableResponse(final Long id, final int numberOfGuests, final boolean empty) {
		this.id = id;
		this.numberOfGuests = numberOfGuests;
		this.empty = empty;
	}

	public static OrderTableResponse of(final Long id, final int numberOfGuests, final boolean empty) {
		return new OrderTableResponse(id, numberOfGuests, empty);
	}

	public static OrderTableResponse of(final OrderTable orderTable) {
		return of(orderTable.getId(), orderTable.numberOfGuests(), orderTable.isEmpty());
	}

	public Long getId() {
		return id;
	}

	public int getNumberOfGuests() {
		return numberOfGuests;
	}

	public boolean isEmpty() {
		return empty;
	}
}
