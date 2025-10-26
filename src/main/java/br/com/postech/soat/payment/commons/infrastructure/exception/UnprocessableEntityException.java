package br.com.postech.soat.payment.commons.infrastructure.exception;

public class UnprocessableEntityException extends BaseException {
    public UnprocessableEntityException(String message) {
        super(message);
    }
}