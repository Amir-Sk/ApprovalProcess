package service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateAndTime {

    // Volatile and not synchronized because I do not expect a deadlock as well to no update/write needed,
    // only initialization and synchronization that would happen naturally when the variable is accessed
    // due to CPU 'memory barrier' command that accompany the executions of the threads.
    private static volatile DateAndTime dateAndTimeInstance;
    private DateTimeFormatter dtf;

    private DateAndTime(){
        if (dateAndTimeInstance != null){
            throw new RuntimeException("Please use getInstance() in order to get the single instance.");
        }
        dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
    }

    public static DateAndTime getInstance(){
        if (dateAndTimeInstance == null){
            dateAndTimeInstance = new DateAndTime();
        }
        return dateAndTimeInstance;
    }

    public String getCurrentTimeAndDate(){
        return dtf.format(LocalDateTime.now());
    }
}