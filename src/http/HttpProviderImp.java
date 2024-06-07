/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package http;

import com.estg.io.HTTPProvider;
import java.io.IOException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author fabio
 */
public class HttpProviderImp {
    
    private HTTPProvider httpProvider;
    
    public HttpProviderImp(){
        this.httpProvider = new HTTPProvider();
    }
    
    public String getAidBoxes() throws IOException, ParseException{
        String url = "https://data.mongodb-api.com/app/data-docuz/endpoint/aidboxes";
        String jsonResponse = httpProvider.getFromURL(url);
        JSONParser parser = new JSONParser();
        JSONArray aidboxes = (JSONArray) parser.parse(jsonResponse);
        return aidboxes.toJSONString();
    }
    
    public String getAidBoxesCode(String codigo) throws IOException, ParseException{
        String url = "https://data.mongodb-api.com/app/data-docuz/endpoint/aidboxesbyid?codigo=" + codigo;
        String jsonResponse = httpProvider.getFromURL(url);
        JSONParser parser = new JSONParser();
        JSONObject aidboxesId = (JSONObject) parser.parse(jsonResponse);
        return aidboxesId.toJSONString();
    }
    
    
    public String getDistancesAidbox(String codigoOrigem, String codigoDestino) throws IOException, ParseException {
        String url = "https://data.mongodb-api.com/app/data-docuz/endpoint/distances?from=" + codigoOrigem + "&to=" + codigoDestino;
        String jsonResponse = httpProvider.getFromURL(url);
        JSONParser parser = new JSONParser();
        JSONObject distances = (JSONObject) parser.parse(jsonResponse);
        return distances.toJSONString();
    }
    
    public String getReadings() throws ParseException, IOException{
        String url = "https://data.mongodb-api.com/app/data-docuz/endpoint/readings";
        String jsonResponse = httpProvider.getFromURL(url);
        JSONParser parser = new JSONParser();
        JSONArray readings = (JSONArray) parser.parse(jsonResponse);
        return readings.toJSONString();
    }
    
        public String getDistances(String codigoOrigem) throws IOException, ParseException {
        String url = "https://data.mongodb-api.com/app/data-docuz/endpoint/distances?from=" + codigoOrigem + "&to=" + "Base";
        String jsonResponse = httpProvider.getFromURL(url);
        JSONParser parser = new JSONParser();
        JSONObject distances = (JSONObject) parser.parse(jsonResponse);
        return distances.toJSONString();
    }
}
