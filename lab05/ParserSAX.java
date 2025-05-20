import org.xml.sax.*;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.File;

public class ParserSAX {
    public static void main(String[] args) {
        try {
            File inputFile = new File("anexo2.xml");
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();

            DefaultHandler handler = new DefaultHandler() {
                public void startElement(String uri, String localName, String qName, Attributes attributes) {
                    System.out.println("Inicio de elemento: " + qName);
                }

                public void characters(char[] ch, int start, int length) {
                    String texto = new String(ch, start, length).trim();
                    if (!texto.isEmpty()) {
                        System.out.println("Contenido: " + texto);
                    }
                }

                public void endElement(String uri, String localName, String qName) {
                    System.out.println("Fin de elemento: " + qName);
                }
            };

            saxParser.parse(inputFile, handler);

        } catch (Exception e) {
            System.err.println("Error al procesar el XML con SAX:");
            e.printStackTrace();
        }
    }
}
