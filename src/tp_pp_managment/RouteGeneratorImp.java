/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp_pp_managment;

import com.estg.core.AidBox;
import com.estg.core.Institution;
import com.estg.core.exceptions.PickingMapException;
import com.estg.pickingManagement.Report;
import com.estg.pickingManagement.Route;
import com.estg.pickingManagement.RouteGenerator;
import com.estg.pickingManagement.RouteValidator;
import com.estg.pickingManagement.Strategy;
import com.estg.pickingManagement.Vehicle;

/**
 *
 * @author Roger Nakauchi
 */
public class RouteGeneratorImp implements RouteGenerator {

    @Override
    public Route[] generateRoutes(Institution instn, Strategy strtg, RouteValidator rv, Report report) throws PickingMapException {
        
        Vehicle[] vehicles = instn.getVehicles();
        AidBox[] aidboxes = instn.getAidBoxes();
        
        Route[] routes = new Route[vehicles.length];
        
        //Variaveis para colocar no report
        int usedVehicles = 0;
        int pickedContainers = 0;
        double totalDistance = 0.0;
        double totalDuration = 0.0;
        
        //Usar o strategy criado
        Route[] generatedRoutes = strtg.generate(instn, rv);
        
        //Add as rotas geradas no array de rotas
        for(int i = 0; i < generatedRoutes.length; i++) {
            routes[i] = generatedRoutes[i];
            
            if(generatedRoutes[i] != null) {
                usedVehicles++;
                pickedContainers += instn.getAidBoxes().length; //Não tenho ctz desse
                totalDistance += generatedRoutes[i].getTotalDistance();
                totalDuration += generatedRoutes[i].getTotalDuration();
            } else {
                throw new PickingMapException("Route can't be null");
            }
        }
        
        //Atualiza o report com os novos dados
        usedVehicles += report.getUsedVehicles();
        pickedContainers += report.getPickedContainers();
        totalDistance += report.getTotalDistance();
        totalDuration += report.getTotalDuration();
        
        //Associar o report com esse metodo. Como?
        
        
        return routes;
    }
    
    
    
}
