package br.com.postech.soat.payment.application.usecases;

import br.com.postech.soat.payment.commons.infrastructure.aop.monitorable.Monitorable;
import br.com.postech.soat.payment.application.repositories.PaymentRepository;
import br.com.postech.soat.payment.domain.entity.Payment;
import br.com.postech.soat.payment.domain.entity.PaymentStatus;
import br.com.postech.soat.payment.domain.valueobject.PaymentId;
import br.com.postech.soat.payment.infrastructure.messaging.PaymentStatusPublisher;
import br.com.postech.soat.payment.infrastructure.messaging.dto.PaymentProcessedMessage;
import br.com.postech.soat.payment.infrastructure.paymentgateway.CheckoutClient;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@Monitorable
@RequiredArgsConstructor
public class ProcessPaymentNotificationUseCase {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessPaymentNotificationUseCase.class);

    private final PaymentRepository paymentRepository;
    private final CheckoutClient checkoutClient;
    private final PaymentStatusPublisher paymentStatusPublisher;

    public void execute(PaymentId paymentId) {

        final String paymentProcessed = checkoutClient.getPaymentDetails(paymentId);

        if (paymentProcessed == null || paymentProcessed.trim().isEmpty()) {
            throw new RuntimeException("Failed to process payment notification");
        }

        final Payment payment = paymentRepository.findById(paymentId);

        PaymentStatus gatewayStatus = parsePaymentStatusFromResponse(paymentProcessed);

        switch (gatewayStatus) {
            case APPROVED -> payment.finish();
            case DECLINED -> payment.decline();
            case FAILED -> payment.fail();
            default -> throw new IllegalStateException("Unexpected payment status: " + gatewayStatus);
        }

        paymentRepository.save(payment);

        PaymentProcessedMessage payload = new PaymentProcessedMessage(
            payment.getId().getValue(),
            payment.getOrderId().getValue(),
            payment.getStatus().name()
        );

        LOGGER.info("Publishing payment processed event for order {} with status {}", payload.orderId(), payload.status());
        paymentStatusPublisher.publish(payload);
    }

    private PaymentStatus parsePaymentStatusFromResponse(String paymentProcessed) {
        if (paymentProcessed.contains("Status: DECLINED")) return PaymentStatus.DECLINED;
        if (paymentProcessed.contains("Status: FAILED")) return PaymentStatus.FAILED;
        if (paymentProcessed.contains("Status: APPROVED")) return PaymentStatus.APPROVED;
        return PaymentStatus.APPROVED;
    }
}
