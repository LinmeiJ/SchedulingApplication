package entity;

public class Report {
    private String month;
    private String type;
    private String country;
    private int count;
    private int ctrCount;

    public Report(String type, int count) {
        this.type = type;
        this.count = count;
    }

    public Report() {
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public int getCtrCount() {
        return ctrCount;
    }

    public void setCtrCount(int ctrCount) {
        this.ctrCount = ctrCount;
    }

//    @Override
//    public String toString(){
//        return "Type: "+ this.type + "\t\t" + "Count: " + this.count + "\n================================================================";
//    }
}
