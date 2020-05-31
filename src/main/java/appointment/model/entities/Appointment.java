package appointment.model.entities;

import java.sql.Date;

public class Appointment {
    private int appointmentId;
    private User user;
    private Staffer staffer;
    private Service service;
    private Date appointmentDate;

    private SchedulePoint schedulePoint;


    public Appointment(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Appointment(User user, Staffer staffer, Service service, SchedulePoint schedulePoint) {
        this.user = user;
        this.staffer = staffer;
        this.service = service;
        this.schedulePoint = schedulePoint;
        this.appointmentDate = schedulePoint.getDateStart();
    }

    public Appointment(int appointmentId, User user, Staffer staffer,
                       Service service, Date appointmentDate) {
        this.appointmentId = appointmentId;
        this.user = user;
        this.staffer = staffer;
        this.service = service;
        this.appointmentDate = appointmentDate;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Staffer getStaffer() {
        return staffer;
    }

    public void setStaffer(Staffer staffer) {
        this.staffer = staffer;
    }

    public Service getService() {
        return service;
    }

    public void setService(Service service) {
        this.service = service;
    }

    public Date getAppointmentDate() {
        return appointmentDate;
    }

    public void setAppointmentDate(Date appointmentDate) {
        this.appointmentDate = appointmentDate;
    }

    public SchedulePoint getSchedulePoint() {
        return schedulePoint;
    }

    public void setSchedulePoint(SchedulePoint schedulePoint) {
        this.schedulePoint = schedulePoint;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentId=" + appointmentId +
                ", user=" + user +
                ", staffer=" + staffer +
                ", service=" + service +
                ", appointmentDate=" + appointmentDate +
                ", schedulePoint=" + schedulePoint +
                '}';
    }
}
