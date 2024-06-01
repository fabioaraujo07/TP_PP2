/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp_pp.Classes;

import com.estg.core.Container;
import com.estg.core.GeographicCoordinates;
import com.estg.core.ItemType;
import com.estg.core.exceptions.AidBoxException;
import com.estg.core.exceptions.ContainerException;
import java.math.MathContext;

/**
 *
 * @author Fábio da Cunha, Roger Nakauchi
 */
public class AidBoxImp implements com.estg.core.AidBox {

    private final double AVERAGE = 60; // suponhamos que seja a velocidade média para cada veículo

    private String code;
    private String zone;
    private String refLocal;
    private GeographicCoordinates coordinates;
    private Container[] containers;
    private int numberContainers;

    public AidBoxImp(String code, String zone, String refLocal, double latitude, double longitude) {
        this.code = code;
        this.zone = zone;
        this.refLocal = refLocal;
        this.coordinates = new GetCoordinatesImp(latitude, longitude);
        this.containers = new ContainerImp[4];
        this.numberContainers = 0;
    }

    @Override
    public double getDistance(com.estg.core.AidBox aidbox) throws AidBoxException {
        if (aidbox == null) {
            throw new AidBoxException("O aidbox não existe");
        }

        //Supostamente tirando as coordenas de cada Aidbox
        //Current AidBox
        double lat1 = this.getLatitude();
        double long1 = this.getLongitude();

        //Parameter AidBox
        double lat2 = aidbox.getCoordinates().getLatitude();
        double long2 = aidbox.getCoordinates().getLongitude();

        //Raio da Terra em KM
        final int R = 6371;

        //Por enquanto vamos deixar assim, confirmar com o professor, porque tudo que aparece é com esta fórmula
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(long2 - long1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c;

        return distance;
    }

    @Override
    public double getDuration(com.estg.core.AidBox aidbox) throws AidBoxException {
        if (aidbox == null) {
            throw new AidBoxException("O aidbox não existe");
        }

        //Calcular a distância 
        double distance = this.getDistance(aidbox);

        //Calcular a duração
        double duration = distance / AVERAGE;// duração em horas 

        //Duração em segundos
        duration *= 3600;

        return duration;
    }

    @Override
    public GeographicCoordinates getCoordinates() {
        return this.coordinates;
    }

    public int findContainer(Container cntnr) {
        for (int i = 0; i < this.numberContainers; i++) {
            if (this.containers[i].equals(cntnr)) {
                return i;
            }
        }
        return -1;//Criar uma exceção para este método
    }

    @Override
    public boolean addContainer(Container cntnr) throws ContainerException {// Já está pronto não mexa no meu código
        if (cntnr == null) {
            throw new ContainerException("Conteiner can't be null");
        }
        if (findContainer(cntnr) != -1) {
            return false;
        }
        if (this.numberContainers == this.containers.length) {
            throw new ContainerException("Max capacity hitted");
        }

        for (int i = 0; 1 < numberContainers; i++) {
            if (containers[i].getType().equals(cntnr.getType())) {
                throw new ContainerException("AidBox already have a container from a given type");
            }
        }

        this.containers[numberContainers++] = cntnr;
        return true;
    }

    @Override
    public Container getContainer(ItemType it) {
        for (Container c : containers) {
            if (c != null && c.getType().equals(it)) {
                return c;
            }
        }
        return null;
    }

    @Override
    public String getCode() {
        return this.code;
    }

    @Override
    public String getZone() {
        return this.zone;
    }

    @Override
    public String getRefLocal() {
        return this.refLocal;
    }
    
    public int getPickedContainers(){
        return this.numberContainers;
    }

    public Container[] getContainers() {
        Container[] copyContainers = new ContainerImp[numberContainers];
        for (int i = 0; i < numberContainers; i++) {
            if (containers[i] != null) {
                copyContainers[i] = this.containers[i];
            }
        }
        return copyContainers;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || !(obj instanceof AidBoxImp)) {
            return false;
        }
        AidBoxImp aidbox = (AidBoxImp) obj;
        return this.code == aidbox.code;
    }

    public double getLatitude() {
        return coordinates.getLatitude();
    }

    public double getLongitude() {
        return coordinates.getLongitude();
    }

    @Override
    public String toString() {
        return "Code: " + code + "\nZone: " + zone + "\nLatitude: " + coordinates.getLatitude()
                + "\nLongitude: " + coordinates.getLongitude();
    }

}
