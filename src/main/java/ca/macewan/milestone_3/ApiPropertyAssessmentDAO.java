/**
 * Student's Name: Orest Dushkevich
 * Milestone #2
 * CMPT 305 LAB X02L Fall 2021
 * Instructor's Name: Indratmo Indratmo
 *
 * Purpose:
 * This is the API implementation to get the property assessment data directly from the city
 * of edmonton API
 */
package ca.macewan.milestone_3;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiPropertyAssessmentDAO implements PropertyAssessmentDAO {
    private String source = "https://data.edmonton.ca/resource/q7d6-ambg.csv";
    private boolean getLess = false;
    private int offsetSize = 10000;
    private boolean concurrentMode = false;
    private int concurrentoffset;
    private int concurrentlimit;


    /**
     * query the account number directly to the online databse through an API call
     * @param accountNumber property account to query
     * @return              the PropertyAssessment object, null if not found
     */
    @Override
    public PropertyAssessment getByAccountNumber(int accountNumber) {
        String query = source + "?account_number=" + accountNumber;
        PropertyAssessments prop = soqlQueryToPropertyAssessments(query);
        if (prop == null) return null;
        return prop.findAccount(accountNumber);

    }
    /**
     * query the neighbourhood directly to the online database through an API call
     * @param neighbourhood neighbourhood name to query
     * @return              the PropertyAssessments object of matching results
     */
    @Override
    public PropertyAssessments getByNeighbourhood(String neighbourhood) {
        String query = source + "?neighbourhood=" + neighbourhood.toUpperCase();
        return soqlQueryToPropertyAssessments(query);
    }

    /**
     * query the assessmentClass directly to the online database through an API call
     * @param assessmentClass neighbourhood name to query
     * @return                  the PropertyAssessments object of matching results
     */
    public PropertyAssessments getByAssessmentClass(String assessmentClass) {

        String query = source + "?$where=&mill_class_1=\'" + assessmentClass.toUpperCase() + "\'+OR+mill_class_2=\'" +assessmentClass.toUpperCase() +"\'+OR+mill_class_3=\'" +assessmentClass.toUpperCase() +"\'";
        System.out.println(query);
        return soqlQueryToPropertyAssessments(query);
    }

    /**
     * this method returns the entire city of edmonton property assesments database
     * in it's current format will take a while to complete
     * @return the PropertyAssessments object of entire database
     */
    @Override
    public PropertyAssessments getAll() {
        this.setOffsetSize(10000);
        getLess = false;
        return soqlQueryToPropertyAssessments(source + "?");
    }

    /**
     * this method returns the entire city of edmonton property assesments database
     * in it's current format will take a while to complete
     * @return the PropertyAssessments object of entire database
     */

    public PropertyAssessments getAllConcurrent(int limit, int offset) {
        PropertyAssessments propertyList = new PropertyAssessments();
        String loopQurey = source + "?" + "&$limit=" + limit + "&$offset=" + offset;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(loopQurey)).GET().build();


        String[] propArrayResponse = null;

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            //spits the response into a String array where each element is its own property assessment
            propArrayResponse = response.body().split("\n");

            //since the first line is the data headers if only one line was returned there was no data in the query
            // if no data is returned from the query returns null
            int entries = propArrayResponse.length;
            if (entries == 1) return new PropertyAssessments();

            PropertyAssessments propertyListChunk = new PropertyAssessments();

            // iterates through the propArrayResponse and splits each index into a string array for the
            // PropertyAssessment constructor
            for (int i = 1; i < entries; i++) {

                // formats the response into string array for the PropertyAssessment constructor
                String prop = propArrayResponse[i].replace("\"", "");
                String[] property = prop.split(",", -1);

                propertyListChunk.add(new PropertyAssessment(property));
            }

            // adds this offset's propertyListChunk to the rest of the propertyList
            propertyList.combine(propertyListChunk);

        } catch(IOException | InterruptedException e){
            e.printStackTrace();
        }

        return propertyList;
    }

    /**
     * changes the API source if it needs to be changed
     *
     * @param source API data source
     */
    @Override
    public void changeSource(String source) {
        this.source = source;
    }

    /**
     * this method only returns a partial List so that milestone#2 runs smoother
     * @return
     */
    @Override
    public PropertyAssessments getLess() {
        //this.setOffsetSize(1000);
        getLess = true;
        return soqlQueryToPropertyAssessments(source + "?");

    }

    /**
     * this is a single use function that can query the API for
     * @param query the API query
     * @return PropertyAssessments object with the query results
     */
    private PropertyAssessments soqlQueryToPropertyAssessments(String query) {

        PropertyAssessments propertyList = new PropertyAssessments();
        // sets up the offset size for the query
        int offset = 0;
        String loopQurey = query + "&$limit=" + offsetSize;

        //sets up the API query request
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(loopQurey)).GET().build();

        String loopQuery = null;
        String[] propArrayResponse = null;


        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            //spits the response into a String array where each element is its own property assessment
            propArrayResponse = response.body().split("\n");

            //since the first line is the data headers if only one line was returned there was no data in the query
            // if no data is returned from the query returns null
            int entries = propArrayResponse.length;
            if (entries == 1) return new PropertyAssessments();



            // this loop iterates though until all the query results are processed and adds all the results
            // to the propertyList arrayList
            // this loop runs until it hits an internal break
            while (true) { //offset < 10
                PropertyAssessments propertyListChunk = new PropertyAssessments();

                // iterates through the propArrayResponse and splits each index into a string array for the
                // PropertyAssessment constructor
                for (int i = 1; i < entries; i++) {

                    // formats the response into string array for the PropertyAssessment constructor
                    String prop = propArrayResponse[i].replace("\"", "");
                    String[] property = prop.split(",", -1);

                    propertyListChunk.add(new PropertyAssessment(property));
                }

                // adds this offset's propertyListChunk to the rest of the propertyList
                propertyList.combine(propertyListChunk);

                if(concurrentMode) return propertyListChunk;

                // sets up the API call for the next offset
                offset++;
                loopQurey = query + "&$limit="+offsetSize+"&$offset=" + offset * offsetSize;

                // tries the next offest to the API, breaks if anything goes wrong
                try {

                    request = HttpRequest.newBuilder().uri(URI.create(loopQurey)).GET().build();
                    response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    propArrayResponse = response.body().split("\n");

                } catch (Exception e){
                    break;
                }

                // breaks if there is no more data and only headers are returned
                entries = propArrayResponse.length;
                if (entries == 1) break;

                // breaks if only doing one API call and not pulling the rest of the data
                if (getLess) break;
                //if(concurrentMode) break;

            }

            } catch(IOException | InterruptedException e){
                    e.printStackTrace();
                }

        return propertyList;
    }




    /**
     * sets the offset size if desired to be smaller
     * @param offsetSize
     */
    public void setOffsetSize(int offsetSize) {
        this.offsetSize = offsetSize;
    }

    public String getSource() {
        return source;
    }
}
