package com.udjc.converter.core.service;

import io.swagger.v3.oas.annotations.servers.Server;
import org.springframework.stereotype.Service;

@Service
public class FormatDetectorImpl {

    public enum DataFormat {
        JSON, XML, UNKNOWN
    }

    public DataFormat detectFormat(String data) {
        if (data == null || data.trim().isEmpty()) {
            return DataFormat.UNKNOWN;
        }

        String trimmed = data.trim();

        if ((trimmed.startsWith("{") && trimmed.endsWith("}")) ||
                (trimmed.startsWith("[") && trimmed.endsWith("]"))) {
            return DataFormat.JSON;
        }

        if (trimmed.startsWith("<?xml") ||
                (trimmed.startsWith("<") && trimmed.endsWith(">"))) {
            return DataFormat.XML;
        }

        return DataFormat.UNKNOWN;
    }
}
