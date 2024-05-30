/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp_pp.Classes;

import java.time.LocalDateTime;

/**
 *
 * @author Roger Nakauchi
 */
public class MeasurementImp implements com.estg.core.Measurement {
    private LocalDateTime date;
    private double value;

    public MeasurementImp(LocalDateTime date, double value) {
        this.date = date;
        this.value = value;
    }
    
    @Override
    public LocalDateTime getDate() {
        return this.date;
    }

    @Override
    public double getValue() {
        return this.value;
    }
}
