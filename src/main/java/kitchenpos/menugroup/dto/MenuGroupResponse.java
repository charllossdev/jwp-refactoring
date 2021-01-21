package kitchenpos.menugroup.dto;

import kitchenpos.menugroup.domain.MenuGroup;

public class MenuGroupResponse {
	private final Long id;
	private final String name;

	public MenuGroupResponse(final Long id, final String name) {
		this.id = id;
		this.name = name;
	}

	public static MenuGroupResponse of(final MenuGroup menuGroup) {
		return of(menuGroup.getId(), menuGroup.getName());
	}

	public static MenuGroupResponse of(final Long id, final String name) {
		return new MenuGroupResponse(id, name);
	}

	public Long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}
}
