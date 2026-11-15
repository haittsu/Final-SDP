import java.math.BigDecimal;

public class ProductInfo {
    private final String id;
    private final String name;
    private BigDecimal price;
    private int stock;

    public ProductInfo(String id, String name, BigDecimal price, int stock) {
        if (id == null || id.isBlank() || name == null || name.isBlank()) {
            throw new IllegalArgumentException("Product ID and name must not be blank.");
        }
        this.id = id;
        this.name = name;
        setPrice(price);
        setStock(stock);
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public int getStock() {
        return stock;
    }

    public void setPrice(BigDecimal price) {
        if (price == null || price.signum() < 0) {
            throw new IllegalArgumentException("Price must not be negative.");
        }
        this.price = price;
    }

    public void setStock(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("Stock must not be negative.");
        }
        this.stock = stock;
    }
}
