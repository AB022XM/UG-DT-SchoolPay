package ug.co.absa.schoolpayme.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ug.co.absa.schoolpayme.domain.PaymentEnt;
import ug.co.absa.schoolpayme.repository.PaymentEntRepository;
import ug.co.absa.schoolpayme.service.dto.PaymentEntDTO;
import ug.co.absa.schoolpayme.service.mapper.PaymentEntMapper;

/**
 * Service Implementation for managing {@link PaymentEnt}.
 */
@Service
@Transactional
public class PaymentEntService {

    private final Logger log = LoggerFactory.getLogger(PaymentEntService.class);

    private final PaymentEntRepository paymentEntRepository;

    private final PaymentEntMapper paymentEntMapper;

    public PaymentEntService(PaymentEntRepository paymentEntRepository, PaymentEntMapper paymentEntMapper) {
        this.paymentEntRepository = paymentEntRepository;
        this.paymentEntMapper = paymentEntMapper;
    }

    /**
     * Save a paymentEnt.
     *
     * @param paymentEntDTO the entity to save.
     * @return the persisted entity.
     */
    public PaymentEntDTO save(PaymentEntDTO paymentEntDTO) {
        log.debug("Request to save PaymentEnt : {}", paymentEntDTO);
        PaymentEnt paymentEnt = paymentEntMapper.toEntity(paymentEntDTO);
        paymentEnt = paymentEntRepository.save(paymentEnt);
        return paymentEntMapper.toDto(paymentEnt);
    }

    /**
     * Update a paymentEnt.
     *
     * @param paymentEntDTO the entity to save.
     * @return the persisted entity.
     */
    public PaymentEntDTO update(PaymentEntDTO paymentEntDTO) {
        log.debug("Request to update PaymentEnt : {}", paymentEntDTO);
        PaymentEnt paymentEnt = paymentEntMapper.toEntity(paymentEntDTO);
        paymentEnt = paymentEntRepository.save(paymentEnt);
        return paymentEntMapper.toDto(paymentEnt);
    }

    /**
     * Partially update a paymentEnt.
     *
     * @param paymentEntDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PaymentEntDTO> partialUpdate(PaymentEntDTO paymentEntDTO) {
        log.debug("Request to partially update PaymentEnt : {}", paymentEntDTO);

        return paymentEntRepository
            .findById(paymentEntDTO.getId())
            .map(existingPaymentEnt -> {
                paymentEntMapper.partialUpdate(existingPaymentEnt, paymentEntDTO);

                return existingPaymentEnt;
            })
            .map(paymentEntRepository::save)
            .map(paymentEntMapper::toDto);
    }

    /**
     * Get all the paymentEnts.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PaymentEntDTO> findAll() {
        log.debug("Request to get all PaymentEnts");
        return paymentEntRepository.findAll().stream().map(paymentEntMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one paymentEnt by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PaymentEntDTO> findOne(Long id) {
        log.debug("Request to get PaymentEnt : {}", id);
        return paymentEntRepository.findById(id).map(paymentEntMapper::toDto);
    }

    /**
     * Delete the paymentEnt by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PaymentEnt : {}", id);
        paymentEntRepository.deleteById(id);
    }
}
