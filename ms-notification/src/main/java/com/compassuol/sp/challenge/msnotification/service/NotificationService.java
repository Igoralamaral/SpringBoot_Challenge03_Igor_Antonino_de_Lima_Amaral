package com.compassuol.sp.challenge.msnotification.service;

import com.compassuol.sp.challenge.msnotification.constants.RabbitMQConstants;
import com.compassuol.sp.challenge.msnotification.dtos.NotificationDTO;
import com.compassuol.sp.challenge.msnotification.factories.NotificationFactory;
import com.compassuol.sp.challenge.msnotification.repositories.NotificationRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Slf4j
@Component
@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    NotificationService(NotificationRepository notificationRepository){
        this.notificationRepository = notificationRepository;
    }

    @RabbitListener(queues = RabbitMQConstants.QUEUE_CREATE)
    private void consumerCreate(NotificationDTO notificationDTO) throws ParseException {
        var notification = NotificationFactory.createNotification(notificationDTO);
        try{
            this.notificationRepository.save(notification);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @RabbitListener(queues = RabbitMQConstants.QUEUE_LOGIN)
    private void consumerLogin(NotificationDTO notificationDTO) throws ParseException {
        var notification = NotificationFactory.createNotification(notificationDTO);
        try{
            this.notificationRepository.save(notification);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @RabbitListener(queues = RabbitMQConstants.QUEUE_UPDATE)
    private void consumerUpdate(NotificationDTO notificationDTO) throws ParseException {
        var notification = NotificationFactory.createNotification(notificationDTO);
        try{
            this.notificationRepository.save(notification);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }

    @RabbitListener(queues = RabbitMQConstants.QUEUE_UPDATE_PASSWORD)
    private void consumerPassword(NotificationDTO notificationDTO) throws ParseException {
        var notification = NotificationFactory.createNotification(notificationDTO);
        try{
            this.notificationRepository.save(notification);
        }catch (Exception e){
            throw new RuntimeException(e.getMessage());
        }
    }
}
