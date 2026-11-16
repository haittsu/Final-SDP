public abstract class ProductDecorator implements Product{
    protected final Product product;
    public ProductDecorator(Product product){
        this.product=product;
    }
    @Override
    public String getId(){
        return product.getId();
    }
}