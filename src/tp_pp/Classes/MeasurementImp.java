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

    /**
     * Constructs a new MeasurementImp with the specified date and value.
     * 
     * @param date the date of the measurement
     * @param value the value of the measurement
     */
    public MeasurementImp(LocalDateTime date, double value) {
        this.date = date;
        this.value = value;
    }
    
    /**
     * {@inheritDoc}
     */
    @Override
    public LocalDateTime getDate() {
        return this.date;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getValue() {
        return this.value;
    }
}
