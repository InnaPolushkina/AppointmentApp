package appointment.model.dao.impl;

import appointment.model.dao.Dao;
import appointment.model.entities.*;
import org.apache.log4j.Logger;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OracleDao implements Dao {

    private static OracleDao instance = new OracleDao();
    private Connection connection;
    private PreparedStatement preparedStatement;
    private ResultSet resultSet;

    private static final Logger LOGGER = Logger.getLogger(OracleDao.class);


    public static OracleDao getInstance() {
        return instance;
    }

    public void connect() throws SQLException {
       try {
            Class.forName("oracle.jdbc.driver.OracleDriver");
            connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","SYSTEM","system");
            if ( connection.isClosed()) {
                LOGGER.error("connection to db is failed");
                throw new SQLException("connection failed !!!");
            }
        }
       catch (Exception ex) {
           LOGGER.error("error while connecting to data base");
           throw new SQLException(ex.getMessage(), "can't connect to db");
        }
    }

   // public static void main(String [] args) throws Exception{
        OracleDao oracleDao = OracleDao.getInstance();
        /*SchedulePoint schedulePoint = oracleDao.getSchedulePointById(4);
        System.out.println("schedulePoint = \n" + schedulePoint.toString());
        User user = oracleDao.getUserById(1);

        Staffer staffer = oracleDao.getStafferById(1);
        System.out.println("Staffer = \n" + staffer.toString());
        Service service = oracleDao.getServiceById(1);
        System.out.println("service = \n" + service.toString());
        Appointment appointment = new Appointment(user, staffer, service, schedulePoint);
        System.out.println("\nappointment = \n" + appointment.toString());
        oracleDao.createAppointment(appointment);*/
        //System.out.println(oracleDao.findAllStaffers().toString());

        //System.out.println(oracleDao.getAppointmentById());

        //System.out.println(oracleDao.getSchedulePointById(2));
        //System.out.println(oracleDao.findSchedulePointByService(new Service(1)));


/*        Staffer staffer = oracleDao.getStafferById(4);
        System.out.println(oracleDao.findSchedulePointByStaffer(staffer).toString());
    //}*/

    private void disconnect() {
        try {
            if(connection != null) {
                connection.close();
            }
            if(preparedStatement != null) {
                preparedStatement.close();
            }
            if(resultSet != null) {
                resultSet.close();
            }
        }
        catch (Exception ex) {
            System.out.println("disconnect " + ex);
        }
    }

    @Override
    public boolean createUser(User user) {

        try {
            connect();
            preparedStatement = connection.prepareStatement("insert into users(user_id, first_name, last_name, password, date_birth, email, phone_number) " +
                    "VALUES(USER_SEQUENSE.nextval, ?, ?, ?, ?,?, ?)");
            //preparedStatement.setInt(1,user.getUserId());
            preparedStatement.setString(1,user.getFirstName());
            preparedStatement.setString(2,user.getLastName());
            preparedStatement.setString(3, user.getPassord());
            preparedStatement.setDate(4, new Date(7852));
            preparedStatement.setString(5, user.getEmail());
            preparedStatement.setString(6, " ");
            preparedStatement.executeUpdate();
            return true;
        }
        catch (SQLException e) {
            LOGGER.error(e);
        }
        disconnect();
        return false;
    }

    private boolean validateLoginUser(User user) {

        return false;
    }

    @Override
    public boolean loginUser(User user) throws SQLException, ClassNotFoundException {
        connect();
        try {
            preparedStatement = connection.prepareStatement("select * from USERS where email = ? and PASSWORD = ?");
            preparedStatement.setString(1,user.getEmail());
            preparedStatement.setString(2, user.getPassord());

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return true;
            }

        }
        catch (SQLException e) {
            LOGGER.error(e);
        }

        disconnect();
        return false;
    }


    @Override
    public User getUserByLastName(String name) {
        try {
            connect();
            preparedStatement = connection.prepareStatement("select * from USERS where last_name = ?");
            preparedStatement.setString(1, name);


            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return parseUserFromResultSet(resultSet);
            }

        }
        catch (SQLException e) {
            LOGGER.error(e);
        }

        disconnect();
        return null;
    }

    @Override
    public User getUserByFirstName(String name) {
        try {
            connect();
            preparedStatement = connection.prepareStatement("select * from USERS where first_name = ?");
            preparedStatement.setString(1, name);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
               return parseUserFromResultSet(resultSet);
            }

        }
        catch (SQLException e) {
            LOGGER.error(e);
        }

        disconnect();
        return null;
    }

    private User parseUserFromResultSet(ResultSet rSet) throws SQLException {
        int id = rSet.getInt(1);
        String firstName = rSet.getString(2);
        String lastName = rSet.getString(3);
        String password = rSet.getString(4);
        Date date = resultSet.getDate(5);
        String email = rSet.getString(6);
        String phoneNumber = rSet.getString(7);
        User user = new User(id, firstName, lastName, password, date, email, phoneNumber);
        return user;
    }

    @Override
    public void createStaffer(Staffer staffer) {

    }

    @Override
    public void createService(Service service) {

    }

    @Override
    public void createAppointment(Appointment appointment) {

        try {
            connect();
            preparedStatement = connection.prepareStatement("insert into appointments(appointment_id, user_id, staffer_id, " +
                    "service_id, app_date, SCHEDULE_POINT_ID)\n" +
                    "VALUES (APP_SEQUENCE.nextval, ?, ?, ?, ?, ?)");

            preparedStatement.setInt(1, appointment.getUser().getUserId());
            preparedStatement.setInt(2, appointment.getStaffer().getStafferId());
            preparedStatement.setInt(3, appointment.getService().getServiceId());
            preparedStatement.setDate(4, appointment.getAppointmentDate());
            preparedStatement.setInt(5, appointment.getSchedulePoint().getSchedulePointId());

            preparedStatement.executeUpdate();
            disconnect();
            updateSchedule(appointment, "busy");
        }
        catch (SQLException e) {
            LOGGER.error(e);
        }
    }

    @Override
    public void updateSchedule(Appointment appointment, String availableStatus) {
        try {
            connect();
//free если время свободно
            preparedStatement = connection.prepareStatement("update schedule \n" +
                    "set available_status = ?\n" +
                    "where SCHEDULE_POINT_ID = ?");
            preparedStatement.setString(1, availableStatus);
            preparedStatement.setInt(2, appointment.getSchedulePoint().getSchedulePointId());

            preparedStatement.executeUpdate();

        }
        catch (SQLException e) {
            LOGGER.error(e);
        }
        disconnect();
    }

    @Override
    public void updateUser(User user) {

    }

    @Override
    public void updateStaffer(Staffer staffer) {

    }

    @Override
    public void updateService(Service service) {

    }

    @Override
    public void updateAppointment(Appointment appointment) {

    }

    @Override
    public List<User> findAllUsers() throws Exception{
        connect();

        disconnect();
        return null;
    }

    @Override
    public List<Appointment> findAllAppointments() throws Exception{
        connect();
        List<Appointment> appointmentList = null;
        try {
            appointmentList = new ArrayList<Appointment>();
            preparedStatement = connection.prepareStatement("select appointment_id, user_id, staffer_id, service_id, app_date from appointments");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                appointmentList.add(parseAppointment(resultSet));
            }

        }
        catch (SQLException ex) {
            System.out.println(ex);
        }


        disconnect();
        return appointmentList;
    }

    private Appointment parseAppointment(ResultSet set) throws SQLException {
        int id = set.getInt(1);
        int userId = set.getInt(2);
        int stafferId = set.getInt(3);
        int serviceId = set.getInt(4);
        Date date = set.getDate(5);
        User user = new User(userId);
        Service service = new Service(serviceId);
        Staffer staffer = new Staffer(stafferId);
        SchedulePoint schedulePoint = new SchedulePoint(set.getInt(6));
        Appointment appointment = new Appointment(id,user, staffer, service, date);
        appointment.setSchedulePoint(schedulePoint);
        return appointment;
    }

    @Override
    public List<Appointment> findAppointmentsByUser(User user) {

        List<Appointment> appointmentList = null;
        try {
            connect();
            appointmentList = new ArrayList<Appointment>();
            preparedStatement = connection.prepareStatement("select u.first_name, u.last_name, st.first_name, st.last_name, ser.name, app.app_date, to_char(sh.date_start, 'hh24:mi') ttime_start, sh.SCHEDULE_POINT_ID " +
                    "from appointments app, users u, services ser, staffers st, schedule sh " +
                    "where app.user_id = u.user_id " +
                    "and ser.service_id = app.service_id " +
                    "and st.staffer_id = app.staffer_id " +
                    "  and app.schedule_point_id = sh.schedule_point_id " +
                    "and app.user_id = ? " +
                    "order by app.app_date desc");
            preparedStatement.setInt(1, user.getUserId());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                appointmentList.add(parseForMainPage(resultSet));
            }
            return appointmentList;
        }
        catch (SQLException ex) {
            System.out.println(ex);
        }


        disconnect();
        return appointmentList;
    }

    private Appointment parseForMainPage(ResultSet rSet) throws SQLException {
        String firstUserName = rSet.getString(1);
        String lastUserName = rSet.getString(2);
        String firstStafferName = rSet.getString(3);
        String lastStafferName = rSet.getString(4);
        String serviceName = rSet.getString(5);
        Date date = rSet.getDate(6);
        String timeStart = rSet.getString(7);
        int schedId = rSet.getInt(8);
        User user = new User(firstUserName, lastUserName);
        Staffer staffer = new Staffer(firstStafferName, lastStafferName);
        Service service = new Service(serviceName);
        SchedulePoint schedulePoint = new SchedulePoint(schedId, timeStart);
        Appointment appointment = new Appointment(1,user, staffer, service,date );
        appointment.setSchedulePoint(schedulePoint);
        return appointment;
    }

    @Override
    public User getUserByEmail(String email) {
        try {
            connect();
            preparedStatement = connection.prepareStatement("select * from USERS where email = ?");
            preparedStatement.setString(1, email);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return parseUserFromResultSet(resultSet);
            }

        }
        catch (SQLException e) {
            LOGGER.error(e);
        }

        disconnect();
        return null;
    }

    @Override
    public List<Service> findAllServices() {
        List<Service> serviceList = null;
        try {
            connect();
            serviceList = new ArrayList<Service>();
            preparedStatement = connection.prepareStatement("select service_id, name, description, duration, price from services");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                serviceList.add(parseServiceFromResultSet(resultSet));
            }
            return serviceList;
        }
        catch (SQLException ex) {
            System.out.println(ex);
        }


        disconnect();
        return serviceList;
    }

    private Service parseServiceFromResultSet(ResultSet resSet) throws SQLException {
        int id = resSet.getInt(1);
        String name = resSet.getString(2);
        String description = resSet.getString(3);
        int duration = resSet.getInt(4);
        int price = resSet.getInt(5);
        return new Service(id, name, description, duration, price);
    }

    @Override
    public List<Staffer> findAllStaffers() {
        List<Staffer> stafferList = null;
        try {
            connect();
            stafferList = new ArrayList<Staffer>();
            preparedStatement = connection.prepareStatement("select STAFFER_ID, first_name, last_name, job_position, email, phone_number from staffers");
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                stafferList.add(parseStafferFromResultSet(resultSet));
            }
            return stafferList;
        }
        catch (SQLException ex) {
            System.out.println(ex);
        }

        disconnect();
        return stafferList;
    }

    private Staffer parseStafferFromResultSet(ResultSet resultSet) throws SQLException{
        int id = resultSet.getInt(1);
        String firstName = resultSet.getString(2);
        String lastName = resultSet.getString(3);
        String jobPosition = resultSet.getString(4);
        String email = resultSet.getString(5);
        String phoneNumber = resultSet.getString(6);
        return new Staffer(id, firstName, lastName, jobPosition, email, phoneNumber);
    }

    @Override
    public User getUserById(int id) {
        try {
            connect();
            preparedStatement = connection.prepareStatement("select * from USERS where user_id = ?");
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return parseUserFromResultSet(resultSet);
            }

        }
        catch (SQLException e) {
            LOGGER.error(e);
        }

        disconnect();
        return null;
    }

    @Override
    public Staffer getStafferById(int id) {
        try {
            connect();
            preparedStatement = connection.prepareStatement("select * from STAFFERS where STAFFER_ID = ?");
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return parseStafferFromResultSet(resultSet);
            }

        }
        catch (SQLException e) {
            LOGGER.error(e);
        }

        disconnect();
        return null;
    }

    @Override
    public Service getServiceById(int id) {
        try {
            connect();
            preparedStatement = connection.prepareStatement("select * from SERVICES where SERVICE_ID = ?");
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return parseServiceFromResultSet(resultSet);
            }

        }
        catch (SQLException e) {
            LOGGER.error(e);
        }

        disconnect();
        return null;
    }

    @Override
    public Appointment getAppointmentById(int id) {
        try {
            connect();
            preparedStatement = connection.prepareStatement("select appointment_id, user_id, staffer_id, service_id, app_date, schedule_point_id from APPOINTMENTS where APPOINTMENT_ID = ?");
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return parseAppointment(resultSet);
            }

        }
        catch (SQLException e) {
            LOGGER.error(e);
        }

        disconnect();
        return null;
    }

    @Override
    public SchedulePoint getSchedulePointById(int id) {
        try {
            connect();
            /*preparedStatement = connection.prepareStatement("select SCHEDULE_POINT_ID, " +
                    "to_date(date_start) ddate_start, to_char(date_start, 'hh24:mi') ttime_start,\n" +
                    "to_date(date_end) ddate_end, to_char(date_end, 'hh24:mi') ttime_end,\n" +
                    "SERVICE_ID, STAFFER_ID, AVAILABLE_STATUS from SCHEDULE where SCHEDULE_POINT_ID = ?");*/
            preparedStatement = connection.prepareStatement("select sh.SCHEDULE_POINT_ID, to_date(sh.date_start) ddate_start, to_char(sh.date_start, 'hh24:mi') ttime_start, " +
                    "to_date(date_end) ddate_end, to_char(date_end, 'hh24:mi') ttime_end, " +
                    "sh.service_id, sh.staffer_id, sh.AVAILABLE_STATUS, ser.name, st.first_name, st.last_name " +
                    "from SCHEDULE sh, services ser, staffers st " +
                    "where sh.SCHEDULE_POINT_ID = ? " +
                    "and ser.service_id = sh.service_id " +
                    "and st.staffer_id = sh.staffer_id");
            preparedStatement.setInt(1, id);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return parseSchedulePointFromResultSet(resultSet);
            }

        }
        catch (SQLException e) {
            LOGGER.error(e);
        }

        disconnect();
        return null;
    }

    private SchedulePoint parseSchedulePointFromResultSet(ResultSet resultSet) throws SQLException{
        int id = resultSet.getInt(1);
        Date dateStart = resultSet.getDate(2);
        String timeStart = resultSet.getString(3);
        Date dateEnd = resultSet.getDate(4);
        String timeEnd = resultSet.getString(5);
        int serviceId = resultSet.getInt(6);
        int stafferId = resultSet.getInt(7);
        //Service service = new Service(resultSet.getInt(6));
       // Staffer staffer = new Staffer(resultSet.getInt(7));
        String status = resultSet.getString(8);
        String serviceName = resultSet.getString(9);
        System.out.println("\nservice name " + serviceName);
        String firstStafferName = resultSet.getString(10);
        String lastStafferName= resultSet.getString(11);
        Service service = new Service(serviceId, serviceName);
        Staffer staffer = new Staffer(stafferId, firstStafferName, lastStafferName);
        SchedulePoint schedulePoint = new SchedulePoint(id,dateStart, timeStart, timeEnd, dateEnd, service, staffer, status);
        System.out.println(schedulePoint.toString());
        return schedulePoint;
    }

    @Override
    public List<SchedulePoint> findSchedulePointByService(Service service) {
        List<SchedulePoint> schedulePoints = null;
        try {
            connect();
            schedulePoints = new ArrayList<SchedulePoint>();
            preparedStatement = connection.prepareStatement("select sh.SCHEDULE_POINT_ID, to_date(sh.date_start) ddate_start, to_char(sh.date_start, 'hh24:mi') ttime_start, " +
                    "to_date(date_end) ddate_end, to_char(date_end, 'hh24:mi') ttime_end, " +
                    "sh.service_id, sh.staffer_id, sh.AVAILABLE_STATUS, ser.name, st.first_name, st.last_name " +
                    "from SCHEDULE sh, services ser, staffers st " +
                    "where sh.service_id = ? " +
                    "and ser.service_id = sh.service_id " +
                    "and st.staffer_id = sh.staffer_id");
            preparedStatement.setInt(1,service.getServiceId());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                schedulePoints.add(parseSchedulePointFromResultSet(resultSet));
            }
            return schedulePoints;
        }
        catch (SQLException ex) {
            System.out.println(ex);
        }

        disconnect();
        return schedulePoints;
    }

    @Override
    public List<SchedulePoint> findSchedulePointByStaffer(Staffer staffer) {
        List<SchedulePoint> schedulePoints = null;
        try {
            connect();
            schedulePoints = new ArrayList<SchedulePoint>();
            preparedStatement = connection.prepareStatement("select sh.SCHEDULE_POINT_ID, to_date(sh.date_start) ddate_start, to_char(sh.date_start, 'hh24:mi') ttime_start, " +
                    "to_date(date_end) ddate_end, to_char(date_end, 'hh24:mi') ttime_end, " +
                    "sh.service_id, sh.staffer_id, sh.AVAILABLE_STATUS, ser.name, st.first_name, st.last_name " +
                    "from SCHEDULE sh, services ser, staffers st " +
                    "where st.staffer_id = ? " +
                    "and ser.service_id = sh.service_id " +
                    "and st.staffer_id = sh.staffer_id");
            preparedStatement.setInt(1,staffer.getStafferId());
            resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                schedulePoints.add(parseSchedulePointFromResultSet(resultSet));
            }
            return schedulePoints;
        }
        catch (SQLException ex) {
            LOGGER.error(ex);
        }

        disconnect();
        return schedulePoints;
    }

    @Override
    public void createSchedulePoint(SchedulePoint schedulePoint) {
        try {
            connect();
            preparedStatement = connection.prepareStatement("insert into SCHEDULE(schedule_point_id, date_start, date_end, service_id, staffer_id, available_status) \n" +
                    "VALUES(schedule_sequence.nextval, ?, ?, ?, ?, 'free');");
            preparedStatement.setDate(1, schedulePoint.getDateStart());
            preparedStatement.setDate(2, schedulePoint.getDateEnd());
            preparedStatement.setInt(3, schedulePoint.getService().getServiceId());
            preparedStatement.setInt(4, schedulePoint.getStaffer().getStafferId());
            preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            LOGGER.error(e);
        }
        disconnect();
    }

    @Override
    public void deleteAppointment(Appointment appointment) {
        try {
            updateSchedule(appointment, "free");
            connect();
            preparedStatement = connection.prepareStatement("delete from appointments where appointment_id = ?");
            preparedStatement.setInt(1, appointment.getAppointmentId());
            preparedStatement.executeUpdate();
            disconnect();
        }
        catch (SQLException e) {
            LOGGER.error(e);
        }
    }
}
