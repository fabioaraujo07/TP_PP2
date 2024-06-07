/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package menu;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 *
 * @author fabio
 */
public class Menu {

    private BufferedReader reader;

    public Menu() {
        this.reader = new BufferedReader(new InputStreamReader(System.in));
    }

    public void start() {
        int option = 0;

        do {
            System.out.println("=== Menu ===");
            System.out.println("1. AidBox");
            System.out.println("2. Containers");
            System.out.println("3. Institution");
            System.out.println("4. Routes");

            System.out.println("5. Exit");
            System.out.println("Option: ");
            try {
                option = Integer.parseInt(reader.readLine());

                switch (option) {
                    case 1:
                        //showAidBoxMenu();
                        break;

                    case 2:
                        break;
                    case 3:
                        
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
    
    
    public void showAidBoxMenu

}
