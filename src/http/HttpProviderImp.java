/*
 * Nome: Roger Nakauchi
 * Número: 8210005
 * Turna: LSIRCT1
 *
 * Nome: Fábio da Cunha
 * Número: 8210619
 * Turna: LSIRCT1
 */
package http;

import com.estg.io.HTTPProvider;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Implementation of an HTTP provider for fetching data related to aid boxes,
 * distances, and readings from external APIs.
 */
public class HttpProviderImp {

    /**
     * The HTTPProvider instance for making HTTP requests.
     */
    private HTTPProvider httpProvider;

    /**
     * Initializes a new instance of the HttpProviderImp class.
     */
    public HttpProviderImp() {
        this.httpProvider = new HTTPProvider();
    }

    /**
     * Retrieves the information of all aid boxes.
     *
     * @return A JSON string containing the information of all aid boxes.
     * @throws IOException if an I/O error occurs while making the request.
     * @throws ParseException if there is an error while parsing the JSON
     * response.
     */
    public String getAidBoxes() throws IOException, ParseException {
        String url = "https://data.mongodb-api.com/app/data-docuz/endpoint/aidboxes";
        String jsonResponse = httpProvider.getFromURL(url);
        JSONParser parser = new JSONParser();
        JSONArray aidboxes = (JSONArray) parser.parse(jsonResponse);
        return aidboxes.toJSONString();
    }

    /**
     * Retrieves the information of an aid box by its code.
     *
     * @param codigo The code of the aid box.
     * @return A JSON string containing the information of the specified aid
     * box.
     * @throws IOException if an I/O error occurs while making the request.
     * @throws ParseException if there is an error while parsing the JSON
     * response.
     */
    public String getAidBoxesCode(String codigo) throws IOException, ParseException {
        String url = "https://data.mongodb-api.com/app/data-docuz/endpoint/aidboxesbyid?codigo=" + codigo;
        String jsonResponse = httpProvider.getFromURL(url);
        JSONParser parser = new JSONParser();
        JSONObject aidboxesId = (JSONObject) parser.parse(jsonResponse);
        return aidboxesId.toJSONString();
    }

    /**
     * Retrieves the distances between two aid boxes.
     *
     * @param codigoOrigem The code of the origin aid box.
     * @param codigoDestino The code of the destination aid box.
     * @return A JSON string containing the distances between the specified aid
     * boxes.
     * @throws IOException if an I/O error occurs while making the request.
     * @throws ParseException if there is an error while parsing the JSON
     * response.
     */
    public String getDistancesAidbox(String codigoOrigem, String codigoDestino) throws IOException, ParseException {
        String url = "https://data.mongodb-api.com/app/data-docuz/endpoint/distances?from=" + codigoOrigem + "&to=" + codigoDestino;
        String jsonResponse = httpProvider.getFromURL(url);
        JSONParser parser = new JSONParser();
        JSONObject distances = (JSONObject) parser.parse(jsonResponse);
        return distances.toJSONString();
    }

    /**
     * Retrieves the readings from sensors.
     *
     * @return A JSON string containing the readings from sensors.
     * @throws ParseException if there is an error while parsing the JSON
     * response.
     * @throws IOException if an I/O error occurs while making the request.
     */
    public String getReadings() throws ParseException, IOException {
        String url = "https://data.mongodb-api.com/app/data-docuz/endpoint/readings";
        String jsonResponse = httpProvider.getFromURL(url);
        JSONParser parser = new JSONParser();
        JSONArray readings = (JSONArray) parser.parse(jsonResponse);
        return readings.toJSONString();
    }

    /**
     * Retrieves the distances from an aid box to the base.
     *
     * @param codigoOrigem The code of the origin aid box.
     * @return A JSON string containing the distances from the specified aid box
     * to the base.
     * @throws IOException if an I/O error occurs while making the request.
     * @throws ParseException if there is an error while parsing the JSON
     * response.
     */
    public String getDistances(String codigoOrigem) throws IOException, ParseException {
        String url = "https://data.mongodb-api.com/app/data-docuz/endpoint/distances?from=" + codigoOrigem + "&to=" + "Base";
        String jsonResponse = httpProvider.getFromURL(url);
        JSONParser parser = new JSONParser();
        JSONObject distances = (JSONObject) parser.parse(jsonResponse);
        return distances.toJSONString();
    }
}
