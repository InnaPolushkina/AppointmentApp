package appointment.model.dao;

import appointment.model.entities.*;

import java.sql.SQLException;
import java.util.List;

public interface Dao {
    void connect() throws SQLException, ClassNotFoundException;


    boolean createUser(User user);
    boolean loginUser(User user) throws SQLException, ClassNotFoundException;

    User getUserByLastName(String name);
    User getUserByFirstName(String name);

    User getUserByEmail(String email);


    void createStaffer(Staffer staffer);
    void createService(Service service);


    void createAppointment(Appointment appointment);
    void deleteAppointment(Appointment appointment);

    void updateUser(User user);
    void updateStaffer(Staffer staffer);
    void updateService(Service service);
    void updateAppointment(Appointment appointment);


    List<User> findAllUsers() throws Exception;
    List<Appointment> findAllAppointments() throws Exception;

    List<Appointment> findAppointmentsByUser(User user);

    List<Service> findAllServices();
    List<Staffer> findAllStaffers();

    User getUserById(int id);
    Staffer getStafferById(int id);
    Service getServiceById(int id);
    Appointment getAppointmentById(int id);
    SchedulePoint getSchedulePointById(int id);



    List<SchedulePoint> findSchedulePointByService(Service service);
    List<SchedulePoint> findSchedulePointByStaffer(Staffer staffer);


    void createSchedulePoint(SchedulePoint schedulePoint);
    void updateSchedule(Appointment appointment, String availableStatus);



}
