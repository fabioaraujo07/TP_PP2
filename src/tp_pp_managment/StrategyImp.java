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
        this.routes = new Route[vehicles.length]; //O que usar para descobrir o tamanho do array de rotas?

        // Cada veículo vai ter uma rota correspondente e vai ser validada
        for (int i = 0; i < vehicles.length; i++) {
            if (vehicles[i] instanceof VehicleImp) {
                if (((VehicleImp) vehicles[i]).isEnabled() == true) {
                    routes[i] = new RouteImp(vehicles[i]); //Cria uma nova rota para o veiculo
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
                    //Verifica se o aidbox é valido para a rota
                    if (aidboxes[i] != null && rv.validate(routes[i], aidboxes[j])) {
                        //Verifica se o aidbox pode ser add na rota
                        if (canAddAidBoxToRoute(routes[i], aidboxes[i])) {
                            routes[i].addAidBox(aidboxes[j]);
                        }
                    }
                } catch (RouteException ex) {
                    ex.printStackTrace();
                }
            }
        }

        return routes;
    }

    
    private boolean canAddAidBoxToRoute(Route route, AidBox aidbox) {
        Vehicle vehicle = route.getVehicle();

        if (vehicle instanceof VehicleImp) {
            VehicleImp vehicleImp = (VehicleImp) vehicle;
            double totalWeight = 0;
            ItemType aidboxType = null;

            //Verificar e calcular o peso total dos containers e checkar o tipo
            AidBox[] existingAidBoxes = route.getRoute(); //Pega as aidbox de uma rota existente
            for (int i = 0; i < aidboxes.length; i++) {
                AidBox existingAidBox = existingAidBoxes[i];
                if (existingAidBoxes[i] != null) {
                    Container[] containers = existingAidBox.getContainers(); //Pega os container da aidbox

                    for (int j = 0; j < containers.length; j++) {
                        do {
                            totalWeight += containers[j].getCapacity(); //Soma a capacidade ao peso total do container
                        } while (totalWeight <= containers[j].getCapacity() || j == containers.length);

                        //Define o tipo, se ainda não tiver definido
                        if (aidboxType == null) {
                            aidboxType = containers[j].getType();
                        } else if (!aidboxType.equals(containers[j].getType())) {
                            return false; //O veiculo só pode carregar um tipo de item
                        }
                    }
                }
            }

        }
        return true;
    }

}
