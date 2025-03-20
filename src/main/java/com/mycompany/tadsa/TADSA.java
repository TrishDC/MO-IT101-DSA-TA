package com.mycompany.tadsa;


import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 *
 * @author trisha
 */
public class TADSA {

    String date;
    String label;
    String brands;
    String engum;
    String purstat;

    public TADSA(String date, String label, String brands, String engum, String purstat) {
        this.date = date;
        this.label = label;
        this.brands = brands;
        this.engum = engum;
        this.purstat = purstat;
    }

    @Override
    public String toString() {
        return date + " " + label + " " + brands + " " + engum + " " + purstat;
    }

    private static void loadFromCSV(ArrayList<TADSA> al) {
        String line = "";
        try (BufferedReader br = new BufferedReader(new FileReader("/Users/trisha/NetBeansProjects/TADSA/Bfile/b.csv"))) {
            br.readLine();
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                TADSA newStock = new TADSA(values[0], values[1], values[2], values[3], values[4]);
                al.add(newStock);
            }
            System.out.println();
        } catch (IOException e) {
            System.out.println("" + e.getMessage());
        }
    }

    public static void main(String[] args) {
        ArrayList<TADSA> al = new ArrayList<TADSA>();
        loadFromCSV(al); 
        int choices = -1;
        Scanner jay = new Scanner(System.in);
        Scanner jays = new Scanner(System.in);

        do {
            System.out.println("--------------MOTORPH--------------");
            System.out.println("-----------INVENTORY DATA----------");
            System.out.println("ADD STOCK:      1");
            System.out.println("VIEW STOCK:   2");
            System.out.println("SORT STOCK:     3");
            System.out.println("SEARCH STOCK:   4");
            System.out.println("DELETE STOCK:   5");
            System.out.print("Enter Your Choice:");
            choices = jay.nextInt();

            switch (choices) {
                case 1:
                    System.out.println("Enter how many inventory you want to put?: ");
                    int n = jay.nextInt();
                    for (int i = 0; i < n; i++) {

                       
                         String date = LocalDate.now().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"));
                        String label = "new";
                        System.out.print("Enter Brand: ");
                        String brands = jays.nextLine();
                        String engum;
                        boolean engineExists;
                        do {
                            System.out.print("Enter Engine Number: ");
                            engum = jays.nextLine();
                            engineExists = false;


                            for (TADSA stock : al) {
                                if (stock.engum.equalsIgnoreCase(engum)) {
                                    System.out.println("Error: Engine number already exists! Please enter a different one.");
                                    engineExists = true;
                                    break;
                                }
                            }
                        } while (engineExists);

                        String purstat = "on hand";
                        TADSA newStock = new TADSA(date, label, brands, engum, purstat);
                        al.add(newStock);
                    }

                    break;

                case 2:
                    System.out.println(al);
                    break;

                case 3:
                 
                    int w = al.size();
                    for (int i = 0; i < w - 1; i++) {
                        for (int j = 0; j < w - i - 1; j++) {
                            if (al.get(j).brands.compareToIgnoreCase(al.get(j + 1).brands) > 0) {
                              
                                TADSA temp = al.get(j);
                                al.set(j, al.get(j + 1));
                                al.set(j + 1, temp);
                            }
                        }
                    }
                    System.out.println(al);
                    break;

                case 4:
                    System.out.println("Enter the Engine Number to search: ");
                    String searchEngum = jays.nextLine();
                    boolean found = false;
                    for (TADSA stock : al) {
                        if (stock.engum.equalsIgnoreCase(searchEngum)) {
                            System.out.println(stock);
                            found = true; 
                            break;
                        }
                    }
                    if (!found) { // Print message if stock not found
                        System.out.println("Stock item not found.");
                    }
                    break;
                case 5:
                  System.out.println("Enter the Engine Number to delete: ");
                  String searchEngums = jays.nextLine();
                  boolean itemFound = false;
                  
                    for (int i = 0; i < al.size(); i++) {
                        TADSA stock = al.get(i);
                        if (stock.engum.equalsIgnoreCase(searchEngums)) {
                            itemFound = true;
                            if (stock.purstat.equalsIgnoreCase("sold") && stock.label.equalsIgnoreCase("old")) {
                                al.remove(i);
                                System.out.println("Item found and deleted (was sold and old).");
                            } else {
                                System.out.println("Item found but not deleted (not both sold and old).");
                            }
                            break;
                            
                        }
                    }

                   
                        
                    
                    if (!itemFound) {
                        System.out.println("Stock item not found.");
                    }
                    break;


            }
        } while (choices != 0);
    }
}
