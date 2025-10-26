package br.com.postech.soat.payment.infrastructure.messaging;

import br.com.postech.soat.payment.customer.domain.valueobject.CustomerId;
import br.com.postech.soat.payment.order.domain.valueobject.OrderId;
import br.com.postech.soat.payment.application.repositories.PaymentRepository;
import br.com.postech.soat.payment.application.usecases.ProcessPaymentNotificationUseCase;
import br.com.postech.soat.payment.domain.entity.Payment;
import br.com.postech.soat.payment.domain.entity.PaymentMethod;
import br.com.postech.soat.payment.domain.entity.PaymentStatus;
import br.com.postech.soat.payment.domain.valueobject.PaymentId;
import br.com.postech.soat.payment.infrastructure.messaging.dto.PaymentProcessedMessage;
import br.com.postech.soat.payment.infrastructure.paymentgateway.CheckoutClient;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class PaymentStatusMessagingComponentTest {

    @Test
    void shouldPublishPaymentStatusEvent() {
        UUID orderIdValue = UUID.randomUUID();
        UUID paymentIdValue = UUID.randomUUID();
        PaymentId paymentId = PaymentId.of(paymentIdValue);

        Payment payment = Payment.builder()
            .paymentId(paymentIdValue)
            .orderId(new OrderId(orderIdValue))
            .customerId(new CustomerId(UUID.randomUUID()))
            .amount(BigDecimal.TEN)
            .status(PaymentStatus.APPROVED)
            .method(PaymentMethod.PIX)
            .createdAt(Instant.now())
            .processedAt(null)
            .build();

        PaymentRepository paymentRepository = mock(PaymentRepository.class);
        CheckoutClient checkoutClient = mock(CheckoutClient.class);
        PaymentStatusPublisher paymentStatusPublisher = mock(PaymentStatusPublisher.class);

        when(checkoutClient.getPaymentDetails(paymentId)).thenReturn("Status: APPROVED");
        when(paymentRepository.findById(paymentId)).thenReturn(payment);

        ProcessPaymentNotificationUseCase useCase = new ProcessPaymentNotificationUseCase(
            paymentRepository,
            checkoutClient,
            paymentStatusPublisher
        );

        useCase.execute(paymentId);

        verify(paymentRepository).save(payment);

        ArgumentCaptor<PaymentProcessedMessage> messageCaptor = ArgumentCaptor.forClass(PaymentProcessedMessage.class);
        verify(paymentStatusPublisher).publish(messageCaptor.capture());
        PaymentProcessedMessage processed = messageCaptor.getValue();
        assertThat(processed.orderId()).isEqualTo(orderIdValue);
        assertThat(processed.status()).isEqualTo(PaymentStatus.FINISHED.name());
    }
}
