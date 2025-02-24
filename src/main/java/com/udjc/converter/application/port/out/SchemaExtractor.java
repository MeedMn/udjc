package com.udjc.converter.application.port.out;

import com.udjc.converter.core.model.SchemaModel;

public interface SchemaExtractor {
    SchemaModel extractSchema(String data);
}
