/*
 * Nome: Roger Nakauchi
 * Número: 8210005
 * Turna: LSIRCT1
 *
 * Nome: Fábio da Cunha
 * Número: 8210619
 * Turna: LSIRCT1
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
 * Implementation of the Route interface, responsible for managing aid boxes
 * within a specific route and associated vehicle.
 *
 * This class provides methods to add, remove, and manage aid boxes in a route,
 * and calculate total distance and duration of the route.
 *
 */
public class RouteImp implements com.estg.pickingManagement.Route {

    /**
     * An array of AidBox objects representing the aid boxes in the route.
     */
    private AidBox[] routes;

    /**
     * The number of aid boxes in the route.
     */
    private int numberAidboxes;

    /**
     * The vehicle associated with the route.
     */
    private Vehicle vehicle;

    /**
     * The total distance of the route.
     */
    private double totalDistance;

    /**
     * The total duration of the route.
     */
    private double totalDuration;

    /**
     * Constructor to initialize the RouteImp with a specific vehicle.
     *
     * @param vehicle The vehicle associated with this route.
     */
    public RouteImp(Vehicle vehicle) {
        this.routes = new AidBox[10];
        this.numberAidboxes = 0;
        this.vehicle = vehicle;
    }

    /**
     * Finds the index of a specific aid box in the route.
     *
     * @param aidbox The aid box to find.
     * @return The index of the aid box.
     * @throws FindException If the aid box is not found in the route.
     */
    public int findAidBox(AidBox aidbox) throws FindException {
        for (int i = 0; i < numberAidboxes; i++) {
            if (routes[i].equals(aidbox)) {
                return i;
            }
        }
        throw new FindException();
    }

    /**
     * Checks if the vehicle can transport the specified aid box.
     *
     * @param aidbox The aid box to check.
     * @return True if the vehicle can transport the aid box, false otherwise.
     * @throws RouteException If the vehicle can't transport the aid box.
     */
    public boolean canTransport(AidBox aidbox) throws RouteException {
        if (vehicle instanceof VehicleImp) {
            VehicleImp v = (VehicleImp) vehicle;

            if (v.isEnabled() == false) {
                throw new RouteException("Vehicle is not enabled");
            }

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

    /**
     * Adds an aid box to the route.
     *
     * @param aidbox The aid box to add.
     * @throws RouteException If the aid box can't be added to the route.
     */
    @Override
    public void addAidBox(AidBox aidbox) throws RouteException {

        if (aidbox == null) {
            throw new RouteException("Aidbox can't be null");
        }
        try {
            if (findAidBox(aidbox) == -1) {
                throw new RouteException("Aidbox already exists in the route");
            }
        } catch (FindException ex) {
            Logger.getLogger(RouteImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            canTransport(aidbox);

        } catch (RouteException ex) {
            throw new RouteException("Vehicle can't transport any of these containers");
        }
        routes[numberAidboxes++] = aidbox;
    }

    /**
     * Removes an aid box from the route.
     *
     * @param aidbox The aid box to remove.
     * @return The removed aid box.
     * @throws RouteException If the aid box can't be removed from the route.
     */
    @Override
    public AidBox removeAidBox(AidBox aidbox) throws RouteException {

        if (aidbox == null) {
            throw new RouteException("Aidbox can't be null");
        }

        int pos;
        try {
            pos = findAidBox(aidbox);
        } catch (FindException ex) {
            Logger.getLogger(RouteImp.class.getName()).log(Level.SEVERE, null, ex);
            throw new RouteException("Error occurred while finding the aidbox");
        }

        if (pos == -1) {
            throw new RouteException("Aidbox could not be found");
        }

        AidBox removedAidBox = this.routes[pos];

        for (int i = pos; i < numberAidboxes - 1; i++) {
            this.routes[i] = this.routes[i + 1];
        }
        this.routes[--numberAidboxes] = null;

        return removedAidBox;
    }

    /**
     * Checks if the route contains a specific aid box.
     *
     * @param aidbox The aid box to check.
     * @return True if the route contains the aid box, false otherwise.
     */
    @Override
    public boolean containsAidBox(AidBox aidbox) {
        for (int i = 0; i < numberAidboxes; i++) {
            if (routes[i].equals(aidbox)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Replaces an aid box in the route with another aid box.
     *
     * @param aidbox The aid box to replace.
     * @param aidbox1 The new aid box to insert.
     * @throws RouteException If the aid box can't be replaced.
     */
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
        int pos = 0;
        try {
            pos = findAidBox(aidbox);
        } catch (FindException ex) {
            Logger.getLogger(RouteImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.routes[pos] = aidbox1;
    }

    /**
     * Inserts an aid box into the route after a specified aid box.
     *
     * @param aidbox The aid box after which the new aid box will be inserted.
     * @param aidbox1 The new aid box to insert.
     * @throws RouteException If the aid box can't be inserted.
     */
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

        int pos = 0;
        try {
            pos = findAidBox(aidbox);
        } catch (FindException ex) {
            Logger.getLogger(RouteImp.class.getName()).log(Level.SEVERE, null, ex);
        }

        for (int i = numberAidboxes; i > pos; i--) {
            routes[i] = routes[i - 1];
        }

        routes[pos + 1] = aidbox1;
        numberAidboxes++;
    }

    /**
     * Gets the current route (array of aid boxes).
     *
     * @return A copy of the current route.
     */
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

    /**
     * Gets the vehicle associated with the route.
     *
     * @return The vehicle associated with the route.
     */
    @Override
    public Vehicle getVehicle() {
        return this.vehicle;
    }

    /**
     * Gets the total distance of the route.
     *
     * @return The total distance of the route.
     */
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

    /**
     * Gets the total duration of the route.
     *
     * @return The total duration of the route.
     */
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

    /**
     * Sets the total distance of the route.
     *
     * @param totalDistance The total distance to set.
     */
    public void setTotalDistance(double totalDistance) {
        this.totalDistance = totalDistance;
    }

    /**
     * Sets the total duration of the route.
     *
     * @param totalDuration The total duration to set.
     */
    public void setTotalDuration(double totalDuration) {
        this.totalDuration = totalDuration;
    }

}
