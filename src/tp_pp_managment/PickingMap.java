/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp_pp_managment;

import com.estg.pickingManagement.Route;
import java.time.LocalDateTime;

/**
 *
 * @author fabio
 */
public class PickingMap implements com.estg.pickingManagement.PickingMap{
    
    private LocalDateTime date;
    
    private Route[] routes;

    public PickingMap(LocalDateTime date, Route[] routes) {
        this.date = date;
        this.routes = routes;
    }
    
    @Override
    public LocalDateTime getDate() {
        return this.date;
    }

    @Override
    public Route[] getRoutes() {
        return this.routes;
    }
    
}
