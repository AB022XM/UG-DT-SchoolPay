package ug.co.absa.schoolpayme.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ug.co.absa.schoolpayme.domain.PaymentEnt;

/**
 * Spring Data JPA repository for the PaymentEnt entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaymentEntRepository extends JpaRepository<PaymentEnt, Long> {}
