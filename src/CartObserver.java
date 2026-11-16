public class CartObserver implements ShopObserver{
    private final ShoppingCart cart;
    private final ProductInfo info;
    public CartObserver(ShoppingCart cart,ProductInfo info){
        if (cart==null|| info==null){
            throw new IllegalArgumentException("Cart and product info are required");
        }
        this.cart=cart;
        this.info=info;
    }
    @Override
    public void onUpdate(String message){
        System.out.println(message);
        System.out.println("Stock:"+info.getStock());
        System.out.println("Cart total:"+cart.getTotal());
    }
}
