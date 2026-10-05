package shop.entity;

public enum OrderStatus_24110347 {
    PENDING("Chờ xác nhận", "warning"),
    CONFIRMED("Đã xác nhận", "info"),
    SHIPPING("Đang giao hàng", "primary"),
    COMPLETED("Đã giao - Đã thu tiền", "success"),
    CANCELLED("Đã hủy", "secondary");

    private final String label;
    private final String badge;

    OrderStatus_24110347(String label, String badge) {
        this.label = label;
        this.badge = badge;
    }

    public String getName() { return name(); }
    public String getLabel() { return label; }
    public String getBadge() { return badge; }

}
