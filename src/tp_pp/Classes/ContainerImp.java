/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp_pp.Classes;

import com.estg.core.ItemType;
import com.estg.core.Measurement;
import com.estg.core.exceptions.MeasurementException;
import java.io.IOException;
import java.time.LocalDate;
import tp_pp_exceptions.FindException;
import org.json.simple.JSONObject;
import tp_pp_managment.VehicleImp;

/**
 * Implementation of the Container interface, representing a container that
 * holds measurements of a specific item type. This class handles operations
 * related to containers such as adding and retrieving measurements.
 *
 * @autor Fábio da Cunha, Roger Nakauchi
 */
public class ContainerImp implements com.estg.core.Container {

    private String code;
    private double capacity;
    private ItemType itemType;
    private Measurement[] measurements;
    private int numberMeasurements;

    /**
     * Constructs a ContainerImp with specified code, capacity, and item type.
     *
     * @param code the code of the container
     * @param capacity the capacity of the container
     * @param itemType the type of item the container holds
     */
    public ContainerImp(String code, double capacity, ItemType itemType) {
        this.code = code;
        this.capacity = capacity;
        this.itemType = itemType;
        this.measurements = new Measurement[10];
        this.numberMeasurements = 0;
    }

    /**
     * Retrieves the code of this container.
     *
     * @return the code of this container
     */
    @Override
    public String getCode() {
        return this.code;
    }

    /**
     * Retrieves the capacity of this container.
     *
     * @return the capacity of this container
     */
    @Override
    public double getCapacity() {
        return this.capacity;
    }

    /**
     * Retrieves the type of this container.
     *
     * @return the type of this container
     */
    @Override
    public ItemType getType() {
        return this.itemType;
    }

    /**
     * Retrieves all measurements stored in this container.
     *
     * @return an array of all measurements stored in this container
     */
    @Override
    public Measurement[] getMeasurements() {
        Measurement[] copyMeasurements = new Measurement[numberMeasurements];
        for (int i = 0; i < numberMeasurements; i++) {
            if (measurements[i] != null) {
                copyMeasurements[i] = this.measurements[i];
            }
        }
        return copyMeasurements;
    }

    /**
     * Retrieves measurements stored in this container for the specified date.
     *
     * @param ld the date for which measurements should be retrieved
     * @return an array of measurements stored in this container for the specified date
     */
    @Override
    public Measurement[] getMeasurements(LocalDate ld) {
        Measurement[] copyMeasurementsDate = new Measurement[numberMeasurements];
        int count = 0;

        for (int i = 0; i < numberMeasurements; i++) {
            try {
                if (measurements[i].getDate().toLocalDate().equals(ld)) {
                    copyMeasurementsDate[count++] = measurements[i];
                }
            } catch (NullPointerException ex) {
                ex.printStackTrace();
            }
        }
        return copyMeasurementsDate;
    }

    /**
     * Adds a measurement to this container.
     *
     * @param msrmnt the measurement to add
     * @return true if the measurement is successfully added, false otherwise
     * @throws MeasurementException if an error occurs while adding the measurement
     */
    @Override
    public boolean addMeasurement(Measurement msrmnt) throws MeasurementException {
        if (msrmnt == null) {
            throw new MeasurementException("Measurement can't be null");
        }
        if (msrmnt.getValue() < 0) {
            throw new MeasurementException("Measurement value is lower than zero");
        }
        try {
            if (msrmnt.getDate().isBefore(measurements[numberMeasurements - 1].getDate())) {
                throw new MeasurementException("Measurement date is before than the last Measurement date");
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }

        for (int i = 0; i < numberMeasurements; i++) {
            try {
                if (msrmnt.getDate().equals(measurements[i].getDate())) {
                    if (msrmnt.getValue() != measurements[i].getValue()) {
                        throw new MeasurementException();
                    }
                    return false;
                }
            } catch (NullPointerException ex) {
                ex.printStackTrace();
            }
        }
        if (numberMeasurements == measurements.length) {
            throw new MeasurementException("Max capacity hitted");
        }

        measurements[numberMeasurements++] = msrmnt;
        return true;
    }

    /**
     * Checks if this container is equal to another object.
     *
     * @param obj the object to compare
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof ContainerImp)) {
            return false;
        }
        ContainerImp cntnr = (ContainerImp) obj;
        return this.code == cntnr.code;
    }

    /**
     * Returns a string representation of this container.
     *
     * @return a string representation of this container
     */
    @Override
    public String toString() {
        return "code= " + code + ", capacity= " + capacity + ", type= " + getType();
    }

    public JSONObject toJsonObj() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", this.code);
        jsonObject.put("capacity", this.capacity);
        jsonObject.put("type", this.itemType);

        return jsonObject;
    }

    public static ContainerImp fromJsonObj(JSONObject jsonObject) {
        try {
            String code = (String) jsonObject.get("codigo");

            Object capacityObj = jsonObject.get("capacidade");
            double capacity;
            if (capacityObj instanceof Long) {
                capacity = ((Long) capacityObj).doubleValue();
            } else {
                capacity = (Double) capacityObj;
            }

            ItemType typeStr = ItemType.valueOf((String) jsonObject.get("type"));

            if (typeStr == null) {
                throw new IllegalArgumentException("ItemType is missing");
            }


            return new ContainerImp(code, capacity, typeStr);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid Container data in JSON: " + jsonObject.toJSONString(), e);
        }
    }
}