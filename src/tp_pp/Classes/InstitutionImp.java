/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp_pp.Classes;

import com.estg.core.AidBox;
import com.estg.core.Container;
import com.estg.core.GeographicCoordinates;
import com.estg.core.ItemType;
import com.estg.core.Measurement;
import com.estg.core.exceptions.AidBoxException;
import com.estg.core.exceptions.ContainerException;
import com.estg.core.exceptions.MeasurementException;
import com.estg.core.exceptions.PickingMapException;
import com.estg.core.exceptions.VehicleException;
import com.estg.pickingManagement.PickingMap;
import com.estg.pickingManagement.Vehicle;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import tp_pp_exceptions.FindException;
import tp_pp_managment.PickingMapImp;
import tp_pp_managment.VehicleImp;

/**
 *
 * @author Roger Nakauchi
 */
public class InstitutionImp implements com.estg.core.Institution {

    private String name;
    private Container[] containers;
    private AidBox[] aidboxes;
    private Measurement[] measurements;
    private Vehicle[] vehicles;
    private PickingMap[] pickingmaps;
    private int numberAidbox;
    private int numberMeasurements;
    private int numberVehicles;
    private int numberPickingmaps;
    private int numberContainers;

    public InstitutionImp(String name) {
        this.name = name;
        this.aidboxes = new AidBoxImp[10];
        this.measurements = new MeasurementImp[10];
        this.vehicles = new VehicleImp[10];
        this.pickingmaps = new PickingMapImp[10];
        this.numberAidbox = 0;
        this.numberMeasurements = 0;
        this.numberPickingmaps = 0;
        this.numberVehicles = 0;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public int findAidBox(AidBox aidBox) throws FindException {
        for (int i = 0; i < numberAidbox; i++) {
            if (this.aidboxes[i].equals(aidBox)) {
                return i;
            }
        }
        throw new FindException("Aidbox not found!");
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

    @Override
    public boolean addAidBox(AidBox aidbox) throws AidBoxException {
        if (aidbox == null) {
            throw new AidBoxException("AidBox can´t be null");
        }
        try {
            if (findAidBox(aidbox) != -1) {
                return false;
            }
        } catch (FindException ex) {
            Logger.getLogger(InstitutionImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (hasDuplicateContainers(aidbox)) {
            throw new AidBoxException("AidBox contains duplicate containers of a certain waste type");
        }
        this.aidboxes[numberAidbox++] = aidbox;
        return true;

    }

    public int findContainer(Container container) throws FindException {
        for (int i = 0; i < numberContainers; i++) {
            if (this.containers[i].equals(container)) {
                return i;
            }
        }
        throw new FindException("Container not found!");
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

        try {
            if (findContainer(cntnr) == -1) {
                throw new ContainerException("Container could not be found");
            }
        } catch (FindException ex) {
            Logger.getLogger(InstitutionImp.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (int i = 0; i < numberMeasurements; i++) {
            if (measurements[i].getDate().equals(msrmnt.getDate())) {
                throw new MeasurementException("Measurement alredy exists for a given date");
            }
            return false;
        }

        measurements[numberMeasurements++] = (MeasurementImp) msrmnt;
        return true;
    }

    @Override
    public AidBox[] getAidBoxes() {
        AidBox[] copy = new AidBoxImp[numberAidbox];
        for (int i = 0; i < numberAidbox; i++) {
            if (aidboxes[i] != null) {
                copy[i] = aidboxes[i];
            }
        }
        return copy;
    }

    @Override
    public Container getContainer(AidBox aidbox, ItemType it) throws ContainerException {
        if (aidbox == null) {
            throw new ContainerException("Aidbox doesn´t exist.");
        }

        Container[] containers = aidbox.getContainers();

        for (int i = 0; i < containers.length; i++) {
            if (containers[i] != null && containers[i].getType().equals(it)) {
                return containers[i];
            }
        }

        throw new ContainerException("Container with the given item type doesn't exist.");

    }

    @Override
    public Vehicle[] getVehicles() {
        Vehicle[] copy = new VehicleImp[numberVehicles];
        for (int i = 0; i < numberVehicles; i++) {
            if (vehicles[i] != null) {
                copy[i] = vehicles[i];
            }
        }
        return copy;
    }

    public int findVehicle(Vehicle vhcl) throws FindException {
        for (int i = 0; i < numberVehicles; i++) {
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
            findVehicle(vhcl);
            return false;

        } catch (FindException ex) {
            this.vehicles[numberVehicles++] = vhcl;
            return true;
        }
    }

    @Override
    public void disableVehicle(Vehicle vhcl) throws VehicleException {

        if (vhcl == null) {
            throw new VehicleException("Vehicle can´t be null");
        }

        try {
            findVehicle(vhcl);
            Vehicle vehicle = vehicles[findVehicle(vhcl)];

            if (vehicle instanceof VehicleImp) {
                if (!((VehicleImp) vehicle).isEnabled()) {
                    throw new VehicleException("Vehicle is already disabled");
                }
                ((VehicleImp) vehicle).setEnabled(false);
            } else {
                throw new VehicleException("Vehicle instance is not valid");
            }

        } catch (FindException ex) {
            throw new VehicleException("Vehicle doesn't exist");
        }
    }

    @Override
    public void enableVehicle(Vehicle vhcl) throws VehicleException {

        if (vhcl == null) {
            throw new VehicleException("Vehicle can´t be null");
        }

        try {
            findVehicle(vhcl);
            Vehicle vehicle = vehicles[findVehicle(vhcl)];

            if (vehicle instanceof VehicleImp) {
                if (((VehicleImp) vehicle).isEnabled()) {
                    throw new VehicleException("Vehicle is already enabled");
                }
                ((VehicleImp) vehicle).setEnabled(true);
            } else {
                throw new VehicleException("Vehicle instance is not valid");
            }

        } catch (FindException ex) {
            throw new VehicleException("Vehicle doens't exist");
        }
    }

    @Override
    public PickingMap[] getPickingMaps() {
        PickingMap[] copy = new PickingMap[numberPickingmaps];
        for (int i = 0; i < numberPickingmaps; i++) {
            if (pickingmaps[i] != null) {
                copy[i] = pickingmaps[i];
            }
        }
        return copy;
    }

    @Override
    public PickingMap[] getPickingMaps(LocalDateTime ldt, LocalDateTime ldt1) {
        PickingMap[] copy = new PickingMap[numberPickingmaps];
        int counter = 0;

        for (int i = 0; i < numberPickingmaps; i++) {
            LocalDateTime date = pickingmaps[i].getDate();
            if (date.isAfter(ldt) && date.isBefore(ldt1)) {
                copy[counter++] = pickingmaps[i];
            }
        }
        return copy;
    }

    @Override
    public PickingMap getCurrentPickingMap() throws PickingMapException {

        if (numberPickingmaps == 0) {
            throw new PickingMapException("There are no picking maps in the institution");
        }
        PickingMap currentPickingMap = pickingmaps[0];
        for (int i = 0; i < numberPickingmaps; i++) {
            if (pickingmaps[i].getDate().isAfter(currentPickingMap.getDate())) {
                currentPickingMap = pickingmaps[i];
            }
        }
        return currentPickingMap;
    }

    public int findPickingMap(PickingMap pickingMap) throws FindException {
        for (int i = 0; i < numberPickingmaps; i++) {
            if (this.pickingmaps[i].equals(pickingMap)) {
                return i;
            }
        }
        throw new FindException("Picking map not found");
    }

    @Override
    public boolean addPickingMap(PickingMap pm) throws PickingMapException {

        if (pm == null) {
            throw new PickingMapException("Picking map can't be null");
        }

        try {
            findPickingMap(pm);
            return false;
        } catch (FindException ex) {
            this.pickingmaps[numberPickingmaps++] = pm;
            return true;
        }
    }

    @Override
    public double getDistance(AidBox aidbox) throws AidBoxException {
        if (aidbox == null) {
            throw new AidBoxException("O aidbox não existe");
        }
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader("Files/Distances.json")) {
            JSONArray distanceArray = (JSONArray) parser.parse(reader);

            for (Object o : distanceArray) {
                JSONObject distancesObject = (JSONObject) o;
                String from = (String) distancesObject.get("from");
                if (from.equals(aidbox.getCode())) {
                    JSONArray distancesToArray = (JSONArray) distancesObject.get("to");
                    for (Object AB : distancesToArray) {
                        JSONObject distancesTO = (JSONObject) AB;
                        String name = (String) distancesTO.get("name");
                        if (name.equals("Base")) {
                            return (long) distancesTO.get("distance");
                        }
                    }
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        throw new AidBoxException("Aid box does not exist: " + aidbox.getCode());
    }

    public int getUsedVehicles() {
        return this.numberVehicles;
    }

    public int getNotUsedVehicles() {
        return this.vehicles.length - this.numberVehicles;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof InstitutionImp)) {
            return false;
        }
        InstitutionImp inst = (InstitutionImp) obj;
        return this.name == inst.name;
    }

    public boolean export(String filePath) {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("numbervehicles", numberVehicles);

        JSONArray vehiclesArray = new JSONArray();
        for (int i = 0; i < numberVehicles; i++) {
            try {
                vehiclesArray.add(((VehicleImp) vehicles[i]).toJsonObj());
            } catch (NullPointerException e) {
            }
        }
        jsonObject.put("Vehicles", vehiclesArray);

        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(jsonObject.toJSONString());
            fileWriter.close();
        } catch (IOException e) {
            
            return false;
        }
        return true;
    }
    
    public boolean importData(String filePath) {
        JSONParser parser = new JSONParser();

        try ( FileReader reader = new FileReader(filePath)) {
            JSONObject jsonObject = (JSONObject) parser.parse(reader);

            JSONArray vehiclesArray = (JSONArray) jsonObject.get("Vehicles");
            for (int i = 0; i < vehiclesArray.size(); i++) {
                try {
                    JSONObject vehicleJson = (JSONObject) vehiclesArray.get(i);
                    Vehicle v = (Vehicle) VehicleImp.fromJsonObj(vehicleJson);
                    this.addVehicle(v);
                } catch (VehicleException e) {
                }
            }
            return true;

        } catch (FileNotFoundException ex) {
            System.out.println("File");
        } catch (IOException ex) {
            System.out.println("2");
        } catch (ParseException ex) {
            System.out.println("3");
        }
        return false;

    }

}
