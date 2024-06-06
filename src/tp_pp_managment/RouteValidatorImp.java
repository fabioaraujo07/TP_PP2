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
    
    private AidBox[] aidbox;
    private Route[] routes;
    private int numberRoutes;

    
    /*
    public RouteValidatorImp(AidBox[] aidbox, Route[] routes, int numberRoutes) {
        this.aidbox = new AidBox[10];
        this.routes = new Route[10];
        this.numberRoutes = 0;
    }
    */
    
    public boolean hasRoute(Route route) {
        for(int i = 0; i < numberRoutes; i++) {
            if(routes[i].equals(route)) {
                return true;
            }
        }
        return false;
    }
    
    //Testing

    //Verificar se está certo
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
            if(!vehicle.canTransport(ItemType.CLOTHING)) {
                return false;
            } else if(!vehicle.canTransport(ItemType.CLOTHING)) {
                return false;
            } else if(!vehicle.canTransport(ItemType.MEDICINE)) {
                return false;
            } else if(!vehicle.canTransport(ItemType.NON_PERISHABLE_FOOD)) {
                return false;
            } else if(!vehicle.canTransport(ItemType.PERISHABLE_FOOD)) {
                return false;
            }
        }
        return true;
    }
    
}
