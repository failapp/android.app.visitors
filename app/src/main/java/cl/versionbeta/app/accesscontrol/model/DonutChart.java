package cl.versionbeta.app.accesscontrol.model;

public class DonutChart {

    private float value;
    private String label;

    public DonutChart() {
        //
    }

    public DonutChart(float value, String label) {
        this.value = value;
        this.label = label;
    }

    public float getValue() {
        return value;
    }
    public void setValue(float value) {
        this.value = value;
    }

    public String getLabel() {
        return label;
    }
    public void setLabel(String label) {
        this.label = label;
    }

}
