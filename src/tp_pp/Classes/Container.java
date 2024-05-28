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
    private Measurement[] measurements;
    private int numberMeasurements;

    public Container(String code, double capacity, ItemType itemType) {
        this.code = code;
        this.capacity = capacity;
        this.itemType = itemType;
        this.measurements = new Measurement[10];
        this.numberMeasurements = 0;
    }
    
    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public double getCapacity() {
        return this.capacity;
    }

    @Override
    public ItemType getType() {
        return this.itemType;
    }

    @Override
    public Measurement[] getMeasurements() { //Confirmar se ta certo
        Measurement[] copyMeasurements = new Measurement[numberMeasurements];
        for (int i = 0; i < numberMeasurements; i++) {
            if(measurements[i] != null){
                copyMeasurements[i] = this.measurements[i];
            }
        }
        return copyMeasurements;
    }

    @Override
    public Measurement[] getMeasurements(LocalDate ld) {
        for(Measurement m : measurements) {
            if(m != null && m.getDate().equals(ld)) {
                return m; //Ainda vou corrigir essa merda
            }
        }    
    }

    @Override
    public boolean addMeasurement(Measurement msrmnt) throws MeasurementException {
        if(msrmnt == null) {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }
        if(numberMeasurements == measurements.length) {
            this.measurements = new Measurement[numberMeasurements * 2];
        }
        measurements[numberMeasurements++] = msrmnt;
        return true;        
    }
    
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
