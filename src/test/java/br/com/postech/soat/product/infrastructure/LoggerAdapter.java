package br.com.postech.soat.payment.product.infrastructure;

import br.com.postech.soat.payment.product.application.adapters.LoggerPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerAdapter implements LoggerPort {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoggerAdapter.class);

    @Override
    public void info(String message) {
        LOGGER.info(message);
    }

    @Override
    public void warn(String message) {
        LOGGER.warn(message);
    }

    @Override
    public void error(String message) {
        LOGGER.error(message);
    }
}
