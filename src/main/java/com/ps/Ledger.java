package com.ps;

import java.io.*;
import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class Ledger {

    private ArrayList<Transaction> transactions;
    private ArrayList<Transaction> onlyDeposits;
    private ArrayList<Transaction> onlyPayments;

    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");

    public Ledger() {

        this.transactions = new ArrayList<>();
        this.onlyDeposits = new ArrayList<>();
        this.onlyPayments = new ArrayList<>();

    }

    public void addDeposit(Scanner scanner) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("transactions.txt", true))) {

            System.out.print("Enter date (YYYY-MM-DD): ");
            LocalDate date = LocalDate.parse(scanner.nextLine(), dateFormat);
            System.out.print("Enter time (HH:mm): ");
            LocalTime time = LocalTime.parse(scanner.nextLine(), timeFormat);
            System.out.print("Enter description for deposit: ");
            String description = scanner.nextLine();
            System.out.print("Enter vendor: ");
            String vendor = scanner.nextLine();
            System.out.print("Enter the amount you want to deposit: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();
            Transaction transaction = new Transaction(date, time, description, vendor, amount);
            transactions.add(transaction);

            onlyDeposits.add(transaction);

            writer.println(transaction);
            System.out.println();
            System.out.println("Deposit added successfully.");
            System.out.println();

        } catch (IOException e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
    }

    public void makePayment(Scanner scanner) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("transactions.txt", true))) {
            System.out.print("Enter date (YYYY-MM-DD): ");
            LocalDate date = LocalDate.parse(scanner.nextLine(), dateFormat);
            System.out.print("Enter time (HH:mm): ");
            LocalTime time = LocalTime.parse(scanner.nextLine(), timeFormat);
            System.out.print("Enter description for payment: ");
            String description = scanner.nextLine();
            System.out.print("Enter vendor: ");
            String vendor = scanner.nextLine();
            System.out.print("Enter the amount you want to pay: ");
            double amount = scanner.nextDouble();
            scanner.nextLine();
            Transaction transaction = new Transaction(date, time, description, vendor, -amount);
            transactions.add(transaction);
            writer.println(transaction);
            System.out.println();
            System.out.println("Payment made successfully.");
            System.out.println();
        } catch (IOException e) {
            System.out.println("Error occurred: " + e.getMessage());
        }
    }

    public void showLedger(Scanner scanner) {
        System.out.println();
        System.out.println("Ledger");
        System.out.println();
        System.out.println("A) All - Display all entries");
        System.out.println("D) Deposits - Display only the entries that are deposits into the account");
        System.out.println("R) Reports - Custom Search");
        System.out.println();

        System.out.print("Choose an option: ");

        String choiceForLedger = scanner.nextLine().toUpperCase();

        switch (choiceForLedger) {

            case "A":

                Collections.reverse(transactions);
                for (Transaction transaction : transactions) {
                    System.out.println(transaction);
                }
                // restore
                Collections.reverse(transactions);
                break;

            case "D":

                Collections.reverse(onlyDeposits);
                for (Transaction transaction : onlyDeposits) {
                    System.out.println(transaction);
                }
                Collections.reverse(onlyDeposits);
                break;

            case "P":
                Collections.reverse(onlyPayments);
                for (Transaction transaction : onlyPayments) {
                    System.out.println(transaction);
                }
                Collections.reverse(onlyDeposits);
                break;
            case "R":
                System.out.println("test3");
                break;
            case "H":
                System.out.println("test4");
                break;

        }


    }

}
