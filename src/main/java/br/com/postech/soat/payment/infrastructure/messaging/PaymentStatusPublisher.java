package br.com.postech.soat.payment.infrastructure.messaging;

import br.com.postech.soat.payment.infrastructure.messaging.dto.PaymentProcessedMessage;
import io.awspring.cloud.sns.core.SnsTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PaymentStatusPublisher {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentStatusPublisher.class);

    private final SnsTemplate snsTemplate;
    private final String paymentStatusTopicArn;

    public PaymentStatusPublisher(SnsTemplate snsTemplate,
                                  @Value("${app.messaging.payment-status-topic}") String paymentStatusTopicArn) {
        this.snsTemplate = snsTemplate;
        this.paymentStatusTopicArn = paymentStatusTopicArn;
    }

    public void publish(PaymentProcessedMessage message) {
        LOGGER.debug("Publishing payment status update for order {}", message.orderId());
        snsTemplate.convertAndSend(paymentStatusTopicArn, message);
    }
}
