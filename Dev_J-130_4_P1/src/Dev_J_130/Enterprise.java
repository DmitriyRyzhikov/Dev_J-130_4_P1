
package Dev_J_130;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Enterprise {
    
    private final Set<Runnable> actors = new HashSet<>();
    private final ThreadGroup providers = new ThreadGroup("Поставщики");
    private final ThreadGroup consumers = new ThreadGroup("Потребители");
    
    public void startBusiness (int prov, int cons) {
    
    //создаем склад, на котором имеется какое-то начальное кол-во товара.    
        Store store = new Store(Randomer.getNumberOfProduct());
        System.out.println(Randomer.timeNow() + "Склад открыт! Запас товара на складе " + store.getProductCount() + " единиц.\n");

    //создаем поставщиков и потребителей           
        Provider[] provider = new Provider[prov];
        for(int i = 0; i < prov; i++){
            provider[i] = new Provider(providers, "Поставщик " + (i+1), store);
            actors.add(provider[i]);
            }
        Consumer[] consumer = new Consumer[cons];
        for(int i = 0; i < cons; i++){
            consumer[i] = new Consumer(consumers, "Потребитель " + (i+1), store);
            actors.add(consumer[i]);
            }

    //создаем и запускаем Observer    
        Observer observer = new Observer(providers, consumers, store, actors.size());
        observer.start();

    /*С интервалом в 2 секунды активируем поставщиков и потребителей из Set<Runnable> actors.
      Запуск происходит в том порядке, в котором они лежат в Set. 
      Применительно к нашему случаю, можно считать его произвольным.  
    */ 
        actors.forEach(actor -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException ex) {  }
            
            if(actor instanceof Provider)
               ((Provider) actor).start();
            else if(actor instanceof Consumer)
               ((Consumer) actor).start();                             
            });
    }    
}
