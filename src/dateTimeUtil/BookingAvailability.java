package dateTimeUtil;

import entity.Appointment;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.*;

import static java.time.temporal.ChronoUnit.MINUTES;

public class BookingAvailability {

    public static Map<LocalTime, LocalTime> availableTimeToDisplay;

    public static Map<Integer, LocalTime> initTimeSlots(){
        Map<Integer, LocalTime> treeMap = new TreeMap<>();
        int hr = 8, minute = 00;

        for(int key = 1; key <= 56; key++){
            if(minute == 60){
                minute = 00;
                hr++;
            }
            treeMap.put(key, LocalTime.of(hr, minute));
            if(hr == 22){
                break;
            }
            minute = minute + 15;
        }
        return treeMap;
    }

    private static List<LocalTime> testing() {
        List<LocalTime> initTime = new ArrayList<>();
        int hr = 8, minute = 00;

        for (int i = 0; i <= 56; i++) {
            if (minute == 60) {
                minute = 00;
                hr++;
            }
            initTime.add(LocalTime.of(hr, minute));
            if (hr == 22) {
                break;
            }
            minute = minute + 15;
        }
        return initTime;
    }


    public static boolean checkBookingStatus(List<Appointment> scheduleList, Timestamp start, Timestamp end) {
        availableTimeToDisplay = new HashMap<>();
        List<LocalTime> availableTimeSlots = testing();

        //getting availableTimeSlots by excluding the ones that already been booked
        for (int i = 0; i < scheduleList.size(); i++) {
            long aptDuration = MINUTES.between(scheduleList.get(i).getStart().toLocalDateTime().toLocalTime(), scheduleList.get(i).getEnd().toLocalDateTime().toLocalTime());
            long durationCount = aptDuration / 15;
            A:
            for (int j = 0; j < availableTimeSlots.size(); ) {
                if (availableTimeSlots.get(j).equals(scheduleList.get(i).getStart().toLocalDateTime().toLocalTime())) {
                    for (int k = j; k <= j + durationCount; k++) {
                        availableTimeSlots.remove(j);
                    }
                    break A;
                }
                j++;
            }
        }


        if (start != null || end != null) {
            // figure out which timeslot the user would like to book
            long newAptDuration = MINUTES.between(start.toLocalDateTime().toLocalTime(), end.toLocalDateTime().toLocalTime());
            long newAptDurationCount = newAptDuration / 15;

            // split the appoint to get ready for checking availability
            List<LocalTime> splitTime = new ArrayList<>(); // this is the list time slots user wants to book

            int incrementTime = 0;
            for (int index = 0; index <= newAptDurationCount; index++) {
                splitTime.add(start.toLocalDateTime().toLocalTime().plusMinutes(incrementTime));
                incrementTime += 15;
            }

            //check whether user wanted timeslots available in the availableTimeSlots list
            for (LocalTime time : splitTime) {
                if (!availableTimeSlots.contains(time)) {
                    displayAvailableTime(availableTimeSlots);
                    return true;
                }
            }
        }
        return false;
    }


    private static void displayAvailableTime(List<LocalTime> availableAllDayTimeSlots) {

        int index;
        LocalTime temp = availableAllDayTimeSlots.get(0);
        for (index = 0; index < availableAllDayTimeSlots.size();) {
            if (index > availableAllDayTimeSlots.size() - 2) {
                availableTimeToDisplay.put(temp, availableAllDayTimeSlots.get(index));
                break;
            }
            else if(availableAllDayTimeSlots.get(index).plusMinutes(15).equals(availableAllDayTimeSlots.get(index + 1))) {
                index++;
            }
            else {
                availableTimeToDisplay.put(temp, availableAllDayTimeSlots.get(index));

                temp = availableAllDayTimeSlots.get(index + 1);
                index++;
            }
        }
//   availableTimeToDisplay.forEach((key, value) -> System.out.println("display time: " + key + " " + value));
    }
}
