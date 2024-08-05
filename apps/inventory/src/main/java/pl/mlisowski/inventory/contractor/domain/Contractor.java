package pl.mlisowski.inventory.contractor.domain;


import io.quarkus.mongodb.panache.common.MongoEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bson.types.ObjectId;
import pl.mlisowski.inventory.contractor.contact.domain.ContactInformation;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@MongoEntity(collection = "Contractor")
@ToString
@EqualsAndHashCode(of = "id")
public class Contractor {

    private ObjectId id;
    private String name;
    private ContactInformation contactInformation;

}
