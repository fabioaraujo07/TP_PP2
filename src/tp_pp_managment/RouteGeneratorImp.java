/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp_pp_managment;

import com.estg.core.AidBox;
import com.estg.core.Institution;
import com.estg.core.exceptions.PickingMapException;
import com.estg.pickingManagement.PickingMap;
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
        for (int i = 0; i < generatedRoutes.length; i++) {
            if (routes[i] == null) {
                throw new PickingMapException("Route can't be null");
            }
            routes[i] = generatedRoutes[i];

            usedVehicles++;
            pickedContainers += aidboxes.length; //Não tenho ctz desse /Cada rota pega tds os containers associados
            totalDistance += generatedRoutes[i].getTotalDistance();
            totalDuration += generatedRoutes[i].getTotalDuration();
        }

        //Atualiza o report com os novos dados
        try {
            usedVehicles += report.getUsedVehicles();
            pickedContainers += report.getPickedContainers();
            totalDistance += report.getTotalDistance();
            totalDuration += report.getTotalDuration();
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new PickingMapException("Error updating report data");
        }

        //Como realmente associar com o report?
        //Usando os metodos sets na classe report? Se sim, tem que criar esses metodos
        return routes;
    }

}
