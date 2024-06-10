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
        AidBoxImp aidBox1 = new AidBoxImp("id1", "code1", "zone1", "refLocal1", 41.3, -8.2);
        AidBoxImp aidBox2 = new AidBoxImp("id2", "code2", "zone2", "refLocal2", 41.4, -8.3);

        // Simulação de distâncias entre as caixas de ajuda
        // Supondo que a distância entre as duas caixas seja de 10.0 km
        // Isso pode variar dependendo de como você simula os dados
        String jsonResponse = "{ \"from\": \"code1\", \"to\": [ { \"name\": \"code2\", \"distance\": 10.0 } ] }";

        // Chamada do método getDistance e verificação do resultado
        try {
            double distance = aidBox1.getDistance(aidBox2);
            System.out.println("Distance between aidBox1 and aidBox2: " + distance + " km");
        } catch (AidBoxException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
