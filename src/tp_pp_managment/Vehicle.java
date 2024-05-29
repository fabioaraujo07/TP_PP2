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
public class Vehicle implements com.estg.pickingManagement.Vehicle{
    
    private double capacity;
    private ItemType supply;

    public Vehicle(double capacity, ItemType supply) {
        this.capacity = capacity;
        this.supply = supply;
    }
    
    

    @Override
    public ItemType getSupplyType() {
        return this.supply;
    }

    @Override
    public double getMaxCapacity() {
        return this.capacity;
    }
    
}
