package src.rubank;
import rubank.*;

import java.util.Scanner;

/**
 * User interface to process command line input for the Account Database.
 * Can process a single or multiple lines at once.
 * @author Dany Chucri, Madhur Nutulapati
 */
public class TransactionManager {
    private final AccountDatabase accountDatabase; // The Database structure to hold bank accounts
    private static final int OPEN_INDICATION = 1;
    private static final int CLOSE_INDICATION = 2;
    private static final int DEPOSIT_INDICATION = 3;
    private static final int WITHDRAW_INDICATION = 4;
    private static final int  INVALID_DATE= 1;
    private static final int  NO_TODAY_NO_FUTURE= 2;

    /**
     * Instantiates the TransactionManager using Account Database.
     */
    public TransactionManager(){
        accountDatabase = new AccountDatabase();
    }

    /**
     * Begins the reading of standard input, parsing and executing commands for Transaction Manager.
     */
    public void run(){
        Scanner S = new Scanner(System.in);
        System.out.println("Transaction Manager is running.");
        while(true){
            String fullCommand = S.nextLine().trim();//read + trim
            if(fullCommand.isEmpty())//blank line
                continue;
            String result = fullCommand.replaceAll("\t+", " ");
            result = result.replaceAll(" +", " ");
            String[] token = result.split(" ");
            String command = token[0];
            switch(command){
                case "O":
                    oCommand(token);
                    break;
                case "C":
                    cCommand(token);
                    break;
                case "D":
                    dCommand(token);
                    break;
                case "W":
                    wCommand(token);
                    break;
                case "P":
                    pCommand();
                    break;
                case "PI":
                    piCommand();
                    break;
                case "UB":
                    ubCommand();
                    break;
                case "Q":
                    System.out.println("Transaction Manager is terminated.");
                    System.exit(0);
                default:
                    System.out.println("Invalid command!");
            }
        }
    }

    /**
     To retrieve the type of bank, given the token string entry
     @param entry String entry of the token.
     @return int 1 - for checking; 2 for College checking; 3 for Money Market; 4 for Savings
     */
    private int bankType(String entry) {
        return switch (entry) {
            case "C" -> 1;
            case "CC" -> 2;
            case "MM" -> 3;
            case "S" -> 4;
            default -> 5;
        };
    }

    /**
     To create Checking object to perform the respective operation of opening,closing, depositing, or withdrawing.
     @param addProfile the Profile object
     @param balance The user balance information
     @param operation Indicates either open, close, Deposit, or Withdraw
     */
    private void createChecking(Profile addProfile, double balance, int operation){
        Checking addAccount = new Checking(addProfile,balance);
        if(operation == OPEN_INDICATION){
            if (accountDatabase.open(addAccount))
                System.out.println(addProfile.getFname() + " " + addProfile.getLname() + " " + addProfile.getDOB() + "(C) opened.");
            else
                System.out.println(addProfile.getFname() + " " + addProfile.getLname() + " " + addProfile.getDOB() + "(C) is already in the database.");
        } else if (operation==CLOSE_INDICATION){
            if (accountDatabase.close(addAccount)) {
                System.out.println(addProfile.getFname() + " " + addProfile.getLname() + " " + addProfile.getDOB() + "(C) has been closed.");
            }
            else
                System.out.println(addProfile.getFname() + " " + addProfile.getLname() + " " + addProfile.getDOB() + "(C) is not in the database.");
        } else if (operation==DEPOSIT_INDICATION) {
            if (!(accountDatabase.contains(addAccount))) {
                System.out.println(addProfile.getFname() + " " + addProfile.getLname() + " " + addProfile.getDOB() + "(C) is not in the database.");
            } else {
                accountDatabase.deposit(addAccount);
                System.out.println(addProfile.getFname() + " " + addProfile.getLname() + " " + addProfile.getDOB() + "(C) Deposit - balance updated.");
            }
        } else if (operation==WITHDRAW_INDICATION) {
            if (!(accountDatabase.contains(addAccount))) {
                System.out.println(addProfile.getFname() + " " + addProfile.getLname() + " " + addProfile.getDOB() + "(C) is not in the database.");
            }
            else {
                if (accountDatabase.withdraw(addAccount))
                    System.out.println(addProfile.getFname() + " " + addProfile.getLname() + " " + addProfile.getDOB() + "(C) Withdraw - balance updated.");
                else
                    System.out.println(addProfile.getFname() + " " + addProfile.getLname() + " " + addProfile.getDOB() + "(C) Withdraw - insufficient fund.");
            }
        } else {
            System.out.println("The account is already on the database.");
        }
    }

