
package EjerPropuestos.eje02.banco.client;
import EjerPropuestos.eje02.banco.interfaces.CreditCard;

import java.rmi.Naming;
public class CreditClient {
     public static void main(String[] args) {
        try {
            CreditCard card = (CreditCard) Naming.lookup("rmi://localhost/CreditCardService");

            System.out.println("Titular: " + card.getCarHolderName());
            System.out.println("Saldo actual: " + card.getBalance());

            card.makePayment(200.0);
            System.out.println("Se hizo un pago de 200");
            System.out.println("Nuevo saldo: " + card.getBalance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
