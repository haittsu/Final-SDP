import java.math.BigDecimal;
public class GiftWrapDecorator extends ProductDecorator {
    public GiftWrapDecorator(Product product){
        super(product);
    }
    @Override
    public String getDescription(){
        return product.getDescription()+"+ gift wrap";
    }
    @Override
    public BigDecimal getPrice() {
        return product.getPrice().add(new BigDecimal("50.00"));
    }
}