    /**
     To create College Checking object to perform the respective operation of opening,closing, depositing, or withdrawing.
     @param addProfile the Profile object
     @param balance The user balance information
     @param code The campus code - > 0 - New Brunswick; 1 - Newark ; 2 - Camden
     @param operation Indicates either open, close, Deposit, or Withdraw
     */
    private void createCollegeChecking(Profile addProfile, double balance, Campus code, int operation){
        CollegeChecking addAccount = new CollegeChecking(addProfile,balance,code);
        if(operation==OPEN_INDICATION){
            if(accountDatabase.open(addAccount))
                System.out.println(addProfile.getFname() + " " + addProfile.getLname() + " " + addProfile.getDOB() + "(CC) opened.");
            else
                System.out.println(addProfile.getFname() + " " + addProfile.getLname() + " " + addProfile.getDOB() + "(CC) is already in the database.");
        } else if(operation==CLOSE_INDICATION){
            if(accountDatabase.close(addAccount))
                System.out.println(addProfile.getFname() + " " + addProfile.getLname() + " " + addProfile.getDOB() + "(CC) has been closed.");
            else
                System.out.println(addProfile.getFname() + " " + addProfile.getLname() + " " + addProfile.getDOB() + "(CC) is not in the database.");
        } else if (operation==DEPOSIT_INDICATION) {
            if (!(accountDatabase.contains(addAccount))) {
                System.out.println(addProfile.getFname() + " " + addProfile.getLname() + " " + addProfile.getDOB() + "(CC) is not in the database.");

            } else {
                accountDatabase.deposit(addAccount);
                System.out.println(addProfile.getFname() + " " + addProfile.getLname() + " " + addProfile.getDOB() + "(CC) Deposit - balance updated.");
            }
        } else if (operation==WITHDRAW_INDICATION) {
            if (!(accountDatabase.contains(addAccount))) {
                System.out.println(addProfile.getFname() + " " + addProfile.getLname() + " " + addProfile.getDOB() + "(CC) is not in the database.");
            }
            else {
                if (accountDatabase.withdraw(addAccount))
                    System.out.println(addProfile.getFname() + " " + addProfile.getLname() + " " + addProfile.getDOB() + "(CC) Withdraw - balance updated.");
                else
                    System.out.println(addProfile.getFname() + " " + addProfile.getLname() + " " + addProfile.getDOB() + "(CC) Withdraw - insufficient fund.");
            }
        } else{
            System.out.println("The account is already on the database.");
        }
    }

