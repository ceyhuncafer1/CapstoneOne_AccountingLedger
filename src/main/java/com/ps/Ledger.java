package com.ps;

import java.io.*;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class Ledger {

    //array list for every entry, entries that are deposits, and entries that are payments
    private ArrayList<Transaction> transactions;
    private ArrayList<Transaction> onlyDeposits;
    private ArrayList<Transaction> onlyPayments;
    private ArrayList<Transaction> initialTransactions;

    //format date and time
    private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM-dd-yyyy");
    private static final DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH:mm");

    //constructor to initialize arraylists for use when an instance of the ledger class is created

    public Ledger() {

        this.transactions = new ArrayList<>();
        this.onlyDeposits = new ArrayList<>();
        this.onlyPayments = new ArrayList<>();
        this.initialTransactions = new ArrayList<>();

    }

    public void readAndWriteTransactions() {

        //open the input file thats initialized for reading and the output file for writing
        try (BufferedReader bufReader = new BufferedReader(new FileReader("inputTransactions.txt"));
             PrintWriter writer = new PrintWriter(new FileWriter("transactions.txt", true))) { // if the file already exists just append to it
             // file writer makes the file, print writer writes to it

            String line;
            double amount = 0;

            // Read each line from the input file until the end
            while ((line = bufReader.readLine()) != null) {
                String[] parts = line.split("\\|"); // // split the line into parts every time | is seen
                LocalDate date = LocalDate.parse(parts[0].trim(), dateFormat);
                LocalTime time = LocalTime.parse(parts[1].trim(), timeFormat);
                String description = parts[2].trim();
                String vendor = parts[3].trim();
                amount = Double.parseDouble(parts[4].trim());
                Transaction transaction = new Transaction(date, time, description, vendor, amount); // // create a Transaction object with the parsed data
                initialTransactions.add(transaction); //add to arraylist

                //sort between payment and deposit
                if (amount < 0) {
                    onlyPayments.add(transaction);
                } else {
                    onlyDeposits.add(transaction);
                }

                // write to transactions.txt
                writer.println(transaction.toString());
            }

            transactions.addAll(initialTransactions); // combine initial data with new transactions user inputs

        } catch (IOException | ArrayIndexOutOfBoundsException | NumberFormatException e) {
            System.err.println("Error reading or writing transactions: " + e.getMessage());
        }

    }

    public void addDeposit(Scanner scanner) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("transactions.txt", true))) {

            //prompt user for deposit information
            System.out.print("Enter date (MM-dd-yyyy): ");
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

            //instance of Transaction object and putting it in both arraylists all transactions and only deposits
            Transaction transaction = new Transaction(date, time, description, vendor, amount);
            transactions.add(transaction);

            onlyDeposits.add(transaction);

            //print it to the .txt
            writer.println(transaction);
            System.out.println();
            System.out.println("Deposit added successfully.");
            System.out.println();

        } catch (InputMismatchException | IOException | DateTimeParseException e) { // catch error to not crash program
            System.out.println("Error occurred: " + e.getMessage());
            System.out.println();
        }
    }

    public void makePayment(Scanner scanner) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("transactions.txt", true))) {

            // input
            System.out.print("Enter date (MM-dd-yyyy): ");
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

            // creating a Transaction object with negative amount as it is a Payment
            Transaction transaction = new Transaction(date, time, description, vendor, -amount); // to show its payment
            transactions.add(transaction);
            onlyPayments.add(transaction);
            writer.println(transaction);
            System.out.println();
            System.out.println("Payment made successfully.");
            System.out.println();

        } catch (InputMismatchException | IOException | DateTimeParseException e) {
            System.out.println("Error occurred: " + e.getMessage());
            System.out.println();
        }
    }

    public void showLedger(Scanner scanner) {

        //display Ledger screen options
        System.out.println();
        System.out.println("Ledger");
        System.out.println();
        System.out.println("A) All - Display all entries");
        System.out.println("D) Deposits - Display only the entries that are deposits into the account");
        System.out.println("P) Payments - Display only the entries that are payments into the account");
        System.out.println("R) Reports - Custom Search");
        System.out.println("H) Home - Go back to the home page");
        System.out.println();

        System.out.print("Choose an option: ");

        String choiceForLedger = scanner.nextLine().toUpperCase(); // take in user input

        switch (choiceForLedger) {

            case "A": // display all entries from newest first - sort by date and time

                /*
                .sort()-ascending order
                comparator.comparing(Transaction::getDate) - creates comparator object that calls .comparing() compares transaction object dates hence getDate of transaction class
                .thenComparing(Transaction::getTime) - if they have the same date, make sure to compare time as well hence .then()
                .reversed() - descending order
                 */

                transactions.sort(Comparator.comparing(Transaction::getDate).thenComparing(Transaction::getTime).reversed());

                for (Transaction transaction : transactions) {
                    System.out.println(transaction); //print all arraylist
                }

                break;

            case "D":
                //display only deposit entries

                onlyDeposits.sort(Comparator.comparing(Transaction::getDate).thenComparing(Transaction::getTime).reversed());

                for (Transaction transaction : onlyDeposits) {
                    System.out.println(transaction); // print only deposit array
                }


                break;

            case "P":
                onlyPayments.sort(Comparator.comparing(Transaction::getDate).thenComparing(Transaction::getTime).reversed());

                for (Transaction transaction : onlyPayments) {
                    System.out.println(transaction); //print only payments array
                }
                break;
            case "R":

                String nestedLedgerChoice = null; // do while loop inside switch case because its nested menus

                do{

                    System.out.println(); // report menu
                    System.out.println("Reports:");
                    System.out.println();
                    System.out.println("1) Month to Date");
                    System.out.println("2) Previous Month");
                    System.out.println("3) Year To Date");
                    System.out.println("4) Previous Year");
                    System.out.println("5) Search by Vendor");
                    System.out.println("0) Back - Go back to the Report page");
                    System.out.println();
                    System.out.println("Choose an option");

                    nestedLedgerChoice = scanner.nextLine(); //input for report screen variable

                    switch (nestedLedgerChoice) {
                        case "1": // month to date: shows entries for first of the month of the current year to the current date.

                            try {

                                System.out.print("Enter month (MM): ");  //input
                                int month = scanner.nextInt();

                                // set the start date to the first day of the given month and year

                                // set the end date to the current date, which will be the filter
                                LocalDate startDate = LocalDate.of(LocalDate.now().getYear(), month, 1);
                                LocalDate endDate = LocalDate.now();//current date

                                ArrayList<Transaction> monthToDateTransactions = new ArrayList<>(); // separate arraylist when this needs to be called to the screen

                                for (Transaction transaction : transactions) {

                                    LocalDate transactionDate = transaction.getDate(); //stores variable type localDate as transaction object getDate information

                                    if (!transactionDate.isBefore(startDate) && !transactionDate.isAfter(endDate)) { // stores data
                                        monthToDateTransactions.add(transaction); //enhanced for loop that iterates over transactions
                                        //transaction.getDate() get the date inside the transaction object
                                        // .isBefore() and .isAfter() - specified range from startDate to endDate
                                    }
                                }

                                Collections.sort(monthToDateTransactions, Comparator.comparing(Transaction::getDate).thenComparing(Transaction::getTime).reversed());

                                /*
                                Collections.sort() is ascending order
                                Comparator.comparing(Transaction::getDate) is what it's sorting by, (time if it's the same date)
                                then you have to reverse it to descending order

                                 */

                                if(monthToDateTransactions.isEmpty()){
                                    System.out.println("No transactions for the search filter Month to Date."); // if empty, tell user its empty
                                } else{

                                    for (Transaction transaction : monthToDateTransactions) {
                                        System.out.println(transaction); // otherwise print entries in new arraylist
                                    }

                                }

                            }catch(Exception e){
                                System.out.println("Invalid input.");
                            }
                            break;
                        case "2":
                            // previous month

                            /*
                            get the current date
                            calculate the previous month by subtracting 1 month from the current date
                            set the start date of the previous month to the first day of the month
                            set the end date of the previous month to the last day of the month
                             */
                            LocalDate currentDate = LocalDate.now();
                            YearMonth previousMonth = YearMonth.from(currentDate).minusMonths(1);

                            LocalDate startDate = LocalDate.of(previousMonth.getYear(), previousMonth.getMonth(), 1);
                            LocalDate endDate = previousMonth.atEndOfMonth();

                            System.out.println("Transactions from the previous month:");

                            ArrayList<Transaction> previousMonthTransactions = new ArrayList<>();

                            for (Transaction transaction : transactions) {
                                LocalDate transactionDate = transaction.getDate();
                                if (!transactionDate.isBefore(startDate) && !transactionDate.isAfter(endDate)) {
                                    previousMonthTransactions.add(transaction);
                                }
                            }

                            Collections.sort(previousMonthTransactions, Comparator.comparing(Transaction::getDate).thenComparing(Transaction::getTime).reversed());

                            if(previousMonthTransactions.isEmpty()){
                                System.out.println("Search filter for Previous Month is empty.");
                            } else{
                                for (Transaction transaction : previousMonthTransactions) {
                                    System.out.println(transaction);
                                }
                            }

                            break;
                        case "3":
                            // year to date
                            int year = scanner.nextInt();

                            //current date
                            currentDate = LocalDate.now();
                            startDate = LocalDate.of(year, 1, 1); // January 1st of current year

                            System.out.println("Transactions from January 1st of the current year to " + currentDate + ":");

                            ArrayList<Transaction> yearToDateTransactions = new ArrayList<>();

                            for (Transaction transaction : transactions) {
                                LocalDate transactionDate = transaction.getDate();
                                if (!transactionDate.isBefore(startDate) && !transactionDate.isAfter(currentDate)) {
                                    yearToDateTransactions.add(transaction);
                                }
                            }

                            Collections.sort(yearToDateTransactions, Comparator.comparing(Transaction::getDate).thenComparing(Transaction::getTime).reversed());

                            if(yearToDateTransactions.isEmpty()){
                                System.out.println("Search filter for Year to Date is empty.");
                            } else{
                                for (Transaction transaction : yearToDateTransactions) {
                                    System.out.println(transaction);
                                }
                            }
                            break;
                        case "4":
                            // previous year

                            //current date
                            currentDate = LocalDate.now();
                            int previousYear = currentDate.minusYears(1).getYear(); //variable to subtract a year

                            startDate = LocalDate.of(previousYear, 1, 1); // using this subtract variable to go back a year of .now();
                            endDate = LocalDate.of(previousYear, 12, 31);

                            System.out.println("Transactions from the previous year:");

                            ArrayList<Transaction> previousYearTransactions = new ArrayList<>();

                            for (Transaction transaction : transactions) {
                                LocalDate transactionDate = transaction.getDate();
                                if (!transactionDate.isBefore(startDate) && !transactionDate.isAfter(endDate)) {
                                    previousYearTransactions.add(transaction);
                                }
                            }

                            Collections.sort(previousYearTransactions, Comparator.comparing(Transaction::getDate).thenComparing(Transaction::getTime).reversed());

                            if(previousYearTransactions.isEmpty()){
                                System.out.println("Search filter for Previous Year is empty.");
                            } else{
                                for (Transaction transaction : previousYearTransactions) {
                                    System.out.println(transaction);
                                }
                            }
                            break;
                        case "5":

                            // search by vendor
                            System.out.println("Enter the vendor: (Company name, person name)");
                            String vendorName = scanner.nextLine().trim(); // take string user input

                            System.out.println("Transactions by " + vendorName + ":");

                            ArrayList<Transaction> vendorList = new ArrayList<>();

                            for (Transaction transaction : transactions) {

                                if (transaction.getVendor().equalsIgnoreCase(vendorName)) { // .equals method for string
                                    vendorList.add(transaction);
                                }
                            }

                            if (vendorList.isEmpty()) {
                                System.out.println("No transactions found for " + vendorName);
                            } else { // no matches

                                vendorList.sort(Comparator.comparing(Transaction::getDate).thenComparing(Transaction::getTime).reversed());

                                for (Transaction transaction : vendorList) {
                                    System.out.println(transaction);
                                }

                            }
                            break;
                        case "0":
                            break;
                        default:
                            System.out.println("Invalid option.");
                    }

                } while(nestedLedgerChoice.equals("0")); // goes back to report page
                break;

            case "H": // goes home screen
                break;
        }


    }

}
