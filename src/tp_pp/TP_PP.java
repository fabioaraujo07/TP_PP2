/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tp_pp;

import com.estg.core.AidBox;
import com.estg.core.Container;
import com.estg.core.ItemType;
import com.estg.core.exceptions.ContainerException;
import java.util.logging.Level;
import java.util.logging.Logger;
import tp_pp.Classes.AidBoxImp;
import tp_pp.Classes.ContainerImp;

/**
 *
 * @author fabio
 */
public class TP_PP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        AidBox a1 = new AidBoxImp("12345", "Praia", "CV", 123456, 32132133);
        Container c1 = new ContainerImp("12", 12, ItemType.MEDICINE);
        Container c2 = new ContainerImp("13", 12, ItemType.MEDICINE);
        
        
        try {
            a1.addContainer(c1);
            a1.addContainer(c2);
        } catch (ContainerException ex) {
            Logger.getLogger(TP_PP.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.out.println(a1.toString());
        
    }
    
}
