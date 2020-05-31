package appointment.controlles;

import appointment.model.dao.Dao;
import appointment.model.dao.impl.OracleDao;
import appointment.model.entities.*;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.sql.SQLException;
import java.util.List;


@Controller
public class AppointmentController {
    private Appointment appointment;
    private Dao oracleDao = OracleDao.getInstance();
    private static final Logger LOGGER = Logger.getLogger(AppointmentController.class);



    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ModelAndView allAppointments() {
        /*ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("appointments");
        modelAndView.addObject("appointment", appointment);
        return modelAndView;*/
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        LOGGER.info("start application  . . . . . . . . . . . .");
        /*if (userDao.connect()) {
            modelAndView.addObject("error", "Connected . . .");
        }*/

        return modelAndView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView loginPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public ModelAndView editAppointment(@RequestParam int id) throws Exception {
        List<Appointment> appointmentList = oracleDao.findAllAppointments();
       /* Map<String, String> map = new HashMap<String, String>();
        String msg = OracleDao.getInstance().getSysdateFromDB();
        map.put("msg", "qwsedrftghjk");*/
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("editAppointment");

        modelAndView.addObject(appointmentList);
        return modelAndView;
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ModelAndView loginUser(@RequestParam String email, @RequestParam String password,
                                  HttpSession httpSession, Model model) {
        User user = new User(email, password);
        try {
            if(oracleDao.loginUser(user)) {
                User appUser = oracleDao.getUserByEmail(user.getEmail());
                List<Appointment> appointmentList = oracleDao.findAppointmentsByUser(appUser);
                ModelAndView modelAndView = new ModelAndView();
                modelAndView.setViewName("appointments");
                modelAndView.addObject("appointmentList", appointmentList);
                modelAndView.addObject("user", appUser);
                if(appointmentList == null || appointmentList.size() == 0) {
                    modelAndView.addObject("errorListMsg", "You don't have any appointments now. Go to all services page for planing");
                }
                httpSession.setAttribute("user", appUser.getUserId());
                return modelAndView;
            } else {
                ModelAndView modelAndView = new ModelAndView();
                modelAndView.setViewName("login");
                modelAndView.addObject("error", "Login failed !");
                return modelAndView;
            }
        } catch (SQLException e) {
            return serverError();
        } catch (ClassNotFoundException e) {
            return serverError();
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ModelAndView register(@RequestParam String firstName, @RequestParam String lastName,
                                 @RequestParam String email, @RequestParam String password,
                                 HttpSession httpSession, Model model) {
        User user = new User(firstName, lastName, email, password);
        //User user = new User(email, password);
        if(oracleDao.createUser(user)) {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("login");
            LOGGER.debug("new user was successfully registered in the system");
            return modelAndView;
        }
        else {
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.setViewName("register");
            modelAndView.addObject("error", "Server error while registering . . . ");
            return modelAndView;
        }
    }

    @RequestMapping(value = "/register", method = RequestMethod.GET)
    public ModelAndView register() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("register");
        return modelAndView;
    }

    private ModelAndView serverError() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        modelAndView.addObject("error", "Server error while login . . . ");
        return modelAndView;
    }


    @RequestMapping(value = "/services", method = RequestMethod.GET)
    public ModelAndView allServices() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("services");
        List<Service> serviceList = oracleDao.findAllServices();
        modelAndView.addObject("serviceList", serviceList);
        return modelAndView;
    }

    @RequestMapping(value = "/services", method = RequestMethod.POST)
    public ModelAndView allServices(@RequestParam int userId) {
        User user = oracleDao.getUserById(userId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("services");
        List<Service> serviceList = oracleDao.findAllServices();
        modelAndView.addObject("serviceList", serviceList);
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @RequestMapping(value = "/staffers", method = RequestMethod.GET)
    public ModelAndView allStaffers() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("staffers");
        List<Staffer> stafferList = oracleDao.findAllStaffers();
        modelAndView.addObject("stafferList", stafferList);
        return modelAndView;
    }

    @RequestMapping(value = "/scheduler", method = RequestMethod.POST)
    public ModelAndView editPage(@RequestParam int id, @RequestParam int userId) {
        User user = oracleDao.getUserById(userId);
        Service service = oracleDao.getServiceById(id);
        List<SchedulePoint> schedulePointList = oracleDao.findSchedulePointByService(service);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("scheduler");
        modelAndView.addObject("schedulePointList", schedulePointList);
        modelAndView.addObject("user", user);
        if(schedulePointList == null || schedulePointList.size() == 0) {
            modelAndView.addObject("errorMsg", "Scheduler does not have any date for creating appointment with this service, sorry .");
        }
        return modelAndView;
    }

    @RequestMapping(value = "/createApp", method = RequestMethod.POST)
    public ModelAndView createApp(@RequestParam int id, @RequestParam int userId, HttpSession httpSession) {
        //Object object = httpSession.getAttribute("user");
        //int userId = (int) object;

        User user = oracleDao.getUserById(userId);
        SchedulePoint schedulePoint = oracleDao.getSchedulePointById(id);
        Appointment appointment = new Appointment(user,schedulePoint.getStaffer(), schedulePoint.getService(),schedulePoint);
        oracleDao.createAppointment(appointment);
        List<Appointment> appointmentList = oracleDao.findAppointmentsByUser(user);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("appointments");
        modelAndView.addObject("appointmentList", appointmentList);
        modelAndView.addObject("errorListMsg", "<script>\n" +
                "    alert( 'New Appointment was successfully created' );\n" +
                "  </script>");
        modelAndView.addObject("user", user);
        return modelAndView;
    }


    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public ModelAndView deleteAppointment(@RequestParam int id) {
        Appointment appointment = oracleDao.getAppointmentById(id);
        oracleDao.deleteAppointment(appointment);
        User user = oracleDao.getUserById(appointment.getUser().getUserId());
        List<Appointment> appointmentList = oracleDao.findAppointmentsByUser(user);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("appointments");
        modelAndView.addObject("appointmentList", appointmentList);
        modelAndView.addObject("errorListMsg", "<script>\n" +
                "    alert( 'Appointment was successfully deleted from your list' );\n" +
                "  </script>");
        return modelAndView;
    }


    @RequestMapping(value = "/schedulerByStaffer", method = RequestMethod.POST)
    public ModelAndView schedulerByStaffer(@RequestParam int id) {
        Staffer staffer = oracleDao.getStafferById(id);
        List<SchedulePoint> schedulePointList = oracleDao.findSchedulePointByStaffer(staffer);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("scheduler");
        modelAndView.addObject("schedulePointList", schedulePointList);
        if(schedulePointList == null || schedulePointList.size() == 0) {
            modelAndView.addObject("errorMsg", "Scheduler does not have any date for creating appointment with this service, sorry .");
        }
        return modelAndView;
    }
}
