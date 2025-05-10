import java.rmi.Naming;
import java.rmi.RemoteException;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;

public class CalculatorClient {
    public static void main(String[] args) {
        int num1 = Integer.parseInt(args[0]);
        int num2 = Integer.parseInt(args[1]);

        try {
            Calculator c = (Calculator) Naming.lookup("rmi://localhost/CalculatorService");

            System.out.println("The subtraction of " + num1 + " and " + num2 + " is: " + c.sub(num1, num2));
            System.out.println("The addition of " + num1 + " and " + num2 + " is: " + c.add(num1, num2));
            System.out.println("The multiplication of " + num1 + " and " + num2 + " is: " + c.mul(num1, num2));
            System.out.println("The division of " + num1 + " and " + num2 + " is: " + c.div(num1, num2));
        } catch (MalformedURLException murle) {
            System.out.println("\nMalformedURLException");
            System.out.println(murle);
        } catch (RemoteException re) {
            System.out.println("\nRemoteException");
            System.out.println(re);
        } catch (NotBoundException nbe) {
            System.out.println("\nNotBoundException");
            System.out.println(nbe);
        } catch (ArithmeticException ae) {
            System.out.println("\nArithmeticException");
            System.out.println(ae);
        }
    }
}