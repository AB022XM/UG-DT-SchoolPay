package ug.co.absa.schoolpayme.service.mapper;

import org.mapstruct.*;
import ug.co.absa.schoolpayme.domain.PaymentChannelEnt;
import ug.co.absa.schoolpayme.service.dto.PaymentChannelEntDTO;

/**
 * Mapper for the entity {@link PaymentChannelEnt} and its DTO {@link PaymentChannelEntDTO}.
 */
@Mapper(componentModel = "spring")
public interface PaymentChannelEntMapper extends EntityMapper<PaymentChannelEntDTO, PaymentChannelEnt> {}
