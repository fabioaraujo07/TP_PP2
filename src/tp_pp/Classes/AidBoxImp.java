/*
 * Nome: Roger Nakauchi
 * Número: 8210005
 * Turna: LSIRCT1
 *
 * Nome: Fábio da Cunha
 * Número: 8210619
 * Turna: LSIRCT1
 */
package tp_pp.Classes;

import com.estg.core.Container;
import com.estg.core.GeographicCoordinates;
import com.estg.core.ItemType;
import com.estg.core.exceptions.AidBoxException;
import com.estg.core.exceptions.ContainerException;
import http.HttpProviderImp;
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
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import http.HttpProviderImp;

/**
 * Implementation of the AidBox interface, representing an aid box with
 * containers and geographic coordinates. This class handles operations related
 * to aid boxes such as distance calculation, container management, and
 * geographic information.
 *
 */
public class AidBoxImp implements com.estg.core.AidBox {

    /**
     * Unique identifier for the aid box.
     */
    private String id;

    /**
     * Code representing the aid box.
     */
    private String code;

    /**
     * Zone in which the aid box is located.
     */
    private String zone;

    /**
     * Reference location for the aid box.
     */
    private String refLocal;

    /**
     * Geographic coordinates of the aid box.
     */
    private GeographicCoordinates coordinates;

    /**
     * Array of containers associated with the aid box.
     */
    private Container[] containers;

    /**
     * Number of containers in the aid box.
     */
    private int numberContainers;

    /**
     * HTTP provider for making requests to external services.
     */
    private static HttpProviderImp httpProvider = new HttpProviderImp();

    /**
     * Constructs an AidBoxImp with specified code, zone, local reference,
     * latitude, and longitude.
     *
     * @param code the code of the aid box
     * @param zone the zone of the aid box
     * @param refLocal the local reference of the aid box
     * @param latitude the latitude of the aid box
     * @param longitude the longitude of the aid box
     */
    public AidBoxImp(String id, String code, String zone, String refLocal, double latitude, double longitude) {
        this.id = id;
        this.code = code;
        this.zone = zone;
        this.refLocal = refLocal;
        this.coordinates = new GetCoordinatesImp(latitude, longitude);
        this.containers = new ContainerImp[4];
        this.numberContainers = 0;
    }

