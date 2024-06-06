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

    public RouteImp(AidBox[] route, int numberAidboxes, double totalDistance, Vehicle vehicle, double totalDuration) {
        this.routes = new AidBox[10];
        this.numberAidboxes = 0;
        this.vehicle = vehicle;
        this.totalDistance = totalDistance;
        this.totalDuration = totalDuration;
    }

    public boolean findAidBox(AidBox aidbox) {
        for (int i = 0; i < numberAidboxes; i++) {
            if (routes[i].equals(aidbox)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addAidBox(AidBox aidbox) throws RouteException {

        if (aidbox == null) {
            throw new RouteException("Aidbox can't be null");
        }
        if (findAidBox(aidbox)) {
            throw new RouteException("Aidbox already exists in the route");
        }
        if (vehicle instanceof VehicleImp) {
            for(int i = 0; i < aidbox.getContainers().length; i++) {
                if(!((VehicleImp) vehicle).canTransport(aidbox.getContainers()[i].getType())) {
                    throw new RouteException("Vehicle can't transport container type");
                }
            }
        }
        routes[numberAidboxes++] = aidbox;
    }

    @Override
    public AidBox removeAidBox(AidBox aidbox) throws RouteException {

        AidBox removedAidbox = null;

        if (aidbox == null) {
            throw new RouteException("Aidbox can't be null");
        }
        if (findAidBox(aidbox) == false) {
            throw new RouteException("Aidbox could not be found");
        }
        for (int i = 0; i < numberAidboxes; i++) {
            if (routes[i].equals(aidbox)) {
                removedAidbox = routes[i];
            }

            for (int j = i; j < numberAidboxes - 1; j++) {
                routes[j] = routes[j + 1];
            }
            routes[numberAidboxes - 1] = null;
            numberAidboxes--;
        }
        return removedAidbox;
    }

    @Override
    public boolean containsAidBox(AidBox aidbox) {
        return findAidBox(aidbox);
    }

    @Override
    public void replaceAidBox(AidBox aidbox, AidBox aidbox1) throws RouteException {

        if (aidbox == null) {
            throw new RouteException("Any AidBox can´t be null");
        }
        if (aidbox1 == null) {
            throw new RouteException("Any AidBox can´t be null");
        }
        if (findAidBox(aidbox) == false) {
            throw new RouteException("AidBox to replace is not in the route");
        }
        if (findAidBox(aidbox1) == true) {
            throw new RouteException("AidBox to insert is already in the route");
        }
        if (vehicle instanceof VehicleImp) {
            for(int i = 0; i < aidbox.getContainers().length; i++) {
                if(!((VehicleImp) vehicle).canTransport(aidbox.getContainers()[i].getType())) {
                    throw new RouteException("Vehicle can't transport container type");
                }
            }
        }

        for (int i = 0; i < numberAidboxes; i++) {
            if (routes[i].equals(aidbox)) {
                routes[i] = aidbox1;
            }
        }

    }

    @Override
    public void insertAfter(AidBox aidbox, AidBox aidbox1) throws RouteException {

        if (aidbox == null) {
            throw new RouteException("AidBox to insert before can´t be null");
        }
        if (aidbox1 == null) {
            throw new RouteException("AidBox to insert can´t be null");
        }
        if (findAidBox(aidbox1) == true) {
            throw new RouteException("AidBox is already in the route");
        }
        if (vehicle instanceof VehicleImp) {
            for(int i = 0; i < aidbox.getContainers().length; i++) {
                if(!((VehicleImp) vehicle).canTransport(aidbox.getContainers()[i].getType())) {
                    throw new RouteException("Vehicle can't transport container type");
                }
            }
        }

        int positionAidBox1 = -1;
        for (int i = 0; i < numberAidboxes; i++) {
            if(routes[i].equals(positionAidBox1)) {
                positionAidBox1 = i;
            }
        }
        
        for(int i = numberAidboxes; i > positionAidBox1; i--) {
            routes[i] = routes[i - 1];
        }
        
        routes[positionAidBox1 + 1] = aidbox1;
        numberAidboxes++;

    }    
    

    @Override
    public AidBox[] getRoute() {
        AidBox[] copy = new AidBox[numberAidboxes];
        for(int i = 0; i < numberAidboxes; i++) {
            if(routes[i] != null) {
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
        for(int i = 0; i < numberAidboxes; i++) {
            AidBox current = routes[i];
            AidBox next = routes[i + 1];
            try {
                totalDistance += current.getDistance(next);
            } catch (AidBoxException ex) {
                System.out.println("Error during the calculation");
            }
        }
        return totalDistance;
    }

    
    @Override
    public double getTotalDuration() {
        double totalDuration = 0;
        
        for(int i = 0; i < numberAidboxes; i++) {
            AidBox current = routes[i];
            AidBox next = routes[i + 1];
            try {
                totalDuration += current.getDuration(next);
            } catch (AidBoxException ex) {
                System.out.println("Error during the calculation");
            }
        }
        return totalDuration;
    }
    
    

}
