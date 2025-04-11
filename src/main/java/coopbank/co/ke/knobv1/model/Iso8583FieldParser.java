package coopbank.co.ke.knobv1.model;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class Iso8583FieldParser {
    private final Map<String, Map<Integer, String[]>> mtiFieldConfigs;

    public Iso8583FieldParser() throws Exception {
        this.mtiFieldConfigs = parseXmlConfig();
    }

    /**
     * Get the type and length for a field in a given MTI.
     *
     * @param mti      The MTI (e.g., "1200", "1210")
     * @param fieldNum The field number (e.g., 2, 3, 11)
     * @return Array of {type, length}, or null if not found.
     */


    public String[] getFieldTypeAndLength(String mti, int fieldNum) {
        Map<Integer, String[]> fields = mtiFieldConfigs.get(mti);
        if (fields != null) {
            return fields.get(fieldNum);
        }
        return null;
    }

    // Parse the XML config into a structured map
    private Map<String, Map<Integer, String[]>> parseXmlConfig() throws Exception {
        Map<String, Map<Integer, String[]>> configMap = new HashMap<>();

        try (InputStream is = getClass().getClassLoader().getResourceAsStream("j8583.xml")) {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc;
            doc = builder.parse(is);

            NodeList parseNodes = doc.getElementsByTagName("parse");
            for (int i = 0; i < parseNodes.getLength(); i++) {
                Element parseElement = (Element) parseNodes.item(i);
                String mti = parseElement.getAttribute("type");
                Map<Integer, String[]> fields = new HashMap<>();

                NodeList fieldNodes = parseElement.getElementsByTagName("field");
                for (int j = 0; j < fieldNodes.getLength(); j++) {
                    Element fieldElement = (Element) fieldNodes.item(j);
                    int num = Integer.parseInt(fieldElement.getAttribute("num"));
                    String type = fieldElement.getAttribute("type");
                    String length = fieldElement.getAttribute("length");
                    // Default length for variable-length fields
                    if (length.isEmpty()) {
                        if (type.equals("LLVAR")) {
                            length = "0"; // Typical max for LLVAR
                        } else if (type.equals("LLLVAR")) {
                            length = "0"; // Typical max for LLLVAR
                        } else {
                            length = "0"; // Unknown
                        }
                    }
                    fields.put(num, new String[]{type, length});
                }
                configMap.put(mti, fields);
            }
        }
        return configMap;
    }
}
