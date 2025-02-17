/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package dsams2;
        import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
//to pick up where you left off, may issue sa searching mo


/**
 *
 * @author zach malonjao
 */
public class DsaMs2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        String csvFile = "inventory.csv"; // Path to your CSV file
        BinarySearchTree bst = new BinarySearchTree();

        readCSVAndPopulateBST(csvFile, bst);

        bst.displayBST();
        
        
        Scanner sc = new Scanner(System.in);
        boolean isDone = false;

        while (!isDone) {
            System.out.println("----------------------------");
            System.out.print("Add, Delete, Sort, Search or Exit? ");
            String command = sc.nextLine();
            
            

            switch (command.toLowerCase()) {
                case "add":
                    //ID
                    int newId = bst.findMax()+1;
                    //DATE
                    LocalDate date = LocalDate.now();
                         DateTimeFormatter f = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                        String newDate = date.format(f);
                    //STOCK LABEL    
                    String newStockLabel = "New";
                    //BRAND
                    String newBrand="";
                   while (true) {    
                        System.out.print("Insert Brand: ");
                        newBrand = sc.nextLine().trim();  // Remove leading and trailing spaces

                        if (newBrand.isEmpty()) {
                            System.out.println("Engine Number cannot be empty or just spaces. Please try again.");
                            continue;  // Ask for input again
                        }else{
                            break;
                        }
                   }
                    //ENGINE NUMBER  
                   String newEngineNumber = "";
                    while (true) {
                        System.out.print("Insert Engine Number: ");
                        String engineNumber = sc.nextLine().trim(); // Remove leading and trailing spaces

                        if (engineNumber.isEmpty()) {
                            System.out.println("Engine Number cannot be empty or just spaces. Please try again.");
                        } else if (bst.isEngineNumberFound(engineNumber)) {
                            System.out.println("Engine Number already exists, please try again.");
                        } else {
                            newEngineNumber = engineNumber;
                            break;
                        }
                    }     
                        
                        
                    
                    //STATUS    
                        String statusTest;
                        String newStatus = "";
                 while(true){         
                     System.out.print("Insert Purchase Status: ");
                        statusTest = sc.nextLine();
                       if(statusTest.equals("On-hand") || statusTest.equals("Sold")){
                           newStatus = statusTest;
                           break;
                       }else{
                          System.out.println("Input is case-sensitive. Please input either 'On-hand' or 'Sold'. ");
                       }                                                                       
                 } 
                bst.insert(newId, newDate, newStockLabel, newBrand, newEngineNumber, newStatus); 
                bst.displayBST();
                bst.writeBSTtoCSV("inventory.csv");
                
                    break;
                    
                    
               case "search":
                System.out.print("Insert EngineID to search:");
                String engineNumberToSearch = sc.nextLine().trim();
                
                Node node = bst.searchEngineNumber(engineNumberToSearch);
                if (node != null) { // Check if the node is not null
                    System.out.println(node.id + "," + node.date + "," + node.stockLabel + "," + node.brand + "," + node.engineNumber + "," + node.status);
                } else {
                    System.out.println("Record with " + engineNumberToSearch + " not found");
                }
                
                
                break;
                    
                    
                case "sort":
                    System.out.println("Sorted Inventory by Brand:");
                    bst.displayBSTSortedByBrand();
                    
                    break;
                case "delete":
                    System.out.print("Insert unique engine ID: ");
                  String engineNumber = sc.nextLine().trim();
                 
                   
                    
                   Node newNode = bst.searchEngineNumber(engineNumber);
                   
                    if (newNode != null) { // Check if the node is not null
                       
                       // System.out.println(newNode.id + "," + newNode.date + "," + newNode.stockLabel + "," + newNode.brand + "," + newNode.engineNumber + "," + newNode.status);
                        
                        switch(newNode.stockLabel){
                            case "New":
                                System.out.println("You can't delete New stocks");
                                break;
                            case "Old":
                                boolean isValidChoice = false; // Initialize to false
                                while (!isValidChoice) { // Change to while (!isValidChoice)
                                    System.out.print("Do you wish to delete '" + newNode.id + "," + newNode.date + "," + newNode.stockLabel + "," + newNode.brand + "," + newNode.engineNumber + "," + newNode.status + "'? (Yes or No) ");
                                    String decision = sc.nextLine();

                                    switch (decision.toLowerCase()) {
                                        case "yes":
                                           isValidChoice = true; 
                                           bst.remove(newNode.id);
                                           bst.displayBST();
                                           bst.writeBSTtoCSV(csvFile);
                                           System.out.println("Successfully deleted");
                                         
                                            break; // Exit the inner switch
                                        case "no":
                                            isValidChoice = true;
                                            System.out.println("Deletion Cancelled...");
                                            isValidChoice = true; // Set to true to exit the loop
                                            break; // Exit the inner switch
                                        default:
                                            System.out.println("Please choose either yes or no");
                                    }
                                }                                
                                break;
                            default: System.out.println("Error: New or Old not found in the CSV record you are trying to search");
                    
                        }
                    
                    
                    
                    } else {
                        System.out.println("Record with " + engineNumber + " not found");
                    }
                   
                    break;
                case "exit":
                    isDone = true;
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid command");
            }
        }
    }
    // Method to read the CSV file and populate the BST
    public static void readCSVAndPopulateBST(String csvFile, BinarySearchTree bst) {
        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim(); // Trim whitespace
                if (line.isEmpty()) { // Skip empty lines
                    continue;
                }
                String[] parts = line.split(","); // Split the line into an array
                if (parts.length < 6) { // Check if there are enough parts
                    System.out.println("Skipping line due to insufficient data: " + line);
                    continue; // Skip lines that don't have enough data
                }
                int id = Integer.parseInt(parts[0].trim()); // Convert the first part to an integer
                String date = parts[1].trim(); // Get the second part as a string
                String stockLabel = parts[2].trim(); // Get the third part as a string
                String brand = parts[3].trim(); // Get the fourth part as a string
                String engineNumber = parts[4].trim(); // Get the fifth part as a string
                String status = parts[5].trim(); // Get the sixth part as a string
                bst.insert(id, date, stockLabel, brand, engineNumber, status); // Insert into the BST
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Error parsing number: " + e.getMessage());
        }
    }
    
    
    
}
