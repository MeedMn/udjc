package com.udjc.converter.application.service;

import com.udjc.converter.application.port.in.ConversionUseCase;
import com.udjc.converter.application.port.out.CodeWriter;
import com.udjc.converter.application.port.out.SchemaExtractor;
import com.udjc.converter.core.exception.ConversionException;
import com.udjc.converter.core.service.ConverterServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class ConversionService implements ConversionUseCase {

    private final ConverterServiceImpl converterService;
    private final SchemaExtractor schemaExtractor;
    private final CodeWriter codeWriter;

    public ConversionService(ConverterServiceImpl converterService, SchemaExtractor schemaExtractor, CodeWriter codeWriter) {
        this.converterService = converterService;
        this.schemaExtractor = schemaExtractor;
        this.codeWriter = codeWriter;
    }

    @Override
    public Object convert(String input, boolean asEntity) throws ConversionException {
        Object result = converterService.convert(input, asEntity);
        return result;
    }
}
