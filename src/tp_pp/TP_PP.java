/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package tp_pp;

import com.estg.core.AidBox;
import com.estg.core.Container;
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
           
           String aidboxes = http.getReadings();
           System.out.println("Aidboxes: " + aidboxes);
       }catch(IOException | ParseException e){
           e.printStackTrace();
       }
    }
    
}
