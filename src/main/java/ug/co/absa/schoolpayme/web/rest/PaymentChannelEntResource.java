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
import ug.co.absa.schoolpayme.repository.PaymentChannelEntRepository;
import ug.co.absa.schoolpayme.service.PaymentChannelEntService;
import ug.co.absa.schoolpayme.service.dto.PaymentChannelEntDTO;
import ug.co.absa.schoolpayme.web.rest.errors.BadRequestAlertException;

/**
 * REST controller for managing {@link ug.co.absa.schoolpayme.domain.PaymentChannelEnt}.
 */
@RestController
@RequestMapping("/api")
public class PaymentChannelEntResource {

    private final Logger log = LoggerFactory.getLogger(PaymentChannelEntResource.class);

    private static final String ENTITY_NAME = "paymentChannelEnt";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PaymentChannelEntService paymentChannelEntService;

    private final PaymentChannelEntRepository paymentChannelEntRepository;

    public PaymentChannelEntResource(
        PaymentChannelEntService paymentChannelEntService,
        PaymentChannelEntRepository paymentChannelEntRepository
    ) {
        this.paymentChannelEntService = paymentChannelEntService;
        this.paymentChannelEntRepository = paymentChannelEntRepository;
    }

    /**
     * {@code POST  /payment-channel-ents} : Create a new paymentChannelEnt.
     *
     * @param paymentChannelEntDTO the paymentChannelEntDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paymentChannelEntDTO, or with status {@code 400 (Bad Request)} if the paymentChannelEnt has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/payment-channel-ents")
    public ResponseEntity<PaymentChannelEntDTO> createPaymentChannelEnt(@Valid @RequestBody PaymentChannelEntDTO paymentChannelEntDTO)
        throws URISyntaxException {
        log.debug("REST request to save PaymentChannelEnt : {}", paymentChannelEntDTO);
        if (paymentChannelEntDTO.getId() != null) {
            throw new BadRequestAlertException("A new paymentChannelEnt cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PaymentChannelEntDTO result = paymentChannelEntService.save(paymentChannelEntDTO);
        return ResponseEntity
            .created(new URI("/api/payment-channel-ents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /payment-channel-ents/:id} : Updates an existing paymentChannelEnt.
     *
     * @param id the id of the paymentChannelEntDTO to save.
     * @param paymentChannelEntDTO the paymentChannelEntDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentChannelEntDTO,
     * or with status {@code 400 (Bad Request)} if the paymentChannelEntDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paymentChannelEntDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/payment-channel-ents/{id}")
    public ResponseEntity<PaymentChannelEntDTO> updatePaymentChannelEnt(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody PaymentChannelEntDTO paymentChannelEntDTO
    ) throws URISyntaxException {
        log.debug("REST request to update PaymentChannelEnt : {}, {}", id, paymentChannelEntDTO);
        if (paymentChannelEntDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentChannelEntDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentChannelEntRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        PaymentChannelEntDTO result = paymentChannelEntService.update(paymentChannelEntDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentChannelEntDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code PATCH  /payment-channel-ents/:id} : Partial updates given fields of an existing paymentChannelEnt, field will ignore if it is null
     *
     * @param id the id of the paymentChannelEntDTO to save.
     * @param paymentChannelEntDTO the paymentChannelEntDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paymentChannelEntDTO,
     * or with status {@code 400 (Bad Request)} if the paymentChannelEntDTO is not valid,
     * or with status {@code 404 (Not Found)} if the paymentChannelEntDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the paymentChannelEntDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/payment-channel-ents/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<PaymentChannelEntDTO> partialUpdatePaymentChannelEnt(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody PaymentChannelEntDTO paymentChannelEntDTO
    ) throws URISyntaxException {
        log.debug("REST request to partial update PaymentChannelEnt partially : {}, {}", id, paymentChannelEntDTO);
        if (paymentChannelEntDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, paymentChannelEntDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!paymentChannelEntRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<PaymentChannelEntDTO> result = paymentChannelEntService.partialUpdate(paymentChannelEntDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paymentChannelEntDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /payment-channel-ents} : get all the paymentChannelEnts.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of paymentChannelEnts in body.
     */
    @GetMapping("/payment-channel-ents")
    public List<PaymentChannelEntDTO> getAllPaymentChannelEnts() {
        log.debug("REST request to get all PaymentChannelEnts");
        return paymentChannelEntService.findAll();
    }

    /**
     * {@code GET  /payment-channel-ents/:id} : get the "id" paymentChannelEnt.
     *
     * @param id the id of the paymentChannelEntDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paymentChannelEntDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/payment-channel-ents/{id}")
    public ResponseEntity<PaymentChannelEntDTO> getPaymentChannelEnt(@PathVariable Long id) {
        log.debug("REST request to get PaymentChannelEnt : {}", id);
        Optional<PaymentChannelEntDTO> paymentChannelEntDTO = paymentChannelEntService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paymentChannelEntDTO);
    }

    /**
     * {@code DELETE  /payment-channel-ents/:id} : delete the "id" paymentChannelEnt.
     *
     * @param id the id of the paymentChannelEntDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/payment-channel-ents/{id}")
    public ResponseEntity<Void> deletePaymentChannelEnt(@PathVariable Long id) {
        log.debug("REST request to delete PaymentChannelEnt : {}", id);
        paymentChannelEntService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
