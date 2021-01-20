package kitchenpos.ui;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kitchenpos.application.MenuGroupService;
import kitchenpos.domain.MenuGroup;

@RestController
@RequestMapping("/api/menu-groups")
public class MenuGroupRestController {
	private final MenuGroupService menuGroupService;

	public MenuGroupRestController(final MenuGroupService menuGroupService) {
		this.menuGroupService = menuGroupService;
	}

	@PostMapping
	public ResponseEntity<MenuGroup> create(@RequestBody final MenuGroup menuGroup) {
		final MenuGroup created = menuGroupService.create(menuGroup);
		final URI uri = URI.create("/api/menu-groups/" + created.getId());
		return ResponseEntity.created(uri)
			.body(created);
	}

	@GetMapping
	public ResponseEntity<List<MenuGroup>> list() {
		return ResponseEntity.ok()
			.body(menuGroupService.list());
	}
}
