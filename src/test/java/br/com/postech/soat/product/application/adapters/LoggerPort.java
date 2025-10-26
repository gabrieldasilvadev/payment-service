package br.com.postech.soat.payment.product.application.adapters;

public interface LoggerPort {
    void info(String message);
    void warn(String message);
    void error(String message);
}
