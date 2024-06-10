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
 */
public class PickingMapImp implements com.estg.pickingManagement.PickingMap {

    /**
     * The creation date of the picking map.
     */
    private LocalDateTime date;

    /**
     * An array of Route objects representing the routes associated with the
     * picking map.
     */
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
