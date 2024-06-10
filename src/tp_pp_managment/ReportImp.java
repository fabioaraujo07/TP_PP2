/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp_pp_managment;

import com.estg.core.Institution;
import java.time.LocalDateTime;
import tp_pp.Classes.InstitutionImp;
import com.estg.core.AidBox;
import com.estg.pickingManagement.Route;
import tp_pp.Classes.AidBoxImp;

/**
 * Implementation of the Report interface for managing and generating reports
 * related to the institution's logistics operations.
 * 
 * This class stores and provides access to various metrics such as the number
 * of used vehicles, picked containers, total distance, total duration, etc.
 * It also allows setting these metrics after computing the results.
 *
 * @author Fábio da Cunha, Roger Nakauchi
 */
public class ReportImp implements com.estg.pickingManagement.Report {

    private LocalDateTime date;
    private Institution institution;
    private AidBox aidbox;
    private Route distance;
    private Route duration;

    /**
     * Constructor to initialize the ReportImp with the specified date,
     * institution, and container.
     * 
     * @param date The date of the report.
     * @param institution The institution associated with the report.
     * @param container The container (aidbox) related to the report.
     */
    public ReportImp(LocalDateTime date, Institution institution, AidBox container) {
        this.date = date;
        this.institution = institution;
        this.aidbox = container;
    }

