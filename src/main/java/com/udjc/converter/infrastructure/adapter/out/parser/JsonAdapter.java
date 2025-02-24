package com.udjc.converter.infrastructure.adapter.out.parser;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.udjc.converter.application.port.out.SchemaExtractor;
import com.udjc.converter.core.model.SchemaModel;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.Map;

@Component
public class JsonAdapter implements SchemaExtractor {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public SchemaModel extractSchema(String data) {
        try {
            JsonNode rootNode = objectMapper.readTree(data);
            if (rootNode.isArray() && rootNode.size() > 0) {
                rootNode = rootNode.get(0);
            }
            return parseJsonNode("Root", rootNode);
        } catch (Exception e) {
            throw new RuntimeException("Error parsing JSON: " + e.getMessage(), e);
        }
    }

    private SchemaModel parseJsonNode(String name, JsonNode node) {
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
                    SchemaModel nestedSchema = parseJsonNode(fieldName, childNode);
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
                            SchemaModel nestedSchema = parseJsonNode(fieldName, firstElem);
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
        } else {
            return "Object";
        }
    }
}