/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tp_pp;

import com.estg.core.AidBox;
import com.estg.core.Container;
import com.estg.core.Institution;
import com.estg.core.ItemType;
import com.estg.core.exceptions.AidBoxException;
import com.estg.core.exceptions.ContainerException;
import http.HttpProviderImp;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.parser.ParseException;
import tp_pp.Classes.AidBoxImp;
import tp_pp.Classes.ContainerImp;
import tp_pp.Classes.InstitutionImp;

/**
 *
 * @author fabio
 */
public class TP_PP {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws AidBoxException {
       try{
           HttpProviderImp http = new HttpProviderImp();
//           AidBox a1 = new AidBoxImp("id","CAIXF33", "Praia", "uisfai", 0, 0);
//           AidBox a2 = new AidBoxImp("id","Base", "Praia", "uisfai", 0, 0);
//           Institution i1 = new InstitutionImp("Base");
           
           System.out.println(http.getReadings());
           
//           System.out.println(i1.getDistance(a1));
//           
//           System.out.println(a1.getDuration(a2));
//           String aidboxes = http.getDistancesAidbox("CAIXF37", "Base");
//           System.out.println("Aidboxes: " + aidboxes);
       }catch(IOException | ParseException e){
           e.printStackTrace();
       }
    }
    
}
