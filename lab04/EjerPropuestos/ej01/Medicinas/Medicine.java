package Medicinas;

import java.rmi.server.UnicastRemoteObject;

public class Medicine extends UnicastRemoteObject implements MedicineInterface {
    private String name;
    private float unitPrice;
    private int stock;

    public Medicine() throws Exception {
        super();
    }

    public Medicine(String name, float price, int stock) throws Exception {
        super();
        this.name = name;
        this.unitPrice = price;
        this.stock = stock;
    }

    @Override
    public Medicine getMedicine(int amount) throws Exception {
        if (this.stock <= 0)
            throw new StockException("Stock empty");
        if (this.stock - amount < 0)
            throw new StockException("Stock not amount of medicine");

        this.stock -= amount;
        return new Medicine(name, unitPrice * amount, stock);
    }

    @Override
    public int getStock() throws Exception {
        return this.stock;
    }

    @Override
    public String print() throws Exception {
        return this.name + "\nPrice: " + this.unitPrice + "\nStock: " + this.stock;
    }
}
