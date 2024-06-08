/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp_pp_managment;

import com.estg.core.AidBox;
import com.estg.core.Container;
import com.estg.core.Institution;
import com.estg.core.ItemType;
import com.estg.core.Measurement;
import com.estg.pickingManagement.Route;
import com.estg.pickingManagement.RouteValidator;
import com.estg.pickingManagement.Strategy;
import com.estg.pickingManagement.Vehicle;
import com.estg.pickingManagement.exceptions.RouteException;
import tp_pp_exceptions.StrategyException;

/**
 *
 * @author Roger Nakauchi
 */
public class StrategyImp implements Strategy {

    private Route[] routes;
    private Vehicle[] vehicles;
    private AidBox[] aidboxes;
    private Container[] containers;

    @Override
    public Route[] generate(Institution instn, RouteValidator rv) {

        int count = 0;
        int countActiveVehicles = 0;
        this.vehicles = instn.getVehicles();
        this.aidboxes = instn.getAidBoxes();
        this.routes = new Route[vehicles.length];

        // Cada veículo vai ter uma rota correspondente e vai ser validada
        for (int i = 0; i < vehicles.length; i++) {
            if (vehicles[i] instanceof VehicleImp) {
                if (((VehicleImp) vehicles[i]).isEnabled() == true) {
                    routes[i] = new RouteImp(vehicles[i]);
                    countActiveVehicles++;
                }
            }
            if (countActiveVehicles == 0) {
                System.out.println("No actived vehicles available"); //Melhorar essa parte
                return null;
            }
        }
        

        for (int i = 0; i < routes.length; i++) {
            for (int j = 0; j < aidboxes.length; j++) {
                containers = aidboxes[j].getContainers();
                try {
                    if (rv.validate(routes[i], aidboxes[j])) {
                        //Faz a estrategia
                        routes[i].addAidBox(aidboxes[j]);
                        //Verificar se os measurements no container não utrapassa os limites                        
                        //Se o limite não atingir o limite do container, verificar se tem mais para adicionar, sem passar o limite do container
                        //Se percorrer todo o array e não tiver mais measurements do mesmo tipo para adicionar, o veiculo deve partir
                        for(int k = j; k < aidboxes.length; k++) {
                            AidBox nextAidbox = aidboxes[k];
                            if(nextAidbox != null && rv.validate(routes[i], nextAidbox)) {
                                routes[i].addAidBox(nextAidbox);
                            }
                        }

                        //Verificar se o veiculo só carrega um tipo de item
                        
                    } else {
                        routes[i].removeAidBox(aidboxes[j]);
                    }
                } catch (RouteException ex) {
                    ex.printStackTrace();
                }
            }
        }

        return routes;
    }
    
    //Ainda ta incompleto
    private boolean canAddAidBoxToRoute(Route route, AidBox aidbox) {
        Vehicle vehicle = route.getVehicle();
        
        if(vehicle instanceof VehicleImp) {
            VehicleImp vehicleImp = (VehicleImp) vehicle;
            double totalWeight = 0;
            ItemType aidboxType = null;
            
            
            //Verificar e calcular o peso total dos containers e checkar o tipo
            //AidBox[] existingAidBoxes = 
            for(int i = 0; i < aidboxes.length; i++) {
                //AidBox existingAidBox = 
            }
            
            
        }
        return true;
    }
    
    

}
