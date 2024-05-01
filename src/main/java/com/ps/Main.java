package com.ps;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Ledger ledger = new Ledger(); // create instance of Ledger

        String choice; // store user choice

        //home screen
        System.out.println();
        System.out.println("Home Screen:");
        System.out.println();
        System.out.println("D) Add deposit");
        System.out.println("P) Make payment (debit)");
        System.out.println("L) Ledger");
        System.out.println("X) Exit");
        System.out.println();
        System.out.print("Choose an option: ");

        do {

            choice = scanner.nextLine().toUpperCase();

            switch (choice) { // handle user choices
                case "D":
                    ledger.addDeposit(scanner); // calls addDeposit from Ledger, once done reprints home screen and takes in user input again to keep program looping
                    System.out.println("Home Screen:");
                    System.out.println("D) Add deposit");
                    System.out.println("P) Make payment (debit)");
                    System.out.println("L) Ledger");
                    System.out.println("X) Exit");
                    System.out.println();
                    System.out.print("Choose an option: ");
                    break;
                case "P":
                    ledger.makePayment(scanner);
                    System.out.println("Home Screen:");
                    System.out.println("D) Add deposit");
                    System.out.println("P) Make payment (debit)");
                    System.out.println("L) Ledger");
                    System.out.println("X) Exit");
                    System.out.println();
                    System.out.print("Choose an option: ");
                    break;
                case "L":
                    ledger.showLedger(scanner);
                    System.out.println();

                    System.out.println("Home Screen:");
                    System.out.println("D) Add deposit");
                    System.out.println("P) Make payment (debit)");
                    System.out.println("L) Ledger");
                    System.out.println("X) Exit");
                    System.out.println();
                    break;
                case "X":
                    System.out.println("Exited.");
                    break;
                default:
                    System.out.println("Choose an option:");
            }
        } while (!choice.equals("X")); // if X exit program
    }
}