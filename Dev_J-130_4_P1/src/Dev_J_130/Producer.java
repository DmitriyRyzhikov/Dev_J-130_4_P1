
package Dev_J_130;

public class Producer extends Thread{
    
    Store store;
    
    public Producer(String name, Store store){
        super(name);
        this.store = store;
    }

    @Override
    public void run() {
        while(true) {
            if(store.getProductCount() > 20)
                break;
        try {
            int deliveryTime = MyRandom.getTimeSleep();
            System.out.println(MyRandom.timeNow() + this.getName() + " собрался доставить товар на склад. Доставка займет " + deliveryTime + " единиц времени.");
            Thread.sleep(deliveryTime);
            int deliveredProduct = MyRandom.getNumberOfProduct();
            System.out.println(MyRandom.timeNow() + this.getName() + " привез на склад " + deliveredProduct + " шт. Разгружаемся.");
            store.putToStore(deliveredProduct);
            int newTradeTime = MyRandom.getTimeSleep();
            System.out.println(MyRandom.timeNow() + this.getName() + " отправился за новой партией товара. Следующая доставка через " + newTradeTime + " единиц времени.");   
            Thread.sleep(newTradeTime);
        } catch (InterruptedException ex) { }        
      }
    }
}
