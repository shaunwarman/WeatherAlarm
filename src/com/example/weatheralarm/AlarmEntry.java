package com.example.weatheralarm;

public class AlarmEntry {
	long pos;
	String time;
	String isSunday = "false";
	String isMonday = "false";
	String isTuesday = "false";
	String isWednesday = "false";
	String isThursday = "false";
	String isFriday = "false";
	String isSaturday = "false";
	String isPrecipitation = "false";
	String isHourly = "false";
    String isRepeat = "false";
    String meridiem;
    String on_or_off = "false";
	
	public AlarmEntry() {}

    public AlarmEntry(long pos, String time, String sunday, String monday,
                      String tuesday, String wednesday, String thursday,
                      String friday, String saturday, String isPrecipitation,
                      String isHourly, String isRepeat, String meridiem,
                      String on_or_off) {
        this.pos = pos;
        this.time = time;
        this.isSunday = sunday;
        this.isMonday = monday;
        this.isTuesday = tuesday;
        this.isWednesday = wednesday;
        this.isThursday = thursday;
        this.isFriday = friday;
        this.isSaturday = saturday;
        this.isPrecipitation = isPrecipitation;
        this.isHourly = isHourly;
        this.isRepeat = isRepeat;
        this.meridiem = meridiem;
        this.on_or_off = on_or_off;
    }

    public void setPos(long id) {
		this.pos = id;
	}
	
	public long getPos() {
		return this.pos;
	}
	
	public void setTime(String time) {
		this.time = time;
	}
	
	public String getTime() {
		return this.time;
	}
	
	public void setIsSunday(String isSunday) {
		this.isSunday = isSunday;
	}
	
	public String getIsSunday() {
		return this.isSunday;
	}
	
	public void setIsMonday(String isMonday) {
		this.isMonday = isMonday;
	}
	
	public String getIsMonday() {
		return this.isMonday;
	}

    public String getIsTuesday() {
        return this.isTuesday;
    }

    public void setIsTuesday(String isTuesday) {
        this.isTuesday = isTuesday;
    }

    public String getIsWednesday() {
        return this.isWednesday;
    }

    public void setIsWednesday(String isWednesday) {
        this.isWednesday = isWednesday;
    }

    public String getIsThursday() {
        return this.isThursday;
    }

    public void setIsThursday(String isThursday) {
        this.isThursday = isThursday;
    }

    public String getIsFriday() {
        return this.isFriday;
    }

    public void setIsFriday(String isFriday) {
        this.isFriday = isFriday;
    }

    public String getIsSaturday() {
        return this.isSaturday;
    }

    public void setIsSaturday(String isSaturday) {
        this.isSaturday = isSaturday;
    }

    public String getIsPrecipitation() {
        return this.isPrecipitation;
    }

    public void setIsPrecipitation(String isPrecipitation) {
        this.isPrecipitation = isPrecipitation;
    }

    public String getIsHourly() {
        return this.isHourly;
    }

    public void setIsHourly(String isHourly) {
        this.isHourly = isHourly;
    }

    public String getIsRepeat() {
        return this.isRepeat;
    }

    public void setIsRepeat(String isRepeat) {
        this.isRepeat = isRepeat;
    }

    public String getMeridiem() {
        return this.meridiem;
    }

    public void setMeridiem(String meridiem) {
        this.meridiem = meridiem;
    }

    public String getOnOrOff() {
        return this.on_or_off;
    }

    public void setOnOrOff(String on_or_off) {
        this.on_or_off = on_or_off;
    }

}