    /**
     To create Money Market object to perform the respective operation of opening,closing, depositing, or withdrawing.
     @param addProfile the Profile object
     @param balance The user balance information
     @param operation Indicates either open, close, Deposit, or Withdraw
     */
    private void createMoneyMarket(Profile addProfile, double balance, int operation){
        MoneyMarket addAccount = new MoneyMarket(addProfile,balance);
        if( operation == OPEN_INDICATION ){
            if (balance < 2000) {
                System.out.println("Minimum of $2000 to open a Money Market account.");
            }
            else if(accountDatabase.open(addAccount))
                System.out.println(addProfile.getFname() + " " + addProfile.getLname() + " " + addProfile.getDOB() + "(MM) opened.");
            else
                System.out.println(addProfile.getFname() + " " + addProfile.getLname() + " " + addProfile.getDOB() + "(MM) is already in the database.");
        } else if(operation==CLOSE_INDICATION){
            if(accountDatabase.close(addAccount))
                System.out.println(addProfile.getFname() + " " + addProfile.getLname() + " " + addProfile.getDOB() + "(MM) has been closed.");
            else
                System.out.println(addProfile.getFname() + " " + addProfile.getLname() + " " + addProfile.getDOB() + "(MM) is not in the database.");
        } else if (operation==DEPOSIT_INDICATION) {
            if (!(accountDatabase.contains(addAccount))) {
                System.out.println(addProfile.getFname() + " " + addProfile.getLname() + " " + addProfile.getDOB() + "(MM) is not in the database.");

            } else {
                accountDatabase.deposit(addAccount);
                System.out.println(addProfile.getFname() + " " + addProfile.getLname() + " " + addProfile.getDOB() + "(MM) Deposit - balance updated.");
            }
        } else if (operation==WITHDRAW_INDICATION) {
            if (!(accountDatabase.contains(addAccount))) {
                System.out.println(addProfile.getFname() + " " + addProfile.getLname() + " " + addProfile.getDOB() + "(MM) is not in the database.");
            }
            else {
                if (accountDatabase.withdraw(addAccount))
                    System.out.println(addProfile.getFname() + " " + addProfile.getLname() + " " + addProfile.getDOB() + "(MM) Withdraw - balance updated.");
                else
                    System.out.println(addProfile.getFname() + " " + addProfile.getLname() + " " + addProfile.getDOB() + "(MM) Withdraw - insufficient fund.");
            }
        } else{
            System.out.println("The account is already on the database.");
        }
    }

    /**
     To create Savings object to perform the respective operation of opening,closing, depositing, or withdrawing.
     @param addProfile the Profile object
     @param balance The user balance information
     @param loyal indicating whether holder is Loyal or not. 1 Being loyal and otherwise being not loyal.
     @param operation Indicates either open, close, Deposit, or Withdraw
     */
    private void createSavings(Profile addProfile, double balance, int loyal, int operation){
        boolean loyalKey = loyal == 1;
        Savings addAccount = new Savings(addProfile, balance, loyalKey);
        if(operation == OPEN_INDICATION){
            if(accountDatabase.open(addAccount))
                System.out.println(addProfile.getFname() + " " + addProfile.getLname() + " " + addProfile.getDOB() + "(S) opened.");
            else
                System.out.println(addProfile.getFname() + " " + addProfile.getLname() + " " + addProfile.getDOB() + "(S) is already in the database.");
        } else if(operation==CLOSE_INDICATION){
            if(accountDatabase.close(addAccount))
                System.out.println(addProfile.getFname() + " " + addProfile.getLname() + " " + addProfile.getDOB() + "(S) has been closed.");
            else
                System.out.println(addProfile.getFname() + " " + addProfile.getLname() + " " + addProfile.getDOB() + "(S) is not in the database.");
        } else if (operation==DEPOSIT_INDICATION) {
            if (!(accountDatabase.contains(addAccount))) {
                System.out.println(addProfile.getFname() + " " + addProfile.getLname() + " " + addProfile.getDOB() + "(S) is not in the database.");

            } else {
                accountDatabase.deposit(addAccount);
                System.out.println(addProfile.getFname() + " " + addProfile.getLname() + " " + addProfile.getDOB() + "(S) Deposit - balance updated.");
            }
        } else if (operation==WITHDRAW_INDICATION) {
            if (!(accountDatabase.contains(addAccount))) {
                System.out.println(addProfile.getFname() + " " + addProfile.getLname() + " " + addProfile.getDOB() + "(S) is not in the database.");
            }
            else {
                if (accountDatabase.withdraw(addAccount))
                    System.out.println(addProfile.getFname() + " " + addProfile.getLname() + " " + addProfile.getDOB() + "(S) Withdraw - balance updated.");
                else
                    System.out.println(addProfile.getFname() + " " + addProfile.getLname() + " " + addProfile.getDOB() + "(S) Withdraw - insufficient fund.");
            }
        } else{
            System.out.println("The account is already on the database.");
        }
    }

