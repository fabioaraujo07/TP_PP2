/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp_pp.Classes;

import com.estg.core.AidBox;
import com.estg.core.Container;
import com.estg.core.ItemType;
import com.estg.core.Measurement;
import com.estg.core.exceptions.AidBoxException;
import com.estg.core.exceptions.ContainerException;
import com.estg.core.exceptions.MeasurementException;
import com.estg.core.exceptions.PickingMapException;
import com.estg.core.exceptions.VehicleException;
import com.estg.pickingManagement.PickingMap;
import com.estg.pickingManagement.Vehicle;
import java.time.LocalDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import tp_pp_exceptions.FindException;
import tp_pp_managment.VehicleImp;

/**
 *
 * @author Roger Nakauchi
 */
public class InstitutionImp implements com.estg.core.Institution {

    private String name;
    private Container[] containers;
    private AidBox[] aidbox;
    private Measurement[] measurements;
    private Vehicle[] vehicles;
    private PickingMap[] pickingmaps;
    private int nAidbox;
    private int numberMeasurements;
    private int nVehicles;
    private int nPickingmaps;
    private int numberContainers;

    public InstitutionImp(String name, AidBox[] aidbox, Measurement[] measurements, Vehicle[] vehicles, PickingMap[] pickingmaps) {
        this.name = name;
        this.aidbox = new AidBoxImp[10];
        this.measurements = new MeasurementImp[10];
        this.vehicles = new VehicleImp[10];
        this.pickingmaps = new PickingMap[10];
        this.nAidbox = 0;
        this.numberMeasurements = 0;
        this.nPickingmaps = 0;
        this.nVehicles = 0;
    }

    @Override
    public String getName() {
        return this.name;
//        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody;
    }

    public int findAidBox(AidBox aidBox) {
        for (int i = 0; i < nAidbox; i++) {
            if (this.aidbox[i].equals(aidBox)) {
                return i;
            }
        }
        return -1;
    }

    private boolean hasDuplicateContainers(AidBox aidbox) {
        Container[] container = aidbox.getContainers();
        for (int i = 0; i < container.length; i++) {
            for (int j = i + 1; j < container.length; i++) {
                if (container[i].getType().equals(container[j].getType())) {
                    return true;
                }
            }
        }
        return false;
    }

    // Testando
    @Override
    public boolean addAidBox(AidBox aidbox) throws AidBoxException {
        if (aidbox == null) {
            throw new AidBoxException("AidBox can´t be null");
        }
        if (findAidBox(aidbox) != -1) {
            return false;
        }
        if (hasDuplicateContainers(aidbox)) {
            throw new AidBoxException("AidBox contains duplicate containers of a certain waste type");
        }
        this.aidbox[nAidbox++] = aidbox;
        return true;

    }

    //gygwefuygyufgweyugfweyugewf8u
    public int findContainer(Container container) {
        for (int i = 0; i < numberContainers; i++) {
            if (this.containers[i].equals(container)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public boolean addMeasurement(Measurement msrmnt, Container cntnr) throws ContainerException, MeasurementException {

        if (cntnr == null) {
            throw new ContainerException("Container can't be null");
        }

        if (msrmnt == null) {
            throw new MeasurementException("Measurement can't be null");
        }

        if (msrmnt.getValue() < 0) {
            throw new MeasurementException("Measurement value can less than zero");
        }

        if (msrmnt.getValue() > cntnr.getCapacity()) {
            throw new MeasurementException("Measurement can't be higger than capacity");
        }

        if (findContainer(cntnr) == -1) {
            throw new ContainerException("Container could not be found");
        }

        for (int i = 0; i < numberMeasurements; i++) {
            if (measurements[i].getDate().equals(msrmnt.getDate())) {
                if (measurements[i].getValue() != msrmnt.getValue()) {
                    throw new MeasurementException("Measurement alredy exists for a given date");
                }
                return false;
            }
        }

        measurements[numberMeasurements++] = msrmnt;
        return true;

    }

    @Override
    public AidBox[] getAidBoxes() {
        AidBox[] copy = new AidBoxImp[nAidbox];
        for (int i = 0; i < nAidbox; i++) {
            if (aidbox[i] != null) {
                copy[i] = aidbox[i];
            }
        }
        return copy;
    }

    @Override
    public Container getContainer(AidBox aidbox, ItemType it) throws ContainerException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Vehicle[] getVehicles() {
        Vehicle[] copy = new VehicleImp[nVehicles];
        for (int i = 0; i < nVehicles; i++) {
            if (vehicles[i] != null) {
                copy[i] = vehicles[i];
            }
        }
        return copy;
    }

    public int findVehicle(Vehicle vhcl) throws FindException {
        for (int i = 0; i < nVehicles; i++) {
            if (this.vehicles[i].equals(vhcl)) {
                return i;
            }
        }
        throw new FindException("Vehicle not found");
    }

    @Override
    public boolean addVehicle(Vehicle vhcl) throws VehicleException {
        if (vhcl == null) {
            throw new VehicleException("Vehicle can´t be null");
        }
        try {
            //tentar encontrar vhcl
            findVehicle(vhcl);
            //se ainda estiver em execução aqui é porque o vhcl foi encontrado,
            // nao pode addicionar
            return false;

        } catch (FindException ex) {
            //se veiculo nao foi encontrado, pode addicionar
            this.vehicles[nVehicles++] = vhcl;
            return true;
        }

    }

    @Override
    public void disableVehicle(Vehicle vhcl) throws VehicleException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void enableVehicle(Vehicle vhcl) throws VehicleException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public PickingMap[] getPickingMaps() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public PickingMap[] getPickingMaps(LocalDateTime ldt, LocalDateTime ldt1) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public PickingMap getCurrentPickingMap() throws PickingMapException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean addPickingMap(PickingMap pm) throws PickingMapException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public double getDistance(AidBox aidbox) throws AidBoxException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
