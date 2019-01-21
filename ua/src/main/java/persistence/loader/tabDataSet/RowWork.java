package persistence.loader.tabDataSet;

import persistence.loader.DataSet;

/**
 * A description of a row of a table: Work
 * @author POM
 */
public class RowWork extends RowIdNameDescription {

    /**
     * A scheme of a work
     */
    private String scheme = "Image/Manufacturing/Manufacture of bearings.png";
    /**
     * Overall size of the production area on the y-axis (according to the orientation of the scheme).
     */
    private double overallSize = 100;
    /**
     * A scale for a equipment.
     */
    private double scaleEquipment =1.0;

    /**
     * There initializes a row in a table: Work.
     *
     * @param id                Id of a work
     * @param name              A name of a work
     * @param scheme            A scheme of a work
     * @param overallSize	Overall size of the production area on the x-axis (according to the orientation of the scheme).
     * @param scaleEquipment	A scale for a equipment.
     * @param description	A description of a work
     */
    public RowWork(int id, String name, String scheme, double overallSize, double scaleEquipment, String description) {
        super(id, name, description);
        this.scheme = scheme;
        this.overallSize = overallSize;
        this.scaleEquipment = scaleEquipment;
    }

    public RowWork() {
    }

    public RowWork(DataSet dataSet, Class cL) {
        super(dataSet, cL);
    }

    public String getScheme() {
        return scheme;
    }

    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    public double getOverallSize() {
        return overallSize;
    }

    public void setOverallSize(double overallSize) {
        this.overallSize = overallSize;
    }

    public double getScaleEquipment() {
        return scaleEquipment;
    }

    public void setScaleEquipment(double scaleEquipment) {
        this.scaleEquipment = scaleEquipment;
    }
}