    /**
     Helper method to give us the respective phrase for the enum Campus class.
     @param campCode 0-New Brunswick; 1-Newark;2-Camden
     @return string returning the phrase for the respective campCode integer
     */
    private String checkCampusCode(int campCode){
        if(campCode==0){
            return "NEW_BRUNSWICK";
        } else if (campCode==1) {
            return "NEWARK";
        } else if (campCode==2) {
            return "CAMDEN";
        }
        else{
            return "INVALID";
        }
    }

    /**
     * Command for opening an Account to the Account Database.
     * @param token An array of tokens from the command-line arguments.
     */
    private void oCommand(String[] token) {
        if (token.length == 1) {
            System.out.println("Invalid Command!");
        }
        else if (token.length >= 2 && token.length <= 5) {
            System.out.println("Missing data for opening an account.");
        }
        else try {
            int key = bankType(token[1]);
            Date dateInput = createDate(token[4], key);
            if (dateInput == null) return;
            Profile addProfile = new Profile(token[2], token[3], dateInput);
            double balance;
            try { balance = Double.parseDouble(token[5]);
            } catch (Exception e) {
                System.out.println("Not a valid amount.");
                return;
            }
            if (balance <= 0) {
                System.out.println("Initial deposit cannot be 0 or negative.");
                return;
            }
            if (key == 2) {
                String phraseLoc = checkCampusCode(Integer.parseInt(token[6]));
                if (phraseLoc.equals("INVALID")) {
                    System.out.println("Invalid campus code.");
                }
                else createCollegeChecking(addProfile, balance, Campus.valueOf(phraseLoc), OPEN_INDICATION);
            }
            if (key == 1)
                createChecking(addProfile, Double.parseDouble(token[5]), OPEN_INDICATION);
            else if (key == 3)
                createMoneyMarket(addProfile, Double.parseDouble(token[5]), OPEN_INDICATION);
            else if (key == 4)
                createSavings(addProfile, Double.parseDouble(token[5]), Integer.parseInt(token[6]), OPEN_INDICATION);
            } catch(Exception e){
            System.out.println("Error with adding an account.");
        }
    }

    /**
     * Instantiates a Date object to be used for the creation of an Account.
     * Performs error checks on the validity of a date.
     * @param date A String token in the form of "xx/xx/xxxx".
     * @param collegeIndication indicates whether it is a college checking account to perform age check
     * @return The Date object to be used.
     */
    private Date createDate(String date, int collegeIndication){
        Date addDate = new Date(date);
        if(addDate.isValid() == INVALID_DATE){
            System.out.println("DOB invalid: " + date + " not a valid calendar date!");
            return null;
        } else if (addDate.isValid() == NO_TODAY_NO_FUTURE) {
            System.out.println("DOB invalid: " + date + " cannot be today or a future day.");
            return null;
        } else if(collegeIndication == 2 && addDate.checkAge() >= 24){
            System.out.println("DOB invalid: " + date + " over 24.");
            return null;
        } else if (addDate.checkAge()<16) {
            System.out.println("DOB invalid: " + date + " under 16.");
            return null;
        }
        return addDate;
    }

    /**
     * Command for closing an Account from the Account Database.
     * @param token An array of tokens from the command-line arguments.
     */
    private void cCommand(String[] token){
        if(token.length!=5){
            System.out.println("Missing data for closing an account.");
            return;
        }
        try{
            int key = bankType(token[1]);
            Date dateInput = new Date(token[4]);
            if (dateInput.isValid() == 2) {
                System.out.println("DOB invalid: " + dateInput + " cannot be today or a future day.");
                return;
            }
            Profile closeAccount = new Profile(token[2],token[3],dateInput);
            if(key==1)
                createChecking(closeAccount,0,CLOSE_INDICATION);
            else if(key==2){
                createCollegeChecking(closeAccount,0, Campus.NEW_BRUNSWICK,CLOSE_INDICATION);}
            else if(key ==3)
                createMoneyMarket(closeAccount,0,CLOSE_INDICATION);
            else if(key == 4)
                createSavings(closeAccount,0,0,CLOSE_INDICATION);
            else
                System.out.println("Not valid Bank Type 2");//specify
        }catch (Exception e){
            System.out.println("Error processing command");
        }
    }

