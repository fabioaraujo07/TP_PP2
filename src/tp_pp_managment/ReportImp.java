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
 *
 * @author fabio
 */
public class ReportImp implements com.estg.pickingManagement.Report {

    private LocalDateTime date;

    private Institution institution;
    private AidBox aidbox;
    private Route distance;
    private Route duration;

    public ReportImp(LocalDateTime date, Institution institution, AidBox container) {
        this.date = date;
        this.institution = institution;
        this.aidbox = container;
    }

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

    @Override
    public LocalDateTime getDate() {
        return this.date;
    }

}
