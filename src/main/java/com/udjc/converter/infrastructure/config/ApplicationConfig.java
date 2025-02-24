package com.udjc.converter.infrastructure.config;

import com.udjc.converter.application.port.out.CodeWriter;
import com.udjc.converter.application.port.out.SchemaExtractor;
import com.udjc.converter.infrastructure.adapter.out.generator.JavaFileGenerator;
import com.udjc.converter.infrastructure.adapter.out.parser.JsonAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class ApplicationConfig {

    @Bean
    @Primary
    public CodeWriter codeWriter(JavaFileGenerator javaFileGenerator) {
        return javaFileGenerator;
    }

    @Bean
    @Primary
    public SchemaExtractor schemaExtractor(JsonAdapter jsonAdapter) {
        return jsonAdapter;
    }
}
