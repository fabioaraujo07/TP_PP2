/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp_pp_managment;

import com.estg.core.AidBox;
import com.estg.core.ItemType;
import com.estg.core.exceptions.AidBoxException;
import com.estg.pickingManagement.Vehicle;
import com.estg.pickingManagement.exceptions.RouteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import tp_pp_exceptions.FindException;

/**
 *
 * @author fabio
 */
public class RouteImp implements com.estg.pickingManagement.Route {

    private AidBox[] routes;
    private int numberAidboxes;
    private Vehicle vehicle;
    private double totalDistance;
    private double totalDuration;
    

    public RouteImp(AidBox[] route, int numberAidboxes, Vehicle vehicle) {
        this.routes = new AidBox[10];
        this.numberAidboxes = 0;
        this.vehicle = vehicle;
    }

    public int findAidBox(AidBox aidbox) {
        for (int i = 0; i < numberAidboxes; i++) {
            if (routes[i].equals(aidbox)) {
                return i;
            }
        }
        return -1;
    }

    public boolean canTransport(AidBox aidbox) throws RouteException {
    if (vehicle instanceof VehicleImp) {
        VehicleImp v = (VehicleImp) vehicle;

        for (int i = 0; i < aidbox.getContainers().length; i++) {
            ItemType type = aidbox.getContainers()[i].getType();
            if (v.getSupplyType().equals(type)) {
                if (type.equals(ItemType.PERISHABLE_FOOD) && getTotalDistance() > v.getKms()) {
                    throw new RouteException("Total distance exceeds limit for perishable food");
                }
                return true;
            }
        }
    }
    throw new RouteException("Vehicle can't transport any of these containers");
}


    @Override
    public void addAidBox(AidBox aidbox) throws RouteException {

        if (aidbox == null) {
            throw new RouteException("Aidbox can't be null");
        }
        if (findAidBox(aidbox) == -1) {
            throw new RouteException("Aidbox already exists in the route");
        }
        try {
            canTransport(aidbox);

        } catch (RouteException ex) {
            throw new RouteException("Vehicle can't transport any of these containers");
        }
        routes[numberAidboxes++] = aidbox;
    }

    @Override
    public AidBox removeAidBox(AidBox aidbox) throws RouteException {

        int pos = findAidBox(aidbox);

        if (aidbox == null) {
            throw new RouteException("Aidbox can't be null");
        }
        if (pos == -1) {
            throw new RouteException("Aidbox could not be found");
        }
        AidBox removedAidBox = this.routes[pos];
        this.routes[pos] = this.routes[numberAidboxes - 1];
        this.routes[--numberAidboxes] = null;

        return removedAidBox;
    }

    @Override
    public boolean containsAidBox(AidBox aidbox) {
        for (int i = 0; i < numberAidboxes; i++) {
            if (routes[i].equals(aidbox)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void replaceAidBox(AidBox aidbox, AidBox aidbox1) throws RouteException {

        if (aidbox == null) {
            throw new RouteException("Any AidBox can´t be null");
        }
        if (aidbox1 == null) {
            throw new RouteException("Any AidBox can´t be null");
        }
        if (!containsAidBox(aidbox)) {
            throw new RouteException("AidBox to replace is not in the route");
        }
        if (containsAidBox(aidbox1)) {
            throw new RouteException("AidBox to insert is already in the route");
        }
        try {
            canTransport(aidbox);
        } catch (RouteException e) {
            throw new RouteException("Vehicle can't transport any of these containers");
        }
        int pos = findAidBox(aidbox);
        this.routes[pos] = aidbox1;
    }

    @Override
    public void insertAfter(AidBox aidbox, AidBox aidbox1) throws RouteException {

        if (aidbox == null) {
            throw new RouteException("AidBox can´t be null");
        }
        if (aidbox1 == null) {
            throw new RouteException("AidBox can´t be null");
        }
        if (!containsAidBox(aidbox)) {
            throw new RouteException("AidBox is not in the route");
        }
        if (containsAidBox(aidbox1)) {
            throw new RouteException("AidBox to insert is already in the route");
        }
        try {
            canTransport(aidbox);
        } catch (RouteException e) {
            throw new RouteException("Vehicle can't transport any of these containers");
        }

        int pos = findAidBox(aidbox);

        for (int i = numberAidboxes; i > pos; i--) {
            routes[i] = routes[i - 1];
        }

        routes[pos + 1] = aidbox1;
        numberAidboxes++;
    }

    @Override
    public AidBox[] getRoute() {
        AidBox[] copy = new AidBox[numberAidboxes];
        for (int i = 0; i < numberAidboxes; i++) {
            if (routes[i] != null) {
                copy[i] = routes[i];
            }
        }
        return copy;
    }

    @Override
    public Vehicle getVehicle() {
        return this.vehicle;
    }

    @Override
    public double getTotalDistance() {

        double totalDistance = 0;

        try {
            for (int i = 0; i < numberAidboxes; i++) {
                AidBox current = routes[i];
                AidBox next = routes[i + 1];
                totalDistance += current.getDistance(next);
            }
        } catch (AidBoxException ex) {
            System.out.println("Error during the calculation");
        }
        return totalDistance;
    }

    @Override
    public double getTotalDuration() {
        double totalDuration = 0;

        try {
            for (int i = 0; i < numberAidboxes; i++) {
                AidBox current = routes[i];
                AidBox next = routes[i + 1];
                totalDuration += current.getDuration(next);
            }
        } catch (AidBoxException ex) {
            System.out.println("Error during the calculation");
        }
        return totalDuration;
    }

}
