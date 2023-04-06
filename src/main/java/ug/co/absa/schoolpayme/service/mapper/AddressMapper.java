package ug.co.absa.schoolpayme.service.mapper;

import org.mapstruct.*;
import ug.co.absa.schoolpayme.domain.Address;
import ug.co.absa.schoolpayme.service.dto.AddressDTO;

/**
 * Mapper for the entity {@link Address} and its DTO {@link AddressDTO}.
 */
@Mapper(componentModel = "spring")
public interface AddressMapper extends EntityMapper<AddressDTO, Address> {}
