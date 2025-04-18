public class Main {

    public static void main(String[] args) {
     
        CubbyHole cubbyHole= new CubbyHole();
        Consumidor consumidor= new Consumidor(cubbyHole, 1);
        Productor productor = new Productor(cubbyHole,1);

        productor.start();
        consumidor.start();




    }

    
}