    /**
     * Will check the balance when called
     * @param token An array of tokens from the command-line arguments.
     * @param type deposit or withdrawal
     */
    private double checkBalance(String token, String type) {
        double balanceAmount;
        try {
            balanceAmount = Double.parseDouble(token);
        } catch (Exception e) {
            System.out.println("Not a valid amount.");
            return -1;
        }
        if (balanceAmount <= 0){
            String s;
            if (type.equals("Deposit")) s = "Deposit";
            else s = "Withdraw";
            System.out.println(s + " - amount cannot be 0 or negative.");
            return -1;
        }
        return balanceAmount;
    }

    /**
     * Command for depositing into Account from the Account Database.
     * @param token An array of tokens from the command-line arguments.
     */
    private void dCommand(String[] token) {
        if(token.length!=6){
            System.out.println("Invalid command format.");
            return;
        }
        try{
            int key = bankType(token[1]);
            Date dateInput = new Date(token[4]);
            if (dateInput.isValid() == 2) {
                System.out.println("DOB invalid: " + dateInput + " cannot be today or a future day.");
                return;
            }
            double depositAmount = checkBalance(token[5], "Deposit");
            if (depositAmount <= 0) return;
            Profile depositAccount = new Profile(token[2],token[3],dateInput);
            if(key==1)
                createChecking(depositAccount,depositAmount,DEPOSIT_INDICATION);
            else if(key==2)
                createCollegeChecking(depositAccount,depositAmount, Campus.NEW_BRUNSWICK,DEPOSIT_INDICATION);
            else if(key ==3)
                createMoneyMarket(depositAccount,depositAmount,DEPOSIT_INDICATION);
            else if(key == 4)
                createSavings(depositAccount,depositAmount,0,DEPOSIT_INDICATION);
        }catch (Exception e){
            System.out.println("Error processing command");
        }
    }

    /**
     * Command for withdrawing from Account from the Account Database.
     * @param token An array of tokens from the command-line arguments.
     */
    private void wCommand(String [] token){
        if(token.length!=6){
            System.out.println("Invalid command format.");
            return;
        }
        try{
            int key = bankType(token[1]);
            Date dateInput = new Date(token[4]);
            if (dateInput.isValid() == 2) {
                System.out.println("DOB invalid: " + dateInput + " cannot be today or a future day.");
                return;
            }
            double withdrawAmount = checkBalance(token[5], "Withdraw");
            if (withdrawAmount <= 0) return;
            Profile depositAccount = new Profile(token[2],token[3],dateInput);
            if(key==1)
                createChecking(depositAccount,withdrawAmount,WITHDRAW_INDICATION);
            else if(key==2)
                createCollegeChecking(depositAccount,withdrawAmount, Campus.NEW_BRUNSWICK,WITHDRAW_INDICATION);
            else if(key ==3)
                createMoneyMarket(depositAccount,withdrawAmount,WITHDRAW_INDICATION);
            else if(key == 4)
                createSavings(depositAccount,withdrawAmount,0,WITHDRAW_INDICATION);
            else
                System.out.println("Not valid Bank Type 4");//specify
        }catch (Exception e){
            System.out.println("Error processing command");
        }
    }

    /**
     *printing sorted accounts from accountDatabase
     */
    private void pCommand(){
        accountDatabase.printSorted();
    }

    /**
     *printing fees and Interests
     */
    private void piCommand(){
        accountDatabase.printFeesAndInterests();
    }

    /**
     *printing and updating balances
     */
    private void ubCommand(){
        accountDatabase.printUpdatedBalances();
    }

    public static void main(String[] args){
    }
}