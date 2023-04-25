package com.example.truck.business.service;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import javax.persistence.metamodel.SingularAttribute;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import org.apache.commons.lang3.StringUtils;
import io.tesler.api.data.dictionary.IDictionaryType;
import io.tesler.api.data.dictionary.LOV;
import io.tesler.api.data.dto.DataResponseDTO;
import io.tesler.constgen.DtoField;
import io.tesler.core.crudma.bc.BusinessComponent;
import io.tesler.core.crudma.impl.VersionAwareResponseService;
import io.tesler.core.service.rowmeta.FieldMetaBuilder;
import io.tesler.core.util.session.SessionService;
import io.tesler.model.core.entity.BaseEntity;
import io.tesler.model.core.entity.User;

import static io.tesler.api.data.dao.SpecificationUtils.falseSpecification;
import static io.tesler.model.core.entity.BaseEntity_.id;

public abstract class AbstractTeslerService<T extends DataResponseDTO, E extends BaseEntity>
		extends VersionAwareResponseService<T, E> {

	@Autowired
	private SessionService sessionService;

	protected AbstractTeslerService(final Class<T> typeOfDTO, Class<E> typeOfEntity,
																	final SingularAttribute<? super E, ? extends BaseEntity> parentSpec,
																	final Class<? extends FieldMetaBuilder<T>> metaBuilder) {
		super(typeOfDTO, typeOfEntity, parentSpec, metaBuilder);
	}

	protected final void setIfChanged(final T dto, final DtoField<T, String> field, final IDictionaryType lovType,
																		final Consumer<LOV> entitySetter) {
		setMappedIfChanged(dto, field, entitySetter, val -> {
			if (StringUtils.isNotBlank(val)) {
				return lovType.lookupName(val);
			} else {
				return null;
			}
		});
	}

	protected final <Entity> Function<Long, Entity> findEntity(final JpaRepository<Entity, Long> repository) {
		return value -> Optional.ofNullable(value).flatMap(repository::findById).orElse(null);
	}

	protected final Specification<E> defaultParentSpecification(final BusinessComponent bc) {
		return bc.getParentIdAsLong() != null ? (root, cq, cb) -> cb.equal(root.get(id), bc.getParentIdAsLong()) : falseSpecification();
	}

	protected User getCurrentUser() {
		return sessionService.getSessionUser();
	}

	protected LOV getCurrentUserRole() {
		return sessionService.getSessionUserRole();
	}

}
