
package Dev_J_130;

import java.util.concurrent.TimeUnit;

public class Consumer extends Thread {
    
    private final Store store;
    
    public Consumer(ThreadGroup group, String name, Store store) {
        super(group, name);
        this.store = store;
    }

    /*
    Экземпляры класса Consumer - потребители, работают какждый в своем потоке. Продолжительность действия каждого
    потребителя определяется бесконечным циклом, условием выхода из которого является появление на складе товара 
    в количестве, больше чем 20 шт. Для окончательного завершения работы потребителей, это условие должно 
    выполниться для каждого из них в момент времени, совпадающим с началом очередной итерации. 
    */
    
    @Override
    public void run() {
        while(true){

            if(store.getProductCount() > 20) {
               System.out.println(Randomer.timeNow() + this.getName() + " достиг условия завершения работы. Good by!");
               break; }
        try {
            int shippedProduct = Randomer.getNumberOfProduct();
            System.out.println(Randomer.timeNow() + this.getName() + " прибыл на склад. Планирует забрать " + shippedProduct + " единиц товара. Проверяется наличие товара...");
            
            while(shippedProduct > store.getProductCount()){
                  int waitTime = Randomer.getTimeOut();
                  System.out.println(Randomer.timeNow() + this.getName() + " узнал, что товара нет в наличии. Будет ждать. Ожидание займет " + waitTime + " единиц времени.");
                  TimeUnit.SECONDS.sleep(waitTime);
                  System.out.println(Randomer.timeNow() + this.getName() + " закончил ждать. Вновь проверяет наличие товара. Все еще хочет забрать " + shippedProduct + " единиц товара.");
            }
            store.getFromStore(shippedProduct);
            int loadTime = Randomer.getTimeOut();
            System.out.println(Randomer.timeNow() + this.getName() + " загружается. Погрузка займет " + loadTime + " единиц времени.");
            TimeUnit.SECONDS.sleep(loadTime);          
            System.out.println(Randomer.timeNow() + this.getName() + " загрузился. Увозит со склада " + shippedProduct + " единиц товара. Покинул склад.");
            
            int newTradeTime = Randomer.getTimeOut();
            System.out.println(Randomer.timeNow() + this.getName() + " скоро вернется опять. Следующая погрузка планируется через " + newTradeTime + " единиц времени.");   
            TimeUnit.SECONDS.sleep(newTradeTime);
        } catch (InterruptedException ex) { break;}     
      }
   } 
}
