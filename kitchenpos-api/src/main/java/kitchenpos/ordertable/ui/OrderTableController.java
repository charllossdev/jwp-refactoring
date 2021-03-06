package kitchenpos.ordertable.ui;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import kitchenpos.ordertable.application.OrderTableService;
import kitchenpos.ordertable.dto.OrderTableRequest;
import kitchenpos.ordertable.dto.OrderTableResponse;

@RestController
public class OrderTableController {
	private final OrderTableService orderTableService;

	public OrderTableController(final OrderTableService orderTableService) {
		this.orderTableService = orderTableService;
	}

	@PostMapping("/api/tables")
	public ResponseEntity<OrderTableResponse> create(@RequestBody final OrderTableRequest request) {
		final OrderTableResponse created = orderTableService.create(request);
		final URI uri = URI.create("/api/tables/" + created.getId());
		return ResponseEntity.created(uri).body(created);
	}

	@GetMapping("/api/tables")
	public ResponseEntity<List<OrderTableResponse>> list() {
		return ResponseEntity.ok().body(orderTableService.list());
	}

	@PutMapping("/api/tables/{orderTableId}/empty")
	public ResponseEntity<OrderTableResponse> changeEmpty(
		@PathVariable final Long orderTableId,
		@RequestBody final OrderTableRequest request
	) {
		return ResponseEntity.ok().body(orderTableService.changeEmpty(orderTableId, request));
	}

	@PutMapping("/api/tables/{orderTableId}/number-of-guests")
	public ResponseEntity<OrderTableResponse> changeNumberOfGuests(
		@PathVariable final Long orderTableId,
		@RequestBody final OrderTableRequest request
	) {
		return ResponseEntity.ok().body(orderTableService.changeNumberOfGuests(orderTableId, request));
	}
}
