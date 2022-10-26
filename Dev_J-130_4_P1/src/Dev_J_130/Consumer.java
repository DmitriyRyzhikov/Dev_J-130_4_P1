
package Dev_J_130;

public class Consumer extends Thread {
    
    private final Store store;
    
    public Consumer(String name, Store store) {
        super(name);
        this.store = store;
    }

    @Override
    public void run() {
        while(true){
            if(store.getProductCount() > 20)
                break;
        try {
            int shippedProduct = MyRandom.getNumberOfProduct();
            System.out.println(MyRandom.timeNow() + this.getName() + " прибыл на склад. Хочет загрузить " + shippedProduct + " шт. товара. Проверим наличие...");
            while(shippedProduct > store.getProductCount()){
                  int waitTime = MyRandom.getTimeSleep();
                  System.out.println(MyRandom.timeNow() + this.getName() + " товара нет в наличии. Подождем. Ожидание займет " + waitTime*3 + " единиц времени.");
                  Thread.sleep(waitTime*3);
                  System.out.println(MyRandom.timeNow() + this.getName() + " закончил ждать. Вновь проверяем наличие товара. Все еще хочет забрать " + shippedProduct + " шт. товара.");
            }
            store.getFromStore(shippedProduct);
            int loadTime = MyRandom.getTimeSleep();
            System.out.println(MyRandom.timeNow() + this.getName() + " загружается. Погрузка займет " + loadTime + " единиц времени.");
            Thread.sleep(loadTime);            
            System.out.println(MyRandom.timeNow() + this.getName() + " загрузился. Загружено со склада " + shippedProduct + " шт. товара. Покидает склад.");
            
            int newTradeTime = MyRandom.getTimeSleep();
            System.out.println(MyRandom.timeNow() + this.getName() + " скоро вернется опять. Следующая погрузка через " + newTradeTime + " единиц времени.");   
            Thread.sleep(newTradeTime);
        } catch (InterruptedException ex) { }     
    }
   }
}
