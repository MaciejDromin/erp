package pl.mlisowski.finances.objectvalues.application.port;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.mlisowski.finances.objectvalues.domain.ObjectValue;
import pl.mlisowski.finances.objectvalues.domain.proj.ObjectIdProj;

import java.util.List;

@Repository
public interface ObjectValueRepository extends JpaRepository<ObjectValue, String> {

    List<ObjectIdProj> findAllProjectedBy();

}
