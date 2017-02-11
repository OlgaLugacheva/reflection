package reflection;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Olga on 11.02.2017.
 */
public class XMLSerializator {

    public static void main(String[] args) {


        try {
            DocumentBuilder documentBuilder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document document = documentBuilder.parse("People.xml");

            Node root = document.getFirstChild();
            System.out.println("ROOT:" + root.getNodeName());

            NodeList children = root.getChildNodes();
            for (int i = 0; i < children.getLength(); i++) {
                Node child = children.item(i);
                if (child.getNodeType() != Node.TEXT_NODE) {
                    System.out.println("CHILD:" + child.getNodeName());
                }
            }

            Element object = document.createElement("object");
            Element name = document.createElement("name");
            name.setTextContent("John");
            Element age = document.createElement("age");
            age.setTextContent("20");
            Element salary = document.createElement("salary");
            salary.setTextContent("20");

            object.appendChild(name);
            object.appendChild(age);
            object.appendChild(salary);

            root.appendChild(object);

            writeDocument(document);

        } catch (ParserConfigurationException ex) {
            ex.printStackTrace(System.out);
        } catch (SAXException ex) {
            ex.printStackTrace(System.out);
        } catch (IOException ex) {
            ex.printStackTrace(System.out);
        }
    }

    private static void writeDocument(Document document) throws TransformerFactoryConfigurationError {
        DOMSource source = new DOMSource(document);
        FileOutputStream fos = null;
        try {
            Transformer tr = TransformerFactory.newInstance().newTransformer();
            fos = new FileOutputStream("other.xml");
            StreamResult result = new StreamResult(fos);
            tr.transform(source, result);
            fos.close();
        } catch (Exception e) {
            e.printStackTrace(System.out);
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException ex) {
                    ex.printStackTrace(System.out);
                }
            }
        }
    }




}
