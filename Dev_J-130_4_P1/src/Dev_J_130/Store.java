
package Dev_J_130;

public class Store {
    
    private int productCount;
    
    public Store(int initialProductCount){
        this.productCount = initialProductCount;
    }

    public int getProductCount() {
        return productCount;
    }
        
    public void putToStore(int deliveredProduct){
        
        productCount = productCount + deliveredProduct;
        System.out.println(Randomer.timeNow() + "Склад информирует: товар привез и разгрузил " 
                + Thread.currentThread().getName() + ". Сейчас товара на складе " + productCount + " шт.");        
    }
    
    public void getFromStore(int shippedProduct){
 
        productCount = productCount - shippedProduct;
        System.out.println(Randomer.timeNow() + "Склад информирует: товар в наличии. Товар получил " 
                + Thread.currentThread().getName() + ". Сейчас товара на складе " + productCount + " шт.");        
    }
}
