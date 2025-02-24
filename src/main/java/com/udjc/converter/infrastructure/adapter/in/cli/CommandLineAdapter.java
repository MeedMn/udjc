package com.udjc.converter.infrastructure.adapter.in.cli;

import com.udjc.converter.application.port.in.ConversionUseCase;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineAdapter implements CommandLineRunner {

    private final ConversionUseCase conversionUseCase;

    public CommandLineAdapter(ConversionUseCase conversionUseCase) {
        this.conversionUseCase = conversionUseCase;
    }

    @Override
    public void run(String... args) throws Exception {
        if (args.length > 0) {
            String input = args[0];
            boolean asEntity = args.length > 1 && Boolean.parseBoolean(args[1]);
            Object result = conversionUseCase.convert(input, asEntity);
            System.out.println("Conversion result: " + result);
        } else {
            System.out.println("No input provided for conversion.");
        }
    }
}
