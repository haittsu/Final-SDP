import java.math.BigDecimal;
public class ChecksB {
    public static void main (String[] args){
        checkDecorator();
        checkObserver();
        checkLivePrice();
        checkAdapter();
        System.out.println("all B checks passed.");
    }
    private static ProductInfo laptopInfo(){
        return new ProductInfo("LAP-1","Laptop",new BigDecimal("1000.00"),5);
    }
    private static void checkDecorator(){
        Product laptop = new Laptop.Builder(laptopInfo()).ram(16).build();
        Product wrapped = new GiftWrapDecorator(laptop);
        checkMoney(wrapped.getPrice(),"1150.00");
        checkMoney(laptop.getPrice(),"1100.00");
        if(!wrapped.getId().equals(laptop.getId())){
            throw new AssertionError("Gift wrap changed the product ID");
        }
    }
    private static void checkObserver() {
        ProductInfo info = laptopInfo();
        int[] updates = {0};
        ShopObserver observer = message -> updates[0]++;
        info.addObserver(observer);
        info.setPrice(new BigDecimal("1200.00"));
        info.setStock(0);
        if (updates[0] != 2) {
            throw new AssertionError("Expected one price event and one stock event");
        }

        info.removeObserver(observer);
        info.setStock(5);

        if (updates[0] != 2) {
            throw new AssertionError("Removed observer still receives updates");
        }
    }

    private static void checkLivePrice() {
        ProductInfo info = laptopInfo();
        Product laptop = new Laptop.Builder(info).ram(16).build();
        ShoppingCart cart = new ShoppingCart();
        cart.add(new GiftWrapDecorator(laptop));
        cart.setDiscountStrategy(new StudentDiscount());

        checkMoney(cart.getTotal(), "1035.00");
        info.setPrice(new BigDecimal("1200.00"));
        checkMoney(cart.getTotal(), "1215.00");
    }

    private static void checkAdapter() {
        OldPaymentSystem oldSystem = new OldPaymentSystem();
        PaymentProcessor payment = new PaymentAdapter(oldSystem);

        payment.pay(new BigDecimal("1035.00"));
        if (oldSystem.getLastPaidCents() != 103500) {
            throw new AssertionError("Expected 103500 cents");
        }

        for (String invalid : new String[] {"0.00", "-1.00"}) {
            try {
                payment.pay(new BigDecimal(invalid));
                throw new AssertionError("Accepted invalid amount " + invalid);
            } catch (IllegalArgumentException expected) {
                if (oldSystem.getLastPaidCents() != 103500) {
                    throw new AssertionError("Invalid amount changed the last payment");
                }
            }
        }
    }

    private static void checkMoney(BigDecimal actual, String expected) {
        if (actual.compareTo(new BigDecimal(expected)) != 0) {
            throw new AssertionError("Expected " + expected + ", got " + actual);
        }
    }
}
