package model;

import java.util.concurrent.atomic.AtomicInteger;
import lombok.NonNull;
import service.DateAndTime;

public class Decision {

    private final DateAndTime dateAndTimeInstance = DateAndTime.getInstance();

    private String timestamp;
    private boolean decision;
    private User user;
    private String comment = "";
    private static AtomicInteger decCount = new AtomicInteger(0);
    private final int id;

    public Decision(@NonNull boolean decision, @NonNull User user, String comment) {
        this.id = decCount.incrementAndGet();
        this.decision = decision;
        this.user = user;
        if (!comment.isBlank()){
            this.comment = comment;
        }
        this.timestamp = dateAndTimeInstance.getCurrentTimeAndDate();
    }

    public int getId() {
        return id;
    }

    public String getComment() {
        return comment;
    }

    public User getUser() {
        return user;
    }

    public boolean isApproved() {
        return decision;
    }

    public String getTimestamp() {
        return timestamp;
    }
}
