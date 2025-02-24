package com.udjc.converter.core.service;

import com.udjc.converter.core.exception.ConversionException;
import com.udjc.converter.core.model.SchemaModel;
import org.springframework.stereotype.Service;

@Service
public class ConverterServiceImpl {

    private final FormatDetectorImpl formatDetector;
    private final CodeGeneratorImpl codeGenerator;

    public ConverterServiceImpl(FormatDetectorImpl formatDetector, CodeGeneratorImpl codeGenerator) {
        this.formatDetector = formatDetector;
        this.codeGenerator = codeGenerator;
    }

    public Object convert(String input, boolean asEntity) throws ConversionException {
        FormatDetectorImpl.DataFormat format = formatDetector.detectFormat(input);
        if (format == FormatDetectorImpl.DataFormat.UNKNOWN) {
            throw new ConversionException("Unsupported data format");
        }
        SchemaModel schema = new SchemaModel("Sample");
        schema.addField("id", SchemaModel.FieldDefinition.builder()
                .type("String")
                .isArray(false)
                .isNested(false)
                .build());

        if (asEntity) {
            return codeGenerator.generateEntity(schema);
        } else {
            return codeGenerator.generatePojo(schema);
        }
    }
}
