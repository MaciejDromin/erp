package com.soitio.finances.objectvalues.application.port;

import com.soitio.commons.models.dto.finances.ObjectType;
import com.soitio.finances.common.persistence.OrgRepository;
import com.soitio.finances.objectvalues.domain.ObjectValue;
import com.soitio.finances.objectvalues.domain.proj.ObjectIdProj;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface ObjectValueRepository extends OrgRepository<ObjectValue, String> {

    List<ObjectIdProj> findAllProjectedByObjectTypeAndOrgId(ObjectType objectType, String orgId);

    Page<ObjectValue> findAllPageableByObjectTypeAndOrgId(ObjectType objectType, Pageable pageable, String orgId);

    Page<ObjectValue> findAllPageableByObjectTypeAndObjectIdInAndOrgId(ObjectType objectType,
                                                                       Set<String> objectIds,
                                                                       Pageable pageable, String orgId);

    List<ObjectValue> findAllByObjectTypeAndOrgId(ObjectType objectType, String orgId);

    ObjectValue findFirstByObjectIdInAndOrgIdOrderByAmountDesc(Set<String> value, String orgId);

    List<ObjectValue> findAllByObjectIdIn(Set<String> ids);

}
