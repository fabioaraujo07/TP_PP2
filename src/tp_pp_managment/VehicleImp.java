/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp_pp_managment;

import com.estg.core.ItemType;

/**
 *
 * @author fabio
 */
public class VehicleImp implements com.estg.pickingManagement.Vehicle {

    private double capacity;
    private ItemType supply;
    private boolean enabled;
    private boolean isUsed;
    private double kmsForPerishableFood;

    public VehicleImp(double capacity, ItemType supply) {
        this.capacity = capacity;
        this.supply = supply;
        this.enabled = true;
        this.isUsed = false;
    }

    public VehicleImp(double capacity, double kmsForPerishableFood) {
        this.capacity = capacity;
        this.kmsForPerishableFood = kmsForPerishableFood;
        this.supply = ItemType.PERISHABLE_FOOD;
        this.enabled = true;
        this.isUsed = false;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public ItemType getSupplyType() {
        return this.supply;
    }

    @Override
    public double getMaxCapacity() {
        return this.capacity;
    }

    public boolean isUsed() {
        return isUsed;
    }

    public void setUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }
    
    public double getKms(){
        return kmsForPerishableFood;
    }

}
