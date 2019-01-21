package persistence.loader.tabDataSet;

import persistence.loader.DataSet;

public class RowMachine extends RowIdNameDescription {

    /**
     * Initializes a row in the table: Machine
     * @param id	Id of the machine
     * @param name	Name of the machine
     * @param locationX	Location of the machine in the work (X coordinate)
     * @param locationY	Location of the machine in the work (Y coordinate)
     * @param angle  	The angle of placement machine (Clockwise)
     * @param state	Machine condition (the probability that the machine is working)
     * @param description	Machine description
     */
    public RowMachine(int id, String name, double locationX, double locationY, double angle, double state, String description) {
        super(id, name, description);
        this.locationX = locationX;
        this.locationY = locationY;
        this.angle = angle;
        this.setState(state);
    }

    public RowMachine() {
    }

    public RowMachine(DataSet dataSet, Class cL) {
        super(dataSet, cL);
    }

    public double getLocationX() {
        return locationX;
    }

    public void setLocationX(double locationX) {
        this.locationX = locationX;
    }

    public double getLocationY() {
        return locationY;
    }

    public void setLocationY(double locationY) {
        this.locationY = locationY;
    }

    public Double getState() {
        return state;
    }

    public void setState(Double state) {
        this.state = state;
    }

    public double getAngle() {        return angle;    }

    public void setAngle(double angle) {        this.angle = angle;    }

    private double locationX = 0;

    private double locationY = 0;

    private double angle = 0;

    private double state = 0.0;
}
