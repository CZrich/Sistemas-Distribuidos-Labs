package productos;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author richard
 */
@WebService(serviceName = "Compras")
@javax.xml.ws.soap.Addressing
public class Compras {

    // Catálogo de productos con sus precios
    private Map<String, Double> catalogo;
    
    public Compras() {
        inicializarCatalogo();
    }
    
    private void inicializarCatalogo() {
        catalogo = new HashMap<>();
        catalogo.put("papa", 2.50);
        catalogo.put("leche", 3.75);
        catalogo.put("pan", 1.25);
        catalogo.put("arroz", 4.20);
        catalogo.put("azucar", 2.80);
        catalogo.put("aceite", 5.50);
        catalogo.put("huevos", 6.00);
        catalogo.put("pollo", 12.50);
    }
    
    @WebMethod(operationName = "hello")
    public String hello(@WebParam(name = "name") String txt) {
        return "Hello " + txt + " !";
    }
    
    /**
     * Obtener la lista de productos disponibles con sus precios
     */
    @WebMethod(operationName = "obtenerCatalogo")
    public String obtenerCatalogo() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== CATÁLOGO DE PRODUCTOS ===\n");
        for (Map.Entry<String, Double> entry : catalogo.entrySet()) {
            sb.append(String.format("%-10s: $%.2f\n", 
                entry.getKey().toUpperCase(), entry.getValue()));
        }
        return sb.toString();
    }
    
    
    @WebMethod(operationName = "comprarProducto")
    public String comprarProducto(
            @WebParam(name = "producto") String producto,
            @WebParam(name = "cantidad") double cantidad) {
        
        
        if (producto == null || producto.trim().isEmpty()) {
            return "Error: Debe especificar un producto válido.";
        }
        
       
        if (cantidad <= 0) {
            return "Lo siento, ingrese una cantidad positiva.";
        }
        
        String prod = producto.trim().toLowerCase();
        
        if (catalogo.containsKey(prod)) {
            double precio = catalogo.get(prod);
            double total = cantidad * precio;
            
            return String.format(
                "=== COMPRA INDIVIDUAL ===\n" +
                "Producto: %s\n" +
                "Cantidad: %.1f\n" +
                "Precio unitario: $%.2f\n" +
                "TOTAL: $%.2f\n" +
                "¡Gracias por su compra!",
                producto.toUpperCase(), cantidad, precio, total
            );
        } else {
            return "El producto '" + producto + "' no está disponible en nuestro catálogo.";
        }
    }

    @WebMethod(operationName = "calcularCompra")
    public String calcularCompra(
            @WebParam(name = "productos") String productos,
            @WebParam(name = "cantidades") String cantidades) {
        
        try {
            // Validar parámetros de entrada
            if (productos == null || productos.trim().isEmpty() || 
                cantidades == null || cantidades.trim().isEmpty()) {
                return "Error: Debe especificar productos y cantidades válidos.";
            }
            
            String[] listaProductos = productos.split(",");
            String[] listaCantidades = cantidades.split(",");
            
            if (listaProductos.length != listaCantidades.length) {
                return "Error: El número de productos no coincide con el número de cantidades.";
            }
            
            StringBuilder resultado = new StringBuilder();
            resultado.append("=== FACTURA DE COMPRA ===\n");
            resultado.append("Producto\t\tCantidad\tPrecio Unit.\tSubtotal\n");
            resultado.append("--------------------------------------------------------\n");
            
            double total = 0.0;
            
            for (int i = 0; i < listaProductos.length; i++) {
                String producto = listaProductos[i].trim().toLowerCase();
                
                try {
                    double cantidad = Double.parseDouble(listaCantidades[i].trim());
                    
                    // Validar cantidad positiva
                    if (cantidad <= 0) {
                        return "Lo siento, ingrese cantidades positivas.";
                    }
                    
                    if (catalogo.containsKey(producto)) {
                        double precio = catalogo.get(producto);
                        double subtotal = cantidad * precio;
                        total += subtotal;
                        
                        resultado.append(String.format("%-15s\t%.1f\t\t$%.2f\t\t$%.2f\n",
                            producto.toUpperCase(), cantidad, precio, subtotal));
                    } else {
                        resultado.append(String.format("%-15s\t%.1f\t\tNO DISPONIBLE\n",
                            producto.toUpperCase(), cantidad));
                    }
                    
                } catch (NumberFormatException e) {
                    return "Error: Ingrese cantidades válidas (números).";
                }
            }
            
            resultado.append("--------------------------------------------------------\n");
            resultado.append(String.format("TOTAL A PAGAR: $%.2f\n", total));
            resultado.append("========================================\n");
            resultado.append("¡Gracias por su compra!");
            
            return resultado.toString();
            
        } catch (Exception e) {
            return "Error al procesar la compra: " + e.getMessage();
        }
    }
}