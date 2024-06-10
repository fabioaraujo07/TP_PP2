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
import java.util.logging.Level;
import java.util.logging.Logger;
import tp_pp_exceptions.StrategyException;

/**
 * Implementation of the Strategy interface to generate routes for collecting
 * containers from aid boxes based on the received measurements.
 * 
 * This class generates routes for vehicles to pick up items from aid boxes in
 * an institution according to a specified strategy and validates them using a
 * route validator.
 *
 * @author Fábio da Cunha, Roger Nakauchi
 */
public class StrategyImp implements Strategy {

    private int numberStrategies;
    private int numberTypes;
    private Route[] strategies;
    private RouteValidator validated;
    private Institution institution;
    private ItemType[] types;

    /**
     * Constructs a new StrategyImp with the specified institution.
     * 
     * @param institution The institution containing vehicles and aid boxes.
     */
    public StrategyImp(Institution institution) {
        this.strategies = new Route[10];
        this.institution = institution;
        this.types = new ItemType[4];
        this.numberStrategies = 0;
        this.numberTypes = 0;
    }

    /**
     * Retrieves the number of the last measurements taken for the specified container.
     * 
     * @param container The container to get the number of measurements for.
     * @return The number of measurements.
     */
    private int lastMeasurement(Container container) {
        int numberMeasurement = 0;

        Measurement[] measurement = container.getMeasurements();
        for (int i = 0; i < measurement.length; i++) {
            if (measurement != null) {
                numberMeasurement++;
            }
        }
        return numberMeasurement;
    }

    /**
     * Checks if the specified item type has already been picked.
     * 
     * @param type The item type to check.
     * @return true if the type has already been picked, false otherwise.
     */
    private boolean typeAlreadyPicked(ItemType type) {
        for (int i = 0; i < this.numberTypes; i++) {
            if (this.types[i].equals(type)) {
                return true;
            }
        }

        this.types[numberTypes++] = type;
        return false;
    }

    /**
     * Checks if the specified vehicle is enabled.
     * 
     * @param vehicle The vehicle to check.
     * @return true if the vehicle is enabled, false otherwise.
     */
    private boolean isEnabled(Vehicle vehicle) {

        if (vehicle instanceof VehicleImp) {
            VehicleImp v = (VehicleImp) vehicle;
            if (v.isEnabled()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieves the measurement value of the last measurement for the specified container.
     * 
     * @param container The container to get the measurement value for.
     * @return The measurement value of the last measurement.
     */
    private double getContainerMeasurementValue(Container container) {
        int lastMeasurementIndex = lastMeasurement(container);
        if (lastMeasurementIndex >= 0) {
            return container.getMeasurements()[lastMeasurementIndex].getValue();
        }
        return 0;
    }

    /**
     * Retrieves the measurement value of the container if it is full.
     * 
     * @param container The container to check.
     * @return The measurement value if the container is full, 0 otherwise.
     */
    private double takeContainer(Container container) {
        double msrmntValue = container.getMeasurements()[lastMeasurement(container)].getValue();

        if (msrmntValue == container.getCapacity()) {
            return msrmntValue;
        }
        return 0;
    }

    /**
     * Counts the number of non-null vehicles in the specified array.
     * 
     * @param vehicles The array of vehicles to count.
     * @return The number of non-null vehicles.
     */
    private int numberOfVehicles(Vehicle[] vehicles) {
        int nVehicles = 0;

        for (int i = 0; i < vehicles.length; i++) {

            if (vehicles[i] != null) {
                nVehicles++;
            }
        }
        return nVehicles;
    }

    /**
     * Removes aid boxes from the route if they do not contain any items.
     * 
     * @param route The route to remove empty aid boxes from.
     */
    private void removeEmptyAidBoxes(Route route) {
        AidBox[] aidBoxes = route.getRoute();

        for (int i = 0; i < aidBoxes.length; i++) {
            ItemType type = route.getVehicle().getSupplyType();
            Container container = aidBoxes[i].getContainer(type);

            if (takeContainer(container) == 0) {

                try {
                    route.removeAidBox(aidBoxes[i]);
                } catch (RouteException ex) {
                    System.out.println("The aidbox isn't valid");
                }
            }
        }
    }

    /**
     * Checks if the vehicle can continue with the current load based on the total distance for perishable food.
     * 
     * @param vehicle The vehicle to check.
     * @param route The route the vehicle is following.
     * @return true if the vehicle can continue, false otherwise.
     */
    private boolean canContinueWithCurrentLoad(Vehicle vehicle, Route route) {
        if (vehicle instanceof VehicleImp) {
            VehicleImp refrigeratedVehicle = (VehicleImp) vehicle;
            if (refrigeratedVehicle.getSupplyType().equals(ItemType.PERISHABLE_FOOD)) {
                double totalDistance = route.getTotalDistance();
                return totalDistance <= refrigeratedVehicle.getKms();
            }
        }
        return true;
    }

    /**
     * Generates routes for vehicles to pick up items from aid boxes in the
     * specified institution according to the provided route validator.
     * 
     * @param instn The institution containing vehicles and aid boxes.
     * @param rv The route validator to validate the generated routes.
     * @return An array of generated routes.
     */
    @Override
    public Route[] generate(Institution instn, RouteValidator rv) {

        Vehicle[] vehicles = instn.getVehicles();
        int numberVehicles = numberOfVehicles(this.institution.getVehicles());

        for (int i = 0; i < vehicles.length; i++) {

            if (!(typeAlreadyPicked(vehicles[i].getSupplyType())) && isEnabled(vehicles[i])) {

                Route route = new RouteImp(vehicles[i]);
                ItemType type = vehicles[i].getSupplyType();
                AidBox[] aidBoxes = instn.getAidBoxes();
                double load = 0;
                int position = 0;

                do {
                    AidBox aidbox = aidBoxes[position];
                    Container container = aidBoxes[position].getContainer(type);

                    if (rv.validate(strategies[numberStrategies++], aidBoxes[position])) {
                        load += container.getMeasurements()[lastMeasurement(container)].getValue();
                        try {
                            this.strategies[numberStrategies].addAidBox(aidbox);
                        } catch (RouteException ex) {
                            ex.printStackTrace();
                        }
                    }

                    if (load >= vehicles[i].getMaxCapacity() || !canContinueWithCurrentLoad(vehicles[i], route)) {
                        try {
                            route.removeAidBox(aidBoxes[position]);
                            load -= takeContainer(container);
                            this.strategies[numberStrategies++] = route;
                            route = new RouteImp(vehicles[i]);
                            load = 0;

                        } catch (RouteException ex) {
                            ex.printStackTrace();
                            throw new IllegalArgumentException("The aidbox insn't valid");
                        }
                    }

                    position++;
                } while (position < aidBoxes.length && aidBoxes[position] != null);

                if (route.getRoute().length > 0) {
                    this.strategies[numberStrategies++] = route;
                }
            }
        }
        return strategies;
    }

}
