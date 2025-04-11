package coopbank.co.ke.knobv1.model;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;



public class ISO8583FieldMapper {
    private final Map<String, Integer> fieldNameToNumber;
    private final Map<Integer, String> fieldNumberToName;

    public ISO8583FieldMapper() {
        this.fieldNameToNumber = new HashMap<>();
        this.fieldNumberToName = new HashMap<>();
        initializeFieldMappings();
    }


    private void initializeFieldMappings() {
        try (InputStream xmlStream = getClass().getClassLoader()
                .getResourceAsStream("fields.xml")) {

            if (xmlStream == null) {
                throw new RuntimeException("fields.xml not found in resources");
            }

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document doc = builder.parse(xmlStream);

            NodeList fields = doc.getElementsByTagName("field");
            for (int i = 0; i < fields.getLength(); i++) {
                Element field = (Element) fields.item(i);
                int num = Integer.parseInt(field.getAttribute("num"));
                String code = field.getAttribute("code");

                fieldNameToNumber.put(code, num);
                fieldNumberToName.put(num, code);
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize ISO8583 field mappings", e);
        }
    }

    /**
     * Get field number by field name (returns 0 if field not found)
     */


    public int getFieldValue(String fieldName) {
        return fieldNameToNumber.getOrDefault(fieldName, 0);
    }

    /**
     * Get field name by field number (returns null if field not found)
     */
    public String getFieldName(int fieldNumber) {
        return fieldNumberToName.get(fieldNumber);
    }

    /**
     * Check if a field name exists in the specification
     */
    public boolean isValidFieldName(String fieldName) {
        return fieldNameToNumber.containsKey(fieldName);
    }

    /**
     * Check if a field number exists in the specification
     */
    public boolean isValidFieldNumber(int fieldNumber) {
        return fieldNumberToName.containsKey(fieldNumber);
    }

}
