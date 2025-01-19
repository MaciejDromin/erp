package com.soitio.finances.common.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@SuppressWarnings({":checkstyle", "checkstyle:InterfaceTypeParameterName"})
@NoRepositoryBean
public interface OrgRepository<T, ID> extends JpaRepository<T, ID> {

    Page<T> findAllByOrgId(Pageable pageable, String orgId);

    T getReferenceByIdAndOrgId(ID id, String orgId);

}
