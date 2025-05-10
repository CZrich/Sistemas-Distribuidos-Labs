package EjerPropuestos.eje02.banco.server;

import EjerPropuestos.eje02.banco.interfaces.CreditCard;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class CreditCardImpl extends UnicastRemoteObject implements CreditCard {
    private String cardHolderName;
    private double balance;

    public CreditCardImpl(String cardHolderName, double initialBalance) throws RemoteException {
        
        super();
        this.cardHolderName = cardHolderName;
        this.balance = initialBalance;

    }

        @Override
        public double getBalance() throws RemoteException {
            return balance;
        }
   

    @Override
    public void makePayment(double amount) throws RemoteException {
        if (amount > balance) {
            throw new RemoteException("Insufficient funds");
        }
        balance -= amount;
    }

    @Override
    public String getCarHolderName() throws RemoteException {
        return cardHolderName;
    }

}
