package br.com.postech.soat.payment.domain.valueobject;

import br.com.postech.soat.payment.commons.domain.Identifier;
import java.util.UUID;

public class CustomerId extends Identifier {

    public CustomerId(UUID value) {
        super(value);
    }

    public static CustomerId generate() {
        return new CustomerId(UUID.randomUUID());
    }

    public static CustomerId of(UUID value) {
        return new CustomerId(value);
    }

    public static CustomerId of(String value) {
        return new CustomerId(UUID.fromString(value));
    }
}
