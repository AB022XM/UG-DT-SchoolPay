package ug.co.absa.schoolpayme.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import ug.co.absa.schoolpayme.domain.enumeration.PaymentChannel;
import ug.co.absa.schoolpayme.domain.enumeration.RecordStatus;

/**
 * A PaymentEnt.
 */
@Entity
@Table(name = "payment_ent")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class PaymentEnt implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotNull
    @Type(type = "uuid-char")
    @Column(name = "record_unique_identifier", length = 36, nullable = false, unique = true)
    private UUID recordUniqueIdentifier;

    @NotNull
    @Column(name = "payment_id", nullable = false)
    private Integer paymentId;

    @NotNull
    @Column(name = "payment_code", nullable = false)
    private Integer paymentCode;

    @Column(name = "customer_id")
    private String customerId;

    @Column(name = "fee_amount")
    private Integer feeAmount;

    @Enumerated(EnumType.STRING)
    @Column(name = "is_amount_fixed")
    private RecordStatus isAmountFixed;

    @Size(min = 3, max = 200)
    @Column(name = "fee_description", length = 200)
    private String feeDescription;

    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "fixed_amount", length = 50, nullable = false)
    private String fixedAmount;

    @NotNull
    @Size(min = 3, max = 50)
    @Column(name = "payment_name", length = 50, nullable = false)
    private String paymentName;

    @Column(name = "outstanding_amount")
    private Integer outstandingAmount;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "payment_channel", nullable = false)
    private PaymentChannel paymentChannel;

    @Column(name = "free_field_1")
    private String freeField1;

    @Column(name = "free_field_2")
    private String freeField2;

    @Column(name = "free_field_3")
    private String freeField3;

    @OneToMany(mappedBy = "paymentEnt")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "paymentEnt" }, allowSetters = true)
    private Set<AssociatedFees> associatedFees = new HashSet<>();

    @ManyToOne
    @JsonIgnoreProperties(value = { "paymentEnts", "paybills" }, allowSetters = true)
    private Biller biller;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public PaymentEnt id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getRecordUniqueIdentifier() {
        return this.recordUniqueIdentifier;
    }

    public PaymentEnt recordUniqueIdentifier(UUID recordUniqueIdentifier) {
        this.setRecordUniqueIdentifier(recordUniqueIdentifier);
        return this;
    }

    public void setRecordUniqueIdentifier(UUID recordUniqueIdentifier) {
        this.recordUniqueIdentifier = recordUniqueIdentifier;
    }

    public Integer getPaymentId() {
        return this.paymentId;
    }

    public PaymentEnt paymentId(Integer paymentId) {
        this.setPaymentId(paymentId);
        return this;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }

    public Integer getPaymentCode() {
        return this.paymentCode;
    }

    public PaymentEnt paymentCode(Integer paymentCode) {
        this.setPaymentCode(paymentCode);
        return this;
    }

    public void setPaymentCode(Integer paymentCode) {
        this.paymentCode = paymentCode;
    }

    public String getCustomerId() {
        return this.customerId;
    }

    public PaymentEnt customerId(String customerId) {
        this.setCustomerId(customerId);
        return this;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Integer getFeeAmount() {
        return this.feeAmount;
    }

    public PaymentEnt feeAmount(Integer feeAmount) {
        this.setFeeAmount(feeAmount);
        return this;
    }

    public void setFeeAmount(Integer feeAmount) {
        this.feeAmount = feeAmount;
    }

    public RecordStatus getIsAmountFixed() {
        return this.isAmountFixed;
    }

    public PaymentEnt isAmountFixed(RecordStatus isAmountFixed) {
        this.setIsAmountFixed(isAmountFixed);
        return this;
    }

    public void setIsAmountFixed(RecordStatus isAmountFixed) {
        this.isAmountFixed = isAmountFixed;
    }

    public String getFeeDescription() {
        return this.feeDescription;
    }

    public PaymentEnt feeDescription(String feeDescription) {
        this.setFeeDescription(feeDescription);
        return this;
    }

    public void setFeeDescription(String feeDescription) {
        this.feeDescription = feeDescription;
    }

    public String getFixedAmount() {
        return this.fixedAmount;
    }

    public PaymentEnt fixedAmount(String fixedAmount) {
        this.setFixedAmount(fixedAmount);
        return this;
    }

    public void setFixedAmount(String fixedAmount) {
        this.fixedAmount = fixedAmount;
    }

    public String getPaymentName() {
        return this.paymentName;
    }

    public PaymentEnt paymentName(String paymentName) {
        this.setPaymentName(paymentName);
        return this;
    }

    public void setPaymentName(String paymentName) {
        this.paymentName = paymentName;
    }

    public Integer getOutstandingAmount() {
        return this.outstandingAmount;
    }

    public PaymentEnt outstandingAmount(Integer outstandingAmount) {
        this.setOutstandingAmount(outstandingAmount);
        return this;
    }

    public void setOutstandingAmount(Integer outstandingAmount) {
        this.outstandingAmount = outstandingAmount;
    }

    public PaymentChannel getPaymentChannel() {
        return this.paymentChannel;
    }

    public PaymentEnt paymentChannel(PaymentChannel paymentChannel) {
        this.setPaymentChannel(paymentChannel);
        return this;
    }

    public void setPaymentChannel(PaymentChannel paymentChannel) {
        this.paymentChannel = paymentChannel;
    }

    public String getFreeField1() {
        return this.freeField1;
    }

    public PaymentEnt freeField1(String freeField1) {
        this.setFreeField1(freeField1);
        return this;
    }

    public void setFreeField1(String freeField1) {
        this.freeField1 = freeField1;
    }

    public String getFreeField2() {
        return this.freeField2;
    }

    public PaymentEnt freeField2(String freeField2) {
        this.setFreeField2(freeField2);
        return this;
    }

    public void setFreeField2(String freeField2) {
        this.freeField2 = freeField2;
    }

    public String getFreeField3() {
        return this.freeField3;
    }

    public PaymentEnt freeField3(String freeField3) {
        this.setFreeField3(freeField3);
        return this;
    }

    public void setFreeField3(String freeField3) {
        this.freeField3 = freeField3;
    }

    public Set<AssociatedFees> getAssociatedFees() {
        return this.associatedFees;
    }

    public void setAssociatedFees(Set<AssociatedFees> associatedFees) {
        if (this.associatedFees != null) {
            this.associatedFees.forEach(i -> i.setPaymentEnt(null));
        }
        if (associatedFees != null) {
            associatedFees.forEach(i -> i.setPaymentEnt(this));
        }
        this.associatedFees = associatedFees;
    }

    public PaymentEnt associatedFees(Set<AssociatedFees> associatedFees) {
        this.setAssociatedFees(associatedFees);
        return this;
    }

    public PaymentEnt addAssociatedFees(AssociatedFees associatedFees) {
        this.associatedFees.add(associatedFees);
        associatedFees.setPaymentEnt(this);
        return this;
    }

    public PaymentEnt removeAssociatedFees(AssociatedFees associatedFees) {
        this.associatedFees.remove(associatedFees);
        associatedFees.setPaymentEnt(null);
        return this;
    }

    public Biller getBiller() {
        return this.biller;
    }

    public void setBiller(Biller biller) {
        this.biller = biller;
    }

    public PaymentEnt biller(Biller biller) {
        this.setBiller(biller);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof PaymentEnt)) {
            return false;
        }
        return id != null && id.equals(((PaymentEnt) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PaymentEnt{" +
            "id=" + getId() +
            ", recordUniqueIdentifier='" + getRecordUniqueIdentifier() + "'" +
            ", paymentId=" + getPaymentId() +
            ", paymentCode=" + getPaymentCode() +
            ", customerId='" + getCustomerId() + "'" +
            ", feeAmount=" + getFeeAmount() +
            ", isAmountFixed='" + getIsAmountFixed() + "'" +
            ", feeDescription='" + getFeeDescription() + "'" +
            ", fixedAmount='" + getFixedAmount() + "'" +
            ", paymentName='" + getPaymentName() + "'" +
            ", outstandingAmount=" + getOutstandingAmount() +
            ", paymentChannel='" + getPaymentChannel() + "'" +
            ", freeField1='" + getFreeField1() + "'" +
            ", freeField2='" + getFreeField2() + "'" +
            ", freeField3='" + getFreeField3() + "'" +
            "}";
    }
}
