package com.soitio.finances.common.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrgRepository<T, ID> extends JpaRepository<T, ID> {

    Page<T> findAllByOrgId(Pageable pageable, String orgId);

}
