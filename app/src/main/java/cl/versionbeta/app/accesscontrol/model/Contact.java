package cl.versionbeta.app.accesscontrol.model;

import java.util.Date;

import cl.versionbeta.app.accesscontrol.util.Util;

public class Contact {

    private Integer id;
    private String fullName;
    private boolean enabled;
    private Area area;

    public Contact() {
    }

    public Contact(String fullName) {
        this.fullName = fullName;
        this.enabled = true;
    }

    public Contact(int id, String fullName) {
        this.id = id;
        this.fullName = fullName;
        this.enabled = true;
    }

    public Contact(Integer id, String fullName) {
        this.id = id;
        this.fullName = fullName;
        this.enabled = true;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    @Override
    public String toString() {
        return this.getFullName();
    }
}
