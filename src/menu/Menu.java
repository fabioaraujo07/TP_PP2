/*
 * Nome: Roger Nakauchi
 * Número: 8210005
 * Turna: LSIRCT1
 *
 * Nome: Fábio da Cunha
 * Número: 8210619
 * Turna: LSIRCT1
 */
package menu;

import com.estg.core.AidBox;
import com.estg.core.Container;
import com.estg.core.Institution;
import com.estg.core.ItemType;
import com.estg.core.Measurement;
import com.estg.core.exceptions.AidBoxException;
import com.estg.core.exceptions.ContainerException;
import com.estg.core.exceptions.InstitutionException;
import com.estg.core.exceptions.MeasurementException;
import com.estg.core.exceptions.VehicleException;
import com.estg.pickingManagement.Vehicle;
import http.HttpProviderImp;
import http.ImporterImp;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import tp_pp.Classes.AidBoxImp;
import tp_pp.Classes.ContainerImp;
import tp_pp.Classes.InstitutionImp;
import tp_pp.Classes.MeasurementImp;
import tp_pp_managment.VehicleImp;

/**
 * The Menu class provides a user interface for managing an institution's aid
 * boxes, containers, measurements, and vehicles. It allows users to add, list,
 * and view details about these entities. The class also handles importing and
 * exporting data for the institution.
 */
public class Menu {

    private Institution inst;
    private AidBox aid;
    private static String filePath;

    private BufferedReader reader;
    private static HttpProviderImp httpProvider = new HttpProviderImp();

