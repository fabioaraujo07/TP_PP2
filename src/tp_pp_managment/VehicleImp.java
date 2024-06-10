/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp_pp_managment;

import com.estg.core.ItemType;
import com.estg.pickingManagement.Vehicle;
import org.json.simple.JSONObject;

/**
 * Implementation of the Vehicle interface, representing a vehicle with specific
 * characteristics such as capacity, supply type, and other attributes.
 * 
 * This class provides methods to get and set various attributes of the vehicle,
 * convert the vehicle to and from JSON format, and check equality with another
 * vehicle.
 *
 * @author Fábio da Cunha, Roger Nakauchi
 */
public class VehicleImp implements com.estg.pickingManagement.Vehicle {

    private double capacity;
    private ItemType supply;
    private boolean enabled;
    private boolean isUsed;
    private double kmsForPerishableFood;
    private Vehicle[] vehicles;
    private int nVehicles;

    /**
     * Constructor to initialize the VehicleImp with a specific capacity and
     * supply type.
     * 
     * @param capacity The maximum capacity of the vehicle.
     * @param supply The type of supply the vehicle can carry.
     */
    public VehicleImp(double capacity, ItemType supply) {
        this.nVehicles = 0;
        this.vehicles = new Vehicle[10];
        this.capacity = capacity;
        this.supply = supply;
        this.enabled = true;
        this.isUsed = false;
    }

    /**
     * Constructor to initialize the VehicleImp with a specific capacity and
     * kilometers limit for perishable food.
     * 
     * @param capacity The maximum capacity of the vehicle.
     * @param kmsForPerishableFood The maximum kilometers the vehicle can travel
     *                             when carrying perishable food.
     */
    public VehicleImp(double capacity, double kmsForPerishableFood) {
        this.nVehicles = 0;
        this.vehicles = new Vehicle[10];
        this.capacity = capacity;
        this.kmsForPerishableFood = kmsForPerishableFood;
        this.supply = ItemType.PERISHABLE_FOOD;
        this.enabled = true;
        this.isUsed = false;
    }

    /**
     * Checks if the vehicle is enabled.
     * 
     * @return True if the vehicle is enabled, false otherwise.
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets the enabled status of the vehicle.
     * 
     * @param enabled The new enabled status of the vehicle.
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Gets the type of supply the vehicle can carry.
     * 
     * @return The type of supply.
     */
    @Override
    public ItemType getSupplyType() {
        return this.supply;
    }

    /**
     * Gets the maximum capacity of the vehicle.
     * 
     * @return The maximum capacity.
     */
    @Override
    public double getMaxCapacity() {
        return this.capacity;
    }

    /**
     * Checks if the vehicle is currently in use.
     * 
     * @return True if the vehicle is in use, false otherwise.
     */
    public boolean isUsed() {
        return isUsed;
    }

    /**
     * Sets the used status of the vehicle.
     * 
     * @param isUsed The new used status of the vehicle.
     */
    public void setUsed(boolean isUsed) {
        this.isUsed = isUsed;
    }

    /**
     * Gets the maximum kilometers the vehicle can travel when carrying
     * perishable food.
     * 
     * @return The maximum kilometers for perishable food.
     */
    public double getKms() {
        return kmsForPerishableFood;
    }

    /**
     * Converts the vehicle to a JSON object.
     * 
     * @return The JSON representation of the vehicle.
     */
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

    /**
     * Creates a VehicleImp instance from a JSON object.
     * 
     * @param jsonObject The JSON object representing the vehicle.
     * @return The created VehicleImp instance.
     */
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

    /**
     * Checks if this vehicle is equal to another object.
     * 
     * @param obj The object to compare with.
     * @return True if the vehicles are equal, false otherwise.
     */
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
