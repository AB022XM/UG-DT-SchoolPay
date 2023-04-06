package ug.co.absa.schoolpayme.service.mapper;

import org.mapstruct.*;
import ug.co.absa.schoolpayme.domain.BillerCatergory;
import ug.co.absa.schoolpayme.service.dto.BillerCatergoryDTO;

/**
 * Mapper for the entity {@link BillerCatergory} and its DTO {@link BillerCatergoryDTO}.
 */
@Mapper(componentModel = "spring")
public interface BillerCatergoryMapper extends EntityMapper<BillerCatergoryDTO, BillerCatergory> {}
