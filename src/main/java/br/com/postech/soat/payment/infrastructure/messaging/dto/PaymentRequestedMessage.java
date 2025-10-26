package br.com.postech.soat.payment.infrastructure.messaging.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentRequestedMessage(
    UUID orderId,
    UUID customerId,
    BigDecimal amount,
    String paymentMethod
) {
}
