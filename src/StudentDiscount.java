import java.math.BigDecimal;
import java.math.RoundingMode;

public class StudentDiscount implements DiscountStrategy {
    @Override
    public BigDecimal apply(BigDecimal subtotal) {
        return subtotal.multiply(new BigDecimal("0.90"))
                .setScale(2, RoundingMode.HALF_UP);
    }
}
