/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp_pp.Classes;

import com.estg.core.ItemType;
import com.estg.core.Measurement;
import com.estg.core.exceptions.MeasurementException;
import java.time.LocalDate;

/**
 *
 * @author Roger Nakauchi
 */
public abstract class Container implements com.estg.core.Container {
    
    private String code;
    private double capacity;
    private ItemType itemType;

    public Container(String code, double capacity, ItemType itemType) {
        this.code = code;
        this.capacity = capacity;
        this.itemType = itemType;
    }

    @Override
    public abstract boolean addMeasurement(Measurement msrmnt) throws MeasurementException;

    @Override
    public abstract Measurement[] getMeasurements(LocalDate ld);

    @Override
    public abstract Measurement[] getMeasurements();

    @Override
    public abstract ItemType getType();

    @Override
    public abstract double getCapacity();

    @Override
    public abstract String getCode();
    
    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }
        if(obj == null || !(obj instanceof Container)){
            return false;
        }
        Container cntnr = (Container) obj;
        return this.code == cntnr.code;
    }
    
    
    
}
