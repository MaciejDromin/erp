package com.soitio.inventory.contractor.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.bson.types.ObjectId;
import com.soitio.inventory.contractor.contact.domain.dto.ContactInformationDto;

@Builder
@AllArgsConstructor
@Getter
@ToString
public class ContractorDto {

    private ObjectId id;
    private String name;
    private ContactInformationDto contactInformation;

}
