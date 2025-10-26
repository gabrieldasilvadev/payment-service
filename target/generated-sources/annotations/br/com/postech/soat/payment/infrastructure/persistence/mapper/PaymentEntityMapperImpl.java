package br.com.postech.soat.payment.infrastructure.persistence.mapper;

import br.com.postech.soat.customer.domain.valueobject.CustomerId;
import br.com.postech.soat.order.domain.valueobject.OrderId;
import br.com.postech.soat.payment.domain.entity.Payment;
import br.com.postech.soat.payment.domain.valueobject.PaymentId;
import br.com.postech.soat.payment.infrastructure.persistence.entity.PaymentEntity;
import java.util.UUID;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-26T10:01:28-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.8 (Eclipse Adoptium)"
)
public class PaymentEntityMapperImpl implements PaymentEntityMapper {

    @Override
    public PaymentEntity mapFrom(Payment payment) {
        if ( payment == null ) {
            return null;
        }

        PaymentEntity paymentEntity = new PaymentEntity();

        paymentEntity.setId( paymentIdValue( payment ) );
        paymentEntity.setOrderId( paymentOrderIdValue( payment ) );
        paymentEntity.setCustomerId( paymentCustomerIdValue( payment ) );
        paymentEntity.setMethod( payment.getMethod() );
        paymentEntity.setStatus( payment.getStatus() );
        paymentEntity.setAmount( payment.getAmount() );
        paymentEntity.setCreatedAt( payment.getCreatedAt() );
        paymentEntity.setProcessedAt( payment.getProcessedAt() );

        return paymentEntity;
    }

    @Override
    public Payment mapFrom(PaymentEntity paymentEntity) {
        if ( paymentEntity == null ) {
            return null;
        }

        Payment.PaymentBuilder payment = Payment.builder();

        payment.orderId( paymentEntityToOrderId( paymentEntity ) );
        payment.customerId( paymentEntityToCustomerId( paymentEntity ) );
        payment.paymentId( paymentEntity.getId() );
        payment.method( paymentEntity.getMethod() );
        payment.status( paymentEntity.getStatus() );
        payment.createdAt( paymentEntity.getCreatedAt() );
        payment.processedAt( paymentEntity.getProcessedAt() );
        payment.amount( paymentEntity.getAmount() );

        return payment.build();
    }

    private UUID paymentIdValue(Payment payment) {
        PaymentId id = payment.getId();
        if ( id == null ) {
            return null;
        }
        return id.getValue();
    }

    private UUID paymentOrderIdValue(Payment payment) {
        OrderId orderId = payment.getOrderId();
        if ( orderId == null ) {
            return null;
        }
        return orderId.getValue();
    }

    private UUID paymentCustomerIdValue(Payment payment) {
        CustomerId customerId = payment.getCustomerId();
        if ( customerId == null ) {
            return null;
        }
        return customerId.value();
    }

    protected OrderId paymentEntityToOrderId(PaymentEntity paymentEntity) {
        if ( paymentEntity == null ) {
            return null;
        }

        UUID value = null;

        value = paymentEntity.getOrderId();

        OrderId orderId = new OrderId( value );

        return orderId;
    }

    protected CustomerId paymentEntityToCustomerId(PaymentEntity paymentEntity) {
        if ( paymentEntity == null ) {
            return null;
        }

        UUID value = null;

        value = paymentEntity.getCustomerId();

        CustomerId customerId = new CustomerId( value );

        return customerId;
    }
}
