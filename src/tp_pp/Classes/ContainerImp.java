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

/**
 *
 * @author Roger Nakauchi
 */
public class ContainerImp implements com.estg.core.Container {

    private String code;
    private double capacity;
    private ItemType itemType;
    private Measurement[] measurements;
    private int numberMeasurements;

    public ContainerImp(String code, double capacity, ItemType itemType) {
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
    public Measurement[] getMeasurements() { 
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
        Measurement[] copyMeasurementsDate = new Measurement[numberMeasurements];
        int count = 0;
        
        for(int i = 0; i < numberMeasurements; i++) {
            try {
                if(measurements[i].getDate().toLocalDate().equals(ld)) {
                    copyMeasurementsDate[count++] = measurements[i];
                }
            } catch(NullPointerException ex) {
                ex.printStackTrace();
            }
        }
        return copyMeasurementsDate;
    }

    @Override
    public boolean addMeasurement(Measurement msrmnt) throws MeasurementException {
        if(msrmnt == null) {
            throw new MeasurementException("Measurement can't be null"); 
        }
        if(msrmnt.getValue() < 0) {
            throw new MeasurementException("Measurement value is lower than zero");
        }
        try {
            if(msrmnt.getDate().isBefore(measurements[numberMeasurements - 1].getDate())) {
                throw new MeasurementException("Measurement date is before than the last Measurement date");
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            ex.printStackTrace();
        }
        
        for(int i = 0; i < numberMeasurements; i++) {
            try {
                if(msrmnt.getDate().equals(measurements[i].getDate())) {
                    if(msrmnt.getValue() != measurements[i].getValue()) {
                        throw new MeasurementException();
                    }
                    return false;
                }
            } catch(NullPointerException ex) {
                ex.printStackTrace();
            }
        }
        if(numberMeasurements == measurements.length) {
            throw new MeasurementException("Max capacity hitted");
        }
        
        measurements[numberMeasurements++] = msrmnt;
        return true;        
    }
    
    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }
        if(obj == null || !(obj instanceof ContainerImp)){
            return false;
        }
        ContainerImp cntnr = (ContainerImp) obj;
        return this.code == cntnr.code;
    }

    @Override
    public String toString() {
        return "code=" + code + ", capacity=" + capacity;
    }
    
    
    
}
