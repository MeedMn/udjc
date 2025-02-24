package com.udjc.converter.application.port.in;

import com.udjc.converter.core.exception.ConversionException;

public interface ConversionUseCase {
    Object convert(String input, boolean asEntity) throws ConversionException;
}
