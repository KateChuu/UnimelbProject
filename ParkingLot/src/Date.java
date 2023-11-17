/*
 * @author: Man Hua, Chu
 * @Email: manhuac@unimelb.student.edu.au
 * @StudentNo.: 1403798
 */

import java.time.format.DateTimeFormatter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

public class Date{
    private LocalDate entryDate;
    private LocalDate exitDate;
    private LocalDateTime entryDatetime;
    private LocalDateTime exitDatetime;
    private static final int MIN_YEAR = 1970;
    private static final int MAX_YEAR = 2099;

    // constructor
    Date() {
        this.entryDatetime = LocalDateTime.now();
        this.exitDatetime = LocalDateTime.now();
    }

    public LocalDate getEntryDate() {
        return this.entryDate;
    }
    public LocalDate getExitDate() {
        return this.exitDate;
    }
    public LocalDateTime getEntryDatetime() {
        return this.entryDatetime;
    }
    public LocalDateTime getExitDatetime() {
        return this.exitDatetime;
    }
    public void setEntryDate(String date) {
        this.entryDate = parseDate(date);
    }
    public void setExitDate(String date) {
        this.exitDate = parseDate(date);
    }
    public void setEntryDatetime(String time) {
        this.entryDatetime = parse(getEntryDate(), time);
    }
    public void setExitDatetime(String time) {
        this.exitDatetime = parse(getExitDate(), time);
    }

    // parse the date entered by user to localDate object
    public LocalDate parseDate(String date){
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate validDate;
        try {
            validDate = LocalDate.parse(date, format);

        }catch(Exception e) {
            System.out.println("Incorrect date format, please enter date in yyyy-MM-dd format again!");
            return null;
        }
        if(validDate.getYear() < MIN_YEAR || validDate.getYear() > MAX_YEAR){
            System.out.println("Incorrect date format, please enter date in yyyy-MM-dd format again between 1970-01-01 and 2099-12-31!");
            return null;
        }
        return validDate;
    }

    // parse the datetime entered by user to localDateTime object
    public LocalDateTime parse(LocalDate date, String time){
        // convert date to a string
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String str = dateFormat.format(date);

        DateTimeFormatter datetimeFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        // just for the case when user enters 24:00
        if(time.substring(0, 2).equals("24")) {
            System.out.println("Incorrect time format, please enter time in HH:mm format again!");
            return null;
        }
        try {
            LocalDateTime validDatetime = LocalDateTime.parse(str + " " + time, datetimeFormat);
            return validDatetime;
        }catch (Exception e) {
            System.out.println("Incorrect time format, please enter time in HH:mm format again!");
            return null;
        }
    }

    // compare whether exit datetime is earlier than entry datetime
    public boolean compareDates() {
        if(getExitDate().isBefore(getEntryDate())) {
            return false;
        }
        return true;
    }

    // calculate the difference in hours between entry datetime and exit datetime
    public int getDifferenceInHours(){
        long hours = getEntryDatetime().until(getExitDatetime(), ChronoUnit.HOURS);
        if(getExitDatetime().isBefore(getEntryDatetime())) {
            return -1;
        }
		if(getExitDatetime().isEqual(getEntryDatetime())) {
            return 0;
        }
        // if the remaining minute is less than 60 min, round it to 1 hour 
		if(getEntryDatetime().getMinute() != getExitDatetime().getMinute()) {
			hours++;
		}      
		return (int)hours;
    }
}
