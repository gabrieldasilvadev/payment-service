package br.com.postech.soat.payment.domain.valueobject;

import br.com.postech.soat.payment.commons.domain.Identifier;
import java.util.UUID;

public class OrderId extends Identifier {

    public OrderId(UUID value) {
        super(value);
    }

    public static OrderId generate() {
        return new OrderId(UUID.randomUUID());
    }

    public static OrderId of(UUID value) {
        return new OrderId(value);
    }

    public static OrderId of(String value) {
        return new OrderId(UUID.fromString(value));
    }
}
