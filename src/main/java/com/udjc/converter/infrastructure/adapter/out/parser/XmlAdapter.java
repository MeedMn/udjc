package com.udjc.converter.infrastructure.adapter.out.parser;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.udjc.converter.application.port.out.SchemaExtractor;
import com.udjc.converter.core.model.SchemaModel;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;

@Component
public class XmlAdapter implements SchemaExtractor {

    private final XmlMapper xmlMapper = new XmlMapper();

    @Override
    public SchemaModel extractSchema(String data) {
        try {
            JsonNode rootNode = xmlMapper.readTree(data);
            if (rootNode.isObject() && rootNode.size() == 1) {
                Iterator<Map.Entry<String, JsonNode>> fields = rootNode.fields();
                if (fields.hasNext()) {
                    Map.Entry<String, JsonNode> entry = fields.next();
                    return parseXmlNode(entry.getKey(), entry.getValue());
                }
            }
            return parseXmlNode("Root", rootNode);
        } catch (Exception e) {
            throw new RuntimeException("Error parsing XML: " + e.getMessage(), e);
        }
    }

    private SchemaModel parseXmlNode(String name, JsonNode node) {
        SchemaModel schema = new SchemaModel(name);
        if (node.isObject()) {
            Iterator<Map.Entry<String, JsonNode>> fields = node.fields();
            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> entry = fields.next();
                String fieldName = entry.getKey();
                JsonNode childNode = entry.getValue();
                if (childNode.isValueNode()) {
                    String type = determineValueType(childNode);
                    schema.addField(fieldName, SchemaModel.FieldDefinition.builder()
                            .type(type)
                            .isArray(false)
                            .isNested(false)
                            .build());
                } else if (childNode.isObject()) {
                    SchemaModel nestedSchema = parseXmlNode(fieldName, childNode);
                    schema.addField(fieldName, SchemaModel.FieldDefinition.builder()
                            .type(fieldName + "Type")
                            .isArray(false)
                            .isNested(true)
                            .nestedTypeName(fieldName)
                            .build());
                    schema.addNestedSchema(nestedSchema);
                } else if (childNode.isArray()) {
                    if (childNode.size() > 0) {
                        JsonNode firstElem = childNode.get(0);
                        if (firstElem.isObject()) {
                            SchemaModel nestedSchema = parseXmlNode(fieldName, firstElem);
                            schema.addField(fieldName, SchemaModel.FieldDefinition.builder()
                                    .type(fieldName + "Type")
                                    .isArray(true)
                                    .isNested(true)
                                    .nestedTypeName(fieldName)
                                    .build());
                            schema.addNestedSchema(nestedSchema);
                        } else {
                            String type = determineValueType(firstElem);
                            schema.addField(fieldName, SchemaModel.FieldDefinition.builder()
                                    .type(type)
                                    .isArray(true)
                                    .isNested(false)
                                    .build());
                        }
                    } else {
                        schema.addField(fieldName, SchemaModel.FieldDefinition.builder()
                                .type("Object")
                                .isArray(true)
                                .isNested(false)
                                .build());
                    }
                }
            }
        }
        return schema;
    }
    private String determineValueType(JsonNode node) {
        if (node.isTextual()) {
            return "String";
        } else if (node.isNumber()) {
            return "Number";
        } else if (node.isBoolean()) {
            return "Boolean";
        }
        return "Object";
    }
}
