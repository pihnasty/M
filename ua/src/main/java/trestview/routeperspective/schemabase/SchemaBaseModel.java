package trestview.routeperspective.schemabase;

import designpatterns.ObservableDS;
import entityProduction.Machine;
import entityProduction.Work;
import trestview.resourcelink.schemawork.Q;
import trestview.routeperspective.schemaroute.mesh.MeshController;
import trestview.routeperspective.schemaroute.mesh.MeshModel;
import trestview.routeperspective.schemaroute.mesh.MeshView;
import trestview.table.tablemodel.abstracttablemodel.Rule;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class SchemaBaseModel extends ObservableDS implements Observer {

    protected Work work;
    protected List<Q> qs;
    protected List<MeshView> meshes;

    public SchemaBaseModel(ObservableDS observableDS, Rule rule) {
        super(observableDS, rule);
        this.meshes = new ArrayList<>();
        if (!trest.getWorks().isEmpty()) {
            createDataSchemaModel(trest.getWorks().get(0));
        }
    }

    protected void createDataSchemaModel(Work work) {
        meshes.clear();
        this.work = work;
        work.getSubject_labours()
                .forEach(subject_labour -> subject_labour.getRoutes()
                        .forEach(route -> route.getLineroutes().forEach(lineroute -> {
                            MeshModel meshModel = new MeshModel(getMachine(lineroute.getNameMachine()), lineroute, work);
                            MeshController meshController = new MeshController(meshModel);
                            MeshView meshView = new MeshView(meshModel, meshController);
                            meshes.add(meshView);
                        })));
    }

    private Machine getMachine(String machineName) {

        for (Work itemWork : trest.getWorks()) {
            for (Machine itemMachine : itemWork.getMachines()) {
                if (itemMachine.getName().equals(machineName)) {
                    return itemMachine;
                }
            }
        }
        System.out.println("machine name is not found. NULL");
        return null;
    }

    @Override
    public void update(Observable o, Object arg) {

    }

//------------------------------------------------------------------------

    public Work getWork() {
        return work;
    }

    public void setWork(Work work) {
        this.work = work;
    }

    public List<MeshView> getMeshes( ) {
        return meshes;
    }

    public void setMeshes(List<MeshView> meshes) {
        this.meshes = meshes;
    }
}
