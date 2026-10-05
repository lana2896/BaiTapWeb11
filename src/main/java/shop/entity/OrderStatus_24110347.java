package shop.entity;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

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

    public Set<OrderStatus_24110347> nextStatuses() {
        switch (this) {
            case PENDING:
                return EnumSet.of(CONFIRMED, CANCELLED);
            case CONFIRMED:
                return EnumSet.of(SHIPPING, CANCELLED);
            case SHIPPING:
                return EnumSet.of(COMPLETED, CANCELLED);
            default:
                return Collections.emptySet();
        }
    }

    public List<OrderStatus_24110347> getNextStatusList() {
        return List.copyOf(nextStatuses());
    }

    public boolean canChangeTo(OrderStatus_24110347 target) {
        return target != null && nextStatuses().contains(target);
    }

    public boolean isFinished() {
        return this == COMPLETED || this == CANCELLED;
    }

    public static OrderStatus_24110347 parse(String raw) {
        if (raw == null) {
            return null;
        }
        try {
            return OrderStatus_24110347.valueOf(raw.trim().toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}
