package br.com.postech.soat.payment.infrastructure.http.mapper;

import br.com.postech.soat.customer.domain.valueobject.CustomerId;
import br.com.postech.soat.openapi.model.PaymentMethodDto;
import br.com.postech.soat.openapi.model.PostPaymentsRequestDto;
import br.com.postech.soat.order.domain.valueobject.OrderId;
import br.com.postech.soat.payment.application.command.InitiatePaymentCommand;
import br.com.postech.soat.payment.domain.entity.PaymentMethod;
import java.math.BigDecimal;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-10-26T10:01:28-0300",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.8 (Eclipse Adoptium)"
)
public class PaymentCommandMapperImpl implements PaymentCommandMapper {

    @Override
    public InitiatePaymentCommand toCommand(PostPaymentsRequestDto postPaymentsRequestDto) {
        if ( postPaymentsRequestDto == null ) {
            return null;
        }

        PaymentMethod paymentMethod = null;
        BigDecimal amount = null;

        String value = postPaymentsRequestDtoPaymentMethodValue( postPaymentsRequestDto );
        if ( value != null ) {
            paymentMethod = Enum.valueOf( PaymentMethod.class, value );
        }
        if ( postPaymentsRequestDto.getAmount() != null ) {
            amount = BigDecimal.valueOf( postPaymentsRequestDto.getAmount() );
        }

        OrderId orderId = new OrderId(postPaymentsRequestDto.getOrderId());
        CustomerId customerId = new CustomerId(postPaymentsRequestDto.getCustomerId());

        InitiatePaymentCommand initiatePaymentCommand = new InitiatePaymentCommand( orderId, customerId, paymentMethod, amount );

        return initiatePaymentCommand;
    }

    private String postPaymentsRequestDtoPaymentMethodValue(PostPaymentsRequestDto postPaymentsRequestDto) {
        PaymentMethodDto paymentMethod = postPaymentsRequestDto.getPaymentMethod();
        if ( paymentMethod == null ) {
            return null;
        }
        return paymentMethod.getValue();
    }
}
