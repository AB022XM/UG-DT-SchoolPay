package ug.co.absa.schoolpayme.web.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ug.co.absa.schoolpayme.IntegrationTest;
import ug.co.absa.schoolpayme.domain.PaymentEnt;
import ug.co.absa.schoolpayme.domain.enumeration.PaymentChannel;
import ug.co.absa.schoolpayme.domain.enumeration.RecordStatus;
import ug.co.absa.schoolpayme.repository.PaymentEntRepository;
import ug.co.absa.schoolpayme.service.dto.PaymentEntDTO;
import ug.co.absa.schoolpayme.service.mapper.PaymentEntMapper;

/**
 * Integration tests for the {@link PaymentEntResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PaymentEntResourceIT {

    private static final UUID DEFAULT_RECORD_UNIQUE_IDENTIFIER = UUID.randomUUID();
    private static final UUID UPDATED_RECORD_UNIQUE_IDENTIFIER = UUID.randomUUID();

    private static final Integer DEFAULT_PAYMENT_ID = 1;
    private static final Integer UPDATED_PAYMENT_ID = 2;

    private static final Integer DEFAULT_PAYMENT_CODE = 1;
    private static final Integer UPDATED_PAYMENT_CODE = 2;

    private static final String DEFAULT_CUSTOMER_ID = "AAAAAAAAAA";
    private static final String UPDATED_CUSTOMER_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_FEE_AMOUNT = 1;
    private static final Integer UPDATED_FEE_AMOUNT = 2;

    private static final RecordStatus DEFAULT_IS_AMOUNT_FIXED = RecordStatus.ACTIVE;
    private static final RecordStatus UPDATED_IS_AMOUNT_FIXED = RecordStatus.INACTIVE;

    private static final String DEFAULT_FEE_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_FEE_DESCRIPTION = "BBBBBBBBBB";

    private static final String DEFAULT_FIXED_AMOUNT = "AAAAAAAAAA";
    private static final String UPDATED_FIXED_AMOUNT = "BBBBBBBBBB";

    private static final String DEFAULT_PAYMENT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PAYMENT_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_OUTSTANDING_AMOUNT = 1;
    private static final Integer UPDATED_OUTSTANDING_AMOUNT = 2;

    private static final PaymentChannel DEFAULT_PAYMENT_CHANNEL = PaymentChannel.OVERTHECOUNTER;
    private static final PaymentChannel UPDATED_PAYMENT_CHANNEL = PaymentChannel.ABSAINTERNETBANKING;

    private static final String DEFAULT_FREE_FIELD_1 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_1 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_2 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_2 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_3 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_3 = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/payment-ents";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PaymentEntRepository paymentEntRepository;

    @Autowired
    private PaymentEntMapper paymentEntMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaymentEntMockMvc;

    private PaymentEnt paymentEnt;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentEnt createEntity(EntityManager em) {
        PaymentEnt paymentEnt = new PaymentEnt()
            .recordUniqueIdentifier(DEFAULT_RECORD_UNIQUE_IDENTIFIER)
            .paymentId(DEFAULT_PAYMENT_ID)
            .paymentCode(DEFAULT_PAYMENT_CODE)
            .customerId(DEFAULT_CUSTOMER_ID)
            .feeAmount(DEFAULT_FEE_AMOUNT)
            .isAmountFixed(DEFAULT_IS_AMOUNT_FIXED)
            .feeDescription(DEFAULT_FEE_DESCRIPTION)
            .fixedAmount(DEFAULT_FIXED_AMOUNT)
            .paymentName(DEFAULT_PAYMENT_NAME)
            .outstandingAmount(DEFAULT_OUTSTANDING_AMOUNT)
            .paymentChannel(DEFAULT_PAYMENT_CHANNEL)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3);
        return paymentEnt;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentEnt createUpdatedEntity(EntityManager em) {
        PaymentEnt paymentEnt = new PaymentEnt()
            .recordUniqueIdentifier(UPDATED_RECORD_UNIQUE_IDENTIFIER)
            .paymentId(UPDATED_PAYMENT_ID)
            .paymentCode(UPDATED_PAYMENT_CODE)
            .customerId(UPDATED_CUSTOMER_ID)
            .feeAmount(UPDATED_FEE_AMOUNT)
            .isAmountFixed(UPDATED_IS_AMOUNT_FIXED)
            .feeDescription(UPDATED_FEE_DESCRIPTION)
            .fixedAmount(UPDATED_FIXED_AMOUNT)
            .paymentName(UPDATED_PAYMENT_NAME)
            .outstandingAmount(UPDATED_OUTSTANDING_AMOUNT)
            .paymentChannel(UPDATED_PAYMENT_CHANNEL)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);
        return paymentEnt;
    }

    @BeforeEach
    public void initTest() {
        paymentEnt = createEntity(em);
    }

    @Test
    @Transactional
    void createPaymentEnt() throws Exception {
        int databaseSizeBeforeCreate = paymentEntRepository.findAll().size();
        // Create the PaymentEnt
        PaymentEntDTO paymentEntDTO = paymentEntMapper.toDto(paymentEnt);
        restPaymentEntMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentEntDTO)))
            .andExpect(status().isCreated());

        // Validate the PaymentEnt in the database
        List<PaymentEnt> paymentEntList = paymentEntRepository.findAll();
        assertThat(paymentEntList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentEnt testPaymentEnt = paymentEntList.get(paymentEntList.size() - 1);
        assertThat(testPaymentEnt.getRecordUniqueIdentifier()).isEqualTo(DEFAULT_RECORD_UNIQUE_IDENTIFIER);
        assertThat(testPaymentEnt.getPaymentId()).isEqualTo(DEFAULT_PAYMENT_ID);
        assertThat(testPaymentEnt.getPaymentCode()).isEqualTo(DEFAULT_PAYMENT_CODE);
        assertThat(testPaymentEnt.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
        assertThat(testPaymentEnt.getFeeAmount()).isEqualTo(DEFAULT_FEE_AMOUNT);
        assertThat(testPaymentEnt.getIsAmountFixed()).isEqualTo(DEFAULT_IS_AMOUNT_FIXED);
        assertThat(testPaymentEnt.getFeeDescription()).isEqualTo(DEFAULT_FEE_DESCRIPTION);
        assertThat(testPaymentEnt.getFixedAmount()).isEqualTo(DEFAULT_FIXED_AMOUNT);
        assertThat(testPaymentEnt.getPaymentName()).isEqualTo(DEFAULT_PAYMENT_NAME);
        assertThat(testPaymentEnt.getOutstandingAmount()).isEqualTo(DEFAULT_OUTSTANDING_AMOUNT);
        assertThat(testPaymentEnt.getPaymentChannel()).isEqualTo(DEFAULT_PAYMENT_CHANNEL);
        assertThat(testPaymentEnt.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testPaymentEnt.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testPaymentEnt.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void createPaymentEntWithExistingId() throws Exception {
        // Create the PaymentEnt with an existing ID
        paymentEnt.setId(1L);
        PaymentEntDTO paymentEntDTO = paymentEntMapper.toDto(paymentEnt);

        int databaseSizeBeforeCreate = paymentEntRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentEntMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentEntDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PaymentEnt in the database
        List<PaymentEnt> paymentEntList = paymentEntRepository.findAll();
        assertThat(paymentEntList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkRecordUniqueIdentifierIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentEntRepository.findAll().size();
        // set the field null
        paymentEnt.setRecordUniqueIdentifier(null);

        // Create the PaymentEnt, which fails.
        PaymentEntDTO paymentEntDTO = paymentEntMapper.toDto(paymentEnt);

        restPaymentEntMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentEntDTO)))
            .andExpect(status().isBadRequest());

        List<PaymentEnt> paymentEntList = paymentEntRepository.findAll();
        assertThat(paymentEntList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPaymentIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentEntRepository.findAll().size();
        // set the field null
        paymentEnt.setPaymentId(null);

        // Create the PaymentEnt, which fails.
        PaymentEntDTO paymentEntDTO = paymentEntMapper.toDto(paymentEnt);

        restPaymentEntMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentEntDTO)))
            .andExpect(status().isBadRequest());

        List<PaymentEnt> paymentEntList = paymentEntRepository.findAll();
        assertThat(paymentEntList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPaymentCodeIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentEntRepository.findAll().size();
        // set the field null
        paymentEnt.setPaymentCode(null);

        // Create the PaymentEnt, which fails.
        PaymentEntDTO paymentEntDTO = paymentEntMapper.toDto(paymentEnt);

        restPaymentEntMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentEntDTO)))
            .andExpect(status().isBadRequest());

        List<PaymentEnt> paymentEntList = paymentEntRepository.findAll();
        assertThat(paymentEntList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkFixedAmountIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentEntRepository.findAll().size();
        // set the field null
        paymentEnt.setFixedAmount(null);

        // Create the PaymentEnt, which fails.
        PaymentEntDTO paymentEntDTO = paymentEntMapper.toDto(paymentEnt);

        restPaymentEntMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentEntDTO)))
            .andExpect(status().isBadRequest());

        List<PaymentEnt> paymentEntList = paymentEntRepository.findAll();
        assertThat(paymentEntList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPaymentNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentEntRepository.findAll().size();
        // set the field null
        paymentEnt.setPaymentName(null);

        // Create the PaymentEnt, which fails.
        PaymentEntDTO paymentEntDTO = paymentEntMapper.toDto(paymentEnt);

        restPaymentEntMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentEntDTO)))
            .andExpect(status().isBadRequest());

        List<PaymentEnt> paymentEntList = paymentEntRepository.findAll();
        assertThat(paymentEntList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkPaymentChannelIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentEntRepository.findAll().size();
        // set the field null
        paymentEnt.setPaymentChannel(null);

        // Create the PaymentEnt, which fails.
        PaymentEntDTO paymentEntDTO = paymentEntMapper.toDto(paymentEnt);

        restPaymentEntMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentEntDTO)))
            .andExpect(status().isBadRequest());

        List<PaymentEnt> paymentEntList = paymentEntRepository.findAll();
        assertThat(paymentEntList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPaymentEnts() throws Exception {
        // Initialize the database
        paymentEntRepository.saveAndFlush(paymentEnt);

        // Get all the paymentEntList
        restPaymentEntMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentEnt.getId().intValue())))
            .andExpect(jsonPath("$.[*].recordUniqueIdentifier").value(hasItem(DEFAULT_RECORD_UNIQUE_IDENTIFIER.toString())))
            .andExpect(jsonPath("$.[*].paymentId").value(hasItem(DEFAULT_PAYMENT_ID)))
            .andExpect(jsonPath("$.[*].paymentCode").value(hasItem(DEFAULT_PAYMENT_CODE)))
            .andExpect(jsonPath("$.[*].customerId").value(hasItem(DEFAULT_CUSTOMER_ID)))
            .andExpect(jsonPath("$.[*].feeAmount").value(hasItem(DEFAULT_FEE_AMOUNT)))
            .andExpect(jsonPath("$.[*].isAmountFixed").value(hasItem(DEFAULT_IS_AMOUNT_FIXED.toString())))
            .andExpect(jsonPath("$.[*].feeDescription").value(hasItem(DEFAULT_FEE_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].fixedAmount").value(hasItem(DEFAULT_FIXED_AMOUNT)))
            .andExpect(jsonPath("$.[*].paymentName").value(hasItem(DEFAULT_PAYMENT_NAME)))
            .andExpect(jsonPath("$.[*].outstandingAmount").value(hasItem(DEFAULT_OUTSTANDING_AMOUNT)))
            .andExpect(jsonPath("$.[*].paymentChannel").value(hasItem(DEFAULT_PAYMENT_CHANNEL.toString())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)));
    }

    @Test
    @Transactional
    void getPaymentEnt() throws Exception {
        // Initialize the database
        paymentEntRepository.saveAndFlush(paymentEnt);

        // Get the paymentEnt
        restPaymentEntMockMvc
            .perform(get(ENTITY_API_URL_ID, paymentEnt.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paymentEnt.getId().intValue()))
            .andExpect(jsonPath("$.recordUniqueIdentifier").value(DEFAULT_RECORD_UNIQUE_IDENTIFIER.toString()))
            .andExpect(jsonPath("$.paymentId").value(DEFAULT_PAYMENT_ID))
            .andExpect(jsonPath("$.paymentCode").value(DEFAULT_PAYMENT_CODE))
            .andExpect(jsonPath("$.customerId").value(DEFAULT_CUSTOMER_ID))
            .andExpect(jsonPath("$.feeAmount").value(DEFAULT_FEE_AMOUNT))
            .andExpect(jsonPath("$.isAmountFixed").value(DEFAULT_IS_AMOUNT_FIXED.toString()))
            .andExpect(jsonPath("$.feeDescription").value(DEFAULT_FEE_DESCRIPTION))
            .andExpect(jsonPath("$.fixedAmount").value(DEFAULT_FIXED_AMOUNT))
            .andExpect(jsonPath("$.paymentName").value(DEFAULT_PAYMENT_NAME))
            .andExpect(jsonPath("$.outstandingAmount").value(DEFAULT_OUTSTANDING_AMOUNT))
            .andExpect(jsonPath("$.paymentChannel").value(DEFAULT_PAYMENT_CHANNEL.toString()))
            .andExpect(jsonPath("$.freeField1").value(DEFAULT_FREE_FIELD_1))
            .andExpect(jsonPath("$.freeField2").value(DEFAULT_FREE_FIELD_2))
            .andExpect(jsonPath("$.freeField3").value(DEFAULT_FREE_FIELD_3));
    }

    @Test
    @Transactional
    void getNonExistingPaymentEnt() throws Exception {
        // Get the paymentEnt
        restPaymentEntMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPaymentEnt() throws Exception {
        // Initialize the database
        paymentEntRepository.saveAndFlush(paymentEnt);

        int databaseSizeBeforeUpdate = paymentEntRepository.findAll().size();

        // Update the paymentEnt
        PaymentEnt updatedPaymentEnt = paymentEntRepository.findById(paymentEnt.getId()).get();
        // Disconnect from session so that the updates on updatedPaymentEnt are not directly saved in db
        em.detach(updatedPaymentEnt);
        updatedPaymentEnt
            .recordUniqueIdentifier(UPDATED_RECORD_UNIQUE_IDENTIFIER)
            .paymentId(UPDATED_PAYMENT_ID)
            .paymentCode(UPDATED_PAYMENT_CODE)
            .customerId(UPDATED_CUSTOMER_ID)
            .feeAmount(UPDATED_FEE_AMOUNT)
            .isAmountFixed(UPDATED_IS_AMOUNT_FIXED)
            .feeDescription(UPDATED_FEE_DESCRIPTION)
            .fixedAmount(UPDATED_FIXED_AMOUNT)
            .paymentName(UPDATED_PAYMENT_NAME)
            .outstandingAmount(UPDATED_OUTSTANDING_AMOUNT)
            .paymentChannel(UPDATED_PAYMENT_CHANNEL)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);
        PaymentEntDTO paymentEntDTO = paymentEntMapper.toDto(updatedPaymentEnt);

        restPaymentEntMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paymentEntDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentEntDTO))
            )
            .andExpect(status().isOk());

        // Validate the PaymentEnt in the database
        List<PaymentEnt> paymentEntList = paymentEntRepository.findAll();
        assertThat(paymentEntList).hasSize(databaseSizeBeforeUpdate);
        PaymentEnt testPaymentEnt = paymentEntList.get(paymentEntList.size() - 1);
        assertThat(testPaymentEnt.getRecordUniqueIdentifier()).isEqualTo(UPDATED_RECORD_UNIQUE_IDENTIFIER);
        assertThat(testPaymentEnt.getPaymentId()).isEqualTo(UPDATED_PAYMENT_ID);
        assertThat(testPaymentEnt.getPaymentCode()).isEqualTo(UPDATED_PAYMENT_CODE);
        assertThat(testPaymentEnt.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testPaymentEnt.getFeeAmount()).isEqualTo(UPDATED_FEE_AMOUNT);
        assertThat(testPaymentEnt.getIsAmountFixed()).isEqualTo(UPDATED_IS_AMOUNT_FIXED);
        assertThat(testPaymentEnt.getFeeDescription()).isEqualTo(UPDATED_FEE_DESCRIPTION);
        assertThat(testPaymentEnt.getFixedAmount()).isEqualTo(UPDATED_FIXED_AMOUNT);
        assertThat(testPaymentEnt.getPaymentName()).isEqualTo(UPDATED_PAYMENT_NAME);
        assertThat(testPaymentEnt.getOutstandingAmount()).isEqualTo(UPDATED_OUTSTANDING_AMOUNT);
        assertThat(testPaymentEnt.getPaymentChannel()).isEqualTo(UPDATED_PAYMENT_CHANNEL);
        assertThat(testPaymentEnt.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testPaymentEnt.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testPaymentEnt.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void putNonExistingPaymentEnt() throws Exception {
        int databaseSizeBeforeUpdate = paymentEntRepository.findAll().size();
        paymentEnt.setId(count.incrementAndGet());

        // Create the PaymentEnt
        PaymentEntDTO paymentEntDTO = paymentEntMapper.toDto(paymentEnt);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentEntMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paymentEntDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentEntDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentEnt in the database
        List<PaymentEnt> paymentEntList = paymentEntRepository.findAll();
        assertThat(paymentEntList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPaymentEnt() throws Exception {
        int databaseSizeBeforeUpdate = paymentEntRepository.findAll().size();
        paymentEnt.setId(count.incrementAndGet());

        // Create the PaymentEnt
        PaymentEntDTO paymentEntDTO = paymentEntMapper.toDto(paymentEnt);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentEntMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentEntDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentEnt in the database
        List<PaymentEnt> paymentEntList = paymentEntRepository.findAll();
        assertThat(paymentEntList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPaymentEnt() throws Exception {
        int databaseSizeBeforeUpdate = paymentEntRepository.findAll().size();
        paymentEnt.setId(count.incrementAndGet());

        // Create the PaymentEnt
        PaymentEntDTO paymentEntDTO = paymentEntMapper.toDto(paymentEnt);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentEntMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentEntDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentEnt in the database
        List<PaymentEnt> paymentEntList = paymentEntRepository.findAll();
        assertThat(paymentEntList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePaymentEntWithPatch() throws Exception {
        // Initialize the database
        paymentEntRepository.saveAndFlush(paymentEnt);

        int databaseSizeBeforeUpdate = paymentEntRepository.findAll().size();

        // Update the paymentEnt using partial update
        PaymentEnt partialUpdatedPaymentEnt = new PaymentEnt();
        partialUpdatedPaymentEnt.setId(paymentEnt.getId());

        partialUpdatedPaymentEnt
            .recordUniqueIdentifier(UPDATED_RECORD_UNIQUE_IDENTIFIER)
            .paymentId(UPDATED_PAYMENT_ID)
            .paymentCode(UPDATED_PAYMENT_CODE)
            .feeAmount(UPDATED_FEE_AMOUNT)
            .feeDescription(UPDATED_FEE_DESCRIPTION)
            .fixedAmount(UPDATED_FIXED_AMOUNT)
            .paymentName(UPDATED_PAYMENT_NAME)
            .outstandingAmount(UPDATED_OUTSTANDING_AMOUNT)
            .freeField1(UPDATED_FREE_FIELD_1);

        restPaymentEntMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentEnt.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaymentEnt))
            )
            .andExpect(status().isOk());

        // Validate the PaymentEnt in the database
        List<PaymentEnt> paymentEntList = paymentEntRepository.findAll();
        assertThat(paymentEntList).hasSize(databaseSizeBeforeUpdate);
        PaymentEnt testPaymentEnt = paymentEntList.get(paymentEntList.size() - 1);
        assertThat(testPaymentEnt.getRecordUniqueIdentifier()).isEqualTo(UPDATED_RECORD_UNIQUE_IDENTIFIER);
        assertThat(testPaymentEnt.getPaymentId()).isEqualTo(UPDATED_PAYMENT_ID);
        assertThat(testPaymentEnt.getPaymentCode()).isEqualTo(UPDATED_PAYMENT_CODE);
        assertThat(testPaymentEnt.getCustomerId()).isEqualTo(DEFAULT_CUSTOMER_ID);
        assertThat(testPaymentEnt.getFeeAmount()).isEqualTo(UPDATED_FEE_AMOUNT);
        assertThat(testPaymentEnt.getIsAmountFixed()).isEqualTo(DEFAULT_IS_AMOUNT_FIXED);
        assertThat(testPaymentEnt.getFeeDescription()).isEqualTo(UPDATED_FEE_DESCRIPTION);
        assertThat(testPaymentEnt.getFixedAmount()).isEqualTo(UPDATED_FIXED_AMOUNT);
        assertThat(testPaymentEnt.getPaymentName()).isEqualTo(UPDATED_PAYMENT_NAME);
        assertThat(testPaymentEnt.getOutstandingAmount()).isEqualTo(UPDATED_OUTSTANDING_AMOUNT);
        assertThat(testPaymentEnt.getPaymentChannel()).isEqualTo(DEFAULT_PAYMENT_CHANNEL);
        assertThat(testPaymentEnt.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testPaymentEnt.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testPaymentEnt.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void fullUpdatePaymentEntWithPatch() throws Exception {
        // Initialize the database
        paymentEntRepository.saveAndFlush(paymentEnt);

        int databaseSizeBeforeUpdate = paymentEntRepository.findAll().size();

        // Update the paymentEnt using partial update
        PaymentEnt partialUpdatedPaymentEnt = new PaymentEnt();
        partialUpdatedPaymentEnt.setId(paymentEnt.getId());

        partialUpdatedPaymentEnt
            .recordUniqueIdentifier(UPDATED_RECORD_UNIQUE_IDENTIFIER)
            .paymentId(UPDATED_PAYMENT_ID)
            .paymentCode(UPDATED_PAYMENT_CODE)
            .customerId(UPDATED_CUSTOMER_ID)
            .feeAmount(UPDATED_FEE_AMOUNT)
            .isAmountFixed(UPDATED_IS_AMOUNT_FIXED)
            .feeDescription(UPDATED_FEE_DESCRIPTION)
            .fixedAmount(UPDATED_FIXED_AMOUNT)
            .paymentName(UPDATED_PAYMENT_NAME)
            .outstandingAmount(UPDATED_OUTSTANDING_AMOUNT)
            .paymentChannel(UPDATED_PAYMENT_CHANNEL)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3);

        restPaymentEntMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentEnt.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaymentEnt))
            )
            .andExpect(status().isOk());

        // Validate the PaymentEnt in the database
        List<PaymentEnt> paymentEntList = paymentEntRepository.findAll();
        assertThat(paymentEntList).hasSize(databaseSizeBeforeUpdate);
        PaymentEnt testPaymentEnt = paymentEntList.get(paymentEntList.size() - 1);
        assertThat(testPaymentEnt.getRecordUniqueIdentifier()).isEqualTo(UPDATED_RECORD_UNIQUE_IDENTIFIER);
        assertThat(testPaymentEnt.getPaymentId()).isEqualTo(UPDATED_PAYMENT_ID);
        assertThat(testPaymentEnt.getPaymentCode()).isEqualTo(UPDATED_PAYMENT_CODE);
        assertThat(testPaymentEnt.getCustomerId()).isEqualTo(UPDATED_CUSTOMER_ID);
        assertThat(testPaymentEnt.getFeeAmount()).isEqualTo(UPDATED_FEE_AMOUNT);
        assertThat(testPaymentEnt.getIsAmountFixed()).isEqualTo(UPDATED_IS_AMOUNT_FIXED);
        assertThat(testPaymentEnt.getFeeDescription()).isEqualTo(UPDATED_FEE_DESCRIPTION);
        assertThat(testPaymentEnt.getFixedAmount()).isEqualTo(UPDATED_FIXED_AMOUNT);
        assertThat(testPaymentEnt.getPaymentName()).isEqualTo(UPDATED_PAYMENT_NAME);
        assertThat(testPaymentEnt.getOutstandingAmount()).isEqualTo(UPDATED_OUTSTANDING_AMOUNT);
        assertThat(testPaymentEnt.getPaymentChannel()).isEqualTo(UPDATED_PAYMENT_CHANNEL);
        assertThat(testPaymentEnt.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testPaymentEnt.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testPaymentEnt.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
    }

    @Test
    @Transactional
    void patchNonExistingPaymentEnt() throws Exception {
        int databaseSizeBeforeUpdate = paymentEntRepository.findAll().size();
        paymentEnt.setId(count.incrementAndGet());

        // Create the PaymentEnt
        PaymentEntDTO paymentEntDTO = paymentEntMapper.toDto(paymentEnt);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentEntMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, paymentEntDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentEntDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentEnt in the database
        List<PaymentEnt> paymentEntList = paymentEntRepository.findAll();
        assertThat(paymentEntList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPaymentEnt() throws Exception {
        int databaseSizeBeforeUpdate = paymentEntRepository.findAll().size();
        paymentEnt.setId(count.incrementAndGet());

        // Create the PaymentEnt
        PaymentEntDTO paymentEntDTO = paymentEntMapper.toDto(paymentEnt);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentEntMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentEntDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentEnt in the database
        List<PaymentEnt> paymentEntList = paymentEntRepository.findAll();
        assertThat(paymentEntList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPaymentEnt() throws Exception {
        int databaseSizeBeforeUpdate = paymentEntRepository.findAll().size();
        paymentEnt.setId(count.incrementAndGet());

        // Create the PaymentEnt
        PaymentEntDTO paymentEntDTO = paymentEntMapper.toDto(paymentEnt);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentEntMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(TestUtil.convertObjectToJsonBytes(paymentEntDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentEnt in the database
        List<PaymentEnt> paymentEntList = paymentEntRepository.findAll();
        assertThat(paymentEntList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePaymentEnt() throws Exception {
        // Initialize the database
        paymentEntRepository.saveAndFlush(paymentEnt);

        int databaseSizeBeforeDelete = paymentEntRepository.findAll().size();

        // Delete the paymentEnt
        restPaymentEntMockMvc
            .perform(delete(ENTITY_API_URL_ID, paymentEnt.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PaymentEnt> paymentEntList = paymentEntRepository.findAll();
        assertThat(paymentEntList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
