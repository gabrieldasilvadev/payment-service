package br.com.postech.soat.payment.commons.domain;

public interface EventPublisher {
    void publish(DomainEvent event);
    default void publishAll(Iterable<DomainEvent> events) {
        events.forEach(this::publish);
    }
}
