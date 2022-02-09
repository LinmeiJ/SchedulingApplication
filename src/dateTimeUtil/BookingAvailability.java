package dateTimeUtil;

import entity.Appointment;

import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.*;

import static java.time.temporal.ChronoUnit.MINUTES;

public class BookingAvailability {

    public static Map<Integer, LocalTime> officeTime = initTimeSlots();

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

        for(int i = 0; i <= 56; i++){
            if(minute == 60){
                minute = 00;
                hr++;
            }
            initTime.add(LocalTime.of(hr, minute));
            if(hr == 22){
                break;
            }
            minute = minute + 15;
        }
        return initTime;
    }

    public static boolean checkBookingStatus(List<Appointment> scheduleList, Timestamp start, Timestamp end) {
        List<LocalTime> timeSlotsToDisplay = testing();
        for(int i = 0; i < scheduleList.size(); i++){
            long aptDuration = MINUTES.between(scheduleList.get(i).getStart().toLocalDateTime().toLocalTime(), scheduleList.get(i).getEnd().toLocalDateTime().toLocalTime()); // find duration
            long timeSlotsToken = aptDuration / 15;
            for(Map.Entry<Integer, LocalTime> entry : officeTime.entrySet()){
                if(entry.getValue().equals(scheduleList.get(i).getStart().toLocalDateTime().toLocalTime())){

                }
            }
        }
        //        Set<Map.Entry<Integer, LocalTime>> allTimeSlotsSet = allDayTimeSlots.entrySet();
//        Map.Entry<Integer, LocalTime>[] allTimeSlots = allTimeSlotsSet.toArray(new Map.Entry[allTimeSlotsSet.size()]);

//        Set<Map.Entry<Integer, LocalTime>> availableTimeSlotSet = availableAllDayTimeSlots.entrySet();
//        Map.Entry<Integer, LocalTime>[] availableTimeSlots = availableTimeSlotSet.toArray(new Map.Entry[availableTimeSlotSet.size()]);
//        int index = 0;

//        for (Appointment apt : scheduleList) {
//            long aptDuration = MINUTES.between(apt.getStart().toLocalDateTime().toLocalTime(), apt.getEnd().toLocalDateTime().toLocalTime()); // find duration
//            long timeSlotsTaken = 0;
//            if (aptDuration >= 0) {
//                timeSlotsTaken = aptDuration / 15;
//
//                for (index = 0; index < allTimeSlots.length; index++) {
//                    try {
//                        if (availableTimeSlots[index].getValue().equals(apt.getStart().toLocalDateTime().toLocalTime())) {
//                            for (int i = index; i <= index + timeSlotsTaken; i++) {
////                                availableTimeSlots[i].setValue(null);
//                                availableAllDayTimeSlots.remove(availableTimeSlots[i].getKey());
////                                    index = i + 1;
//                            }
//                            break;
//                        }
//                    } catch (NullPointerException e) {
//                        e.printStackTrace();
//                        continue;
//                    }
//                }
//            }
//
//        }
//
////        availableAllDayTimeSlots.forEach((key, value) -> System.out.println(key + " " + value));
//        if(start != null || end != null) {
//            long newAptDuration = MINUTES.between(start.toLocalDateTime().toLocalTime(), end.toLocalDateTime().toLocalTime());
//            long aptDuration = newAptDuration / 15;
//
//            List<LocalTime> splitTime = new ArrayList<>();
//
//            int incrementTime = 0;
//            for (int i = 0; i <= aptDuration; i++) {
//                splitTime.add(start.toLocalDateTime().toLocalTime().plusMinutes(incrementTime));
//                incrementTime += 15;
//            }
//
//            for (LocalTime localTime : splitTime) {
//                if (!availableAllDayTimeSlots.containsValue(localTime)) {
//                    System.out.println("Double booked, please pick a different time.");
//                    displayAvailableTime(availableAllDayTimeSlots);
//                    return true;
//                }
//            }
//        }
        return false;
    }



    private static void displayAvailableTime(Map<Integer, LocalTime> availableAllDayTimeSlots) {
        Map<LocalTime, LocalTime> availableTimeToDisplay = new HashMap<>();


        Set<Map.Entry<Integer, LocalTime>> availableTimeSlotSet = availableAllDayTimeSlots.entrySet();
        Map.Entry<Integer, LocalTime>[] availableTimeSlots = availableTimeSlotSet.toArray(new Map.Entry[availableTimeSlotSet.size()]);

        int index;
        LocalTime temp = availableTimeSlots[0].getValue();

        for (index = 1; index < availableTimeSlots.length; index++) {
            if(availableTimeSlots[index].getValue().plusMinutes(15).equals(availableTimeSlots[index - 1].getValue())){
                continue;
            }else{
                availableTimeToDisplay.put(temp, availableTimeSlots[index].getValue());
                temp = availableTimeSlots[index].getValue();
            }
        }

        availableTimeToDisplay.forEach((key, value) -> System.out.println("display time: " + key + " " + value));


//        Set<Map.Entry<Integer, LocalTime> > entries = availableAllDayTimeSlots.entrySet();
//
//        for (Map.Entry<Integer, LocalTime> entry : availableAllDayTimeSlots.entrySet()) {
//            LocalTime value = entry.getValue();
//            Integer key = entry.getKey();
//        }
//
//        Map<LocalTime, LocalTime> availableTime = new HashMap<>();
//        LocalTime value;
//        LocalTime key;
//        int placeHolder = 0;
//
////        for(int i = 0; i < availableTimeSlots.size(); i++){
//////            long aptDuration = MINUTES.between(availableAllDayTimeSlots.get(i),  availableAllDayTimeSlots.get(i+1));
////            placeHolder = availableAllDayTimeSlots.;
////            key = availableAllDayTimeSlots.get(placeHolder + 1);
////            if(i != availableAllDayTimeSlots.size() - 1){
////                if(!availableAllDayTimeSlots.get(i).plusMinutes(15).equals(availableAllDayTimeSlots.get(i+1))){
////                    value = availableAllDayTimeSlots.get(i);
////                    placeHolder = i + 1;
////                    availableTime.put(key, value);
////                }
////            }
////        }
//        availableTime.entrySet().forEach(entry -> {
//            System.out.println("Times that are available:" + entry.getKey() + " To " + entry.getValue());
//        });

    }
}
