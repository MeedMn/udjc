package com.udjc.converter.infrastructure.adapter.out.generator;

import com.udjc.converter.application.port.out.CodeWriter;
import org.springframework.stereotype.Component;

import java.io.FileWriter;
import java.io.IOException;

@Component
public class JavaFileGenerator implements CodeWriter {

    @Override
    public void writeCode(String fileName, String code) {
        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(code);
            System.out.println("Generated file: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
