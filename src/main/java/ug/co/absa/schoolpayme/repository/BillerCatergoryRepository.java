package ug.co.absa.schoolpayme.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ug.co.absa.schoolpayme.domain.BillerCatergory;

/**
 * Spring Data JPA repository for the BillerCatergory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BillerCatergoryRepository extends JpaRepository<BillerCatergory, Long> {}
