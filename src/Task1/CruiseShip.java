package Task1;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Objects;
import java.util.Scanner;

public class CruiseShip {
    private static String[] CruiseCabin = new String[12];
    private static final Scanner scanner = new Scanner(System.in);
    private static boolean[] isEditable = new boolean[12];
    private static boolean isValid = true;

    public static void main(String[] args) {

        initialise();
        mainMenu();
    }


    private static void initialise() {
        for (int i = 0; i < 12; i++) {
            CruiseCabin[i] = "*";
            isEditable[i] = true;
        }
    }


    public static void mainMenu() {
        while (isValid) {

            String menuItems = "\n-------------------------------------------------------"
                    .concat("\n|                   CRUISESHIP BOARDING               |")
                    .concat("\n|-----------------------------------------------------|")
                    .concat("\n| V |\tView all Cabins                               |")
                    .concat("\n| A |\tAdd customer to Cabin                         |")
                    .concat("\n| E |\tDisplay empty Cabins                          |")
                    .concat("\n| D |\tDelete customer from Cabin                    |")
                    .concat("\n| F |\tFind cabin from customer name                 |")
                    .concat("\n| S |\tStore Program Data into file                  |")
                    .concat("\n| L |\tLoad Program Data from file                   |")
                    .concat("\n| O |\tView passengersOrdered alphabetically by name |")
                    .concat("\n| E |\tExit the Program                              |")
                    .concat("\n-------------------------------------------------------")
                    .concat("\nChoose Option: ");
            System.out.print(menuItems);
            boolean hasNext = scanner.hasNext();
            if (hasNext) {
                String code = scanner.next().toUpperCase();
                mainMenuInputValidation(code);
            }
        }
        System.out.println("Exiting Program...");
    }


    private static void mainMenuInputValidation(String code) {
        boolean validInput = false;
        String[] validInputArray = {"V","E", "A", "D", "O",
                "S", "L","F", "EXT"};
        int index = 0;

        for (int i = 0; i < validInputArray.length; i++) {
            if (code.equals(validInputArray[i])) {
                validInput = true;
                index = i;
            }
        }

        if (validInput) {
            switch (index) {
                case 0 -> ViewAllCabins();
                case 1 -> DisplayEmptyCabins();
                case 2 -> AddCustomer();
                case 3 -> DeleteCustomer();
                case 4 -> SortCustomerName();
                case 5 -> StoreProgramData();
                case 6 -> loadProgramData();
                case 7 -> GetCabin();
                case 8 -> isValid = false;
            }
        } else {
            isValid = true;
            System.out.println("Invalid Input! Try Again.");
        }
    }


    /**
     * Views All cabins
     */
    private static void ViewAllCabins() {
        System.out.println("\nList of All Cabins");
        for (int i = 0; i < 12; i++) {
            if (CruiseCabin[i].equals("*")) {
                System.out.println("Cabin " + i + " is Empty");
            } else {
                System.out.println("Cabin " + i + " is occupied by " + CruiseCabin[i]);
            }
        }
    }


    /**
     * Display Empty cabins
     */
    private static void DisplayEmptyCabins() {
        System.out.println("\nList of All Cabins");
        int emptyCabinCount = 0;
        for (int i = 0; i < CruiseCabin.length; i++) {
            if (CruiseCabin[i].equals("*")) {
                System.out.println("Cabin " + i + " is Empty");
                emptyCabinCount++;
            }
        }
        if (emptyCabinCount == 0) {
            System.out.println("All Cabins Are Occupied!");
        }
    }


    /**
     * List All Occupied Cruise cabins
     */
    private static void viewAllOccupiedCabins() {
        System.out.println("\nList of All Cabins");
        for (int i = 0; i < CruiseCabin.length; i++) {
            if (!CruiseCabin[i].equals("*")) {
                System.out.println("Cabin " + i + " is occupied by " + CruiseCabin[i]);
            }
        }
        System.out.println(" ");
    }


