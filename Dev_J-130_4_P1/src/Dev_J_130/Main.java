
package Dev_J_130;

public class Main {

    public static void main(String[] args) {
        
        try {new TradeTurnover().startBusiness(3, 3);}                          
        catch (InterruptedException ex) { }    
    } 
}
/*Параметрами метода startBusiness(int prod, int cons) является
  количество поставщиков и потребителей соответственно. */