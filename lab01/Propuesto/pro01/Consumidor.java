public class Consumidor extends Thread{
    private CubbyHole cubbyHole;
    private int numero;
    public Consumidor(CubbyHole cubbyHole, int numero){

        this.cubbyHole=cubbyHole;
        this.numero =numero;
    }

    public void run(){

        int value=0;

        for (int i = 0; i < 10; i++) {
            value= cubbyHole.get();

            System.out.println("Consumidor # "+ this.numero+  " obtiene "+ value);
            
        }
    }
}
