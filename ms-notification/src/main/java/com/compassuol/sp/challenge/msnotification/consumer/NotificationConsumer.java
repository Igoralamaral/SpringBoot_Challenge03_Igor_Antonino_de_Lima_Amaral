package com.compassuol.sp.challenge.msnotification.consumer;

import com.compassuol.sp.challenge.msnotification.constants.RabbitMQConstants;
import com.compassuol.sp.challenge.msnotification.dtos.NotificationDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class NotificationConsumer {
    @RabbitListener(queues = RabbitMQConstants.QUEUE_LOGIN)
    private void consumer(Message message, NotificationDTO notificationDTO){
        log.info("Mensagem recebida: {}", notificationDTO);
    }
}
