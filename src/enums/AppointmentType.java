package enums;

public enum AppointmentType {

    PlanningSession("Planning Session"),
    DeBriefing("De-Briefing");

    private final String aptType;

    AppointmentType(final String aptType) { this.aptType = aptType; }

    public String getAptType() { return aptType; }

}
