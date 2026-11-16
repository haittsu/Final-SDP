import java.math.BigDecimal;

public class ChecksA {
    public static void main(String[] args) {
        checkBuilder();
        checkStrategies();
        checkSuccessfulCheckout();
        checkStockValidation();
        System.out.println("All A checks passed.");
    }

    private static ProductInfo productInfo(int stock) {
        return new ProductInfo("LAP-1", "Campus Laptop", new BigDecimal("1000.00"), stock);
    }

    private static void checkBuilder() {
        ProductInfo info = productInfo(5);
        Laptop basic = new Laptop.Builder(info).build();
        check(basic.getRam() == 8 && "Black".equals(basic.getColor()), "Builder defaults");
        equal(basic.getPrice(), "1000.00");
        Laptop.Builder builder = new Laptop.Builder(info).ram(16).color("Silver");
        Laptop custom = builder.build();
        equal(custom.getPrice(), "1100.00");
        builder.ram(8).color("Black").build();
        check(custom.getRam() == 16 && "Silver".equals(custom.getColor()), "Built configuration must not change");
        boolean rejected = false;
        try {
            new Laptop.Builder(info).ram(12).build();
        } catch (IllegalArgumentException expected) {
            rejected = true;
        }
        check(rejected, "Unsupported RAM must be rejected");
        System.out.println("PASS: Builder defaults, configuration and validation");
    }

    private static void checkStrategies() {
        BigDecimal subtotal = new BigDecimal("1150.00");
        equal(new NoDiscount().apply(subtotal), "1150.00");
        equal(new StudentDiscount().apply(subtotal), "1035.00");
        equal(new StudentDiscount().apply(new BigDecimal("99.95")), "89.96");
        ProductInfo info = productInfo(5);
        ShoppingCart cart = new ShoppingCart();
        cart.add(new GiftWrapDecorator(new Laptop.Builder(info).ram(16).build()));
        equal(cart.getTotal(), "1150.00");
        cart.setDiscountStrategy(new StudentDiscount());
        equal(cart.getTotal(), "1035.00");
        info.setPrice(new BigDecimal("1200.00"));
        equal(cart.getTotal(), "1215.00");
        cart.setDiscountStrategy(new NoDiscount());
        equal(cart.getTotal(), "1350.00");
        System.out.println("PASS: Strategy switching, live prices and rounding");
    }

    private static void checkSuccessfulCheckout() {
        ProductInfo info = productInfo(5);
        ShoppingCart cart = new ShoppingCart();
        BigDecimal[] paid = {BigDecimal.ZERO};
        int[] calls = {0};
        ShopFacade shop = new ShopFacade(info, cart, amount -> {
            paid[0] = amount;
            calls[0]++;
        });
        shop.addProduct(new GiftWrapDecorator(new Laptop.Builder(info).ram(16).color("Silver").build()));
        shop.setDiscount(new StudentDiscount());
        shop.checkout();
        equal(paid[0], "1035.00");
        check(calls[0] == 1 && cart.isEmpty() && info.getStock() == 4, "Successful checkout state");
        boolean rejected = false;
        try {
            shop.checkout();
        } catch (IllegalStateException expected) {
            rejected = true;
        }
        check(rejected && calls[0] == 1, "Empty checkout must not pay again");
        System.out.println("PASS: Facade successful purchase and empty cart guard");
    }

    private static void checkStockValidation() {
        ProductInfo info = productInfo(0);
        ShoppingCart cart = new ShoppingCart();
        int[] calls = {0};
        ShopFacade shop = new ShopFacade(info, cart, amount -> calls[0]++);
        shop.addProduct(new Laptop.Builder(info).build());
        expectStockFailure(shop);
        check(calls[0] == 0 && cart.size() == 1 && info.getStock() == 0, "Shortage must preserve state");
        info.setStock(1);
        shop.addProduct(new Laptop.Builder(info).ram(16).build());
        expectStockFailure(shop);
        check(calls[0] == 0 && cart.size() == 2 && info.getStock() == 1, "All configurations share stock");
        System.out.println("PASS: Facade stock guards and no payment on failure");
    }

    private static void expectStockFailure(ShopFacade shop) {
        boolean rejected = false;
        try {
            shop.checkout();
        } catch (IllegalStateException expected) {
            rejected = true;
        }
        check(rejected, "Checkout must reject insufficient stock");
    }

    private static void equal(BigDecimal actual, String expected) {
        check(actual.compareTo(new BigDecimal(expected)) == 0, "Expected " + expected + ", got " + actual);
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }
}
