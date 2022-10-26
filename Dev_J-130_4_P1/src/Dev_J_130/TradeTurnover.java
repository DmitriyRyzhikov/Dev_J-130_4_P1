
package Dev_J_130;

import java.util.HashSet;
import java.util.Set;

public class TradeTurnover {
    
    private final Set<Runnable> actors = new HashSet<>();
    
    public void startBusiness (int prod, int cons) throws InterruptedException{
        
        Store store = new Store();
        System.out.println(MyRandom.timeNow() + "Склад открыт! Запас товара на складе " + store.getProductCount() + " шт.\n");
        
        Producer[] producer = new Producer[prod];
        for(int i = 0; i < prod; i++){
            producer[i] = new Producer("Поставщик " + (i+1), store);
            actors.add(producer[i]);
            }
        Consumer[] consumer = new Consumer[cons];
        for(int i = 0; i < cons; i++){
            consumer[i] = new Consumer("Потребитель " + (i+1), store);
            actors.add(consumer[i]);
            }
        actors.forEach(actor -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {  }
            if(actor instanceof Producer)
               ((Producer) actor).start();
            else if(actor instanceof Consumer)
               ((Consumer) actor).start();                             
            });
    }    
}
