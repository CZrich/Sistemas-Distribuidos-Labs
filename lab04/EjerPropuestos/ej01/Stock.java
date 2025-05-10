package EjerPropuestos.ej01;

import java.util.HashMap;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Stock extends UnicastRemoteObject implements StockInterface {
    private HashMap<String, MedicineInterface> medicines = new HashMap<>();

    public Stock() throws RemoteException {
        super();
    }

    @Override
    public void addMedicine(String name, float price, int stock) throws Exception {
        medicines.put(name, new Medicine(name, price, stock));
    }

    @Override
    public MedicineInterface buyMedicine(String name, int amount) throws Exception {
        MedicineInterface aux = medicines.get(name);
        if (aux == null) {
            throw new Exception("Impossible to find " + name);
        }
        return aux.getMedicine(amount);
    }

    @Override
    public HashMap<String, MedicineInterface> getStockProducts() throws Exception {
        return this.medicines;
    }
}
