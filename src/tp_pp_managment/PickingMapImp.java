/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp_pp_managment;

import com.estg.pickingManagement.Route;
import java.time.LocalDateTime;

/**
 * Implementation of the PickingMap interface, representing a picking map with 
 * associated routes and a creation date.
 * 
 * This class stores a LocalDateTime object representing the creation date of 
 * the picking map, and an array of Route objects representing the routes 
 * associated with the picking map.
 *
 * @author Fábio da Cunha, Roger Nakauchi
 */
public class PickingMapImp implements com.estg.pickingManagement.PickingMap{
    
    private LocalDateTime date;    
    private Route[] routes;

    /**
     * Constructs a PickingMapImp object with the specified creation date.
     * 
     * @param date The creation date of the picking map.
     */
    public PickingMapImp(LocalDateTime date) {
        this.date = date;
        this.routes = routes;
    }
    
    /**
     * Retrieves the creation date of the picking map.
     * 
     * @return The LocalDateTime object representing the creation date.
     */
    @Override
    public LocalDateTime getDate() {
        return this.date;
    }

    /**
     * Retrieves the array of routes associated with the picking map.
     * 
     * @return An array of Route objects representing the routes.
     */
    @Override
    public Route[] getRoutes() {
        return this.routes;
    }
    
}
