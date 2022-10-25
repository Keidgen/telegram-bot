package pro.sky.telegrambot.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class NotificationTask {

    @Id
    @GeneratedValue
    private Long id;
    private Long idChat;
    private String textNotification;
    private LocalDateTime dateNote;

    public NotificationTask() {
    }

    public NotificationTask(Long id, Long idChat, String textNotification, LocalDateTime dateTime) {
        this.id = id;
        this.idChat = idChat;
        this.textNotification = textNotification;
        this.dateNote = dateTime;
    }

    public NotificationTask(Long idChat, String textNotification, LocalDateTime dateTime) {
        this(null, idChat, textNotification, dateTime);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdChat() {
        return idChat;
    }

    public void setIdChat(Long idChat) {
        this.idChat = idChat;
    }

    public String getTextNotification() {
        return textNotification;
    }

    public void setTextNotification(String textNotification) {
        this.textNotification = textNotification;
    }

    public LocalDateTime getDateTime() {
        return dateNote;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateNote = dateTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationTask that = (NotificationTask) o;
        return Objects.equals(id, that.id) && Objects.equals(idChat, that.idChat) && Objects.equals(textNotification, that.textNotification) && Objects.equals(dateNote, that.dateNote);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, idChat, textNotification, dateNote);
    }

    @Override
    public String toString() {
        return "NotificationTask{" +
                "id=" + id +
                ", idChat='" + idChat + '\'' +
                ", textNotification='" + textNotification + '\'' +
                ", dateTime=" + dateNote +
                '}';
    }

}
