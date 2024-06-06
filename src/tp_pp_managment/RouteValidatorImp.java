/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp_pp_managment;

import com.estg.core.AidBox;
import com.estg.core.ItemType;
import com.estg.pickingManagement.Route;
import com.estg.pickingManagement.RouteValidator;
import com.estg.pickingManagement.Vehicle;
import tp_pp.Classes.AidBoxImp;

/**
 *
 * @author Roger Nakauchi
 */
public class RouteValidatorImp implements RouteValidator {
    
    private Route[] routes;
    private int numberRoutes;
    private int kmsForPerishableFood;

    
    public RouteValidatorImp(int kmsForPerishableFood) {
        this.routes = new Route[10];
        this.numberRoutes = 0;
        this.kmsForPerishableFood = kmsForPerishableFood;
    }
    
    public boolean hasRoute(Route route) {
        for(int i = 0; i < numberRoutes; i++) {
            if(routes[i].equals(route)) {
                return true;
            }
        }
        return false;
    }

    
    @Override
    public boolean validate(Route route, AidBox aidbox) {
        
        if(hasRoute(route) == false) {
            return false;
        }
        if(route.containsAidBox(aidbox)) {
            return false;
        }
        if(route.getVehicle() != null) {
            return false;
        }
        
        if(route.getVehicle() instanceof VehicleImp) {
            VehicleImp vehicle = (VehicleImp) route.getVehicle();
            
            for(int i = 0; i < aidbox.getContainers().length; i++) {
            if(aidbox.getContainers()[i] != null && !vehicle.canTransport(aidbox.getContainers()[i].getType())) {
                return false;
                }
            }
            
            if(vehicle.canTransport(ItemType.PERISHABLE_FOOD) && route.getTotalDistance() > kmsForPerishableFood) {
                return false;
            }
        }
        return true;
    }
    
}
