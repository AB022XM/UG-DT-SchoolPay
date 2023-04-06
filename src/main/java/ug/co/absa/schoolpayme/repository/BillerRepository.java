package ug.co.absa.schoolpayme.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ug.co.absa.schoolpayme.domain.Biller;

/**
 * Spring Data JPA repository for the Biller entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BillerRepository extends JpaRepository<Biller, Long> {}
