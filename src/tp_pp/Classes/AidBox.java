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

/**
 *
 * @author Roger Nakauchi
 */
public class AidBox implements com.estg.core.AidBox {

    private String code;
    private String zone;
    private String refLocal;
    private GetCoordinates coordinates;
    private Container[] containers;
    private int numberContainers;

    public AidBox(String code, String zone, String refLocal, double latitude, double longitude) {
        this.code = code;
        this.zone = zone;
        this.refLocal = refLocal;
        this.coordinates = new GetCoordinates(latitude, longitude);
        this.containers = new Container[4];
        this.numberContainers = 0;
    }

    @Override
    public double getDistance(com.estg.core.AidBox aidbox) throws AidBoxException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public double getDuration(com.estg.core.AidBox aidbox) throws AidBoxException {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
    public boolean addContainer(Container cntnr) throws ContainerException {
        if (cntnr == null) {
            throw new ContainerException("Conteiner não pode ser nulo");
        }
        if (findContainer(cntnr) != -1) {
            return false;
        }
        if (this.numberContainers == this.containers.length) {
            throw new ContainerException("Capacidade máxima atingida");
        }
        
        for(int i = 0; 1 < numberContainers; i++){
            if(containers[i].getType().equals(cntnr.getType())){
                throw new ContainerException("Já existe conteiner do mesmo tipo");
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

    public Container[] getContainers() {
        Container[] copyContainers = new Container[numberContainers];
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
        if (obj == null || !(obj instanceof AidBox)) {
            return false;
        }
        AidBox aidbox = (AidBox) obj;
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
