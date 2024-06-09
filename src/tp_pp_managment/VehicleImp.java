/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp_pp_managment;

import com.estg.core.ItemType;
import com.estg.pickingManagement.Vehicle;
import org.json.simple.JSONObject;

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
    private Vehicle[] vehicles;
    private int nVehicles;

    public VehicleImp(double capacity, ItemType supply) {
        this.nVehicles = 0;
        this.vehicles = new Vehicle[10];
        this.capacity = capacity;
        this.supply = supply;
        this.enabled = true;
        this.isUsed = false;
    }

    public VehicleImp(double capacity, double kmsForPerishableFood) {
        this.nVehicles = 0;
        this.vehicles = new Vehicle[10];
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

    public double getKms() {
        return kmsForPerishableFood;
    }

    public JSONObject toJsonObj() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("capacity", this.capacity);
        jsonObject.put("supply", this.supply.name());
        jsonObject.put("enabled", this.enabled);
        jsonObject.put("isUsed", this.isUsed);
        if (this.supply.equals(ItemType.PERISHABLE_FOOD)) {
            jsonObject.put("kmsForPerishableFood", this.kmsForPerishableFood);
        }
        return jsonObject;
    }

    public static VehicleImp fromJsonObj(JSONObject jsonObject) {
        double capacity = (double) jsonObject.get("capacity");
        ItemType supply = ItemType.valueOf((String) jsonObject.get("supply"));
        boolean enabled = (boolean) jsonObject.get("enabled");
        boolean isUsed = (boolean) jsonObject.get("isUsed");

        System.out.println(supply.toString());

        VehicleImp vehicle;
        if (supply == ItemType.PERISHABLE_FOOD) {
            int kmsForPerishableFood = ((Number) jsonObject.get("kmsForPerishableFood")).intValue();
            vehicle = new VehicleImp(capacity, kmsForPerishableFood);
        } else {
            vehicle = new VehicleImp(capacity, supply);
        }
        vehicle.setEnabled(enabled);
        vehicle.setUsed(isUsed);

        return vehicle;
    }

     @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof VehicleImp)) {
            return false;
        }
        VehicleImp v = (VehicleImp) obj;
        return this.capacity == v.capacity && this.supply == v.supply && this.kmsForPerishableFood == v.kmsForPerishableFood;
    }
    
}
