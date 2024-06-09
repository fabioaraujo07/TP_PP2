/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package menu;

import com.estg.core.Measurement;
import com.estg.core.exceptions.AidBoxException;
import com.estg.core.exceptions.ContainerException;
import http.HttpProviderImp;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import tp_pp.Classes.ContainerImp;

/**
 *
 * @author fabio
 */
public class Menu {

    private BufferedReader reader;
    private static HttpProviderImp httpProvider = new HttpProviderImp();

    public Menu() {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public void start() {
        int option = 0;

        do {
            System.out.println("=== Menu ===");
            System.out.println("1. AidBox");
            System.out.println("2. Containers");
            System.out.println("3. Vehicles");
            System.out.println("4. Institution");
            System.out.println("5. Routes");

            System.out.println("6. Exit");
            System.out.println("Option: ");
            try {
                option = Integer.parseInt(reader.readLine());

                switch (option) {
                    case 1:
                        showAidBoxMenu();
                        break;

                    case 2:
                        showContainerMenu();
                        break;
                    case 3:
                        showVehiclesMenu();
                        break;
                    case 4:

                        break;
                    case 5:
                        break;
                    case 6:
                        break;
                    default:
                        System.out.println("Invalid option!");
                        start();
                }

            } catch (IOException e) {
                System.out.println("Error reading input");
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }
        } while (option != 6);
    }

    public void showAidBoxMenu() {
        boolean exit = false;
        while (!exit) {
            System.out.println("=== Aibox Menu ===");
            System.out.println("1. List all Aid box");
            System.out.println("2. View Aid box by ID");
            System.out.println("3. View distances between Aid boxes");
            System.out.println("4. View duration between Aid boxes");
            System.out.println("5. Exit");
            System.out.println("Select option: ");

            try {
                int option = Integer.parseInt(reader.readLine());

                switch (option) {
                    case 1:
                        listAidBox();
                        break;
                    case 2:
                        viewAidBoxByCode();
                        break;
                    case 3:
                        viewDistances();
                        break;
                    case 4:
                        viewDuration();
                        break;
                    case 5:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid selection. Try again!\n");
                        break;
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.\n\n");
            } catch (IOException e) {
                System.out.println("Error reading input.");
            }
        }
    }

    private void listAidBox() {
        try {
            String jsonResponse = httpProvider.getAidBoxes();
            JSONParser parser = new JSONParser();
            JSONArray aidBoxesArray = (JSONArray) parser.parse(jsonResponse);

            for (Object aidBoxObject : aidBoxesArray) {
                JSONObject aidBox = (JSONObject) aidBoxObject;
                System.out.println("ID: " + aidBox.get("_id"));
                System.out.println("Code: " + aidBox.get("Codigo"));
                System.out.println("Zone: " + aidBox.get("Zona"));
                System.out.println("Latitude: " + aidBox.get("Latitude"));
                System.out.println("Longitude: " + aidBox.get("Longitude"));

                JSONArray contentores = (JSONArray) aidBox.get("Contentores");
                System.out.println("Contentores:");
                for (Object contentorObject : contentores) {
                    JSONObject contentor = (JSONObject) contentorObject;
                    System.out.println("\tCode: " + contentor.get("codigo"));
                    System.out.println("\tCapacity: " + contentor.get("capacidade"));
                }
                System.out.println("-----------------------------");
            }
        } catch (IOException | ParseException e) {
            System.err.println("Error fetching aid boxes: " + e.getMessage());
        }
    }

    private void viewAidBoxByCode() {
        try {
            System.out.print("Enter the Aid Box code: ");
            String code = reader.readLine();

            String jsonResponse = httpProvider.getAidBoxesCode(code);
            JSONParser parser = new JSONParser();
            JSONObject aidBox = (JSONObject) parser.parse(jsonResponse);

            System.out.println("ID: " + aidBox.get("_id"));
            System.out.println("Code: " + aidBox.get("Codigo"));
            System.out.println("Zone: " + aidBox.get("Zona"));
            System.out.println("Latitude: " + aidBox.get("Latitude"));
            System.out.println("Longitude: " + aidBox.get("Longitude"));

            JSONArray contentores = (JSONArray) aidBox.get("Contentores");
            System.out.println("Containers:");
            for (Object contentorObject : contentores) {
                JSONObject contentor = (JSONObject) contentorObject;
                System.out.println("\tCode: " + contentor.get("codigo"));
                System.out.println("\tCapacity: " + contentor.get("capacidade"));
            }
            System.out.println("-----------------------------");
        } catch (IOException | ParseException e) {
            System.err.println("Error fetching aid boxes: " + e.getMessage());
        } catch (ClassCastException e) {
            System.err.println("Error casting JSON object: " + e.getMessage());
        }
    }

    private void viewDistances() {
        try {
            System.out.println("Enter the Aid Box code: ");
            String aidbox1 = reader.readLine();
            System.out.println("Enter the 2nd Aid Box: ");
            String aidbox2 = reader.readLine();

            String jsonResponse = httpProvider.getDistancesAidbox(aidbox1, aidbox2);
            JSONParser parser = new JSONParser();
            JSONObject responseObject = (JSONObject) parser.parse(jsonResponse);

            JSONArray toArray = (JSONArray) responseObject.get("to");
            for (Object toObj : toArray) {
                JSONObject toObject = (JSONObject) toObj;

                String name = (String) toObject.get("name");
                if (name.equals(aidbox2)) {
                    System.out.println("Distance: " + toObject.get("distance"));
                }
            }

        } catch (IOException | ParseException e) {
            System.err.println("Error fetching distances: " + e.getMessage());
        }
    }

    private void viewDuration() {
        try {
            System.out.println("Enter the Aid Box code: ");
            String aidbox1 = reader.readLine();
            System.out.println("Enter the 2nd Aid Box: ");
            String aidbox2 = reader.readLine();

            String jsonResponse = httpProvider.getDistancesAidbox(aidbox1, aidbox2);
            JSONParser parser = new JSONParser();
            JSONObject responseObject = (JSONObject) parser.parse(jsonResponse);

            JSONArray toArray = (JSONArray) responseObject.get("to");
            for (Object toObj : toArray) {
                JSONObject toObject = (JSONObject) toObj;

                String name = (String) toObject.get("name");
                if (name.equals(aidbox2)) {
                    System.out.println("Duration: " + toObject.get("duration"));
                }
            }

        } catch (IOException | ParseException e) {
            System.err.println("Error fetching distances: " + e.getMessage());
        }
    }

    public void showContainerMenu() {
        boolean exit = false;
        while (!exit) {
            System.out.println("=== Container Menu ===");
            System.out.println("1. List all Containers");
            System.out.println("2. View Container's measurements");
            System.out.println("3. Back");
            System.out.println("Select option: ");

            try {
                int option = Integer.parseInt(reader.readLine());

                switch (option) {
                    case 1:
                        listContainers();
                        break;
                    case 2:
                        //containerMeasurements();
                        break;
                    case 3:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid selection. Try again!\n");
                        break;
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.\n\n");
            } catch (IOException e) {
                System.out.println("Error reading input.");
            }
        }
    }

    private void listContainers() {
        try {
            String jsonResponse = httpProvider.getAidBoxes();
            JSONParser parser = new JSONParser();
            JSONArray aidBoxesArray = (JSONArray) parser.parse(jsonResponse);

            for (Object aidBoxObject : aidBoxesArray) {
                JSONObject aidBox = (JSONObject) aidBoxObject;

                JSONArray contentores = (JSONArray) aidBox.get("Contentores");
                System.out.println("Contentores:");
                for (Object contentorObject : contentores) {
                    JSONObject contentor = (JSONObject) contentorObject;
                    System.out.println("\tCode: " + contentor.get("codigo"));
                    System.out.println("\tCapacity: " + contentor.get("capacidade"));
                }
                System.out.println("-----------------------------");
            }
        } catch (IOException | ParseException e) {
            System.err.println("Error fetching aid boxes: " + e.getMessage());
        }
    }

    private void showVehiclesMenu() {
        boolean exit = false;
        while (!exit) {
            System.out.println("=== Vehicle Menu ===");
            System.out.println("1. Create");
            System.out.println("2. Remove");
            System.out.println("3. Update");
            System.out.println("4. List");
            System.out.println("5. Back");
            System.out.println("Select option: ");

            try {
                int option = Integer.parseInt(reader.readLine());

                switch (option) {
                    case 1:
                        //listContainers();
                        break;
                    case 2:
                        //containerMeasurements();
                        break;
                    case 3:
                        //containerMeasurements();
                        break;
                    case 4:
                        //containerMeasurements();
                        break;
                    case 5:
                        exit = true;
                        break;
                    default:
                        System.out.println("Invalid selection. Try again!\n");
                        break;
                }

            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.\n\n");
            } catch (IOException e) {
                System.out.println("Error reading input.");
            }
        }
    }

    public static void main(String[] args) {
        Menu menu = new Menu();
        menu.start();
    }

}
