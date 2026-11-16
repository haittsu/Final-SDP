import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private final List<Product> items = new ArrayList<>();
    private DiscountStrategy discountStrategy = new NoDiscount();

    public void add(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product is required.");
        }
        items.add(product);
    }

    public void setDiscountStrategy(DiscountStrategy discountStrategy) {
        if (discountStrategy == null) {
            throw new IllegalArgumentException("Discount strategy is required.");
        }
        this.discountStrategy = discountStrategy;
    }

    public BigDecimal getSubtotal() {
        BigDecimal subtotal = new BigDecimal("0.00");
        for (Product product : items) {
            subtotal = subtotal.add(product.getPrice());
        }
        return subtotal;
    }

    public BigDecimal getTotal() {
        return discountStrategy.apply(getSubtotal());
    }

    public int size() {
        return items.size();
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public void clear() {
        items.clear();
    }

    public void printCart() {
        if (isEmpty()) {
            System.out.println("The cart is empty.");
            return;
        }
        System.out.println("Shopping cart:");
        for (Product product : items) {
            System.out.println(product.getDescription() + " - USD " + product.getPrice());
        }
        BigDecimal subtotal = getSubtotal();
        BigDecimal total = discountStrategy.apply(subtotal);
        System.out.println("Subtotal: USD " + subtotal);
        System.out.println("Discount: USD " + subtotal.subtract(total));
        System.out.println("Total: USD " + total);
    }
}
