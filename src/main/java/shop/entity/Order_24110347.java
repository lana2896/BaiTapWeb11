package shop.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Orders")
public class Order_24110347 implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String PAYMENT_COD = "COD";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "OrderId")
    private Integer orderId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Username", nullable = false)
    private User_24110347 user;

    @Column(name = "ReceiverName", length = 100, nullable = false)
    private String receiverName;

    @Column(name = "Phone", length = 15, nullable = false)
    private String phone;

    @Column(name = "Address", length = 255, nullable = false)
    private String address;

    @Column(name = "Note", length = 500)
    private String note;

    @Column(name = "PaymentMethod", length = 20, nullable = false)
    private String paymentMethod;

    @Column(name = "Paid", nullable = false)
    private Boolean paid;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PaidDate")
    private Date paidDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "Status", length = 20, nullable = false)
    private OrderStatus_24110347 status;

    @Column(name = "TotalAmount", precision = 18, scale = 0, nullable = false)
    private BigDecimal totalAmount;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CreatedDate", nullable = false)
    private Date createdDate;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "UpdatedDate")
    private Date updatedDate;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("orderDetailId ASC")
    private List<OrderDetail_24110347> details = new ArrayList<>();

    public Order_24110347() {
    }

    public void addDetail(OrderDetail_24110347 detail) {
        detail.setOrder(this);
        details.add(detail);
    }

    public Integer getOrderId() { return orderId; }
    public void setOrderId(Integer orderId) { this.orderId = orderId; }

    public User_24110347 getUser() { return user; }
    public void setUser(User_24110347 user) { this.user = user; }

    public String getReceiverName() { return receiverName; }
    public void setReceiverName(String receiverName) { this.receiverName = receiverName; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getPaymentMethodLabel() {
        return PAYMENT_COD.equals(paymentMethod) ? "Thanh toán khi nhận hàng (COD)" : paymentMethod;
    }

    public boolean isPaid() { return paid != null && paid; }
    public void setPaid(Boolean paid) { this.paid = paid; }

    public Date getPaidDate() { return paidDate; }
    public void setPaidDate(Date paidDate) { this.paidDate = paidDate; }

    public OrderStatus_24110347 getStatus() { return status; }
    public void setStatus(OrderStatus_24110347 status) { this.status = status; }

    public boolean isCancellableByUser() { return status == OrderStatus_24110347.PENDING; }

    public BigDecimal getTotalAmount() { return totalAmount == null ? BigDecimal.ZERO : totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public Date getCreatedDate() { return createdDate; }
    public void setCreatedDate(Date createdDate) { this.createdDate = createdDate; }

    public Date getUpdatedDate() { return updatedDate; }
    public void setUpdatedDate(Date updatedDate) { this.updatedDate = updatedDate; }

    public List<OrderDetail_24110347> getDetails() { return details; }
    public void setDetails(List<OrderDetail_24110347> details) { this.details = details; }
}
