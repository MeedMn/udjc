package com.udjc.converter.infrastructure.adapter.in.rest;

import com.udjc.converter.application.port.in.ConversionUseCase;
import com.udjc.converter.core.exception.ConversionException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Tag(name = "Conversion API", description = "API for converting JSON/XML to Java POJO/Entity")
public class ConversionController {

    private final ConversionUseCase conversionUseCase;

    public ConversionController(ConversionUseCase conversionUseCase) {
        this.conversionUseCase = conversionUseCase;
    }

    @Operation(summary = "Convert JSON/XML input to a Java POJO or Entity")
    @PostMapping("/convert")
    public ResponseEntity<?> convert(@RequestBody String input,
                                     @RequestParam(defaultValue = "false") boolean asEntity) throws ConversionException {
        Object result = conversionUseCase.convert(input, asEntity);
        return ResponseEntity.ok(result);
    }
}
