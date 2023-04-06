package ug.co.absa.schoolpayme.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ug.co.absa.schoolpayme.domain.PaymentChannelEnt;

/**
 * Spring Data JPA repository for the PaymentChannelEnt entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentChannelEntRepository extends JpaRepository<PaymentChannelEnt, Long> {}
