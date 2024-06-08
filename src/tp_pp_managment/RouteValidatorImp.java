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
    

    public RouteValidatorImp() {
        this.routes = new Route[10];
        this.numberRoutes = 0;
    }

    public boolean hasRoute(Route route) {
        for (int i = 0; i < numberRoutes; i++) {
            if (routes[i].equals(route)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean validate(Route route, AidBox aidbox) {

        if (hasRoute(route) == false) {
            return false;
        }
        if (route.containsAidBox(aidbox)) {
            return false;
        }
        if (route.getVehicle() == null) {
            return false;
        }

        if (route instanceof RouteImp) {
            try {
                RouteImp vehicle = (RouteImp) route;
                
                if(!vehicle.canTransport(aidbox)){
                    return false;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                return false;
            }
        }
        return true;
    }

}
