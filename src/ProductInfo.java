import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ProductInfo {
    private final String id;
    private final String name;
    private BigDecimal price;
    private int stock;
    private final List<ShopObserver> observers=new ArrayList<>();

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
        notifyObservers(name+" price:"+this.price);
    }

    public void setStock(int stock) {
        if (stock < 0) {
            throw new IllegalArgumentException("Stock must not be negative.");
        }
        this.stock = stock;
        notifyObservers(name+" stock:"+this.stock);

    }
    public void addObserver(ShopObserver observer){
        if (observer==null){
            throw new IllegalArgumentException("Observer is required");
        }
        observers.add(observer);
    }
    public void removeObserver(ShopObserver observer){
        observers.remove(observer);
    }
    private void notifyObservers(String message){
        for (ShopObserver observer : List.copyOf(observers)){
            observer.onUpdate(message);
        }
    }
}
