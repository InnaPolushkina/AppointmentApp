package appointment.model.entities;

import java.sql.Date;

public class Staffer {
    private int stafferId;
    private String firstName;
    private String lastName;
    private String password;
    private String jobPosition;
    private Date dateBirth;
    private String email;
    private String phoneNumber;
    private Staffer manager;

    public Staffer(String firstName, String lastName, String jobPosition, String email, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.jobPosition = jobPosition;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public Staffer(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Staffer(int id) {
        this.stafferId = id;
    }

    public Staffer(int stafferId, String firstName, String lastName, String password, String jobPosition,
                   Date dateBirth, String email, String phoneNumber, Staffer manager) {
        this.stafferId = stafferId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.jobPosition = jobPosition;
        this.dateBirth = dateBirth;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.manager = manager;
    }

    public Staffer(int id, String firstName, String lastName, String jobPosition, String email, String phoneNumber) {
        this.stafferId = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.jobPosition = jobPosition;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    public Staffer(int stafferId, String firstStafferName, String lastStafferName) {
        this.stafferId = stafferId;
        this.firstName = firstStafferName;
        this.lastName = lastStafferName;
    }

    public int getStafferId() {
        return stafferId;
    }

    public void setStafferId(int stafferId) {
        this.stafferId = stafferId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getJobPosition() {
        return jobPosition;
    }

    public void setJobPosition(String jobPosition) {
        this.jobPosition = jobPosition;
    }

    public Date getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(Date dateBirth) {
        this.dateBirth = dateBirth;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Staffer getManager() {
        return manager;
    }

    public void setManager(Staffer manager) {
        this.manager = manager;
    }

    @Override
    public String toString() {
        return "Staffer{" +
                "stafferId=" + stafferId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", jobPosition='" + jobPosition + '\'' +
                ", dateBirth=" + dateBirth +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", manager=" + manager +
                '}';
    }
}
