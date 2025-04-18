
public class CubbyHole {
    private int contents;
    private boolean available=false;
    public synchronized int get(){

        while (!available) { // espera solo si NO hay contenido disponible
            try {
                wait();
            } catch (InterruptedException e) {
            }
        }
        available=false;

        notify();
        return contents;
    }
    
    public synchronized void put(int value){

        while (available) {
            try{

                wait();

            }catch(InterruptedException e){
                System.out.println(e.getMessage());
            }

            
        }
        contents=value;
        available=true;
        notify();
    }

}
