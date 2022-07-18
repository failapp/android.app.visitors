package cl.versionbeta.app.accesscontrol.model;

import java.util.Date;

import cl.versionbeta.app.accesscontrol.util.Util;

public class Register {

    private Long id;
    private String date;
    private Integer personStatus;
    private String documentImage;  //base64 ..
    private String personCompany;
    private Integer type;
    private String dateCheck;
    private String comment;
    private Person person;
    private Area area;
    private Contact contact;

    public Register(){
        //
    }

    public Register(String date, Person person) {
        this.date = date;
        this.person = person;
        this.personStatus = 0;
        this.type = 1;
        this.documentImage = null;
        this.comment = "";
    }

    public Register(Person person, int type, String comment) {
        Date d = new Date();
        this.date = Util.formatDateTimeService.format(d);
        this.personStatus = 0;
        this.type = type;
        this.documentImage = null;
        this.dateCheck = Util.formatDateService.format(d);
        this.comment = comment;
        this.person = person;
    }

    public Register(Person person, int type, String comment, Area area, Contact contact) {
        Date d = new Date();
        this.date = Util.formatDateTimeService.format(d);
        this.personStatus = 0;
        this.type = type;
        this.documentImage = null;
        this.dateCheck = Util.formatDateService.format(d);
        this.comment = comment;
        this.person = person;
        this.area = area;
        this.contact = contact;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String dateTime) {
        this.date = dateTime;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPersonStatus() {
        return personStatus;
    }
    public void setPersonStatus(Integer personStatus) {
        this.personStatus = personStatus;
    }

    public String getDocumentImage() {
        return documentImage;
    }
    public void setDocumentImage(String documentImage) {
        this.documentImage = documentImage;
    }

    public Integer getType() {
        return type;
    }
    public void setType(Integer type) {
        this.type = type;
    }

    public String getDateCheck() {
        return dateCheck;
    }
    public void setDateCheck(String dateCheck) {
        this.dateCheck = dateCheck;
    }

    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }

    public Area getArea() {
        return area;
    }
    public void setArea(Area area) { this.area = area; }


    public Contact getContact() {
        return contact;
    }
    public void setContact(Contact contact) {
        this.contact = contact;
    }


    public String getPersonCompany() {
        return personCompany;
    }
    public void setPersonCompany(String personCompany) {
        this.personCompany = personCompany;
    }

    @Override
    public String toString() {
        return "Register{" +
                "date='" + date + '\'' +
                ", personStatus=" + personStatus +
                ", documentImage='" + documentImage + '\'' +
                ", personCompany='" + personCompany + '\'' +
                ", type=" + type +
                ", dateCheck='" + dateCheck + '\'' +
                ", comment='" + comment + '\'' +
                ", person=" + person +
                ", area=" + area +
                ", contact=" + contact +
                '}';
    }
}


