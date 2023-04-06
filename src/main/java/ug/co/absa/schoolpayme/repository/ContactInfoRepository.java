package ug.co.absa.schoolpayme.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import ug.co.absa.schoolpayme.domain.ContactInfo;

/**
 * Spring Data JPA repository for the ContactInfo entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContactInfoRepository extends JpaRepository<ContactInfo, Long> {}
