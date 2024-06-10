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

import java.time.LocalDateTime;
import org.json.simple.JSONObject;

/**
 * Implementation of the Measurement interface, representing a measurement with
 * a specific date and value.
 *
 */
public class MeasurementImp implements com.estg.core.Measurement {

    /**
     * The date and time when the measurement was taken.
     */
    private LocalDateTime date;

    /**
     * The value of the measurement.
     */
    private double value;

    /**
     * The identifier of the container associated with the measurement.
     */
    private String contentor;

    /**
     * Constructs a new MeasurementImp with the specified date and value.
     *
     * @param date the date of the measurement
     * @param value the value of the measurement
     */
    public MeasurementImp(String contentor, LocalDateTime date, double value) {
        this.contentor = contentor;
        this.date = date;
        this.value = value;
    }

    /**
     * Retrieves the date of the measurement.
     *
     * @return the date of the measurement
     */
    @Override
    public LocalDateTime getDate() {
        return this.date;
    }

    /**
     * Retrieves the value of the measurement.
     *
     * @return the value of the measurement
     */
    @Override
    public double getValue() {
        return this.value;
    }

    /**
     * Gets the contentor.
     *
     * @return the contentor.
     */
    public String getContentor() {
        return this.contentor;
    }

    /**
     * Converts this MeasurementImp object to a JSON object.
     *
     * @return a JSONObject representing this MeasurementImp.
     */
    public JSONObject toJsonObj() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("contentor", this.contentor);
        jsonObject.put("date", this.date.toString());
        jsonObject.put("value", this.value);
        return jsonObject;
    }

    /**
     * Creates a MeasurementImp object from a JSON object.
     *
     * @param jsonObject the JSON object containing measurement data.
     * @return a MeasurementImp object.
     * @throws IllegalArgumentException if the JSON object contains invalid
     * data.
     */
    public static MeasurementImp fromJsonObj(JSONObject jsonObject) {
        try {
            String contentor = (String) jsonObject.get("contentor");
            String dateString = (String) jsonObject.get("date");
            LocalDateTime date = LocalDateTime.parse(dateString);
            double value = ((Number) jsonObject.get("value")).doubleValue();
            return new MeasurementImp(contentor, date, value);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid Measurement data in JSON: " + jsonObject.toJSONString(), e);
        }
    }

    /**
     * Returns a string representation of this MeasurementImp.
     *
     * @return a string representation of this MeasurementImp.
     */
    @Override
    public String toString() {
        return "Measurements{" + "date=" + date + ", value=" + value + ", contentor=" + contentor + '}';
    }
}
