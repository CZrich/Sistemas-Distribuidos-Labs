public class Productor  extends Thread{

    private CubbyHole cubbyHole;
    private int numero;
    public Productor (CubbyHole cubbyHole, int numero){
        this.cubbyHole=cubbyHole;
        this.numero=numero;
    }

    public void run(){

        for (int i = 0; i < 10; i++) {
            cubbyHole.put(i);
            System.out.println("Productor # "+this.numero+" pone "+i);

            try {
                //sleep((int)(Math.random()*100));
            
                sleep(1000);
            } catch (InterruptedException e) {
                System.out.println(e.getMessage());

            }
        }
    }

}
