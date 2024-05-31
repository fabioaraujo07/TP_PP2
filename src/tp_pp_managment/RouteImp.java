/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp_pp_managment;

import com.estg.core.AidBox;
import com.estg.core.ItemType;
import com.estg.pickingManagement.Vehicle;
import com.estg.pickingManagement.exceptions.RouteException;

/**
 *
 * @author fabio
 */
public class RouteImp implements com.estg.pickingManagement.Route{
    
    private AidBox route[];
    private int numberAidboxes;
    private Vehicle vehicle;

    public RouteImp(AidBox[] route, int numberAidboxes) {
        this.route = new AidBox[10];
        this.numberAidboxes = 0;
    }
    
    
    public boolean hasAidBox(AidBox aidbox) {
        for(int i = 0; i < numberAidboxes; i++) {
            if(route[i].equals(aidbox)) {
                return true;
            }
        }
        return false;
    }
    
    
    @Override
    public void addAidBox(AidBox aidbox) throws RouteException {
        
        if(aidbox == null) {
            throw new RouteException("Aidbox can't be null");
        }
        if(hasAidBox(aidbox)) {
            throw new RouteException("Aidbox already exists in the route");
        }
        if(vehicle instanceof VehicleImp) {
            if(!((VehicleImp) vehicle).canTransport(ItemType.CLOTHING)) {
                throw new RouteException("Vehicle can't transport Clothing Aidbox type");
            }
            if(!((VehicleImp) vehicle).canTransport(ItemType.MEDICINE)) {
                throw new RouteException("Vehicle can't transport Medicine Aidbox type");
            }
            if(!((VehicleImp) vehicle).canTransport(ItemType.NON_PERISHABLE_FOOD)) {
                throw new RouteException("Vehicle can't transport Non Perishable Food Aidbox type");
            }
            if(!((VehicleImp) vehicle).canTransport(ItemType.PERISHABLE_FOOD)) {
                throw new RouteException("Vehicle can't transport Perishable food Aidbox type");
            }
        }
        route[numberAidboxes++] = aidbox;        
    }

    @Override
    public AidBox removeAidBox(AidBox aidbox) throws RouteException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean containsAidBox(AidBox aidbox) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void replaceAidBox(AidBox aidbox, AidBox aidbox1) throws RouteException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void insertAfter(AidBox aidbox, AidBox aidbox1) throws RouteException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public AidBox[] getRoute() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Vehicle getVehicle() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public double getTotalDistance() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public double getTotalDuration() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
