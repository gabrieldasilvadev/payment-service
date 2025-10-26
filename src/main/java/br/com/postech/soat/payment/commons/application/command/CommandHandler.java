package br.com.postech.soat.payment.commons.application.command;

public interface CommandHandler<C extends Command, R> {
    R handle(C command);
}
