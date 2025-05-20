import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.File;

public class ParserDOM {
    public static void main(String[] args) {
        try {
            File inputFile = new File("anexo2.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            dbFactory.setValidating(true);  
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            System.out.println("Elemento raiz: " + doc.getDocumentElement().getNodeName());

            // para los titulos
            NodeList titulos = doc.getElementsByTagName("TITULO");
            for (int i = 0; i < titulos.getLength(); i++) {
                System.out.println("TITULO: " + titulos.item(i).getTextContent().trim());
            }

        } catch (Exception e) {
            System.err.println("Error al procesar el XML con DOM:");
            e.printStackTrace();
        }
    }
}
