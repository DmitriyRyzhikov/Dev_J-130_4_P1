
package Dev_J_130;

import java.util.concurrent.TimeUnit;

public class Provider extends Thread{
    
    private final Store store;
    
    public Provider(ThreadGroup group, String name, Store store){
        super(group, name);
        this.store = store;
    }
/*
    Экземпляры класса Provider - поставщики, работают какждый в своем потоке. Продолжительность действия каждого
    поставщика определяется бесконечным циклом, условием выхода из которого является появление на складе товара 
    в количестве, больше чем 20 шт. Для окончательного завершения работы поставщиков, это условие должно 
    выполниться для каждого из них в момент времени, совпадающим с началом очередной итерации. 
    */
    @Override
    public void run() {
    
            while(true) {
                try {
                    if(store.getProductCount() > 20) {
                        System.out.println(Randomer.timeNow() + this.getName() + " достиг условия завершения работы. Good by!");
                        break; }
                    
                    int deliveryTime = Randomer.getTimeOut();
                    System.out.println(Randomer.timeNow() + this.getName() + " планирует доставить товар на склад. Доставка займет " 
                                       + deliveryTime + " единиц времени.");
                    
                    TimeUnit.SECONDS.sleep(deliveryTime);
                    int deliveredProduct = Randomer.getNumberOfProduct();
                    System.out.println(Randomer.timeNow() + this.getName() + " привез на склад товар в кол-ве " 
                                       + deliveredProduct + " единиц. Разгружается.");
                    
                    store.putToStore(deliveredProduct);
                    
                    int newTradeTime = Randomer.getTimeOut();
                    System.out.println(Randomer.timeNow() + this.getName() + " отправился за новой партией товара. Следующая доставка через " 
                                       + newTradeTime + " единиц времени.");               
                    TimeUnit.SECONDS.sleep(newTradeTime);
                    
                } catch (InterruptedException ex) { }
            }
    }
}
