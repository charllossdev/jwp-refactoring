package kitchenpos.menugroup.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import kitchenpos.common.BaseEntity;

@Entity
public class MenuGroup extends BaseEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;

	protected MenuGroup() {
	}

	protected MenuGroup(Long id, String name) {
		this.id = id;
		this.name = name;
	}

	public static MenuGroup of(String name) {
		return new MenuGroup(null, name);
	}

	public Long getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}
}
