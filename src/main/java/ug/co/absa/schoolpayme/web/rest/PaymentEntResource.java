package ug.co.absa.schoolpayme.web.rest;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.ResponseUtil;
import ug.co.absa.schoolpayme.repository.PaymentEntRepository;
import ug.co.absa.schoolpayme.service.PaymentEntService;
import ug.co.absa.schoolpayme.service.dto.PaymentEntDTO;
import ug.co.absa.schoolpayme.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link ug.co.absa.schoolpayme.domain.PaymentEnt}.
 */
@RestController
@RequestMapping("/api")
public class PaymentEntResource {

    private final Logger log = LoggerFactory.getLogger(PaymentEntResource.class);

    private static final String ENTITY_NAME = "paymentEnt";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaymentEntService paymentEntService;

    private final PaymentEntRepository paymentEntRepository;

    public PaymentEntResource(PaymentEntService paymentEntService, PaymentEntRepository paymentEntRepository) {
        this.paymentEntService = paymentEntService;
        this.paymentEntRepository = paymentEntRepository;
    }

    /**
     * {@code POST  /payment-ents} : Create a new paymentEnt.
     *
     * @param paymentEntDTO the paymentEntDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paymentEntDTO, or with status {@code 400 (Bad Request)} if the paymentEnt has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/payment-ents")
    public ResponseEntity<PaymentEntDTO> createPaymentEnt(@Valid @RequestBody PaymentEntDTO paymentEntDTO) throws URISyntaxException {
        log.debug("REST request to save PaymentEnt : {}", paymentEntDTO);
        if (paymentEntDTO.getId() != null) {
            throw new BadRequestAlertException("A new paymentEnt cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaymentEntDTO result = paymentEntService.save(paymentEntDTO);
        return ResponseEntity
            .created(new URI("/api/payment-ents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /payment-ents/:id} : Updates an existing paymentEnt.
     *
     * @param id the id of the paymentEntDTO to save.
     * @param paymentEntDTO the paymentEntDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentEntDTO,
     * or with status {@code 400 (Bad Request)} if the paymentEntDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paymentEntDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payment-ents/{id}")
    public ResponseEntity<PaymentEntDTO> updatePaymentEnt(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PaymentEntDTO paymentEntDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PaymentEnt : {}, {}", id, paymentEntDTO);
        if (paymentEntDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentEntDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentEntRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PaymentEntDTO result = paymentEntService.update(paymentEntDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentEntDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /payment-ents/:id} : Partial updates given fields of an existing paymentEnt, field will ignore if it is null
     *
     * @param id the id of the paymentEntDTO to save.
     * @param paymentEntDTO the paymentEntDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentEntDTO,
     * or with status {@code 400 (Bad Request)} if the paymentEntDTO is not valid,
     * or with status {@code 404 (Not Found)} if the paymentEntDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the paymentEntDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/payment-ents/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PaymentEntDTO> partialUpdatePaymentEnt(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PaymentEntDTO paymentEntDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PaymentEnt partially : {}, {}", id, paymentEntDTO);
        if (paymentEntDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentEntDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentEntRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PaymentEntDTO> result = paymentEntService.partialUpdate(paymentEntDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentEntDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /payment-ents} : get all the paymentEnts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paymentEnts in body.
     */
    @GetMapping("/payment-ents")
    public List<PaymentEntDTO> getAllPaymentEnts() {
        log.debug("REST request to get all PaymentEnts");
        return paymentEntService.findAll();
    }

    /**
     * {@code GET  /payment-ents/:id} : get the "id" paymentEnt.
     *
     * @param id the id of the paymentEntDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paymentEntDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payment-ents/{id}")
    public ResponseEntity<PaymentEntDTO> getPaymentEnt(@PathVariable Long id) {
        log.debug("REST request to get PaymentEnt : {}", id);
        Optional<PaymentEntDTO> paymentEntDTO = paymentEntService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paymentEntDTO);
    }

    /**
     * {@code DELETE  /payment-ents/:id} : delete the "id" paymentEnt.
     *
     * @param id the id of the paymentEntDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payment-ents/{id}")
    public ResponseEntity<Void> deletePaymentEnt(@PathVariable Long id) {
        log.debug("REST request to delete PaymentEnt : {}", id);
        paymentEntService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
