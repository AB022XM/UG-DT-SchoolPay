package ug.co.absa.schoolpayme.service.mapper;

import org.mapstruct.*;
import ug.co.absa.schoolpayme.domain.Biller;
import ug.co.absa.schoolpayme.domain.PaymentEnt;
import ug.co.absa.schoolpayme.service.dto.BillerDTO;
import ug.co.absa.schoolpayme.service.dto.PaymentEntDTO;

/**
 * Mapper for the entity {@link PaymentEnt} and its DTO {@link PaymentEntDTO}.
 */
@Mapper(componentModel = "spring")
public interface PaymentEntMapper extends EntityMapper<PaymentEntDTO, PaymentEnt> {
    @Mapping(target = "biller", source = "biller", qualifiedByName = "billerId")
    PaymentEntDTO toDto(PaymentEnt s);

    @Named("billerId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    BillerDTO toDtoBillerId(Biller biller);
}
