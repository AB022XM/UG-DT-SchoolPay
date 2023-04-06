package ug.co.absa.schoolpayme.service.mapper;

import org.mapstruct.*;
import ug.co.absa.schoolpayme.domain.AssociatedFees;
import ug.co.absa.schoolpayme.domain.PaymentEnt;
import ug.co.absa.schoolpayme.service.dto.AssociatedFeesDTO;
import ug.co.absa.schoolpayme.service.dto.PaymentEntDTO;

/**
 * Mapper for the entity {@link AssociatedFees} and its DTO {@link AssociatedFeesDTO}.
 */
@Mapper(componentModel = "spring")
public interface AssociatedFeesMapper extends EntityMapper<AssociatedFeesDTO, AssociatedFees> {
    @Mapping(target = "paymentEnt", source = "paymentEnt", qualifiedByName = "paymentEntId")
    AssociatedFeesDTO toDto(AssociatedFees s);

    @Named("paymentEntId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    PaymentEntDTO toDtoPaymentEntId(PaymentEnt paymentEnt);
}
