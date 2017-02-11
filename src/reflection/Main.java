package reflection;

import org.w3c.dom.*;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

/**
 * Created by Olga on 10.02.2017.
 */
public class Main {

    public static void main(String[] args) {

        People people = new People();

        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();


        People people1 = (People) deserialize(dbf, "sample.xml");

        System.out.println(people1);

        try {
            people.getClass().getConstructor();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        people.getClass().getDeclaredFields();
        people.getClass().getMethods();


    }

    static Object deserialize(DocumentBuilderFactory dbf, String filePath) {
        DocumentBuilder builder = null;
        try {
            builder = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document doc = null;
        try {
            doc = builder.parse(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (org.xml.sax.SAXException e) {
            e.printStackTrace();
        }

        return visit(doc, null);

    }

    public static Object visit(Node node, Object target) {
        NodeList list = node.getChildNodes();
        for (int i = 0; i < list.getLength(); i++) {
            Node childNode = list.item(i);
            target = process(childNode, target);
            target = visit(childNode, target);
        }
        return target;
    }

    public static Object process(Node node, Object target) {
        Class targetClass = People.class;
        if (node.getNodeName().equals("object")) {
            try {
                targetClass = Class.forName(node.getAttributes().getNamedItem("type").getNodeValue());
                target = targetClass.newInstance();

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
        if (node instanceof Element){
            Element e = (Element) node;
            NamedNodeMap nnm = e.getAttributes();
            if (!e.getTagName().equals("field")) return target;
            Field tmpField = null;
            String tmpValue = null;
            for (Field f : targetClass.getDeclaredFields()) {
                f.setAccessible(true);
            }
            for (int i = 0; i < nnm.getLength(); i++) {
                Node tmpNode = nnm.item(i);
                try {
                    switch(tmpNode.getNodeName()){
                        case "id":
                            tmpField = targetClass.getDeclaredField(tmpNode.getNodeValue());
                            tmpField.setAccessible(true);
                            break;
                        case "value":
                            tmpValue = tmpNode.getNodeValue();
                            break;
                    }
                } catch (NoSuchFieldException e1) {
                    e1.printStackTrace();
                }
            }

        }

        return target;
    }
}

