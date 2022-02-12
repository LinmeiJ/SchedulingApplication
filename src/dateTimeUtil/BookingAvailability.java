package dateTimeUtil;

import entity.Appointment;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.*;

import static java.time.temporal.ChronoUnit.MINUTES;

/**
 * This contains logics to determine whether a new or updated appointment is already been booked. All the times in used in this class are based on EST timezone
 *
 * @Author Linmei M.
 */
public class BookingAvailability {

    /**
     * initialize a map to hold avaliable time that will be displayed on user screen when they
     * selected time is already booked.
     */
    public static Map<LocalTime, LocalTime> availableTimeToDisplay;

    /**
     * Initialize time slots based on office hour from EST 8am to 10pm with 15 minutes increase rule
     * @return a list of regular office time slots
     */
    private static List<LocalTime> initializedTimeSlots() {
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

    /**
     * This method contains all logics that determine the double booking status
     * @param scheduleList a list that comes from the database and already been scheduled
     * @param start the user input start localTime
     * @param end the user input ent localTime
     * @return true if time is already booked, otherwise returns false.
     */
    public static boolean checkBookingStatus(List<Appointment> scheduleList, Timestamp start, Timestamp end) {
        availableTimeToDisplay = new HashMap<>();
        List<LocalTime> availableTimeSlots = initializedTimeSlots();

        filterOutAlReadyBookedTimeSlots(scheduleList, availableTimeSlots);

        if (start != null || end != null) {
            List<LocalTime> splitTime = getUserWantedTimeSlots(start, end);

            if (checksIfDoubleBooked(availableTimeSlots, splitTime)) return true;
        }
        return false;
    }

    /**
     * This method filters out already scheduled time slots
     * @param scheduleList a set of times slots that already token
     * @param availableTimeSlots all time slots from 8am to 10pm
     */
    private static void filterOutAlReadyBookedTimeSlots(List<Appointment> scheduleList, List<LocalTime> availableTimeSlots) {
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
    }

    /**
     * This method checks whether the time slot is already token
     * @param availableTimeSlots the available times slots for user to book
     * @param splitTime a time slots a user wishes to book
     * @return ture if the time user wants is already token, otherwise return false.
     */
    private static boolean checksIfDoubleBooked(List<LocalTime> availableTimeSlots, List<LocalTime> splitTime) {
        //check whether user wanted timeslots available in the availableTimeSlots list
        for (LocalTime time : splitTime) {
            if (!availableTimeSlots.contains(time)) {
                displayAvailableTime(availableTimeSlots);
                return true;
            }
        }
        return false;
    }

    /**
     * This methods format the time slots based user input
     * @param start start localTime
     * @param end end localTime
     * @return a list of time slots that user wishes to book.
     */
    private static List<LocalTime> getUserWantedTimeSlots(Timestamp start, Timestamp end) {
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
        return splitTime;
    }

    /**
     * This method formats the available times slots to used for displaying on the user interface
     * @param availableAllDayTimeSlots all available times slots to the user
     */
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
