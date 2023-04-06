package ug.co.absa.schoolpayme.service.mapper;

import org.mapstruct.*;
import ug.co.absa.schoolpayme.domain.ValidateCustomerById;
import ug.co.absa.schoolpayme.service.dto.ValidateCustomerByIdDTO;

/**
 * Mapper for the entity {@link ValidateCustomerById} and its DTO {@link ValidateCustomerByIdDTO}.
 */
@Mapper(componentModel = "spring")
public interface ValidateCustomerByIdMapper extends EntityMapper<ValidateCustomerByIdDTO, ValidateCustomerById> {}
