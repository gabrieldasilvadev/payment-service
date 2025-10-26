package br.com.postech.soat.payment.infrastructure.http.mapper;

import br.com.postech.soat.openapi.model.GetPaymentsPaymentId200ResponseDto;
import br.com.postech.soat.openapi.model.PaymentStatusDto;
import br.com.postech.soat.order.domain.valueobject.OrderId;
import br.com.postech.soat.payment.domain.entity.Payment;
import br.com.postech.soat.payment.domain.valueobject.PaymentId;
import java.util.UUID;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-26T10:01:28-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.8 (Eclipse Adoptium)"
)
public class PaymentMapperImpl implements PaymentMapper {

    @Override
    public GetPaymentsPaymentId200ResponseDto toResponse(Payment payment) {
        if ( payment == null ) {
            return null;
        }

        GetPaymentsPaymentId200ResponseDto.Builder getPaymentsPaymentId200ResponseDto = GetPaymentsPaymentId200ResponseDto.builder();

        getPaymentsPaymentId200ResponseDto.paymentId( paymentIdValue( payment ) );
        getPaymentsPaymentId200ResponseDto.orderId( paymentOrderIdValue( payment ) );
        if ( payment.getAmount() != null ) {
            getPaymentsPaymentId200ResponseDto.amount( payment.getAmount().doubleValue() );
        }
        getPaymentsPaymentId200ResponseDto.processedAt( map( payment.getProcessedAt() ) );

        getPaymentsPaymentId200ResponseDto.paymentStatus( PaymentStatusDto.fromValue(payment.getStatus().name()) );

        return getPaymentsPaymentId200ResponseDto.build();
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
}
