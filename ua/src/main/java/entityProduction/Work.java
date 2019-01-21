package entityProduction;
import persistence.loader.DataSet;
import persistence.loader.tabDataSet.RowWork;

import javax.xml.parsers.ParserConfigurationException;
import java.util.ArrayList;
public class Work extends RowWork {

    private ArrayList<Machine> machines = new ArrayList<>();
    private ArrayList<Employee> employees = new ArrayList<>();
    private ArrayList<Subject_labour> subject_labours = new ArrayList<>();
    private ArrayList<Order> orders = new ArrayList<>();
    private double overallSize;
    private double scaleEquipment;

    public Work(int id, String name, String scheme, double overallSize, double scaleEquipment,
            ArrayList<Machine> machines,
            ArrayList<Employee> employees,
            ArrayList<Subject_labour> subject_labours,
            ArrayList<Order> orders,
            String description) {
        super(id, name, scheme, overallSize, scaleEquipment, description);
        this.machines = machines;
        this.employees = employees;
        this.subject_labours = subject_labours;
        this.orders = orders;
        this.overallSize = overallSize;
        this.scaleEquipment = scaleEquipment;
        for (Machine machine: machines) machine.setWork(this);
    }

    public Work() {
    }

    public Work(DataSet dataSet) {
        super(dataSet, Work.class);
    }

    public ArrayList<Machine> getMachines() {
        return machines;
    }

    public void setMachines(ArrayList<Machine> machines) {
        this.machines = machines;
    }

    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(ArrayList<Employee> employees) {
        this.employees = employees;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }

    public ArrayList<Subject_labour> getSubject_labours() {
        return subject_labours;
    }

    public void setSubject_labours(ArrayList<Subject_labour> subject_labours) {
        this.subject_labours = subject_labours;
    }
    
}
