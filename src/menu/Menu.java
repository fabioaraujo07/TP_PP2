/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package menu;

import com.estg.core.AidBox;
import com.estg.core.Institution;
import com.estg.core.ItemType;
import com.estg.core.Measurement;
import com.estg.core.exceptions.AidBoxException;
import com.estg.core.exceptions.ContainerException;
import com.estg.core.exceptions.VehicleException;
import com.estg.pickingManagement.Vehicle;
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
import tp_pp.Classes.AidBoxImp;
import tp_pp.Classes.ContainerImp;
import tp_pp.Classes.InstitutionImp;
import tp_pp_managment.VehicleImp;

/**
 *
 * @author fabio
 */
public class Menu {

    private Institution inst;

    private BufferedReader reader;
    private static HttpProviderImp httpProvider = new HttpProviderImp();

    public Menu(Institution inst) {
        this.inst = inst;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public void start() {
        int option = 0;

        do {
            System.out.println("=== Welcome To Felgueiras Institution ===");
            System.out.println("1. AidBox");
            System.out.println("2. Containers");
            System.out.println("3.Measurements");
            System.out.println("4. Vehicles");
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
                        
                        break;
                    case 4:
                        showVehiclesMenu();
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
            System.out.println("1. Add Aid box");
            System.out.println("2. List Aid Box");
            System.out.println("3. View distances between Aid boxes");
            System.out.println("4. View duration between Aid boxes");
            System.out.println("5. Exit");
            System.out.println("Select option: ");

            try {
                int option = Integer.parseInt(reader.readLine());

                switch (option) {
                    case 1: {
                        try {
                            addAidBox();
                        } catch (ContainerException ex) {
                            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    break;

                    case 2: {
                        try {
                            listAidBox();
                        } catch (AidBoxException ex) {
                            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
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

    private AidBox[] listAidBox() throws AidBoxException {

        AidBox[] aidboxes = inst.getAidBoxes();

        if (aidboxes == null) {
            throw new AidBoxException();
        } else {
            System.out.println("AidboxList: ");
            for (AidBox aidbox : aidboxes) {
                if (aidbox != null) {
                    System.out.println(aidbox);
                }
            }
            return aidboxes;
        }
    }

    private void addAidBox() throws ContainerException {
        try {
            listAidBoxhttp();
            System.out.print("Enter the Aid Box code: ");
            String code = reader.readLine();

            String jsonResponse = httpProvider.getAidBoxesCode(code);
            JSONParser parser = new JSONParser();
            JSONObject aidbox = (JSONObject) parser.parse(jsonResponse);
            
            String id = (String) aidbox.get("_id");
            String code1 = (String) aidbox.get("Codigo");
            String zone = (String) aidbox.get("Zona");
            double latitude = (double) aidbox.get("Latitude");
            double longitude = (double) aidbox.get("Longitude");

            AidBoxImp aid = new AidBoxImp(id, code1, zone, zone, latitude, longitude);

            JSONArray contentoresArray = (JSONArray) aidbox.get("Contentores");
            for (Object contentorObject : contentoresArray) {
                JSONObject contentor = (JSONObject) contentorObject;
                String containerCode = (String) contentor.get("codigo");
                int capacity = ((Long) contentor.get("capacidade")).intValue();

                System.out.println("Select the item type for container " + containerCode + ":");
                System.out.println("1. PERISHABLE_FOOD");
                System.out.println("2. NON_PERISHABLE_FOOD");
                System.out.println("3. CLOTHING");
                System.out.println("4. MEDICINE");

                int option = Integer.parseInt(reader.readLine());

                ItemType itemType;
                switch (option) {
                    case 1:
                        itemType = ItemType.PERISHABLE_FOOD;
                        break;
                    case 2:
                        itemType = ItemType.NON_PERISHABLE_FOOD;
                        break;
                    case 3:
                        itemType = ItemType.CLOTHING;
                        break;
                    case 4:
                        itemType = ItemType.MEDICINE;
                        break;
                    default:
                        System.out.println("Invalid selection. Defaulting to NON_PERISHABLE_FOOD.");
                        itemType = ItemType.NON_PERISHABLE_FOOD;
                        break;
                }

                ContainerImp container = new ContainerImp(containerCode, capacity, itemType);
                try {
                    aid.addContainer(container);
                } catch (ContainerException ex) {
                    System.out.println("Error adding container: " + ex.getMessage());
                }
            }

            try {
                inst.addAidBox(aid);
                System.out.println("Added with success!");
            } catch (AidBoxException ex) {
                System.out.println("Error adding aid box: " + ex.getMessage());
            }
        } catch (IOException | ParseException e) {
            System.err.println("Error fetching aid boxes: " + e.getMessage());
        }
    }

    private void listAidBoxhttp() {
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
                        //addMeasurements();
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
            System.out.println("1. Add");
            System.out.println("2. Remove");
            System.out.println("3. List");
            System.out.println("4. Back");
            System.out.println("Select option: ");

            try {
                int option = Integer.parseInt(reader.readLine());

                switch (option) {
                    case 1:
                        addVehicle();
                        break;
                    case 2: {
                        removeVehicle();
                    }
                    break;
                    case 3:
                        try {
                            listVehicles();
                        } catch (VehicleException ex) {
                            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        break;
                    case 4:
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

    private void addVehicle() {
        try {

            System.out.println("Enter the vehicle capacity: ");
            String capacitystr = reader.readLine();
            double capacity = Double.parseDouble(capacitystr);

            System.out.println("Enter the vehicle type (1.PERISHABLE_FOOD 2.NON_PERISHABLE_FOOD 3.CLOTHING 4.MEDICINE");

            int option = Integer.parseInt(reader.readLine());

            switch (option) {
                case 1:
                    System.out.println("Insert the max kms to the vehicle: ");
                    String kmsstr = reader.readLine();
                    double kms = Double.parseDouble(kmsstr);
                    VehicleImp vehicle = new VehicleImp(capacity, kms);
                    try {
                        inst.addVehicle(vehicle);
                        System.out.println("Added with success!");
                    } catch (VehicleException e) {
                        System.out.println("Error adding vehicle: " + e.getMessage());
                    }
                    break;
                case 2:
                    VehicleImp vehicle1 = new VehicleImp(capacity, ItemType.NON_PERISHABLE_FOOD);
                    try {
                        inst.addVehicle(vehicle1);
                        System.out.println("Added with success!");
                    } catch (VehicleException e) {
                        System.out.println("Error adding vehicle: " + e.getMessage());
                    }
                    break;
                case 3:
                    VehicleImp vehicle2 = new VehicleImp(capacity, ItemType.CLOTHING);
                    try {
                        inst.addVehicle(vehicle2);
                        System.out.println("Added with success!");
                    } catch (VehicleException e) {
                        System.out.println("Error adding vehicle: " + e.getMessage());
                    }
                    break;
                case 4:
                    VehicleImp vehicle3 = new VehicleImp(capacity, ItemType.MEDICINE);
                    try {
                        inst.addVehicle(vehicle3);
                        System.out.println("Added with success!");
                    } catch (VehicleException e) {
                        System.out.println("Error adding vehicle: " + e.getMessage());
                    }
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

    public void listVehicles() throws VehicleException {
        Vehicle[] vehicles = inst.getVehicles();

        if (vehicles == null) {
            throw new VehicleException();
        } else {
            System.out.println("Vehicles List: ");
            for (Vehicle vehicle : vehicles) {
                if (vehicle instanceof VehicleImp) {
                    VehicleImp v = (VehicleImp) vehicle;
                    if (v.getSupplyType().equals(ItemType.PERISHABLE_FOOD)) {
                        System.out.println("Capacity: " + v.getMaxCapacity() + ", Supply: " + v.getSupplyType() + ", Enabled: " + v.isEnabled() + ", Used: " + v.isUsed() + ", Kms: " + v.getKms());
                    } else {
                        System.out.println("Capacity: " + v.getMaxCapacity() + ", Supply: " + v.getSupplyType() + ", Enabled: " + v.isEnabled() + ", Used: " + v.isUsed());
                    }
                }
            }
        }
    }

    private void removeVehicle() {
        try {
            listVehicles();
            System.out.println("Enter the vehicle to remove capacity: ");
            String capacitystr = reader.readLine();
            double capacity = Double.parseDouble(capacitystr);

            System.out.println("Enter the vehicle type (1.PERISHABLE_FOOD 2.NON_PERISHABLE_FOOD 3.CLOTHING 4.MEDICINE");

            int option = Integer.parseInt(reader.readLine());

            switch (option) {
                case 1:
                    System.out.println("Insert vehicle to remove max kms: ");
                    String kmsstr = reader.readLine();
                    double kms = Double.parseDouble(kmsstr);
                    VehicleImp vehicle = new VehicleImp(capacity, kms);
                    try {
                        if (inst instanceof InstitutionImp) {
                            InstitutionImp institu = (InstitutionImp) inst;
                            institu.removeVehicle(vehicle);
                            System.out.println("Removed with success!");
                        }
                    } catch (VehicleException e) {
                        System.out.println("Error removing vehicle: " + e.getMessage());
                    }
                    break;

                case 2:
                    VehicleImp vehicle1 = new VehicleImp(capacity, ItemType.NON_PERISHABLE_FOOD);
                    try {
                        if (inst instanceof InstitutionImp) {
                            InstitutionImp institu = (InstitutionImp) inst;
                            institu.removeVehicle(vehicle1);
                            System.out.println("Removed with success!");
                        }

                    } catch (VehicleException e) {
                        System.out.println("Error removing vehicle: " + e.getMessage());
                    }
                    break;
                case 3:
                    VehicleImp vehicle2 = new VehicleImp(capacity, ItemType.CLOTHING);
                    try {
                        if (inst instanceof InstitutionImp) {
                            InstitutionImp institu = (InstitutionImp) inst;
                            institu.removeVehicle(vehicle2);
                            System.out.println("Removed with success!");
                        }

                    } catch (VehicleException e) {
                        System.out.println("Error removing vehicle: " + e.getMessage());
                    }
                    break;

                case 4:
                    VehicleImp vehicle3 = new VehicleImp(capacity, ItemType.MEDICINE);
                    try {
                        if (inst instanceof InstitutionImp) {
                            InstitutionImp institu = (InstitutionImp) inst;
                            institu.removeVehicle(vehicle3);
                            System.out.println("Removed with success!");
                        }

                    } catch (VehicleException e) {
                        System.out.println("Error removing vehicle: " + e.getMessage());
                    }
                    break;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.\n\n");
        } catch (IOException | VehicleException e) {
            System.out.println("Error reading input.");
        }

    }

    public static void main(String[] args) {
        InstitutionImp inst = new InstitutionImp("ONG");

        if (inst.importData("src/Files/vehicles.json") && inst.importData("src/Files/aidboxArray.json")) {
            System.out.println("Success importing program vehicles");
        }


        Menu menu = new Menu(inst);
        menu.start();

        if (inst.export("src/Files/vehicles.json")&& inst.importData("src/Files/aidboxArray.json")) {
            System.out.println("Success export program vehicles");
        }

    }

}
