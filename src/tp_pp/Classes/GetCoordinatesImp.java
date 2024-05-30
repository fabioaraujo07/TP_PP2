/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp_pp.Classes;

/**
 *
 * @author fabio
 */
public class GetCoordinatesImp implements com.estg.core.GeographicCoordinates{
  
    private double latitude;
    private double longitude;

    public GetCoordinatesImp(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
    
    @Override
    public double getLatitude() {
        return this.latitude;
    }

    @Override
    public double getLongitude() {
        return this.longitude;
    }
    
}
