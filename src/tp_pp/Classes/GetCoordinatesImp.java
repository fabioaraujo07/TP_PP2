/*
 * Nome: Roger Nakauchi
 * Número: 8210005
 * Turna: LSIRCT1
 *
 * Nome: Fábio da Cunha
 * Número: 8210619
 * Turna: LSIRCT1
 */
package tp_pp.Classes;

/**
 * Implementation of the GeographicCoordinates interface, representing a
 * geographical location with latitude and longitude coordinates.
 *
 */
public class GetCoordinatesImp implements com.estg.core.GeographicCoordinates {

    /**
     * The latitude of the geographical location.
     */
    private double latitude;

    /**
     * The longitude of the geographical location.
     */
    private double longitude;

    /**
     * Constructs a GetCoordinatesImp with specified latitude and longitude.
     *
     * @param latitude the latitude of the location
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
