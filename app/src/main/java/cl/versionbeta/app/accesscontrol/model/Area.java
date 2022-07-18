package cl.versionbeta.app.accesscontrol.model;

import java.util.Date;

import cl.versionbeta.app.accesscontrol.util.Util;

public class Area {

    private Integer id;
    private String name;
    private boolean enabled;
    private String created;
    private String updated;

    public Area() {
    }

    public Area(String name) {
        this.name = name;
        this.enabled = true;
        Date date = new Date();
        this.created = Util.formatDateTimeService.format(date);
        this.updated = Util.formatDateTimeService.format(date);
    }

    public Area(int areaId, String name) {
        this.id = areaId;
        this.name = name;
        this.enabled = true;
        Date date = new Date();
        this.created = Util.formatDateTimeService.format(date);
        this.updated = Util.formatDateTimeService.format(date);
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getEnabled() {
        return enabled;
    }
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
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


    @Override
    public String toString() {
        return this.getName();
    }


}
