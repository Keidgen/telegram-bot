package pro.sky.telegrambot.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.entity.NotificationTask;
import pro.sky.telegrambot.repository.NotificationTaskRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class NotificationTaskService {

    private final Logger logger = LoggerFactory.getLogger(NotificationTaskService.class);

    private final NotificationTaskRepository notificationTaskRepository;

    public NotificationTaskService(NotificationTaskRepository notificationTaskRepository) {
        this.notificationTaskRepository = notificationTaskRepository;
    }

    public NotificationTask createTask(NotificationTask notificationTask) {
        logger.info("Was invoked method for create task");
        return notificationTaskRepository.save(notificationTask);
    }

    public List<NotificationTask> getNotificationTask(LocalDateTime localDateTime){
        logger.info("Was invoked method for get tasks");
        return notificationTaskRepository.findNotificationTaskByDateNote(localDateTime);
    }
}
