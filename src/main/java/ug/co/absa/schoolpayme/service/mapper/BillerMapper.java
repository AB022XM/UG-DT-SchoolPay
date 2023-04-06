package ug.co.absa.schoolpayme.service.mapper;

import org.mapstruct.*;
import ug.co.absa.schoolpayme.domain.Biller;
import ug.co.absa.schoolpayme.service.dto.BillerDTO;

/**
 * Mapper for the entity {@link Biller} and its DTO {@link BillerDTO}.
 */
@Mapper(componentModel = "spring")
public interface BillerMapper extends EntityMapper<BillerDTO, Biller> {}
