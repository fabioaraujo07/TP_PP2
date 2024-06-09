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

    private int LastMeasurement(Container container) {
        int numberMeasurement = 0;

        Measurement[] measurement = container.getMeasurements();
        for (int i = 0; i < measurement.length; i++) {
            if (measurement != null) {
                numberMeasurement++;
            }
        }
        return numberMeasurement;
    }

    private boolean TypeAlreadyPicked(ItemType type) {
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

    @Override
    public Route[] generate(Institution instn, RouteValidator rv) {

        Vehicle[] vehicles = instn.getVehicles();

        for (int i = 0; i < strategies.length; i++) { //Mudei o 10 para strategies.length

            if (!(TypeAlreadyPicked(vehicles[i].getSupplyType())) && isEnabled(vehicles[i])) {

                this.strategies[numberStrategies] = new RouteImp(vehicles[i]);
                ItemType type = vehicles[i].getSupplyType();
                AidBox[] aidBoxes = instn.getAidBoxes();
                double load = 0;
                int position = 0;

                do {

                    if (rv.validate(strategies[numberStrategies++], aidBoxes[position])) {
                        Container container = aidBoxes[position].getContainer(type);
                        load += container.getMeasurements()[LastMeasurement(container)].getValue();
                    }

                    if (load >= vehicles[i].getMaxCapacity()) {
                        int index = i;

                        for (int j = i + 1; j < strategies.length; j++) {

                            if (vehicles[j].getSupplyType() == type) {
                                index = j;
                                load = 0.0;
                            }

                            if (j == 9) {
                                i = -1;
                            }
                        }
                        this.strategies[numberStrategies++] = new RouteImp(vehicles[index]);
                    }

                    position++;
                } while (aidBoxes[position] != null);
            }
        }

        return strategies;
    }

}
