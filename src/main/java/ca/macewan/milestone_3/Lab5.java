package ca.macewan.milestone_3;

import static java.lang.String.format;

public class Lab5 {
    public static void main(String[] args) {
        PropertyAssessmentDAO propertyAssessment = new ApiPropertyAssessmentDAO();
        PropertyAssessment prop = propertyAssessment.getByAccountNumber(5146600);
        System.out.println(prop);
        System.out.println();
        System.out.println("downtown");

        //ApiPropertyAssessmentDAO APIProperty = new ApiPropertyAssessmentDAO();
        PropertyAssessments APIproperty = propertyAssessment.getByNeighbourhood("granville");
        int[] data = APIproperty.propertyAssessmentStats();
        printData(data);


        PropertyAssessments property = propertyAssessment.getByAssessmentClass("residential");
        int[] data2 = property.propertyAssessmentStats();
        printData(data2);

        System.out.println();
        System.out.println("get all");
        PropertyAssessments getAll = propertyAssessment.getAll();
        int[] data3 = getAll.propertyAssessmentStats();
        printData(data3);



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
