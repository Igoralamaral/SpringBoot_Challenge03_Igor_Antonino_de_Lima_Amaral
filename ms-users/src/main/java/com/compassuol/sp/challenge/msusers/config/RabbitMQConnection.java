package com.compassuol.sp.challenge.msusers.config;

import com.compassuol.sp.challenge.msusers.constants.RabbitMQConstants;
import jakarta.annotation.PostConstruct;
import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.stereotype.Component;

@Component
public class RabbitMQConnection {

    private static final String EXCHANGE_NAME = "amq.direct";

    private AmqpAdmin amqpAdmin;

    public RabbitMQConnection(AmqpAdmin amqpAdmin) {
        this.amqpAdmin = amqpAdmin;
    }

    private Queue queue(String queueName) {
        return new Queue(queueName, true, false, false);
    }

    private DirectExchange directExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    private Binding binding(Queue queue, DirectExchange exchange) {
        return new Binding(queue.getName(), Binding.DestinationType.QUEUE, exchange.getName(), queue.getName(), null);
    }

    @PostConstruct
    private void add() {
        Queue queueCreate = this.queue(RabbitMQConstants.QUEUE_CREATE);
        Queue queueLogin = this.queue(RabbitMQConstants.QUEUE_LOGIN);
        Queue queueUpdate = this.queue(RabbitMQConstants.QUEUE_UPDATE);
        Queue queueUpdatePassword = this.queue(RabbitMQConstants.QUEUE_UPDATE_PASSWORD);

        DirectExchange directExchange = directExchange();

        Binding bindingCreate = this.binding(queueCreate, directExchange);
        Binding bindingLogin = this.binding(queueLogin, directExchange);
        Binding bindingUpdate = this.binding(queueUpdate, directExchange);
        Binding bindingUpdatePassword = this.binding(queueUpdatePassword, directExchange);

        this.amqpAdmin.declareQueue(queueCreate);
        this.amqpAdmin.declareQueue(queueLogin);
        this.amqpAdmin.declareQueue(queueUpdate);
        this.amqpAdmin.declareQueue(queueUpdatePassword);

        this.amqpAdmin.declareExchange(directExchange);

        this.amqpAdmin.declareBinding(bindingCreate);
        this.amqpAdmin.declareBinding(bindingLogin);
        this.amqpAdmin.declareBinding(bindingUpdate);
        this.amqpAdmin.declareBinding(bindingUpdatePassword);
    }
}
