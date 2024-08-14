package com.soitio.finances.objectvalues.application.port;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.soitio.finances.objectvalues.domain.ObjectType;
import com.soitio.finances.objectvalues.domain.ObjectValue;
import com.soitio.finances.objectvalues.domain.proj.ObjectIdProj;

@Repository
public interface ObjectValueRepository extends JpaRepository<ObjectValue, String> {

    List<ObjectIdProj> findAllProjectedByObjectType(ObjectType objectType);

    Page<ObjectValue> findAllPageableByObjectType(ObjectType objectType, Pageable pageable);

    List<ObjectValue> findAllByObjectType(ObjectType objectType);

}