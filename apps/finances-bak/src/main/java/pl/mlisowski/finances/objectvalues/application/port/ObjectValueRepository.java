package pl.mlisowski.finances.objectvalues.application.port;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import pl.mlisowski.finances.objectvalues.domain.ObjectValue;
import pl.mlisowski.finances.objectvalues.domain.proj.TotalValueProjection;

import java.util.List;

@Repository
public interface ObjectValueRepository extends JpaRepository<ObjectValue, String> {

    @Query("SELECT SUM(o.amount) AS totalAmount, o.currency AS currency, COUNT(o.amount) AS totalCount FROM ObjectValue AS o GROUP BY o.currency ORDER BY o.currency DESC")
    List<TotalValueProjection> countTotalValue();

}
