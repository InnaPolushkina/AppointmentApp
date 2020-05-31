package appointment.model.dao;

import appointment.model.entities.Staffer;

public interface StafferDao {
    boolean createStaffer(Staffer staffer);
    boolean loginStaffer(Staffer staffer);
    Staffer getStafferByLastName(String name);
    String  getStafferByFirstName(String name);
}
