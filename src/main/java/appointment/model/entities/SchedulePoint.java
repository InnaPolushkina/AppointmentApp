package appointment.model.entities;

import java.sql.Date;

public class SchedulePoint {
    private int schedulePointId;
    private Date dateStart;
    private String timeStart;
    private String timeEnd;
    private Date dateEnd;
    private Service service;
    private Staffer staffer;
    private String availableStatus;


    public SchedulePoint(int schedulePointId,
                         Date dateStart, String timeStart,
                         String timeEnd, Date dateEnd,
                         Service service,
                         Staffer staffer,
                         String availableStatus) {
        this.schedulePointId = schedulePointId;
        this.dateStart = dateStart;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.dateEnd = dateEnd;
        this.service = service;
        this.staffer = staffer;
        this.availableStatus = availableStatus;
    }

    public SchedulePoint(Date dateStart, String timeStart,
                         String timeEnd, Date dateEnd,
                         Service service,
                         Staffer staffer,
                         String availableStatus) {
        this.dateStart = dateStart;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
        this.dateEnd = dateEnd;
        this.service = service;
        this.staffer = staffer;
        this.availableStatus = availableStatus;
    }

    public SchedulePoint(int id) {
        this.schedulePointId = id;
    }

    public SchedulePoint(int schedId, String timeStart) {
        this.schedulePointId = schedId;
        this.timeStart = timeStart;
    }

    public int getSchedulePointId() {
        return schedulePointId;
    }

    public void setSchedulePointId(int schedulePointId) {
        this.schedulePointId = schedulePointId;
    }

    public Date getDateStart() {
        return dateStart;
    }

    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public Date getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Staffer getStaffer() {
        return staffer;
    }

    public void setStaffer(Staffer staffer) {
        this.staffer = staffer;
    }

    public String getAvailableStatus() {
        return availableStatus;
    }

    public void setAvailableStatus(String availableStatus) {
        this.availableStatus = availableStatus;
    }

    @Override
    public String toString() {
        return "SchedulePoint{" +
                "schedulePointId=" + schedulePointId +
                ", dateStart=" + dateStart +
                ", timeStart='" + timeStart + '\'' +
                ", timeEnd='" + timeEnd + '\'' +
                ", dateEnd=" + dateEnd +
                ", \nservice=" + service +
                ", staffer=" + staffer +
                ", availableStatus=" + availableStatus +
                '}';
    }
}
