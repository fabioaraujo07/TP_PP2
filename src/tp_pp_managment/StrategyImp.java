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
 *
 * @author Roger Nakauchi
 */
public class StrategyImp implements Strategy {

    private int numberStrategies;
    private int numberTypes;
    private Route[] strategies;
    private RouteValidator validated;
    private Institution institution;
    private ItemType[] types;

    public StrategyImp(Institution institution) {
        this.strategies = new Route[10];
        this.institution = institution;
        this.types = new ItemType[4];
        this.numberStrategies = 0;
        this.numberTypes = 0;
    }

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

    private boolean typeAlreadyPicked(ItemType type) {
        for (int i = 0; i < this.numberTypes; i++) {
            if (this.types[i].equals(type)) {
                return true;
            }
        }

        this.types[numberTypes++] = type;
        return false;
    }

    private boolean isEnabled(Vehicle vehicle) {

        if (vehicle instanceof VehicleImp) {
            VehicleImp v = (VehicleImp) vehicle;
            if (v.isEnabled()) {
                return true;
            }
        }
        return false;
    }

    private double getContainerMeasurementValue(Container container) {
        int lastMeasurementIndex = lastMeasurement(container);
        if (lastMeasurementIndex >= 0) {
            return container.getMeasurements()[lastMeasurementIndex].getValue();
        }
        return 0;
    }

    private double takeContainer(Container container) {
        double msrmntValue = container.getMeasurements()[lastMeasurement(container)].getValue();

        if (msrmntValue == container.getCapacity()) {
            return msrmntValue;
        }
        return 0;
    }

    private int numberOfVehicles(Vehicle[] vehicles) {
        int nVehicles = 0;

        for (int i = 0; i < vehicles.length; i++) {

            if (vehicles[i] != null) {
                nVehicles++;
            }
        }
        return nVehicles;
    }

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
