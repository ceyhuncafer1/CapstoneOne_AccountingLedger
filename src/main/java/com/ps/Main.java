package com.ps;

import java.io.*;
import java.util.*;


public class Main {

    private static PrintWriter writer;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        Ledger ledger = new Ledger(); // create instance of Ledger
        ledger.readAndWriteTransactions(); // initialized the input of initial transactions

        String choice; // store user choice

        //home screen
        System.out.println();
        homeScreen();

        do {

            choice = scanner.nextLine().toUpperCase();

            switch (choice) { // handle user choices
                case "D":
                    ledger.addDeposit(scanner); // calls addDeposit from Ledger, once done reprints home screen and takes in user input again to keep program looping
                    homeScreen();
                    break;
                case "P":
                    ledger.makePayment(scanner);
                    homeScreen();
                    break;
                case "L":
                    ledger.showLedger(scanner);
                    System.out.println();
                    homeScreen();
                    break;
                case "X":
                    System.out.println("Exited.");
                    break;
                default:
                    System.out.println("Choose an option:");
            }
        } while (!choice.equals("X")); // if X exit program

    }

    public static void homeScreen(){
        System.out.println("Home Screen:");
        System.out.println();
        System.out.println("D) Add deposit");
        System.out.println("P) Make payment (debit)");
        System.out.println("L) Ledger");
        System.out.println("X) Exit");
        System.out.println();
        System.out.println("Choose an option:");
    }
}