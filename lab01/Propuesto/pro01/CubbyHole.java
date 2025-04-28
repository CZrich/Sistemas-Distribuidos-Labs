
public class CubbyHole {
    private int contents;
    private boolean available=false;
    public synchronized int get(){

        // espera solo si NO hay contenido disponible
        while (!available) { 
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
