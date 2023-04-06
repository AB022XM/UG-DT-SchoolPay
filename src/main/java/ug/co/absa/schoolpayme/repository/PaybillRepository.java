package ug.co.absa.schoolpayme.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ug.co.absa.schoolpayme.domain.Paybill;

/**
 * Spring Data JPA repository for the Paybill entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PaybillRepository extends JpaRepository<Paybill, Long> {}
