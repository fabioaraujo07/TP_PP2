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
 * Implementation of RouteGenerator interface for generating routes for
 * collecting containers from aid boxes based on a given strategy.
 * 
 * This class generates routes for vehicles to pick up items from aid boxes in an
 * institution according to a specified strategy. It also updates a report with
 * the details of the route generation process.
 *
 * @author Fábio da Cunha, Roger Nakauchi
 */
public class RouteGeneratorImp implements RouteGenerator {

    private Route[] generators;

    /**
     * Constructs a new RouteGeneratorImp with an initial capacity for routes.
     */
    public RouteGeneratorImp() {
        this.generators = new Route[10];
    }

    /**
     * Generates routes for vehicles to pick up items from aid boxes in the
     * specified institution according to the provided strategy and route validator.
     * It also updates the provided report with the details of the generated routes.
     * 
     * @param instn   The institution containing vehicles and aid boxes.
     * @param strtg   The strategy to use for generating routes.
     * @param rv      The route validator to validate the generated routes.
     * @param report  The report to update with details of the generated routes.
     * @return An array of generated routes.
     * @throws PickingMapException If there is an error in generating routes or
     *                             updating the report.
     */
    @Override
    public Route[] generateRoutes(Institution instn, Strategy strtg, RouteValidator rv, Report report) throws PickingMapException {

        Vehicle[] vehicles = instn.getVehicles();
        AidBox[] aidboxes = instn.getAidBoxes();

        //Ensure the routes array is of adequate size
        Route[] routes = new Route[generators.length];

        //Variables for report
        int usedVehicles = 0;
        int pickedContainers = 0;
        double totalDistance = 0.0;
        double totalDuration = 0.0;

        //Use the created strategy
        Route[] generatedRoutes = strtg.generate(instn, rv);

        //Add as rotas geradas no array de rotas
        for (int i = 0; i < generatedRoutes.length; i++) {
            if (routes[i] == null) {
                throw new PickingMapException("Route can't be null");
            }
            routes[i] = generatedRoutes[i];

            usedVehicles++;
            pickedContainers += generatedRoutes[i].getRoute().length; //Each route picks all associated containers
            totalDistance += generatedRoutes[i].getTotalDistance();
            totalDuration += generatedRoutes[i].getTotalDuration();
        }

        //Update the report with the new data
        try {
            if (report instanceof ReportImp) {
                ReportImp rp = (ReportImp) report;
                
                rp.setUsedVehicles(usedVehicles);
                rp.setPickedContainers(pickedContainers);
                rp.setTotalDistance(totalDistance);
                rp.setTotalDuration(totalDuration);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new PickingMapException("Error updating report data");
        }

        return routes;
    }

}