    /**
     *Add customer to Cabin
     */
    private static void AddCustomer() {
        while (true) {
            int CabinNumber;
            String CustomerName;
            char returnChar = CabinChecker();
            if (returnChar == 'N') {
                System.out.println("All Cabins Are Occupied!\nTry Removing Assigned Customer using Option in Main Menu -> Remove Customer from a Cabin");
                break;
            }
            DisplayEmptyCabins();
            do {
                System.out.print("Enter Cabin Number (0 - 11) to Add Customer or 12 to go back to Main Menu: ");
                while (!scanner.hasNextInt()) {
                    System.out.print("Invalid Input! Try Again.\nEnter Cabin Number (0 - 11) or 12 to go back to Main Menu: ");
                    scanner.next();
                }
                CabinNumber = scanner.nextInt();
                if (CabinNumber > 12 || CabinNumber < 0) {
                    System.out.println("Invalid Input! Try Again.");
                }
            } while ((CabinNumber < 0) || (CabinNumber > 12));
            if (CabinNumber == 12) {
                System.out.println("Back to Main Menu....");
                break;
            } else if (isEditable[CabinNumber]) {
                System.out.print("Enter Customer Name for Cabin " + CabinNumber + " : ");
                CustomerName = scanner.next();
                CruiseCabin[CabinNumber] = CustomerName;

                isEditable[CabinNumber] = false;
                System.out.println("Update Successful!\n");
            } else {
                System.out.println("Customer already Assigned to Cabin No: " + CabinNumber + ". wait until Assigned Customer get in.\nOr Try Removing Assigned Customer using Option in Main Menu -> Remove Customer from a Cabin\n");
            }
        }
    }


    /**
     *Delete customer from cabin
     */
    private static void DeleteCustomer() {
        while (true) {
            int CabinNumber;
            char returnChar = CabinChecker();
            if (returnChar == 'Y') {
                System.out.println("All Cabins Are Empty\nTry Adding Customer using Option in Main Menu -> Add Customer to a Booth");
                break;
            }
            viewAllOccupiedCabins();
            do {
                System.out.print("Enter Cabin Number (0 - 11) to Remove Customer or 12 to go back to Main Menu: ");
                while (!scanner.hasNextInt()) {
                    System.out.print("Invalid Input! Try Again.\nEnter Cabin Number (0 - 11) to Remove Customer or 12 to go back to Main Menu: ");
                    scanner.next();
                }
                CabinNumber = scanner.nextInt();
                if (CabinNumber > 12 || CabinNumber < 0) {
                    System.out.println("Invalid Input! Try Again.");
                }
            } while ((CabinNumber < 0) || (CabinNumber > 12));
            if (CabinNumber <= 11) {
                if (!CruiseCabin[CabinNumber].equals("*")) {
                    CruiseCabin[CabinNumber] = "*";
                    isEditable[CabinNumber] = true;
                    System.out.println("Remove Successful!");
                } else {
                    System.out.println("Selected Cabin is Already Empty");
                }
            } else {
                System.out.println("Back to Main Menu....");
                break;
            }
        }
    }


    private static void SortCustomerName() {
        System.out.println("\nCustomer Names Sorted in Alphabetical Order");
        String temporaryString;
        int occupiedCabinCount = 0;
        int arrayLength = CruiseCabin.length;
        String[] CustomerNameArray = new String[arrayLength];
        for (int i = 0; i < arrayLength; i++) {
            CustomerNameArray[i] = CruiseCabin[i].substring(0,1).toUpperCase() + CruiseCabin[i].substring(1);
        }
        String[] newArray = Arrays.copyOf(CustomerNameArray, arrayLength);

        for (int i = 0; i < newArray.length; i++) {
            for (int j = i + 1; j < newArray.length; j++) {
                if (newArray[i].compareTo(newArray[j]) > 0) {
                    temporaryString = newArray[i];
                    newArray[i] = newArray[j];
                    newArray[j] = temporaryString;
                }
            }
        }
        for (String s : newArray) {
            int index = Arrays.asList(CustomerNameArray).indexOf(s);
            if (!s.equals("*")) {
                System.out.println(s + " (Cabin NO " + index + ")");
                occupiedCabinCount++;
            }
        }
        if (occupiedCabinCount == 0) {
            System.out.println("All Cabins are Empty!. No name to Sort \n\t\t\t¯\\_(ツ)_/¯");
        }
    }


