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
import ug.co.absa.schoolpayme.domain.PaymentChannelEnt;
import ug.co.absa.schoolpayme.domain.enumeration.PaymentChannel;
import ug.co.absa.schoolpayme.domain.enumeration.RecordStatus;
import ug.co.absa.schoolpayme.domain.enumeration.RecordStatus;
import ug.co.absa.schoolpayme.repository.PaymentChannelEntRepository;
import ug.co.absa.schoolpayme.service.dto.PaymentChannelEntDTO;
import ug.co.absa.schoolpayme.service.mapper.PaymentChannelEntMapper;

/**
 * Integration tests for the {@link PaymentChannelEntResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PaymentChannelEntResourceIT {

    private static final UUID DEFAULT_RECORD_UNIQUE_IDENTIFIER = UUID.randomUUID();
    private static final UUID UPDATED_RECORD_UNIQUE_IDENTIFIER = UUID.randomUUID();

    private static final Integer DEFAULT_CHANNEL_ID = 1;
    private static final Integer UPDATED_CHANNEL_ID = 2;

    private static final Integer DEFAULT_CHANNEL_CODE = 1;
    private static final Integer UPDATED_CHANNEL_CODE = 2;

    private static final PaymentChannel DEFAULT_CHANNEL_NAME = PaymentChannel.OVERTHECOUNTER;
    private static final PaymentChannel UPDATED_CHANNEL_NAME = PaymentChannel.ABSAINTERNETBANKING;

    private static final RecordStatus DEFAULT_STATUS = RecordStatus.ACTIVE;
    private static final RecordStatus UPDATED_STATUS = RecordStatus.INACTIVE;

    private static final String DEFAULT_FREE_FIELD_1 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_1 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_2 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_2 = "BBBBBBBBBB";

    private static final String DEFAULT_FREE_FIELD_3 = "AAAAAAAAAA";
    private static final String UPDATED_FREE_FIELD_3 = "BBBBBBBBBB";

    private static final RecordStatus DEFAULT_IS_DELETED = RecordStatus.ACTIVE;
    private static final RecordStatus UPDATED_IS_DELETED = RecordStatus.INACTIVE;

    private static final String ENTITY_API_URL = "/api/payment-channel-ents";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong count = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private PaymentChannelEntRepository paymentChannelEntRepository;

    @Autowired
    private PaymentChannelEntMapper paymentChannelEntMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPaymentChannelEntMockMvc;

    private PaymentChannelEnt paymentChannelEnt;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentChannelEnt createEntity(EntityManager em) {
        PaymentChannelEnt paymentChannelEnt = new PaymentChannelEnt()
            .recordUniqueIdentifier(DEFAULT_RECORD_UNIQUE_IDENTIFIER)
            .channelId(DEFAULT_CHANNEL_ID)
            .channelCode(DEFAULT_CHANNEL_CODE)
            .channelName(DEFAULT_CHANNEL_NAME)
            .status(DEFAULT_STATUS)
            .freeField1(DEFAULT_FREE_FIELD_1)
            .freeField2(DEFAULT_FREE_FIELD_2)
            .freeField3(DEFAULT_FREE_FIELD_3)
            .isDeleted(DEFAULT_IS_DELETED);
        return paymentChannelEnt;
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PaymentChannelEnt createUpdatedEntity(EntityManager em) {
        PaymentChannelEnt paymentChannelEnt = new PaymentChannelEnt()
            .recordUniqueIdentifier(UPDATED_RECORD_UNIQUE_IDENTIFIER)
            .channelId(UPDATED_CHANNEL_ID)
            .channelCode(UPDATED_CHANNEL_CODE)
            .channelName(UPDATED_CHANNEL_NAME)
            .status(UPDATED_STATUS)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .isDeleted(UPDATED_IS_DELETED);
        return paymentChannelEnt;
    }

    @BeforeEach
    public void initTest() {
        paymentChannelEnt = createEntity(em);
    }

    @Test
    @Transactional
    void createPaymentChannelEnt() throws Exception {
        int databaseSizeBeforeCreate = paymentChannelEntRepository.findAll().size();
        // Create the PaymentChannelEnt
        PaymentChannelEntDTO paymentChannelEntDTO = paymentChannelEntMapper.toDto(paymentChannelEnt);
        restPaymentChannelEntMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentChannelEntDTO))
            )
            .andExpect(status().isCreated());

        // Validate the PaymentChannelEnt in the database
        List<PaymentChannelEnt> paymentChannelEntList = paymentChannelEntRepository.findAll();
        assertThat(paymentChannelEntList).hasSize(databaseSizeBeforeCreate + 1);
        PaymentChannelEnt testPaymentChannelEnt = paymentChannelEntList.get(paymentChannelEntList.size() - 1);
        assertThat(testPaymentChannelEnt.getRecordUniqueIdentifier()).isEqualTo(DEFAULT_RECORD_UNIQUE_IDENTIFIER);
        assertThat(testPaymentChannelEnt.getChannelId()).isEqualTo(DEFAULT_CHANNEL_ID);
        assertThat(testPaymentChannelEnt.getChannelCode()).isEqualTo(DEFAULT_CHANNEL_CODE);
        assertThat(testPaymentChannelEnt.getChannelName()).isEqualTo(DEFAULT_CHANNEL_NAME);
        assertThat(testPaymentChannelEnt.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPaymentChannelEnt.getFreeField1()).isEqualTo(DEFAULT_FREE_FIELD_1);
        assertThat(testPaymentChannelEnt.getFreeField2()).isEqualTo(DEFAULT_FREE_FIELD_2);
        assertThat(testPaymentChannelEnt.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testPaymentChannelEnt.getIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
    }

    @Test
    @Transactional
    void createPaymentChannelEntWithExistingId() throws Exception {
        // Create the PaymentChannelEnt with an existing ID
        paymentChannelEnt.setId(1L);
        PaymentChannelEntDTO paymentChannelEntDTO = paymentChannelEntMapper.toDto(paymentChannelEnt);

        int databaseSizeBeforeCreate = paymentChannelEntRepository.findAll().size();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPaymentChannelEntMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentChannelEntDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentChannelEnt in the database
        List<PaymentChannelEnt> paymentChannelEntList = paymentChannelEntRepository.findAll();
        assertThat(paymentChannelEntList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void checkRecordUniqueIdentifierIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentChannelEntRepository.findAll().size();
        // set the field null
        paymentChannelEnt.setRecordUniqueIdentifier(null);

        // Create the PaymentChannelEnt, which fails.
        PaymentChannelEntDTO paymentChannelEntDTO = paymentChannelEntMapper.toDto(paymentChannelEnt);

        restPaymentChannelEntMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentChannelEntDTO))
            )
            .andExpect(status().isBadRequest());

        List<PaymentChannelEnt> paymentChannelEntList = paymentChannelEntRepository.findAll();
        assertThat(paymentChannelEntList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkChannelIdIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentChannelEntRepository.findAll().size();
        // set the field null
        paymentChannelEnt.setChannelId(null);

        // Create the PaymentChannelEnt, which fails.
        PaymentChannelEntDTO paymentChannelEntDTO = paymentChannelEntMapper.toDto(paymentChannelEnt);

        restPaymentChannelEntMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentChannelEntDTO))
            )
            .andExpect(status().isBadRequest());

        List<PaymentChannelEnt> paymentChannelEntList = paymentChannelEntRepository.findAll();
        assertThat(paymentChannelEntList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void checkChannelNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = paymentChannelEntRepository.findAll().size();
        // set the field null
        paymentChannelEnt.setChannelName(null);

        // Create the PaymentChannelEnt, which fails.
        PaymentChannelEntDTO paymentChannelEntDTO = paymentChannelEntMapper.toDto(paymentChannelEnt);

        restPaymentChannelEntMockMvc
            .perform(
                post(ENTITY_API_URL)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentChannelEntDTO))
            )
            .andExpect(status().isBadRequest());

        List<PaymentChannelEnt> paymentChannelEntList = paymentChannelEntRepository.findAll();
        assertThat(paymentChannelEntList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    void getAllPaymentChannelEnts() throws Exception {
        // Initialize the database
        paymentChannelEntRepository.saveAndFlush(paymentChannelEnt);

        // Get all the paymentChannelEntList
        restPaymentChannelEntMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(paymentChannelEnt.getId().intValue())))
            .andExpect(jsonPath("$.[*].recordUniqueIdentifier").value(hasItem(DEFAULT_RECORD_UNIQUE_IDENTIFIER.toString())))
            .andExpect(jsonPath("$.[*].channelId").value(hasItem(DEFAULT_CHANNEL_ID)))
            .andExpect(jsonPath("$.[*].channelCode").value(hasItem(DEFAULT_CHANNEL_CODE)))
            .andExpect(jsonPath("$.[*].channelName").value(hasItem(DEFAULT_CHANNEL_NAME.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].freeField1").value(hasItem(DEFAULT_FREE_FIELD_1)))
            .andExpect(jsonPath("$.[*].freeField2").value(hasItem(DEFAULT_FREE_FIELD_2)))
            .andExpect(jsonPath("$.[*].freeField3").value(hasItem(DEFAULT_FREE_FIELD_3)))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.toString())));
    }

    @Test
    @Transactional
    void getPaymentChannelEnt() throws Exception {
        // Initialize the database
        paymentChannelEntRepository.saveAndFlush(paymentChannelEnt);

        // Get the paymentChannelEnt
        restPaymentChannelEntMockMvc
            .perform(get(ENTITY_API_URL_ID, paymentChannelEnt.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(paymentChannelEnt.getId().intValue()))
            .andExpect(jsonPath("$.recordUniqueIdentifier").value(DEFAULT_RECORD_UNIQUE_IDENTIFIER.toString()))
            .andExpect(jsonPath("$.channelId").value(DEFAULT_CHANNEL_ID))
            .andExpect(jsonPath("$.channelCode").value(DEFAULT_CHANNEL_CODE))
            .andExpect(jsonPath("$.channelName").value(DEFAULT_CHANNEL_NAME.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.freeField1").value(DEFAULT_FREE_FIELD_1))
            .andExpect(jsonPath("$.freeField2").value(DEFAULT_FREE_FIELD_2))
            .andExpect(jsonPath("$.freeField3").value(DEFAULT_FREE_FIELD_3))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.toString()));
    }

    @Test
    @Transactional
    void getNonExistingPaymentChannelEnt() throws Exception {
        // Get the paymentChannelEnt
        restPaymentChannelEntMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPaymentChannelEnt() throws Exception {
        // Initialize the database
        paymentChannelEntRepository.saveAndFlush(paymentChannelEnt);

        int databaseSizeBeforeUpdate = paymentChannelEntRepository.findAll().size();

        // Update the paymentChannelEnt
        PaymentChannelEnt updatedPaymentChannelEnt = paymentChannelEntRepository.findById(paymentChannelEnt.getId()).get();
        // Disconnect from session so that the updates on updatedPaymentChannelEnt are not directly saved in db
        em.detach(updatedPaymentChannelEnt);
        updatedPaymentChannelEnt
            .recordUniqueIdentifier(UPDATED_RECORD_UNIQUE_IDENTIFIER)
            .channelId(UPDATED_CHANNEL_ID)
            .channelCode(UPDATED_CHANNEL_CODE)
            .channelName(UPDATED_CHANNEL_NAME)
            .status(UPDATED_STATUS)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .isDeleted(UPDATED_IS_DELETED);
        PaymentChannelEntDTO paymentChannelEntDTO = paymentChannelEntMapper.toDto(updatedPaymentChannelEnt);

        restPaymentChannelEntMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paymentChannelEntDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentChannelEntDTO))
            )
            .andExpect(status().isOk());

        // Validate the PaymentChannelEnt in the database
        List<PaymentChannelEnt> paymentChannelEntList = paymentChannelEntRepository.findAll();
        assertThat(paymentChannelEntList).hasSize(databaseSizeBeforeUpdate);
        PaymentChannelEnt testPaymentChannelEnt = paymentChannelEntList.get(paymentChannelEntList.size() - 1);
        assertThat(testPaymentChannelEnt.getRecordUniqueIdentifier()).isEqualTo(UPDATED_RECORD_UNIQUE_IDENTIFIER);
        assertThat(testPaymentChannelEnt.getChannelId()).isEqualTo(UPDATED_CHANNEL_ID);
        assertThat(testPaymentChannelEnt.getChannelCode()).isEqualTo(UPDATED_CHANNEL_CODE);
        assertThat(testPaymentChannelEnt.getChannelName()).isEqualTo(UPDATED_CHANNEL_NAME);
        assertThat(testPaymentChannelEnt.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPaymentChannelEnt.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testPaymentChannelEnt.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testPaymentChannelEnt.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testPaymentChannelEnt.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void putNonExistingPaymentChannelEnt() throws Exception {
        int databaseSizeBeforeUpdate = paymentChannelEntRepository.findAll().size();
        paymentChannelEnt.setId(count.incrementAndGet());

        // Create the PaymentChannelEnt
        PaymentChannelEntDTO paymentChannelEntDTO = paymentChannelEntMapper.toDto(paymentChannelEnt);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentChannelEntMockMvc
            .perform(
                put(ENTITY_API_URL_ID, paymentChannelEntDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentChannelEntDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentChannelEnt in the database
        List<PaymentChannelEnt> paymentChannelEntList = paymentChannelEntRepository.findAll();
        assertThat(paymentChannelEntList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPaymentChannelEnt() throws Exception {
        int databaseSizeBeforeUpdate = paymentChannelEntRepository.findAll().size();
        paymentChannelEnt.setId(count.incrementAndGet());

        // Create the PaymentChannelEnt
        PaymentChannelEntDTO paymentChannelEntDTO = paymentChannelEntMapper.toDto(paymentChannelEnt);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentChannelEntMockMvc
            .perform(
                put(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtil.convertObjectToJsonBytes(paymentChannelEntDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentChannelEnt in the database
        List<PaymentChannelEnt> paymentChannelEntList = paymentChannelEntRepository.findAll();
        assertThat(paymentChannelEntList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPaymentChannelEnt() throws Exception {
        int databaseSizeBeforeUpdate = paymentChannelEntRepository.findAll().size();
        paymentChannelEnt.setId(count.incrementAndGet());

        // Create the PaymentChannelEnt
        PaymentChannelEntDTO paymentChannelEntDTO = paymentChannelEntMapper.toDto(paymentChannelEnt);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentChannelEntMockMvc
            .perform(
                put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(paymentChannelEntDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentChannelEnt in the database
        List<PaymentChannelEnt> paymentChannelEntList = paymentChannelEntRepository.findAll();
        assertThat(paymentChannelEntList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePaymentChannelEntWithPatch() throws Exception {
        // Initialize the database
        paymentChannelEntRepository.saveAndFlush(paymentChannelEnt);

        int databaseSizeBeforeUpdate = paymentChannelEntRepository.findAll().size();

        // Update the paymentChannelEnt using partial update
        PaymentChannelEnt partialUpdatedPaymentChannelEnt = new PaymentChannelEnt();
        partialUpdatedPaymentChannelEnt.setId(paymentChannelEnt.getId());

        partialUpdatedPaymentChannelEnt
            .channelId(UPDATED_CHANNEL_ID)
            .channelName(UPDATED_CHANNEL_NAME)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .isDeleted(UPDATED_IS_DELETED);

        restPaymentChannelEntMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentChannelEnt.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaymentChannelEnt))
            )
            .andExpect(status().isOk());

        // Validate the PaymentChannelEnt in the database
        List<PaymentChannelEnt> paymentChannelEntList = paymentChannelEntRepository.findAll();
        assertThat(paymentChannelEntList).hasSize(databaseSizeBeforeUpdate);
        PaymentChannelEnt testPaymentChannelEnt = paymentChannelEntList.get(paymentChannelEntList.size() - 1);
        assertThat(testPaymentChannelEnt.getRecordUniqueIdentifier()).isEqualTo(DEFAULT_RECORD_UNIQUE_IDENTIFIER);
        assertThat(testPaymentChannelEnt.getChannelId()).isEqualTo(UPDATED_CHANNEL_ID);
        assertThat(testPaymentChannelEnt.getChannelCode()).isEqualTo(DEFAULT_CHANNEL_CODE);
        assertThat(testPaymentChannelEnt.getChannelName()).isEqualTo(UPDATED_CHANNEL_NAME);
        assertThat(testPaymentChannelEnt.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testPaymentChannelEnt.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testPaymentChannelEnt.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testPaymentChannelEnt.getFreeField3()).isEqualTo(DEFAULT_FREE_FIELD_3);
        assertThat(testPaymentChannelEnt.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void fullUpdatePaymentChannelEntWithPatch() throws Exception {
        // Initialize the database
        paymentChannelEntRepository.saveAndFlush(paymentChannelEnt);

        int databaseSizeBeforeUpdate = paymentChannelEntRepository.findAll().size();

        // Update the paymentChannelEnt using partial update
        PaymentChannelEnt partialUpdatedPaymentChannelEnt = new PaymentChannelEnt();
        partialUpdatedPaymentChannelEnt.setId(paymentChannelEnt.getId());

        partialUpdatedPaymentChannelEnt
            .recordUniqueIdentifier(UPDATED_RECORD_UNIQUE_IDENTIFIER)
            .channelId(UPDATED_CHANNEL_ID)
            .channelCode(UPDATED_CHANNEL_CODE)
            .channelName(UPDATED_CHANNEL_NAME)
            .status(UPDATED_STATUS)
            .freeField1(UPDATED_FREE_FIELD_1)
            .freeField2(UPDATED_FREE_FIELD_2)
            .freeField3(UPDATED_FREE_FIELD_3)
            .isDeleted(UPDATED_IS_DELETED);

        restPaymentChannelEntMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPaymentChannelEnt.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(partialUpdatedPaymentChannelEnt))
            )
            .andExpect(status().isOk());

        // Validate the PaymentChannelEnt in the database
        List<PaymentChannelEnt> paymentChannelEntList = paymentChannelEntRepository.findAll();
        assertThat(paymentChannelEntList).hasSize(databaseSizeBeforeUpdate);
        PaymentChannelEnt testPaymentChannelEnt = paymentChannelEntList.get(paymentChannelEntList.size() - 1);
        assertThat(testPaymentChannelEnt.getRecordUniqueIdentifier()).isEqualTo(UPDATED_RECORD_UNIQUE_IDENTIFIER);
        assertThat(testPaymentChannelEnt.getChannelId()).isEqualTo(UPDATED_CHANNEL_ID);
        assertThat(testPaymentChannelEnt.getChannelCode()).isEqualTo(UPDATED_CHANNEL_CODE);
        assertThat(testPaymentChannelEnt.getChannelName()).isEqualTo(UPDATED_CHANNEL_NAME);
        assertThat(testPaymentChannelEnt.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testPaymentChannelEnt.getFreeField1()).isEqualTo(UPDATED_FREE_FIELD_1);
        assertThat(testPaymentChannelEnt.getFreeField2()).isEqualTo(UPDATED_FREE_FIELD_2);
        assertThat(testPaymentChannelEnt.getFreeField3()).isEqualTo(UPDATED_FREE_FIELD_3);
        assertThat(testPaymentChannelEnt.getIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
    }

    @Test
    @Transactional
    void patchNonExistingPaymentChannelEnt() throws Exception {
        int databaseSizeBeforeUpdate = paymentChannelEntRepository.findAll().size();
        paymentChannelEnt.setId(count.incrementAndGet());

        // Create the PaymentChannelEnt
        PaymentChannelEntDTO paymentChannelEntDTO = paymentChannelEntMapper.toDto(paymentChannelEnt);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPaymentChannelEntMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, paymentChannelEntDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentChannelEntDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentChannelEnt in the database
        List<PaymentChannelEnt> paymentChannelEntList = paymentChannelEntRepository.findAll();
        assertThat(paymentChannelEntList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPaymentChannelEnt() throws Exception {
        int databaseSizeBeforeUpdate = paymentChannelEntRepository.findAll().size();
        paymentChannelEnt.setId(count.incrementAndGet());

        // Create the PaymentChannelEnt
        PaymentChannelEntDTO paymentChannelEntDTO = paymentChannelEntMapper.toDto(paymentChannelEnt);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentChannelEntMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, count.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentChannelEntDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the PaymentChannelEnt in the database
        List<PaymentChannelEnt> paymentChannelEntList = paymentChannelEntRepository.findAll();
        assertThat(paymentChannelEntList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPaymentChannelEnt() throws Exception {
        int databaseSizeBeforeUpdate = paymentChannelEntRepository.findAll().size();
        paymentChannelEnt.setId(count.incrementAndGet());

        // Create the PaymentChannelEnt
        PaymentChannelEntDTO paymentChannelEntDTO = paymentChannelEntMapper.toDto(paymentChannelEnt);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPaymentChannelEntMockMvc
            .perform(
                patch(ENTITY_API_URL)
                    .contentType("application/merge-patch+json")
                    .content(TestUtil.convertObjectToJsonBytes(paymentChannelEntDTO))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the PaymentChannelEnt in the database
        List<PaymentChannelEnt> paymentChannelEntList = paymentChannelEntRepository.findAll();
        assertThat(paymentChannelEntList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePaymentChannelEnt() throws Exception {
        // Initialize the database
        paymentChannelEntRepository.saveAndFlush(paymentChannelEnt);

        int databaseSizeBeforeDelete = paymentChannelEntRepository.findAll().size();

        // Delete the paymentChannelEnt
        restPaymentChannelEntMockMvc
            .perform(delete(ENTITY_API_URL_ID, paymentChannelEnt.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PaymentChannelEnt> paymentChannelEntList = paymentChannelEntRepository.findAll();
        assertThat(paymentChannelEntList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
