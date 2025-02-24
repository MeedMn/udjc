package com.udjc.converter.core.service;

import com.udjc.converter.core.model.SchemaModel;
import com.udjc.converter.core.model.PojoModel;
import com.udjc.converter.core.model.EntityModel;
import com.udjc.converter.core.model.PojoModel.Field;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class CodeGeneratorImpl {

    private static final String GENERATED_PACKAGE = "com.udjc.converter.generated";

    public PojoModel generatePojo(SchemaModel schema) {
        PojoModel pojo = PojoModel.builder()
                .className(schema.getName() + "Pojo")
                .packageName(GENERATED_PACKAGE)
                .build();

        for (Map.Entry<String, SchemaModel.FieldDefinition> entry : schema.getFields().entrySet()) {
            Field field = Field.builder()
                    .name(entry.getKey())
                    .type(entry.getValue().getType())
                    .build();
            pojo.addField(field);
        }
        return pojo;
    }

    public EntityModel generateEntity(SchemaModel schema) {
        String tableName = schema.getName().toLowerCase() + "_table";
        EntityModel entity = new EntityModel(schema.getName() + "Entity", GENERATED_PACKAGE, tableName);
        boolean first = true;
        for (Map.Entry<String, SchemaModel.FieldDefinition> entry : schema.getFields().entrySet()) {
            Field field = Field.builder()
                    .name(entry.getKey())
                    .type(entry.getValue().getType())
                    .build();
            entity.addField(field);
            if (first) {
                entity.addPrimaryKey(entry.getKey());
                first = false;
            }
        }
        return entity;
    }
}
