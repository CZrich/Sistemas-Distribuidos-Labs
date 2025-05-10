package EjerPropuestos.eje02.banco.server;

import EjerPropuestos.eje02.banco.interfaces.CreditCard;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.Naming;
public class CreditServer {

    public static void main(String[] args) {
          try {
            CreditCard card = new CreditCardImpl("Richard", 1000.0);
            LocateRegistry.createRegistry(2025); // o usar rmi registry externo
            
          //  Registry registry = LocateRegistry.createRegistry(1099);
            
            Naming.rebind("rmi://localhost/CreditCardService", card);
            System.out.println("Servidor listo.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
