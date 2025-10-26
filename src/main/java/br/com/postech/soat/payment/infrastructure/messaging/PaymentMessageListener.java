package br.com.postech.soat.payment.infrastructure.messaging;

import br.com.postech.soat.customer.domain.valueobject.CustomerId;
import br.com.postech.soat.order.domain.valueobject.OrderId;
import br.com.postech.soat.payment.application.command.InitiatePaymentCommand;
import br.com.postech.soat.payment.application.usecases.InitiatePaymentUseCase;
import br.com.postech.soat.payment.domain.entity.PaymentMethod;
import br.com.postech.soat.payment.infrastructure.messaging.dto.PaymentRequestedMessage;
import io.awspring.cloud.sqs.annotation.SqsListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PaymentMessageListener {

    private final InitiatePaymentUseCase initiatePaymentUseCase;
    private final Logger logger = LoggerFactory.getLogger(PaymentMessageListener.class);

    public PaymentMessageListener(InitiatePaymentUseCase initiatePaymentUseCase) {
        this.initiatePaymentUseCase = initiatePaymentUseCase;
    }

    @SqsListener("${app.messaging.order-payments-queue}")
    public void receive(PaymentRequestedMessage message) {
        logger.info("Received payment request for order {}", message.orderId());
        try {
            InitiatePaymentCommand command = new InitiatePaymentCommand(
                new OrderId(message.orderId()),
                new CustomerId(message.customerId()),
                PaymentMethod.entryOf(message.paymentMethod()),
                message.amount()
            );
            initiatePaymentUseCase.execute(command);
        } catch (Exception exception) {
            logger.error(
                "Error processing payment request for order {}: {}",
                message.orderId(),
                exception.getMessage(),
                exception
            );
            throw exception;
        }
    }
}
