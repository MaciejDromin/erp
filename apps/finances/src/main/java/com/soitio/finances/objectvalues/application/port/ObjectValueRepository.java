package com.soitio.finances.objectvalues.application.port;

import com.soitio.commons.models.dto.finances.ObjectType;
import com.soitio.finances.objectvalues.domain.ObjectValue;
import com.soitio.finances.objectvalues.domain.proj.ObjectIdProj;
import java.util.List;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ObjectValueRepository extends JpaRepository<ObjectValue, String> {

    List<ObjectIdProj> findAllProjectedByObjectType(ObjectType objectType);

    Page<ObjectValue> findAllPageableByObjectType(ObjectType objectType, Pageable pageable);

    Page<ObjectValue> findAllPageableByObjectTypeAndObjectIdIn(ObjectType objectType,
                                                               Set<String> objectIds,
                                                               Pageable pageable);

    List<ObjectValue> findAllByObjectType(ObjectType objectType);

    ObjectValue findFirstByObjectIdInOrderByAmountDesc(Set<String> value);

    List<ObjectValue> findAllByObjectIdIn(Set<String> ids);

}
