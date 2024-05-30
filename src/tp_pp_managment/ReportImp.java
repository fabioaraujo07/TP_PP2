/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp_pp_managment;

import com.estg.core.Institution;
import java.time.LocalDateTime;
import tp_pp.Classes.InstitutionImp;
import com.estg.core.AidBox;
import tp_pp.Classes.AidBoxImp;

/**
 *
 * @author fabio
 */
public class ReportImp implements com.estg.pickingManagement.Report {

    private LocalDateTime date;

    private Institution institution;
    private AidBox container;

    public ReportImp(LocalDateTime date) {
        this.date = date;
    }

    @Override
    public int getUsedVehicles() {
        if (this.institution instanceof InstitutionImp) {
            InstitutionImp inst = (InstitutionImp) this.institution;
            return inst.getUsedVehicles();
        } else {
            throw new IllegalArgumentException("Institution is not of type InstitutionImp");
        }
    }

    @Override
    public int getPickedContainers() {
        if(this.container instanceof AidBoxImp){
            int count = 0;
            AidBoxImp aid = (AidBoxImp) this.container;
            for(int i = 0; i < aid.)
        }
    }

    @Override
    public double getTotalDistance() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public double getTotalDuration() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int getNonPickedContainers() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public int getNotUsedVehicles() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public LocalDateTime getDate() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
