/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp_pp_managment;

import com.estg.core.AidBox;
import com.estg.core.Institution;
import com.estg.pickingManagement.Route;
import com.estg.pickingManagement.RouteValidator;
import com.estg.pickingManagement.Strategy;
import com.estg.pickingManagement.Vehicle;
import com.estg.pickingManagement.exceptions.RouteException;

/**
 *
 * @author Roger Nakauchi
 */
public class StrategyImp implements Strategy {
    
    // ERRADO

    @Override
    public Route[] generate(Institution instn, RouteValidator rv) {
        
        Vehicle[] vehicles = instn.getVehicles();
        AidBox[] aidboxes = instn.getAidBoxes();
        
        // Cada veículo vai ter uma rota correspondente
        Route[] routes = new Route[vehicles.length];
        
        for(int i = 0; i < vehicles.length; i++) {
            if(((VehicleImp) vehicles[i]).isEnabled() == false) {
                continue; //ignora os veículos desabilitados
            }
            
            // Se o veículo tiver ativo, uma nova rota é criada para ele
            RouteImp route = new RouteImp(new AidBox[10], 0, 0.0, vehicles[i], 0.0);
            
            //Tenta add um aidbox para uma rota
            for(int j = 0; j < aidboxes.length; j++) {
                try {
                    route.addAidBox(aidboxes[j]);
                    if(!rv.validate(route, aidboxes[j])) {
                        route.removeAidBox(aidboxes[j]);
                    }
                } catch (RouteException ex) {
                    ex.printStackTrace();
                }
            }
            routes[i] = route;
        }
        
        return routes;
    }
    
    
    
}
