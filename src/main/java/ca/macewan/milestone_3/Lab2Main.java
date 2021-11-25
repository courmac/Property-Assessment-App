package ca.macewan.milestone_3;


import java.util.Scanner;

import static java.lang.String.format;


public class Lab2Main {


    public static void main(String[] args) {

        // test file names
        //String filePath1 = "D:\\sonocent\\CMPT 305\\Property_test.csv";
        //String filePath2 = "D:\\sonocent\\CMPT 305\\Property_Assessment_Data_2021.csv";
        //String fileName= "Property_Assessment_Data_2021.csv";

        // create empty PropertyAssessments class
        //PropertyAssessments propertyAssessmentsList = new PropertyAssessments();
        PropertyAssessmentDAO propertyAssessmentsListDAO = new CsvPropertyAssessmentDAO();
        propertyAssessmentsListDAO.changeSource("Property_Assessment_Data_2021.csv");
        //propertyAssessmentsList = propertyAssessmentsListDAO.getAll();

        /*
        // get user input for filename
        Scanner scanner= new Scanner(System.in);
        System.out.print("CSV filename: ");
        String userFileInput = scanner.nextLine();
        // System.out.println(userFileInput);

        // opens default file is you press enter
        try {
            if (userFileInput.equals("")) {
                propertyAssessmentsList = propertyAssessmentsListDAO.getAll();
            } else {
                propertyAssessmentsList.csvFileToPropertyAssessments(userFileInput);
            }
        } catch(Exception e){
            throw new Error("Error: can't open file " + userFileInput);
            //System.out.println("Error: can't open file "+userFileInput);
        }

        // reads the csv file and creates a P
        //propertyAssessmentsList.readFile(filePath2);

         */

        // displays the statistics for the csv
        int[] data = propertyAssessmentsListDAO.getAll().propertyAssessmentStats();
        System.out.println("Descriptive statistics of all property assessments");
        printData(data);
        Scanner scanner= new Scanner(System.in);

        scanner= new Scanner(System.in);
        System.out.print("Find a property assessment by account number: ");

        PropertyAssessment property = null;
        String userAccountInput1 = scanner.nextLine();

        try  {

            int accountNum = Integer.parseInt(userAccountInput1);

            //System.out.println("here");

            property = propertyAssessmentsListDAO.getByAccountNumber(accountNum);

        } catch (Exception e){
            // not an int
            System.out.println("Error: invalid account number...");

        }
       if (property == null){
           System.out.println("Data not found");
       } else {
           System.out.println(property);
       }

        System.out.println();
        scanner= new Scanner(System.in);
        System.out.print("Neighbourhood: ");
        String neighbourhoodName;
        neighbourhoodName = scanner.nextLine();
        System.out.println("Statistics (neighbourhood = " + neighbourhoodName+")");

        int[] data2 = propertyAssessmentsListDAO.getByNeighbourhood(neighbourhoodName).propertyAssessmentStats();
        printData(data2);


    }
    public static void printData(int[] data){
        if (data == null){
            System.out.println("Data not found");
            return;
        }
        System.out.println("n = " + data[0]);
        System.out.println("min: $" + format("%,d",data[1]));
        System.out.println("max: $" + format("%,d",data[2]));
        System.out.println("range: $" + format("%,d",data[3]));
        System.out.println("mean: $" + format("%,d",data[4]));
        System.out.println("median: $" + format("%,d", data[5]));
    }



}