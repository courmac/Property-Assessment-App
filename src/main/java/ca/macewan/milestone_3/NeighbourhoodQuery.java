package ca.macewan.milestone_3;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;


import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

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
            neighbourhoodDescription = "";
        }
    }

    /**
     * this function returns how many dogs and cats there are
     * @return
     */
    @Deprecated
    private void getNeighbourhoodPets(){

        String cats = "https://data.edmonton.ca/resource/5squ-mg4w.json?$where=&year=2021&pet_type=Cat&neighbourhood_id=" + neighbourhood_number;
        //String type = "+AND+neighbourhood_id=";
        String dogs = "https://data.edmonton.ca/resource/5squ-mg4w.json?$where=&year=2021&pet_type=Dog&neighbourhood_id="+ neighbourhood_number;

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

            numCats = arrayCats.size();
            numDogs = arrayDogs.size();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Deprecated
    private void buildingPermits(){

        String source = "https://data.edmonton.ca/resource/24uj-dj8v.json?$where=&year=2021&neighbourhood_numberr=" + neighbourhood_number;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(source)).GET().build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String jsonString =  response.body();

            JsonParser parser = new JsonParser();
            JsonArray array = parser.parse(jsonString).getAsJsonArray();

            buildingPermits = array.size();

            for (JsonElement obj : array){
                buildingPermitsValue += obj.getAsJsonObject().get("construction_value").getAsFloat();
            }


        } catch (Exception e) {
            e.printStackTrace();
            neighbourhoodDescription = "";
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

    public int getNumCats() {
        return numCats;
    }

    public int getNumDogs() {
        return numDogs;
    }

    public int getNeighbourhood_number() {
        return neighbourhood_number;
    }

    public String getName() {
        return name;
    }

    public int getBuildingPermits() {
        return buildingPermits;
    }

    public float getBuildingPermitsValue() {
        return buildingPermitsValue;
    }

    public int getTrees() {
        return trees;
    }
}