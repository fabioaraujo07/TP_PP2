package tp_pp.Classes;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import com.estg.core.ItemType;
import com.estg.core.Measurement;
import com.estg.core.exceptions.MeasurementException;
import java.time.LocalDate;
import tp_pp.Classes.Container;

/**
 *
 * @author Roger Nakauchi
 */
public class Clothing extends Container {
    
    private String code;
    private double capacity;
    private ItemType itemType;
    private Measurement[] measurements;
    private int countMeasurements;

    public Clothing(String code, double capacity, ItemType itemType) {
        super(code, capacity, itemType);
    }
    

    @Override
    public boolean addMeasurement(Measurement msrmnt) throws MeasurementException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Measurement[] getMeasurements(LocalDate ld) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Measurement[] getMeasurements() {
        Measurement[] measurementsCopy = new Measurement[countMeasurements];
        for (int i = 0; i < this.countMeasurements; i++) {
            if(measurements[i] != null){
                measurementsCopy[i] = this.measurements[i];
            }
        }
        return measurementsCopy;
    }

    @Override
    public ItemType getType() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public double getCapacity() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public String getCode() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
    
}
