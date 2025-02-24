package com.udjc.converter.core.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a POJO class definition
 */
@Data
@Builder
public class PojoModel {
    private String className;
    private String packageName;
    private List<Field> fields = new ArrayList<>();
    private List<PojoModel> nestedClasses = new ArrayList<>();
    private List<String> imports = new ArrayList<>();

    public PojoModel(String className, String packageName) {
        this.className = className;
        this.packageName = packageName;
    }

    public void addField(Field field) {
        fields.add(field);
    }

    public void addNestedClass(PojoModel nestedClass) {
        nestedClasses.add(nestedClass);
    }

    public void addImport(String importStatement) {
        if (!imports.contains(importStatement)) {
            imports.add(importStatement);
        }
    }

    /**
     * Represents a field in a POJO class
     */
    @Data
    @Builder
    public static class Field {
        private String name;
        private String type;
        private List<Annotation> annotations = new ArrayList<>();

        public Field(String name, String type) {
            this.name = name;
            this.type = type;
        }

        public void addAnnotation(Annotation annotation) {
            annotations.add(annotation);
        }
    }

    /**
     * Represents an annotation on a field
     */
    @Data
    @Builder
    public static class Annotation {
        private String name;
        private Map<String, String> parameters = new HashMap<>();

        public Annotation(String name) {
            this.name = name;
        }

        public void addParameter(String key, String value) {
            parameters.put(key, value);
        }
    }
}

