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
public class VehicleImp implements com.estg.pickingManagement.Vehicle{
    
    private double capacity;
    private ItemType supply;
    private boolean enabled;

    public VehicleImp(double capacity, ItemType supply) {
        this.capacity = capacity;
        this.supply = supply;
        this.enabled = true;
    }
    
    public boolean isEnabled(){
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
    
    public boolean canTransport(ItemType supply) {
        for(int i = 0; i < capacity; i++) {
            if(this.supply.equals(supply)) {
                return true;
            }
        }
        return false;
    }
    
}
