package com.ps;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Ledger ledger = new Ledger();

        String choice;

        do {

            System.out.println("Home Screen:");
            System.out.println("D) Add deposit");
            System.out.println("P) Make payment (debit)");
            System.out.println("L) Ledger");
            System.out.println("X) Exit");
            System.out.print("Choose an option: ");
            choice = scanner.nextLine().toUpperCase();

            switch (choice) {
                case "D":
                    ledger.addDeposit(scanner);
                    break;
                case "P":

                    break;
                case "L":

                    break;
                case "X":
                    System.out.println("Exited.");
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        } while (!choice.equals("X"));
    }
}