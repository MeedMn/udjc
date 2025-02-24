package com.udjc.converter.core.model;

import lombok.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents the schema structure extracted from JSON/XML
 */
@Data
@Builder
@Getter
public class SchemaModel {
    private String name;
    @Builder.Default
    private Map<String, FieldDefinition> fields = new HashMap<>();
    @Builder.Default
    private List<SchemaModel> nestedSchemas = new ArrayList<>();

    public SchemaModel(String name) {
        this.name = name;
    }

    public void addField(String name, FieldDefinition fieldDefinition) {
        fields.put(name, fieldDefinition);
    }

    public void addNestedSchema(SchemaModel nestedSchema) {
        nestedSchemas.add(nestedSchema);
    }

    /**
     * Represents a field definition in the schema
     */
    @NoArgsConstructor
    @AllArgsConstructor
    @Data
    @Builder
    public static class FieldDefinition {
        private String type;
        private boolean isArray;
        private boolean isNested;
        private String nestedTypeName;
    }
}
