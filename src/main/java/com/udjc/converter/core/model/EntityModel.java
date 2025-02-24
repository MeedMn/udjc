package com.udjc.converter.core.model;


import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Spring Boot Entity class definition
 */
@Getter
@Builder
public class EntityModel extends PojoModel {
    private String tableName;
    private List<String> primaryKeys = new ArrayList<>();
    private List<Relationship> relationships = new ArrayList<>();

    public EntityModel(String className, String packageName, String tableName) {
        super(className, packageName);
        this.tableName = tableName;
        addImport("jakarta.persistence.Entity");
        addImport("jakarta.persistence.Table");
        addImport("jakarta.persistence.Id");
        addImport("jakarta.persistence.Column");
    }

    public void addPrimaryKey(String fieldName) {
        primaryKeys.add(fieldName);
    }

    public void addRelationship(Relationship relationship) {
        relationships.add(relationship);
        // Add appropriate import
        addImport("jakarta.persistence." + relationship.getType().name());
    }

    /**
     * Represents a relationship between entities
     */
    @Data
    @Builder
    public static class Relationship {
        public enum RelationType {
            OneToOne, OneToMany, ManyToOne, ManyToMany
        }

        private String fieldName;
        private String targetEntity;
        private RelationType type;
        private boolean fetchLazy;
        private String mappedBy;

        public Relationship(String fieldName, String targetEntity, RelationType type) {
            this.fieldName = fieldName;
            this.targetEntity = targetEntity;
            this.type = type;
            this.fetchLazy = true;
        }
    }
}
