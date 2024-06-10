/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp_pp.Classes;

import java.time.LocalDateTime;

/**
 * Implementation of the Measurement interface, representing a measurement 
 * with a specific date and value.
 *
 * @author Fábio da Cunha, Roger Nakauchi
 */
public class MeasurementImp implements com.estg.core.Measurement {
    
    private LocalDateTime date;
    private double value;
    private String contentor;
    /**
     * Constructs a new MeasurementImp with the specified date and value.
     * 
     * @param date the date of the measurement
     * @param value the value of the measurement
     */
    public MeasurementImp(String contentor,LocalDateTime date, double value) {
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
    
    public String getcontentor(){
        return this.contentor;
    }
}
