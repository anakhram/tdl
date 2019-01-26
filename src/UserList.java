
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class UserList {
    String name, body;
    LocalDate date, deadline;

    public UserList(String name, String body, String deadline, String date){
        this.name = name;
        this.body = body;
        DateTimeFormatter f = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        if (deadline != null) {
            try {
                this.deadline = LocalDate.parse(deadline, f);
            }
            catch (Exception e) { this.deadline=null; }
        }
        else
        {
            this.deadline = null;
        }
        this.date = LocalDate.parse(date,f);

    }

    public String getTimeLeft(){
        if (this.deadline == null){
            return "---";
        }
        Long range = ChronoUnit.DAYS.between(LocalDate.now(), this.deadline);
        return range.toString();
    }

    public String getDeadline(){
        if (this.deadline == null){
            return "Не указано";
        }
        return this.deadline.toString();
    }
}
