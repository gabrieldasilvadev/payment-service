package br.com.postech.soat.payment.commons.infrastructure.exception;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}
