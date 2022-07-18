package cl.versionbeta.app.accesscontrol.model;

import java.util.Date;

import cl.versionbeta.app.accesscontrol.util.Util;

public class Person {

    private Integer id;
    private String dni;
    private String firstName;
    private String lastName;
    private String gender;
    private String created;
    private String updated;

    private int status;
    private String comment;

    public Person() {
        //
    }

    public Person(String dni, String firstName, String lastName) {
        this.dni = dni;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = "1"; //"M";
        Date date = new Date();
        this.created = Util.formatDateTimeService.format(date);
        this.updated = Util.formatDateTimeService.format(date);
        this.status = 0;
        this.comment = "";
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }

    public String getDni() {
        return dni;
    }
    public void setDni(String dni) {
        this.dni = dni;
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
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }
    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }


    public int getStatus() {
        return status;
    }
    public void setStatus(int status) {
        this.status = status;
    }

    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", dni='" + dni + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", gender='" + gender + '\'' +
                ", updated='" + updated + '\'' +
                '}';
    }
}
