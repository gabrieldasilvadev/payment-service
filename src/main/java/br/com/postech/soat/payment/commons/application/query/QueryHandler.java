package br.com.postech.soat.payment.commons.application.query;

public interface QueryHandler<Q extends Query, R> {
    R handle(Q query);
}
