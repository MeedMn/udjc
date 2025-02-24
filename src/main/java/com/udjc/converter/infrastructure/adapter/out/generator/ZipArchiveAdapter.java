package com.udjc.converter.infrastructure.adapter.out.generator;

import com.udjc.converter.application.port.out.CodeWriter;
import org.springframework.stereotype.Component;

@Component
public class ZipArchiveAdapter implements CodeWriter {

    @Override
    public void writeCode(String fileName, String code) {
        throw new UnsupportedOperationException("ZIP packaging is not supported.");
    }
}
