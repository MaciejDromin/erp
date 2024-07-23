package pl.mlisowski.inventory.property.information;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.bson.codecs.pojo.annotations.BsonDiscriminator;
import pl.mlisowski.inventory.property.area.Area;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@BsonDiscriminator
@ToString
public class LandInformation extends PropertyInformation {

    private LandClassification landClassification;
    private Area landArea;

}
