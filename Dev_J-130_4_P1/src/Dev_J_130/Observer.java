/*
  Основная задача класса Observer - деактивация потоков Потребителей при возникновении
определенных условий. Так как Поставщики и Потребители завершают свою работу в разное время и 
в случайном порядке, может сложиться ситуация, когда условие для выхода из бесконечного цикла метода
Run класса Consumer никогда не будет достигнуто. Например, при наличии на складе товара в количестве 
едва превышающим 20, поставщики с короткими временными задержками начнут завершать свою работу. 
В это же время, часть потребителей может находиться в состоянии ожидания и, "проснувшись", начнут
забирать товар со склада. Если к этому моменту не останется активных поставщиков, при наличии хотя бы 
одного активного потребителя, количество товара на складе может оказаться ниже порогового значения 20, 
что не позволит последним потребителям завершить свою работу. Для решения этой задачи, Observer работает
в отдельном потоке и 1 раз в секунду проверяет кол-во активных потоков в группах "Поставщики" и "Потребители".
Если возникнет условие при котором кол-во товара на складе будет <= 20, кол-во активных поставщиков = 0, 
а количество активных потребителей > 0, Observer создаст условия для завершения оставшися активных потоков потребителей. 
Попутно реализуется сервисная функция по информированию о количестве активных поставщиков и потребителей. Если
количество активных потоков отличается от данных, полученных при предыдущей итерации - новые данные о количестве
активных потоков выводятся в консоль.
*/
package Dev_J_130;

import java.util.concurrent.TimeUnit;

public class Observer extends Thread{
    
    private final ThreadGroup providers;
    private final ThreadGroup consumers;
    private final Store store;
    private final int numberOfActors;

    public Observer(ThreadGroup providers, ThreadGroup consumers, Store store, int numberOfActors){
        this.providers = providers;
        this.consumers = consumers;
        this.store = store;
        this.numberOfActors = numberOfActors;
    }

    @Override
    public void run(){
        
        int countIteration = 0;
        int activeProviders = 0;
        int activeConsumers = 0;
        System.out.println("Observer запущен...\nObserver сообщает: происходит активация участников бизнеспроцесса.");
        
        while(true){
                        
    /*Нормальный выход из цикла. Проверка по кол-ву итераций нужна, чтобы не завершиться в начале - на этапе активации.
      После 4 итераций (чуть более 4-х секунд) гарантированно будет хоть один активный поток (через 2 секунды должен активироваться первый...)      
    */          
            if(activeConsumers == 0 && activeProviders == 0 && countIteration > 4)
               break;
    /*Принудительное завершение еще активных Consumers при мертвых Providers. При таком условии внутри if, нужна проверка
      countIteration > numberOfActors*2 + 2, чтобы не завершиться на этапе активации потоков после запуска приложения. 
      После запуска приложения, при активации потоков, достигнув countIteration == numberOfActors*2 + 2 все потоки 
      должны быть активны (1 Actor - 2 секунды, 1 итерация - 1 секунда).
            
      Если будет выполнено условие внутри if, для всех еще активных потоков из группы consumers будет применен метод interrupt().
      Если в этот момент поток находится в ожидании из-за TimeUnit.SECONDS.sleep(seconds), в методе run() класса Consumer будет 
      брошено InterruptedException. Результатом обработки этого исключения будет выход из цикла и завершение работы потока. Если в
      момент применения метода interrupt() кто-то из группы consumers не будет спать, то описанное выше произойдет при следующей итерации
      этого цикла, когда потребитель уснет в безнадежной попытке дождаться поступления товара на склад.
    */         
            if(activeConsumers > 0 && activeProviders == 0 && store.getProductCount() <= 20 && countIteration > numberOfActors*2 + 2) {
                System.out.println("Observer сообщает: товара на складе сегодня уже не будет. Расходитесь. Ждать бессмысленно.");
                consumers.interrupt();  }
            
            try { TimeUnit.SECONDS.sleep(1); } 
            catch (InterruptedException ex) { }
            
            if(activeProviders != providers.activeCount() || activeConsumers != consumers.activeCount()){
               System.out.println("Observer сообщает: Активных поставщиков сейчас - " + providers.activeCount() + 
                                "\nObserver сообщает: Активных потребителей сейчас - " + consumers.activeCount());
               activeProviders = providers.activeCount();
               activeConsumers = consumers.activeCount();
            }            
            countIteration++;
        }
        System.out.println("Observer закончил работу.\nСклад закрыт.");
    }   
}
