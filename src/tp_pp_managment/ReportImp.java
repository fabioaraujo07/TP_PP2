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
    private AidBox container;
    private Route distance;
    private Route duration;

    public ReportImp(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public int getUsedVehicles() throws IllegalArgumentException {
        if (this.institution == null) {
            throw new IllegalArgumentException("Institution can't be null");
        }
        if (this.institution instanceof InstitutionImp) {
            InstitutionImp inst = (InstitutionImp) this.institution;
            return inst.getUsedVehicles();
        } else {
            throw new IllegalArgumentException("Institution is not of type InstitutionImp");
        }
    }

    @Override
    public int getPickedContainers() {
        if (this.container == null) {
            throw new IllegalArgumentException("Container can't be null");
        }
        if (this.container instanceof AidBoxImp) {
            AidBoxImp aid = (AidBoxImp) this.container;
            return aid.getPickedContainers();
        } else {
            throw new IllegalArgumentException("Container is not of type AidboxImp");
        }
    }

    @Override
    public double getTotalDistance() {
        if (this.distance == null) {
            throw new IllegalArgumentException("Distance can't be null");
        }
        if (this.distance instanceof RouteImp) {
            RouteImp dist = (RouteImp) this.distance;
            return this.getTotalDistance();
        } else {
            throw new IllegalArgumentException("Distance is not of type RouteImp");
        }
    }

    @Override
    public double getTotalDuration() {
        if (this.duration == null) {
            throw new IllegalArgumentException("Duration can't be null");
        }
        if (this.duration instanceof RouteImp) {
            RouteImp dur = (RouteImp) this.duration;
            return dur.getTotalDuration();
        } else {
            throw new IllegalArgumentException("Container is not of type AidboxImp");
        }
    }

    @Override
    public int getNonPickedContainers() {
        if (this.container == null) {
            return -1;
        }
        if (this.container instanceof AidBoxImp) {
            AidBoxImp cntnr = (AidBoxImp) this.container;
            return cntnr.getNonPickedContainers();
        } else {
            throw new IllegalArgumentException("Container is not of type AidboxImp");
        }
    }

    @Override
    public int getNotUsedVehicles() {
        if (this.institution == null) {
            return -1;
        }
        if (this.institution instanceof InstitutionImp) {
            InstitutionImp vhcl = (InstitutionImp) this.institution;
            return vhcl.getNotUsedVehicles();
        } else {
            throw new IllegalArgumentException("Container is not of type AidboxImp");
        }

    }

    @Override
    public LocalDateTime getDate() {
        return this.date;
    }

}
