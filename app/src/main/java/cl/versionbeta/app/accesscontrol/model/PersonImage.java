package cl.versionbeta.app.accesscontrol.model;

import java.util.Arrays;

public class PersonImage {

    private int id;
    private int personId;
    private byte[] personImg;
    private byte[] patentImg;

    public PersonImage() {
        //
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public byte[] getPersonImg() {
        return personImg;
    }

    public void setPersonImg(byte[] personImg) {
        this.personImg = personImg;
    }

    public byte[] getPatentImg() {
        return patentImg;
    }

    public void setPatentImg(byte[] patentImg) {
        this.patentImg = patentImg;
    }

    @Override
    public String toString() {
        return "PersonImage{" +
                "id=" + id +
                ", personId=" + personId +
                ", personImg=" + Arrays.toString(personImg).substring(1,10) +
                ", patentImg=" + Arrays.toString(patentImg).substring(1,10) +
                '}';
    }
}
