package ca.macewan.milestone_3;


import java.util.Scanner;

import static java.lang.String.format;

public class Lab3Main {
    public static void main(String[] args) throws Exception {
/*
        // test file names
        //String filePath1 = "D:\\sonocent\\CMPT 305\\Property_test.csv";
         String filePath2 = "D:\\sonocent\\CMPT 305\\Property_Assessment_Data_2021.csv";
        //String fileName = "Property_Assessment_Data_2021.csv";

        // create empty PropertyAssessments class
        PropertyAssessments propertyAssessmentsList = new PropertyAssessments();

        // get user input for filename
        Scanner scanner = new Scanner(System.in);
        System.out.print("CSV filename: ");
        String userFileInput = scanner.nextLine();
        //System.out.println(userFileInput);



        // opens default file
        if (userFileInput.equals("")) {
            propertyAssessmentsList.csvFileToPropertyAssessments(filePath2);
        } else {
            propertyAssessmentsList.csvFileToPropertyAssessments(userFileInput);
        }


 */
        Scanner scanner = new Scanner(System.in);
        PropertyAssessments propertyAssessmentsList = new PropertyAssessments();
        PropertyAssessmentDAO propertyAssessmentsListDAO = new CsvPropertyAssessmentDAO();
        propertyAssessmentsList = propertyAssessmentsListDAO.getAll();

        System.out.print("Assessment class: ");
        String assessmentClass = scanner.nextLine();
        int[] data = propertyAssessmentsListDAO.getByAssessmentClass(assessmentClass).propertyAssessmentStats();
        System.out.println("Statistics (assessment class = " + assessmentClass + ")");
        printData(data);

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
