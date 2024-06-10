/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package tp_pp.Classes;

/**
 * Implementation of the GeographicCoordinates interface, representing a geographical location
 * with latitude and longitude coordinates.
 * 
 * @autor Fábio da Cunha, Roger Nakauchi
 */
public class GetCoordinatesImp implements com.estg.core.GeographicCoordinates{
  
    private double latitude;
    private double longitude;

    /**
     * Constructs a GetCoordinatesImp with specified latitude and longitude.
     *
     * @param latitude  the latitude of the location
     * @param longitude the longitude of the location
     */
    public GetCoordinatesImp(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
    
    /**
     * Retrieves the latitude of this location.
     *
     * @return the latitude of this location
     */
    @Override
    public double getLatitude() {
        return this.latitude;
    }

    /**
     * Retrieves the longitude of this location.
     *
     * @return the longitude of this location
     */
    @Override
    public double getLongitude() {
        return this.longitude;
    }
    
}
