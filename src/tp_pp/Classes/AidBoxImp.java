/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp_pp.Classes;

import com.estg.core.Container;
import com.estg.core.GeographicCoordinates;
import com.estg.core.ItemType;
import com.estg.core.exceptions.AidBoxException;
import com.estg.core.exceptions.ContainerException;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import tp_pp_exceptions.FindException;

/**
 *
 * @author Fábio da Cunha, Roger Nakauchi
 */
public class AidBoxImp implements com.estg.core.AidBox {

    private final double AVERAGE = 60; // suponhamos que seja a velocidade média para cada veículo

    private String code;
    private String zone;
    private String refLocal;
    private GeographicCoordinates coordinates;
    private Container[] containers;
    private int numberContainers;

    public AidBoxImp(String code, String zone, String refLocal, double latitude, double longitude) {
        this.code = code;
        this.zone = zone;
        this.refLocal = refLocal;
        this.coordinates = new GetCoordinatesImp(latitude, longitude);
        this.containers = new ContainerImp[4];
        this.numberContainers = 0;
    }

    @Override
    public double getDistance(com.estg.core.AidBox aidbox) throws AidBoxException {
        if (aidbox == null) {
            throw new AidBoxException("O aidbox não existe");
        }
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader("Files/Distances.json")) {
            JSONArray distanceArray = (JSONArray) parser.parse(reader);

            for (Object o : distanceArray) {
                JSONObject distancesObject = (JSONObject) o;
                String from = (String) distancesObject.get("from");
                if (from.equals(this.code)) {
                    JSONArray distancesToArray = (JSONArray) distancesObject.get("to");
                    for (Object AB : distancesToArray) {
                        JSONObject distancesTO = (JSONObject) AB;

                        String name = (String) distancesTO.get("name");
                        if (name.equals(aidbox.getCode())) {
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

    @Override
    public double getDuration(com.estg.core.AidBox aidbox) throws AidBoxException {
        if (aidbox == null) {
            throw new AidBoxException("O aidbox não existe");
        }
        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader("Files/Distances.json")) {
            JSONArray distanceArray = (JSONArray) parser.parse(reader);

            for (Object o : distanceArray) {
                JSONObject distancesObject = (JSONObject) o;
                String from = (String) distancesObject.get("from");
                if (from.equals(this.code)) {
                    JSONArray distancesToArray = (JSONArray) distancesObject.get("to");
                    for (Object AB : distancesToArray) {
                        JSONObject distancesTO = (JSONObject) AB;

                        String name = (String) distancesTO.get("name");
                        if (name.equals(aidbox.getCode())) {
                            return (long) distancesTO.get("duration");
                        }
                    }
                }
            }
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }
        throw new AidBoxException("Aid box does not exist: " + aidbox.getCode());
    }

    @Override
    public GeographicCoordinates getCoordinates() {
        return this.coordinates;
    }

    public int findContainer(Container cntnr) throws FindException {
        for (int i = 0; i < this.numberContainers; i++) {
            if (this.containers[i].equals(cntnr)) {
                return i;
            }
        }
        throw new FindException("Container not found");
    }

    @Override
    public boolean addContainer(Container cntnr) throws ContainerException {// Já está pronto não mexa no meu código
        if (cntnr == null) {
            throw new ContainerException("Conteiner can't be null");
        }
        try {
            if (findContainer(cntnr) != -1) {
                return false;
            }
        } catch (FindException ex) {
            Logger.getLogger(AidBoxImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (this.numberContainers == this.containers.length) {
            throw new ContainerException("Max capacity hitted");
        }

        for (int i = 0; 1 < numberContainers; i++) {
            if (containers[i].getType().equals(cntnr.getType())) {
                throw new ContainerException("AidBox already have a container from a given type");
            }
        }

        this.containers[numberContainers++] = cntnr;
        return true;
    }

    @Override
    public Container getContainer(ItemType it) {
        for (int i = 0; i < numberContainers; i++) {
            if (containers[i] != null && containers[i].getType().equals(it)) {
                return containers[i];
            }
        }
        return null;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getZone() {
        return this.zone;
    }

    @Override
    public String getRefLocal() {
        return this.refLocal;
    }

    public int getPickedContainers() {
        return this.numberContainers;
    }

    public int getNonPickedContainers() {
        return containers.length - this.numberContainers;
    }

    public Container[] getContainers() {
        Container[] copyContainers = new ContainerImp[numberContainers];
        for (int i = 0; i < numberContainers; i++) {
            if (containers[i] != null) {
                copyContainers[i] = this.containers[i];
            }
        }
        return copyContainers;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof AidBoxImp)) {
            return false;
        }
        AidBoxImp aidbox = (AidBoxImp) obj;
        return this.code == aidbox.code;
    }

    public double getLatitude() {
        return coordinates.getLatitude();
    }

    public double getLongitude() {
        return coordinates.getLongitude();
    }

    @Override
    public String toString() {
        String result = "Code: " + code + "\n"
                + "Zone: " + zone + "\n"
                + "Latitude: " + coordinates.getLatitude() + "\n"
                + "Longitude: " + coordinates.getLongitude() + "\n"
                + "Containers:\n";

        for (int i = 0; i < numberContainers; i++) {
            if (containers[i] != null) {
                result += containers[i].toString() + "\n";
            }
        }

        return result;
    }

}
