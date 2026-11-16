import java.math.BigDecimal;
public class PaymentAdapter implements PaymentProcessor{
    private final OldPaymentSystem oldSystem;
    public PaymentAdapter(OldPaymentSystem oldSystem){
        if(oldSystem==null){
            throw new IllegalArgumentException("Payment system is required");
        }
        this.oldSystem = oldSystem;
    }
    @Override
    public void pay(BigDecimal amount){
        if(amount==null||amount.signum()<=0){
            throw new IllegalArgumentException("Amount must be positive");
        }
        long cents;
        try{
            cents=amount.movePointRight(2).longValueExact();
        }catch(ArithmeticException e) {
            throw new IllegalArgumentException("Amount must fit in whole cents", e);
        }
        oldSystem.payInCents(cents);
    }
}