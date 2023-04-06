package ug.co.absa.schoolpayme.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ug.co.absa.schoolpayme.domain.Paybill;
import ug.co.absa.schoolpayme.repository.PaybillRepository;
import ug.co.absa.schoolpayme.service.dto.PaybillDTO;
import ug.co.absa.schoolpayme.service.mapper.PaybillMapper;

/**
 * Service Implementation for managing {@link Paybill}.
 */
@Service
@Transactional
public class PaybillService {

    private final Logger log = LoggerFactory.getLogger(PaybillService.class);

    private final PaybillRepository paybillRepository;

    private final PaybillMapper paybillMapper;

    public PaybillService(PaybillRepository paybillRepository, PaybillMapper paybillMapper) {
        this.paybillRepository = paybillRepository;
        this.paybillMapper = paybillMapper;
    }

    /**
     * Save a paybill.
     *
     * @param paybillDTO the entity to save.
     * @return the persisted entity.
     */
    public PaybillDTO save(PaybillDTO paybillDTO) {
        log.debug("Request to save Paybill : {}", paybillDTO);
        Paybill paybill = paybillMapper.toEntity(paybillDTO);
        paybill = paybillRepository.save(paybill);
        return paybillMapper.toDto(paybill);
    }

    /**
     * Update a paybill.
     *
     * @param paybillDTO the entity to save.
     * @return the persisted entity.
     */
    public PaybillDTO update(PaybillDTO paybillDTO) {
        log.debug("Request to update Paybill : {}", paybillDTO);
        Paybill paybill = paybillMapper.toEntity(paybillDTO);
        paybill = paybillRepository.save(paybill);
        return paybillMapper.toDto(paybill);
    }

    /**
     * Partially update a paybill.
     *
     * @param paybillDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PaybillDTO> partialUpdate(PaybillDTO paybillDTO) {
        log.debug("Request to partially update Paybill : {}", paybillDTO);

        return paybillRepository
            .findById(paybillDTO.getId())
            .map(existingPaybill -> {
                paybillMapper.partialUpdate(existingPaybill, paybillDTO);

                return existingPaybill;
            })
            .map(paybillRepository::save)
            .map(paybillMapper::toDto);
    }

    /**
     * Get all the paybills.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PaybillDTO> findAll() {
        log.debug("Request to get all Paybills");
        return paybillRepository.findAll().stream().map(paybillMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one paybill by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PaybillDTO> findOne(Long id) {
        log.debug("Request to get Paybill : {}", id);
        return paybillRepository.findById(id).map(paybillMapper::toDto);
    }

    /**
     * Delete the paybill by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Paybill : {}", id);
        paybillRepository.deleteById(id);
    }
}
