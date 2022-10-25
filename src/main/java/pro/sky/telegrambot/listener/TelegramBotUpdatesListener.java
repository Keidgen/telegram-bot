package pro.sky.telegrambot.listener;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.UpdatesListener;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.SendResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import pro.sky.telegrambot.entity.NotificationTask;
import pro.sky.telegrambot.service.NotificationTaskService;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TelegramBotUpdatesListener implements UpdatesListener {

    private Logger logger = LoggerFactory.getLogger(TelegramBotUpdatesListener.class);

    private final NotificationTaskService notificationTaskService;

    @Autowired
    private TelegramBot telegramBot;

    public TelegramBotUpdatesListener(NotificationTaskService notificationTaskService) {
        this.notificationTaskService = notificationTaskService;
    }

    @PostConstruct
    public void init() {
        telegramBot.setUpdatesListener(this);
    }

    @Override
    public int process(List<Update> updates) {

        updates.forEach(update -> {
            logger.info("Processing update: {}", update);
            // Process your updates here
            Long chatId = update.message().chat().id();
            Pattern pattern = Pattern.compile("([0-9\\.\\:\\s]{16})(\\s)([\\W+]+)");
            NotificationTask notificationTask = new NotificationTask();
            NotificationTask createdNotificationTask = new NotificationTask();

            if (update.message().text().equals("/start") ) {
                SendResponse response = telegramBot.execute(new SendMessage(chatId, "Здравствуй, путник!"));
            } else {
                Matcher matcher = pattern.matcher(update.message().text());
                if (matcher.matches()) {
                    String date = matcher.group(1);
                    String item = matcher.group(3);

                    notificationTask.setIdChat(chatId);
                    notificationTask.setTextNotification(item);
                    notificationTask.setDateTime(LocalDateTime.parse(date, DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")));

                    createdNotificationTask = notificationTaskService.createTask(notificationTask);

                }
            }

        });
        return UpdatesListener.CONFIRMED_UPDATES_ALL;
    }

    @Scheduled(cron = "0 0/1 * * * *")
    public int run() {
        try {
            LocalDateTime localDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
            List<NotificationTask> notificationTaskList;
            notificationTaskList = notificationTaskService.getNotificationTask(localDateTime);
            notificationTaskList
                    .forEach(task -> {
                        Long idChat = task.getIdChat();
                        SendResponse response = telegramBot.execute(new SendMessage(idChat, task.getTextNotification()));
                    } );
        } catch (NullPointerException ignored){
        }
        finally {
            return UpdatesListener.CONFIRMED_UPDATES_ALL;
        }
    }

}
