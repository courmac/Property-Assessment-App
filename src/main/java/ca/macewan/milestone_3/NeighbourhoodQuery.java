package ca.macewan.milestone_3;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.text.NumberFormat;

public class NeighbourhoodQuery {

    private int neighbourhood_number;
    private String name;
    private String neighbourhoodDescription;
    private int numCats;
    private int numDogs;
    private int buildingPermits;
    private float buildingPermitsValue;
    private int trees;

    NeighbourhoodQuery(int neighbourhood_number){
        this.neighbourhood_number = neighbourhood_number;
        neighbourhoodDescription();
        getNeighbourhoodPets();
        buildingPermits();
        trees();
    }

    /**
     * This function returns a description of the neighbourhood  uses neighbourhood_number as a parameter
     *
     * @return
     */
    @Deprecated
    private void neighbourhoodDescription(){
        String type = "neighbourhood_number=";
        String source = "https://data.edmonton.ca/resource/65fr-66s6.json?"+ type + neighbourhood_number;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(source)).GET().build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String jsonString =  response.body();

            JsonParser parser = new JsonParser();
            JsonArray array = parser.parse(jsonString).getAsJsonArray();

            neighbourhoodDescription = array.get(0).getAsJsonObject().get("description").getAsString();
            name = array.get(0).getAsJsonObject().get("name").getAsString();

        } catch (Exception e) {
            e.printStackTrace();
            neighbourhoodDescription = "No Data";
        }
    }

    /**
     * this function returns how many dogs and cats there are
     * @return
     */
    @Deprecated
    private void getNeighbourhoodPets(){

        String cats = "https://data.edmonton.ca/resource/5squ-mg4w.json?$where=&year=2021&pet_type=Cat&neighbourhood_id=" + neighbourhood_number+"&$select=count(neighbourhood_id)";
        //String type = "+AND+neighbourhood_id=";
        String dogs = "https://data.edmonton.ca/resource/5squ-mg4w.json?$where=&year=2021&pet_type=Dog&neighbourhood_id="+ neighbourhood_number+"&$select=count(neighbourhood_id)";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest requestCats = HttpRequest.newBuilder().uri(URI.create(cats)).GET().build();
        HttpRequest requestDogs = HttpRequest.newBuilder().uri(URI.create(dogs)).GET().build();

        try {
            HttpResponse<String> responseCats = client.send(requestCats, HttpResponse.BodyHandlers.ofString());
            String jsonStringCats =  responseCats.body();

            HttpResponse<String> responseDogs = client.send(requestDogs, HttpResponse.BodyHandlers.ofString());
            String jsonStringDogs =  responseDogs.body();

            JsonParser parser = new JsonParser();
            JsonArray arrayCats = parser.parse(jsonStringCats).getAsJsonArray();
            JsonArray arrayDogs = parser.parse(jsonStringDogs).getAsJsonArray();

            numCats = arrayCats.get(0).getAsJsonObject().get("count_neighbourhood_id").getAsInt();
            numDogs = arrayDogs.get(0).getAsJsonObject().get("count_neighbourhood_id").getAsInt();

        } catch (Exception e) {
            e.printStackTrace();
            numCats = 0;
            numDogs = 0;

        }
    }

    @Deprecated
    private void buildingPermits(){

        String sourceNumPermits = "https://data.edmonton.ca/resource/24uj-dj8v.json?$where=&year=2021&neighbourhood_numberr="+neighbourhood_number+"&$select=count(row_id)";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest requestNumPermits = HttpRequest.newBuilder().uri(URI.create(sourceNumPermits)).GET().build();

        try {
            HttpResponse<String> responseNumPermits = client.send(requestNumPermits, HttpResponse.BodyHandlers.ofString());
            String jsonStringNumPermits =  responseNumPermits.body();

            JsonParser parser = new JsonParser();
            JsonArray arrayNumPermits = parser.parse(jsonStringNumPermits).getAsJsonArray();

            buildingPermits = arrayNumPermits.get(0).getAsJsonObject().get("count_row_id").getAsInt();
            String source = "https://data.edmonton.ca/resource/24uj-dj8v.json?$where=&year=2021&neighbourhood_numberr="+neighbourhood_number;
            HttpClient client2 = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder().uri(URI.create(source)).GET().build();
            HttpResponse<String> responseNumPermits2 = client2.send(request, HttpResponse.BodyHandlers.ofString());
            String jsonStringNumPermits2 =  responseNumPermits2.body();

            JsonParser parser2 = new JsonParser();
            JsonArray arrayNumPermits2 = parser2.parse(jsonStringNumPermits2).getAsJsonArray();

            for (int i=0; i < arrayNumPermits2.size(); i++){

                if (arrayNumPermits2.get(i).getAsJsonObject().get("construction_value") == null) {
                    continue;
                } else {
                    buildingPermitsValue += arrayNumPermits2.get(i).getAsJsonObject().get("construction_value").getAsFloat();
                }


            }
        } catch (Exception e) {
            e.printStackTrace();
            buildingPermits = 1;
            buildingPermitsValue = 1;
        }
    }

    @Deprecated
    private void trees(){
        String source = "https://data.edmonton.ca/resource/eecg-fc54.json?$where=neighbourhood_name=\'" + name + "\'&$select=count(id)";
        //String source = "https://data.edmonton.ca/resource/65fr-66s6.json?"+ type + neighbourhood_number;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(source)).GET().build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String jsonString =  response.body();

            JsonParser parser = new JsonParser();
            JsonArray array = parser.parse(jsonString).getAsJsonArray();

            trees = array.get(0).getAsJsonObject().get("count_id").getAsInt();
            //name = array.get(0).getAsJsonObject().get("name").getAsString();

        } catch (Exception e) {
            //e.printStackTrace();
            trees = 0;
        }
    }


    public String getNeighbourhoodDescription() {
        return neighbourhoodDescription;
    }

    public String getNumCats() {
        return String.valueOf(numCats);
    }

    public String getNumDogs() {
        return String.valueOf(numDogs);
    }

    public int getNeighbourhood_number() {
        return neighbourhood_number;
    }

    public String getName() {
        return name;
    }

    public String getBuildingPermits() {

        return String.valueOf(buildingPermits);
    }

    public String getBuildingPermitsValue() {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String moneyString = formatter.format(buildingPermitsValue);
        return moneyString;
    }

    public String getTrees() {
        return String.valueOf(trees);
    }
}