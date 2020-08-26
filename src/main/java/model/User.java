package model;

import java.util.concurrent.atomic.AtomicInteger;

public class User {

    private static AtomicInteger usersCount = new AtomicInteger(0);
    private final int id;
    private String name;
    private String email;
    private volatile boolean isNotified = false;

    public User(String name, String email){
        this.id = usersCount.incrementAndGet();
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getId() { return id; }

    public boolean isNotified() {
        return isNotified;
    }

    public void setNotified(boolean notified) {
        isNotified = notified;
    }
}