    /**
     * Retrieves the distance between this aid box and the institution base.
     *
     * @param aidbox the other aid box
     * @return the distance between an aid box and the institution base
     * @throws AidBoxException if an error occurs during the distance retrieval
     */
    @Override
    public double getDistance(com.estg.core.AidBox aidbox) throws AidBoxException {
        if (aidbox == null) {
            throw new AidBoxException("O aidbox não existe");
        }

        try {
            String jsonResponse = httpProvider.getAidBoxes();
            JSONParser parser = new JSONParser();
            JSONArray aidboxes = (JSONArray) parser.parse(jsonResponse);

            for (Object o : aidboxes) {
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

    /**
     * Retrieves the duration to travel between this aid box and the institution
     * base.
     *
     * @param aidbox the other aid box
     * @return the duration to travel between an aid box and the institution
     * base
     * @throws AidBoxException if an error occurs during the duration retrieval
     */
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

    /**
     * Retrieves the geographic coordinates of this aid box
     *
     * @return the geographic coordinates
     */
    @Override
    public GeographicCoordinates getCoordinates() {
        return this.coordinates;
    }

    /**
     * Finds the index of the specified container in this aid box.
     *
     * @param cntnr the container to find
     * @return the index of the container
     * @throws FindException if the container is not found
     */
    public int findContainer(Container cntnr) throws FindException {
        for (int i = 0; i < this.numberContainers; i++) {
            if (this.containers[i].equals(cntnr)) {
                return i;
            }
        }
        throw new FindException("Container not found");
    }

    /**
     * Adds a container to this aid box
     *
     * @param cntnr the container to add
     * @return true if the container is successfully added, false otherwise
     * @throws ContainerException if an error occurs while adding the container
     */
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
            if (this.numberContainers == this.containers.length) {
                throw new ContainerException("Max capacity hitted");
            }

            for (int i = 0; i < numberContainers; i++) {
                if (containers[i].getType().equals(cntnr.getType()) && containers[i].getCode().equals(cntnr.getCode())) {
                    throw new ContainerException("AidBox already have a container from a given type");
                }
            }

            this.containers[numberContainers++] = cntnr;
            return true;
        }
        return false;
    }

    /**
     * Retrieves a container of the specified item type from this aid box
     *
     * @param it the item type to retrieve
     * @return the container of the specified item type, or null if not found
     */
    @Override
    public Container getContainer(ItemType it) {
        for (int i = 0; i < numberContainers; i++) {
            if (containers[i] != null && containers[i].getType().equals(it)) {
                return containers[i];
            }
        }
        return null;
    }

    /**
     * Retrieves the code of this aid box
     *
     * @return the code of this aid box
     */
    @Override
    public String getCode() {
        return this.code;
    }

    /**
     * Retrieves the zone of this aid box
     *
     * @return the zone of this aid box
     */
    @Override
    public String getZone() {
        return this.zone;
    }

    /**
     * Retrieves the local reference of this aid box
     *
     * @return the local reference of this aid box
     */
    @Override
    public String getRefLocal() {
        return this.refLocal;
    }

    /**
     * Gets the number of picked containers in this aid box.
     *
     * @return the number of picked containers
     */
    public int getPickedContainers() {
        return this.numberContainers;
    }

    /**
     * Gets the number of non-picked containers in this aid box.
     *
     * @return the number of non-picked containers
     */
    public int getNonPickedContainers() {
        return containers.length - this.numberContainers;
    }

    /**
     * Gets an array of containers in this aid box.
     *
     * @return an array of containers
     */
    public Container[] getContainers() {
        Container[] copyContainers = new ContainerImp[numberContainers];
        for (int i = 0; i < numberContainers; i++) {
            if (containers[i] != null) {
                copyContainers[i] = this.containers[i];
            }
        }
        return copyContainers;
    }

    /**
     * Checks if this aid box is equal to another object
     *
     * @param obj the object to compare
     * @return true if the objects are equal, false otherwise
     */
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

    /**
     * Gets the latitude of this aid box.
     *
     * @return the latitude
     */
    public double getLatitude() {
        return coordinates.getLatitude();
    }

    /**
     * Gets the longitude of this aid box.
     *
     * @return the longitude
     */
    public double getLongitude() {
        return coordinates.getLongitude();
    }

    /**
     * Returns a string representation of this aid box.
     *
     * @return a string representation of this aid box
     */
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

    public JSONObject toJSONObj() {

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("_id", this.id);
        jsonObject.put("Code", this.code);
        jsonObject.put("Zone", this.zone);
        jsonObject.put("Latitude", this.coordinates.getLatitude());
        jsonObject.put("Longitude", this.coordinates.getLongitude());

        JSONArray containersArray = new JSONArray();
        for (Container container : this.containers) {
            if (container != null) {
                if (container instanceof ContainerImp) {
                    ContainerImp c = (ContainerImp) container;
                    containersArray.add(c.toJsonObj());
                }
            }
        }
        jsonObject.put("Containers", containersArray);

        return jsonObject;
    }

    public static AidBoxImp fromJsonObj(JSONObject jsonObject) {

        String id = (String) jsonObject.get("_id");
        String code = (String) jsonObject.get("Code");
        String zone = (String) jsonObject.get("Zone");
        double latitude = (double) jsonObject.get("Latitude");
        double longitude = (double) jsonObject.get("Longitude");

        AidBoxImp aid = new AidBoxImp(id, code, zone, zone, latitude, longitude);

        JSONArray containersArray = (JSONArray) jsonObject.get("Containers");
        for (Object container : containersArray) {
            JSONObject containerJson = (JSONObject) container;
            ContainerImp containers = ContainerImp.fromJsonObj(containerJson);
            try {
                aid.addContainer(containers);
            } catch (ContainerException e) {
                e.printStackTrace();
            }

        }

        return aid;
    }

}