    /**
     * Gets the number of used vehicles in the institution.
     * 
     * @return The number of used vehicles.
     * @throws IllegalArgumentException If the institution is null or not of type InstitutionImp.
     */
    @Override
    public int getUsedVehicles() throws IllegalArgumentException {
        if (this.institution == null) {
            throw new IllegalArgumentException("Institution can't be null");
        }
        try {
            if (this.institution instanceof InstitutionImp) {
                InstitutionImp inst = (InstitutionImp) this.institution;
                return inst.getUsedVehicles();
            } else {
                throw new IllegalArgumentException("Institution is not of type InstitutionImp");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalArgumentException("Error while getting used vehicles");
        }
    }

    /**
     * Gets the number of picked containers in the institution.
     * 
     * @return The number of picked containers.
     * @throws IllegalArgumentException If the container is null or not of type AidboxImp.
     */
    @Override
    public int getPickedContainers() {
        if (this.aidbox == null) {
            throw new IllegalArgumentException("Container can't be null");
        }
        try {
            if (this.aidbox instanceof AidBoxImp) {
                AidBoxImp aid = (AidBoxImp) this.aidbox;
                return aid.getPickedContainers();
            } else {
                throw new IllegalArgumentException("Container is not of type AidboxImp");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalArgumentException("Error while getting picked containers");
        }
    }

    /**
     * Gets the total distance covered in the routes.
     * 
     * @return The total distance.
     * @throws IllegalArgumentException If the distance route is null or not of type RouteImp.
     */
    @Override
    public double getTotalDistance() {
        if (this.distance == null) {
            throw new IllegalArgumentException("Distance can't be null");
        }
        try {
            if (this.distance instanceof RouteImp) {
                RouteImp dist = (RouteImp) this.distance;
                return this.getTotalDistance();
            } else {
                throw new IllegalArgumentException("Distance is not of type RouteImp");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalArgumentException("Error while getting total distance");
        }
    }

    /**
     * Gets the total duration of the routes.
     * 
     * @return The total duration.
     * @throws IllegalArgumentException If the duration route is null or not of type RouteImp.
     */
    @Override
    public double getTotalDuration() {
        if (this.duration == null) {
            throw new IllegalArgumentException("Duration can't be null");
        }
        try {
            if (this.duration instanceof RouteImp) {
                RouteImp dur = (RouteImp) this.duration;
                return dur.getTotalDuration();
            } else {
                throw new IllegalArgumentException("Container is not of type AidboxImp");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalArgumentException("Error while getting total duration");
        }
    }

    /**
     * Gets the number of non-picked containers in the institution.
     * 
     * @return The number of non-picked containers, or -1 if the container is null.
     * @throws IllegalArgumentException If the container is not of type AidboxImp.
     */
    @Override
    public int getNonPickedContainers() {
        if (this.aidbox == null) {
            return -1;
        }
        try {
            if (this.aidbox instanceof AidBoxImp) {
                AidBoxImp cntnr = (AidBoxImp) this.aidbox;
                return cntnr.getNonPickedContainers();
            } else {
                throw new IllegalArgumentException("Container is not of type AidboxImp");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalArgumentException("Error while getting non picked containers");
        }
    }

    /**
     * Gets the number of not used vehicles in the institution.
     * 
     * @return The number of not used vehicles, or -1 if the institution is null.
     * @throws IllegalArgumentException If the institution is not of type InstitutionImp.
     */
    @Override
    public int getNotUsedVehicles() {
        if (this.institution == null) {
            return -1;
        }
        try {
            if (this.institution instanceof InstitutionImp) {
                InstitutionImp vhcl = (InstitutionImp) this.institution;
                return vhcl.getNotUsedVehicles();
            } else {
                throw new IllegalArgumentException("Container is not of type AidboxImp");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalArgumentException("Error while getting not used vehicles");
        }

    }

    /**
     * Gets the date of the report.
     * 
     * @return The date of the report.
     */
    @Override
    public LocalDateTime getDate() {
        return this.date;
    }

    /**
     * Sets the number of used vehicles in the institution.
     * 
     * @param addUsedVehicles The number of vehicles to add to the current count of used vehicles.
     * @throws IllegalArgumentException If the institution is not of type InstitutionImp or any error occurs while setting the used vehicles.
     */
    public void setUsedVehicles(int addUsedVehicles) {
        try {
            int currentUsedVehicles = this.getUsedVehicles();
            if (this.institution instanceof InstitutionImp) {
                InstitutionImp inst = (InstitutionImp) this.institution;
                inst.setUsedVehicles(currentUsedVehicles + addUsedVehicles);
            } else {
                throw new IllegalArgumentException("Institution isn't an InstitutionImp");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalArgumentException("Error while setting used vehicles");
        }
    }

    /**
     * Sets the number of picked containers in the institution.
     * 
     * @param addPickedContainers The number of containers to add to the current count of picked containers.
     * @throws IllegalArgumentException If the institution is not of type InstitutionImp or any error occurs while setting the picked containers.
     */
    public void setPickedContainers(int addPickedContainers) {
        try {
            int currentPickedContainers = this.getPickedContainers();
            if (this.institution instanceof InstitutionImp) {
                InstitutionImp inst = (InstitutionImp) this.institution;
                inst.setPickedContainers(currentPickedContainers + addPickedContainers);
            } else {
                throw new IllegalArgumentException("Institution ins't an InstitutionImp");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalArgumentException("Error while setting picked containers");
        }
    }

    /**
     * Sets the total distance covered in the routes.
     * 
     * @param addTotalDistance The distance to add to the current total distance.
     * @throws IllegalArgumentException If the distance route is not of type RouteImp or any error occurs while setting the total distance.
     */
    public void setTotalDistance(double addTotalDistance) {
        try {
            double currentTotalDistance = this.getTotalDistance();
            if (this.distance instanceof RouteImp) {
                RouteImp dist = (RouteImp) this.distance;
                dist.setTotalDistance(currentTotalDistance + addTotalDistance);
            } else {
                throw new IllegalArgumentException("Distance is not a type of RouteImp");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalArgumentException("Error while setting total distance");
        }
    }

    /**
     * Sets the total duration of the routes.
     * 
     * @param addTotalDuration The duration to add to the current total duration.
     * @throws IllegalArgumentException If the duration route is not of type RouteImp or any error occurs while setting the total duration.
     */
    public void setTotalDuration(double addTotalDuration) {
        try {
            double currentTotalDuration = this.getTotalDuration();
            if (this.duration instanceof RouteImp) {
                RouteImp dur = (RouteImp) this.duration;
                dur.setTotalDuration(currentTotalDuration + addTotalDuration);
            } else {
                throw new IllegalArgumentException("Duration is not a type of RouteImp");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw new IllegalArgumentException("Error while setting total duration");
        }
    }

}
