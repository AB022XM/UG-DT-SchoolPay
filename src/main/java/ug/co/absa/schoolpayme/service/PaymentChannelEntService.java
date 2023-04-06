package ug.co.absa.schoolpayme.service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ug.co.absa.schoolpayme.domain.PaymentChannelEnt;
import ug.co.absa.schoolpayme.repository.PaymentChannelEntRepository;
import ug.co.absa.schoolpayme.service.dto.PaymentChannelEntDTO;
import ug.co.absa.schoolpayme.service.mapper.PaymentChannelEntMapper;

/**
 * Service Implementation for managing {@link PaymentChannelEnt}.
 */
@Service
@Transactional
public class PaymentChannelEntService {

    private final Logger log = LoggerFactory.getLogger(PaymentChannelEntService.class);

    private final PaymentChannelEntRepository paymentChannelEntRepository;

    private final PaymentChannelEntMapper paymentChannelEntMapper;

    public PaymentChannelEntService(
        PaymentChannelEntRepository paymentChannelEntRepository,
        PaymentChannelEntMapper paymentChannelEntMapper
    ) {
        this.paymentChannelEntRepository = paymentChannelEntRepository;
        this.paymentChannelEntMapper = paymentChannelEntMapper;
    }

    /**
     * Save a paymentChannelEnt.
     *
     * @param paymentChannelEntDTO the entity to save.
     * @return the persisted entity.
     */
    public PaymentChannelEntDTO save(PaymentChannelEntDTO paymentChannelEntDTO) {
        log.debug("Request to save PaymentChannelEnt : {}", paymentChannelEntDTO);
        PaymentChannelEnt paymentChannelEnt = paymentChannelEntMapper.toEntity(paymentChannelEntDTO);
        paymentChannelEnt = paymentChannelEntRepository.save(paymentChannelEnt);
        return paymentChannelEntMapper.toDto(paymentChannelEnt);
    }

    /**
     * Update a paymentChannelEnt.
     *
     * @param paymentChannelEntDTO the entity to save.
     * @return the persisted entity.
     */
    public PaymentChannelEntDTO update(PaymentChannelEntDTO paymentChannelEntDTO) {
        log.debug("Request to update PaymentChannelEnt : {}", paymentChannelEntDTO);
        PaymentChannelEnt paymentChannelEnt = paymentChannelEntMapper.toEntity(paymentChannelEntDTO);
        paymentChannelEnt = paymentChannelEntRepository.save(paymentChannelEnt);
        return paymentChannelEntMapper.toDto(paymentChannelEnt);
    }

    /**
     * Partially update a paymentChannelEnt.
     *
     * @param paymentChannelEntDTO the entity to update partially.
     * @return the persisted entity.
     */
    public Optional<PaymentChannelEntDTO> partialUpdate(PaymentChannelEntDTO paymentChannelEntDTO) {
        log.debug("Request to partially update PaymentChannelEnt : {}", paymentChannelEntDTO);

        return paymentChannelEntRepository
            .findById(paymentChannelEntDTO.getId())
            .map(existingPaymentChannelEnt -> {
                paymentChannelEntMapper.partialUpdate(existingPaymentChannelEnt, paymentChannelEntDTO);

                return existingPaymentChannelEnt;
            })
            .map(paymentChannelEntRepository::save)
            .map(paymentChannelEntMapper::toDto);
    }

    /**
     * Get all the paymentChannelEnts.
     *
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public List<PaymentChannelEntDTO> findAll() {
        log.debug("Request to get all PaymentChannelEnts");
        return paymentChannelEntRepository
            .findAll()
            .stream()
            .map(paymentChannelEntMapper::toDto)
            .collect(Collectors.toCollection(LinkedList::new));
    }

    /**
     * Get one paymentChannelEnt by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<PaymentChannelEntDTO> findOne(Long id) {
        log.debug("Request to get PaymentChannelEnt : {}", id);
        return paymentChannelEntRepository.findById(id).map(paymentChannelEntMapper::toDto);
    }

    /**
     * Delete the paymentChannelEnt by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete PaymentChannelEnt : {}", id);
        paymentChannelEntRepository.deleteById(id);
    }
}
