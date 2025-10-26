package br.com.postech.soat.payment.commons.application.command;

public interface UnitCommandHandler<C extends Command> {
    void handle(C command);
}
