
package Dev_J_130;

public class Store {
    
    private int productCount = MyRandom.getNumberOfProduct();

    public int getProductCount() {
        return productCount;
    }
        
    public void putToStore(int deliveredProduct){
        
        productCount = productCount + deliveredProduct;
        System.out.println(MyRandom.timeNow() + "Товар привез и разгрузил " + Thread.currentThread().getName() + ". Сейчас товара на складе " + productCount + " шт.");
        
    }
    
    public void getFromStore(int shippedProduct){
 
        productCount = productCount - shippedProduct;
        System.out.println(MyRandom.timeNow() + "Товар в наличии. Товар забирает " + Thread.currentThread().getName() + ". Сейчас товара на складе " + productCount + " шт.");
        
    }
}
