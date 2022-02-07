package dateUtil;

import entity.Appointment;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

import static java.time.temporal.ChronoUnit.MINUTES;

public class BookingAvailability {

    public static Map<Integer, LocalTime> initTimeSlots(){
        Map<Integer, LocalTime> treeMap = new TreeMap<>();
        int hr = 8, minute = 00;

        for(int key = 8; key <= 64; key++){
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

    public static void isDoubleBooking(List<Appointment> scheduleList, Timestamp start, Timestamp end) {
        Map<Integer, LocalTime> allDayTimeSlots = initTimeSlots();
        Set<Map.Entry<Integer, LocalTime>> allTimeSlotsSet = allDayTimeSlots.entrySet();
        Map.Entry<Integer, LocalTime>[] allTimeSlots = allTimeSlotsSet.toArray(new Map.Entry[allTimeSlotsSet.size()]);

        Map<Integer, LocalTime> availableAllDayTimeSlots = initTimeSlots();
        Set<Map.Entry<Integer, LocalTime>> availableTimeSlotSet = availableAllDayTimeSlots.entrySet();
        Map.Entry<Integer, LocalTime>[] availableTimeSlots = availableTimeSlotSet.toArray(new Map.Entry[availableTimeSlotSet.size()]);
        for (Appointment apt : scheduleList) {
            long aptDuration = MINUTES.between(apt.getStart().toLocalDateTime(), apt.getEnd().toLocalDateTime()); // find duration
            long timeSlotsTaken = aptDuration / 15; // 8:00 - 8:45 = 3  get duration

            for (int i = 0; i < allTimeSlots.length; i++) { // iterator all timeslots to find the one
                if (allTimeSlots[i].getValue().equals(apt.getStart().toLocalDateTime().toLocalTime())) {
                    System.out.println("key-->" + allTimeSlots[i].getKey() + "value->" + allTimeSlots[i].getValue());
                    System.out.println("key-->" + availableTimeSlots[i].getKey() + "value-->" + availableTimeSlots[i].getValue());
                    for (int j = i; j < i + timeSlotsTaken; j++) {
                        availableTimeSlots[i].setValue(null);
                    }

                    System.out.println("key-->" + allTimeSlots[i].getKey() + "value->" + allTimeSlots[i].getValue());
                    System.out.println("key-->" + availableTimeSlots[i].getKey() + "value-->" + availableTimeSlots[i].getValue());
                }
                break;
            }
            availableAllDayTimeSlots.forEach((key, value) -> System.out.println(key + " " + value));
            // 1. find scheduled apt in the map

            // 2. if found, increment the index by timeSlotsTaken
            // 3. save those timeslots to the new map

            // 4. continue loop and mark the timeslots until end

            // 5. loop through the new map
            // 6. list the keys that the value is null
            // 7. print the timeslot based on the key value
            // 8. convert to local time, then display on the screen.
        }
    }
}
