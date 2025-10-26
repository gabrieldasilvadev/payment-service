package br.com.postech.soat.payment.commons.infrastructure.exception;

public class ResourceConflictException extends BaseException {
    public ResourceConflictException(String message) {
        super(message);
    }
}