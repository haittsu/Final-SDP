import java.math.BigDecimal;

public class Main {
    public static void main(String[] args) {
        ProductInfo info = new ProductInfo("LAP-1", "Campus Laptop", new BigDecimal("1000.00"), 5);
        ShoppingCart cart = new ShoppingCart();
        PaymentProcessor payment = new PaymentAdapter(new OldPaymentSystem());
        ShopFacade shop = new ShopFacade(info, cart, payment);

        ShopObserver observer = new CartObserver(cart, info);
        info.addObserver(observer);

        System.out.println("=== Configurable Laptop Store ===");
        Laptop laptop = new Laptop.Builder(info).ram(16).color("Silver").build();
        System.out.println("Configured laptop: " + laptop.getDescription());
        System.out.println("Configured price: USD " + laptop.getPrice());
        Product wrappedLaptop = new GiftWrapDecorator(laptop);
        shop.addProduct(wrappedLaptop);

        System.out.println("\n=== Cart without discount ===");
        shop.showCart();
        shop.setDiscount(new StudentDiscount());
        System.out.println("\n=== Cart with student discount ===");
        shop.showCart();

        System.out.println("\n=== Live price update ===");
        info.setPrice(new BigDecimal("1200.00"));
        shop.showCart();
        info.setPrice(new BigDecimal("1000.00"));

        System.out.println("\n=== Stock validation ===");
        info.setStock(0);
        try {
            shop.checkout();
        } catch (IllegalStateException expected) {
            System.out.println("Checkout blocked: " + expected.getMessage());
        }

        info.setStock(5);
        System.out.println("\n=== Successful checkout ===");
        shop.checkout();
        shop.showCart();
        System.out.println("Remaining stock: " + info.getStock());
        info.removeObserver(observer);
    }

}
