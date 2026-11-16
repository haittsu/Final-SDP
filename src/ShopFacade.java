import java.math.BigDecimal;

public class ShopFacade {
    private final ProductInfo info;
    private final ShoppingCart cart;
    private final PaymentProcessor payment;

    public ShopFacade(ProductInfo info, ShoppingCart cart, PaymentProcessor payment) {
        if (info == null || cart == null || payment == null) {
            throw new IllegalArgumentException("Product information, cart and payment are required.");
        }
        this.info = info;
        this.cart = cart;
        this.payment = payment;
    }

    public void addProduct(Product product) {
        if (product == null || !info.getId().equals(product.getId())) {
            throw new IllegalArgumentException("Product must belong to this store.");
        }
        cart.add(product);
    }

    public void setDiscount(DiscountStrategy strategy) {
        cart.setDiscountStrategy(strategy);
    }

    public void showCart() {
        cart.printCart();
    }

    public void checkout() {
        if (cart.isEmpty()) {
            throw new IllegalStateException("The cart is empty.");
        }
        int count = cart.size();
        if (count > info.getStock()) {
            throw new IllegalStateException("Not enough stock for this purchase.");
        }
        BigDecimal total = cart.getTotal();
        if (total.signum() <= 0) {
            throw new IllegalStateException("The total must be greater than zero.");
        }

        payment.pay(total);
        cart.clear();
        info.setStock(info.getStock() - count);
        System.out.println("Purchase completed. Total: USD " + total);
    }
}
