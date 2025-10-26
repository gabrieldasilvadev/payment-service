package br.com.postech.soat.payment.application.command;

import br.com.postech.soat.payment.commons.application.command.Command;
import br.com.postech.soat.payment.domain.entity.PaymentMethod;
import br.com.postech.soat.payment.domain.valueobject.CustomerId;
import br.com.postech.soat.payment.domain.valueobject.OrderId;
import java.math.BigDecimal;

public record InitiatePaymentCommand(
    OrderId orderId,
    CustomerId customerId,
    PaymentMethod paymentMethod,
    BigDecimal amount
) implements Command {
}
