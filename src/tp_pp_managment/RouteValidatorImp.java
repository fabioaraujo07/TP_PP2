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
import com.estg.pickingManagement.Route;
import com.estg.pickingManagement.RouteValidator;
import com.estg.pickingManagement.Vehicle;
import com.estg.pickingManagement.exceptions.RouteException;
import java.util.logging.Level;
import java.util.logging.Logger;
import tp_pp.Classes.AidBoxImp;

/**
 * Implementation of the RouteValidator interface, responsible for validating
 * routes based on aid boxes.
 * 
 * This class provides a method to validate a route by checking if it contains
 * a specific aid box. If the aid box is not already in the route, it attempts
 * to add it. If the addition fails, the validation returns false.
 *
 */
public class RouteValidatorImp implements RouteValidator {

    /**
     * Validates a route by checking if it contains a specific aid box.
     * 
     * @param route The route to validate.
     * @param aidbox The aid box to check for in the route.
     * @return True if the route is valid, false otherwise.
     */
    @Override
    public boolean validate(Route route, AidBox aidbox) {

        if (aidbox == null) {
            return false;
        }

        if (!route.containsAidBox(aidbox)) {
            try {
                route.addAidBox(aidbox);
            } catch (RouteException ex) {
                return false;
            }
        }
        return true;
    }

}
