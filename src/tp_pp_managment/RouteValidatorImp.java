/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
 *
 * @author Roger Nakauchi
 */
public class RouteValidatorImp implements RouteValidator {

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
