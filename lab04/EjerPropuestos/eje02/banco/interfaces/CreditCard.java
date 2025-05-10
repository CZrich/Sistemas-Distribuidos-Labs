package EjerPropuestos.eje02.banco.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
public interface CreditCard extends Remote {

    double getBalance( )throws RemoteException ;
    void makePayment (double amount) throws RemoteException ;
    String getCarHolderName() throws RemoteException ;

}