    /**
     * Constructor for the Menu class.
     *
     * @param inst the institution to be managed.
     */
    public Menu(Institution inst) {
        this.inst = inst;
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     * Starts the main menu interface and handles user input for different
     * options.
     */
    public void start() {
        int option = 0;

        do {
            System.out.println("=== Welcome To Felgueiras Institution ===");
            System.out.println("1. AidBox");
            System.out.println("2. Containers");
            System.out.println("3. Measurements");
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
                        showVehiclesMenu();
                        break;
                    case 4:
                        break;
                    case 5:
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
        } while (option != 5);
    }

    /**
     * Displays the aid box management menu and handles user input for different
     * options.
     */
    public void showAidBoxMenu() {
        boolean exit = false;
        while (!exit) {
            System.out.println("=== Aibox Menu ===");
            System.out.println("1. Add Aid box");
            System.out.println("2. Distances between Aidboxes");
            System.out.println("3. Duration between Aidboxes");
            System.out.println("4. View Aid Box by Code");
            System.out.println("5. List Aid Box");
            System.out.println("6. Back");
            System.out.println("Select option: ");

            try {
                int option = Integer.parseInt(reader.readLine());

                switch (option) {
                    case 1: {
                        try {
                            addAidBox();
                        } catch (ContainerException ex) {
                            ex.printStackTrace();
                        }
                    }
                    break;
                    case 2:
                        viewDistances();
                        break;
                    case 3:
                        viewDuration();
                        break;
                    case 4:
                        viewAidBoxByCode();
                        break;
                    case 5:
                        try {
                            listAidBox();
                        } catch (AidBoxException ex) {
                            ex.printStackTrace();
                        }
                        break;
                    case 6:
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

    /**
     * Lists all aid boxes and prints their details to the console.
     *
     * @return an array of AidBox objects.
     * @throws AidBoxException if there is an error listing aid boxes.
     */
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

    /**
     * Adds a new aid box by fetching data from an HTTP provider and adding it
     * to the institution.
     *
     * @throws ContainerException if there is an error adding a container to the
     * aid box.
     */
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

    /**
     * Lists all aid boxes fetched from an HTTP provider and prints their
     * details to the console.
     */
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

    /**
     * Prompts the user to enter an Aid Box code and displays details about the
     * specified Aid Box. The details include the ID, Code, Zone, Latitude,
     * Longitude, and Containers within the Aid Box.
     */
    private void viewAidBoxByCode() {
        listAidBoxhttp();
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

    /**
     * Prompts the user to enter two Aid Box codes and displays the distance
     * between them.
     */
    private void viewDistances() {
        listAidBoxhttp();
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

    /**
     * Prompts the user to enter two Aid Box codes and displays the duration
     * between them.
     */
    private void viewDuration() {
        listAidBoxhttp();
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

    /**
     * Displays the container menu and handles user input for various container
     * operations. The options include listing all containers, adding
     * measurements, listing all measurements, and exiting.
     */
    public void showContainerMenu() {
        boolean exit = false;
        while (!exit) {
            System.out.println("=== Container Menu ===");
            System.out.println("1. List all Containers");
            System.out.println("2. Add Measurements");
            System.out.println("3. List all Measurements");
            System.out.println("4. Back");
            System.out.println("Select option: ");

            try {
                int option = Integer.parseInt(reader.readLine());

                switch (option) {
                    case 1:
                        listContainers();
                        break;

                    case 2: {
                        try {
                            addMeasurements();
                        } catch (ParseException ex) {
                            ex.printStackTrace();
                        } catch (MeasurementException ex) {
                            ex.printStackTrace();
                        }
                    }
                    break;

                    case 3:
                        listMeasurements();
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

    /**
     * Fetches and lists all containers within the aid boxes from the provider.
     * For each container, the details include the container code and capacity.
     */
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

    /**
     * Fetches and lists all measurements from the provider. The details of each
     * measurement include the container code, date, and value.
     */
    private void listMeasurements() {
        try {
            String jsonResponse = httpProvider.getReadings();
            JSONParser parser = new JSONParser();
            JSONArray readingsArray = (JSONArray) parser.parse(jsonResponse);

            for (Object readingObject : readingsArray) {
                JSONObject reading = (JSONObject) readingObject;
                String contentor = (String) reading.get("contentor");
                String data = (String) reading.get("data");
                long valor = (long) reading.get("valor");

                System.out.println("Code: " + contentor);
                System.out.println("Date: " + data);
                System.out.println("Value: " + valor);
                System.out.println("-----------------------------");
            }
        } catch (IOException | ParseException e) {
            System.out.println("Error fetching readings: " + e.getMessage());
        }
    }

    /**
     * Adds measurements to the specified container based on the user input.
     *
     * @throws ParseException if there is an error parsing the input data
     * @throws IOException if there is an error reading the input
     * @throws MeasurementException if there is an error adding the measurement
     */
    private void addMeasurements() throws ParseException, IOException, MeasurementException {
        try {
            listAidBox();
        } catch (AidBoxException ex) {
            Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
        }
        String jsonResponse = httpProvider.getReadings();
        JSONParser parser = new JSONParser();
        JSONArray measurements = (JSONArray) parser.parse(jsonResponse);

        System.out.println("Enter the Container code: ");
        String containerCode = reader.readLine();

        AidBox[] aidboxes = inst.getAidBoxes();

        int i = 0;
        boolean found = false;
        Container container = null;

        while (i < aidboxes.length && !found) {
            Container[] containers = aidboxes[i].getContainers();
            for (int j = 0; j < containers.length && !found; j++) {
                if (containers[j].getCode().equals(containerCode)) {
                    container = containers[j];
                    found = true;
                }
            }
            i++;
        }

        if (container != null) {
            for (i = 0; i < measurements.size(); i++) {
                JSONObject measurementJson = (JSONObject) measurements.get(i);
                String contentor = (String) measurementJson.get("contentor");

                if (contentor.equals(containerCode)) {
                    String dateStr = (String) measurementJson.get("data");
                    Instant instant = Instant.parse(dateStr);
                    ZonedDateTime zonedDateTime = instant.atZone(ZoneId.of("UTC"));

                    LocalDateTime data = zonedDateTime.toLocalDateTime();

                    long value = (long) measurementJson.get("valor");
                    Measurement m = new MeasurementImp(contentor, LocalDateTime.MAX, value);
                    try {
                        container.addMeasurement(m);
                    } catch (MeasurementException e) {
                        e.printStackTrace();
                        System.out.println("Error ading measurements");
                    }
                }
            }
        } else {
            System.out.println("Container code not found");
        }

    }

    /**
     * Displays the vehicle management menu and handles user input for various
     * operations related to vehicles.
     */
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

    /**
     * Prompts the user to add a new vehicle and handles the process of creating
     * and adding the vehicle to the institution.
     */
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

    /**
     * Lists all the vehicles associated with the institution.
     *
     * @throws VehicleException if there are no vehicles found.
     */
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

    /**
     * Prompts the user to remove a vehicle based on user input.
     */
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

    /**
     * Main method to run the menu application.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        InstitutionImp inst = new InstitutionImp("ONG");
        ImporterImp importer = new ImporterImp(inst);

        if (importer == null) {
            System.out.println("No data to be imported");
        }
        try {
            importer.importData(inst);
        } catch (IOException | InstitutionException ex) {
            System.out.println("Error while importing data");
        }

        Menu menu = new Menu(inst);
        menu.start();

        if (importer == null) {
            System.out.println("Data could not be exported");
        }
        try {
            importer.exportData(inst);
        } catch (IOException | InstitutionException ex) {
            System.out.println("Error while exporting data");
        }

    }

}