    /**
     *Store program data into file
     */
    private static void StoreProgramData() {
        if (!(CabinChecker() == 'Y')) {
            try {
                System.out.println("File Saving....");
                File file = new File("./saveData/"); // create new File object

                if (!file.exists()) {
                    Files.createDirectory(Path.of("./saveData/"));
                }

                String saveFilename = new SimpleDateFormat("yyyyMMdd_HHmmss'.dat'").format(new java.util.Date());
                String saveFilePath = "./saveData/" + saveFilename;

                FileOutputStream saveDataFile = new FileOutputStream(saveFilePath);
                ObjectOutputStream saveFile = new ObjectOutputStream(saveDataFile);


                saveFile.writeObject(CruiseCabin);
                saveFile.writeObject(isEditable);

                System.out.println("File Saved Successfully!");
            } catch (Exception e) {
                System.out.println("Oops! Something went Wrong.");
            }
        } else {
            System.out.println("All Cabins Are Empty\nNothing to Save.");
        }
    }


    /**
     *Load program data from file
     */
    private static void loadProgramData() {
        try {
            int saveDataIndex;
            String[] saveFileList;
            File file = new File("./saveData/");

            FilenameFilter filenameFilter = (dir, name) -> name.endsWith(".dat");
            saveFileList = file.list(filenameFilter);

            if (!file.exists() || Objects.requireNonNull(saveFileList).length == 0) {
                System.out.println("No Save Data Found!\n\t¯\\_(ツ)_/¯");
            } else {
                System.out.println("\nList Of Save Data");
                for (int i = 0; i < saveFileList.length; i++) {
                    System.out.println("[" + i + "] " + saveFileList[i].substring(0, saveFileList[i].lastIndexOf(".")));
                }

                do { // this loop validate user input
                    System.out.print("Enter Save Data Index [0 - " + (saveFileList.length - 1) + "] to Load Save Data: ");
                    while (!scanner.hasNextInt()) {
                        System.out.print("Invalid Input! Try Again.\nEnter Save Data Index (0 - " + saveFileList.length + ") to Load Save Data: ");
                        scanner.next();
                    }
                    saveDataIndex = scanner.nextInt();
                    if ((saveDataIndex < 0) || (saveDataIndex > saveFileList.length)) {
                        System.out.println("Invalid Input! Try Again.");
                    }
                } while ((saveDataIndex < 0) || (saveDataIndex > saveFileList.length));
                System.out.println("File Loading...");

                String savedFileName = "./saveData/" + saveFileList[saveDataIndex];

                FileInputStream savedDataFile = new FileInputStream(savedFileName);
                ObjectInputStream savedFile = new ObjectInputStream(savedDataFile);


                CruiseCabin = (String[]) (savedFile.readObject());
                isEditable = (boolean[]) savedFile.readObject();

                System.out.println("File Loaded Successfully!");
            }
        } catch (Exception e) {
            System.out.println("Oops! Something went Wrong.");
        }
    }








    private static char CabinChecker() {
        int trueCount = 0;
        int falseCount = 0;
        char returnChar = ' ';
        for (String s : CruiseShip.CruiseCabin) {
            if (s.equals("*")) {
                trueCount++;
            } else {
                falseCount++;
            }
        }
        if (trueCount == 12) {
            returnChar = 'Y';
        } else if (falseCount == 12) {
            returnChar = 'N';
        }
        return returnChar;
    }

    /**
     *Find cabin from customer name
     */
    private static void GetCabin() {
        boolean isFound = false;
        System.out.print("Please enter the Customer Name: ");
        String customerName = scanner.next();
        for (int index = 0; index < CruiseCabin.length; index++ ) {
            if (CruiseCabin[index].equals(customerName) ){
                System.out.print("Cabin Number is: " + index);
                isFound = true;
                break;
            }
        }
        if (!isFound){
            System.out.print("Cabin Not Found!");
        }
    }
}