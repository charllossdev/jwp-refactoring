package kitchenpos.menugroup.application;

import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kitchenpos.menugroup.domain.MenuGroup;
import kitchenpos.menugroup.domain.MenuGroupRepository;
import kitchenpos.menugroup.dto.MenuGroupRequest;
import kitchenpos.menugroup.dto.MenuGroupResponse;

@Service
@Transactional(readOnly = true)
public class MenuGroupService {
	private final MenuGroupRepository menuGroupRepository;

	public MenuGroupService(final MenuGroupRepository menuGroupRepository) {
		this.menuGroupRepository = menuGroupRepository;
	}

	@Transactional
	public MenuGroupResponse create(final MenuGroupRequest request) {
		MenuGroup persistMenuGroup = menuGroupRepository.save(request.toEntity());
		return MenuGroupResponse.of(persistMenuGroup);
	}

	public List<MenuGroupResponse> list() {
		return menuGroupRepository.findAll().stream()
			.map(MenuGroupResponse::of)
			.collect(Collectors.toList());
	}

	public MenuGroup findById(Long menuGroupId) {
		return menuGroupRepository.findById(menuGroupId).orElseThrow(EntityNotFoundException::new);
	}
}
