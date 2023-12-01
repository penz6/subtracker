package reminderbot;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.TimerTask;

public class CheckDate extends TimerTask {
    public void run(){
        //check the date
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd");
        LocalDateTime now = LocalDateTime.now();
        ArrayList<String> dates;
        //try to get the dates
        try {
            dates = FileManagement.getDates(now.format(dtf));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Bot.remindUser(dates);

    }
}
