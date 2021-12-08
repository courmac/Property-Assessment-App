package ca.macewan.milestone_3;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.web.WebView;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ResourceBundle;

public class PropertyInfoView implements Initializable {

    @FXML
    private Label nhTitle;
    @FXML
    private Label nhNeighbourhood;
    @FXML
    private Label nhDescription;
    @FXML
    private WebView webView;

    // Statistics
    @FXML
    private Label numCats;
    @FXML
    private Label numDogs;
    @FXML
    private Label numTrees;
    @FXML
    private Label numPermits;
    @FXML
    private Label valuePermits;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // get state instances
        PropertyHolder holder = PropertyHolder.getInstance();
        TableViewObject row = holder.getProperty();
        PropertyAssessments propAssessments = holder.getImportedPropertyAssessments();

        int nhID = propAssessments.findAccount(row.getAccount()).getNeighbourhood().getNeighbourhoodId();

        NeighbourhoodQuery nhQuery = new NeighbourhoodQuery(nhID);



        // set views
        nhTitle.setText(row.getAddress());
        nhNeighbourhood.setText(row.getNeighbourhood());
//        nhDescription.setText("Lorem ipsum dolor sit amet, consectetur adipiscing elit. Curabitur risus dolor, auctor non diam tincidunt, malesuada maximus mi. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia curae; Proin varius elit eu elit tincidunt, sit amet gravida nunc tincidunt. Maecenas in augue ac massa accumsan posuere eu sed enim. Cras elit tortor, lacinia in ligula vitae, suscipit dapibus ipsum. Praesent ornare erat nec nisi consectetur, et ultrices enim pulvinar.");
        nhDescription.setText(nhQuery.getNeighbourhoodDescription());
        numCats.setText(nhQuery.getNumCats());
        numDogs.setText(nhQuery.getNumDogs());
        numPermits.setText(nhQuery.getBuildingPermits());
        numTrees.setText(nhQuery.getTrees());
        valuePermits.setText(nhQuery.getBuildingPermitsValue());

//      Google Maps Embed
        String address = row.getAddress().replace(" ","+");
        String place = address + ",Edmonton+AB";
        webView.getEngine().loadContent("<iframe\n" +
                "  width=\"460\"\n" +
                "  height=\"372\"\n" +
                "  style=\"border:0\"\n" +
                "  loading=\"lazy\"\n" +
                "  allowfullscreen\n" +
                "  src=\"https://www.google.com/maps/embed/v1/place?key=AIzaSyBjZyG-LSQkR6BgsyiB9--f9_FgmBYHecU\n" +
                "    &q=" + place + "\">\n" +
                "</iframe>");

    }


        // TODO neighbourhood stats
//    NeighbourhoodQuery neighbourhoodQuery = new NeighbourhoodQuery(neighbourhood_number);
//    String neighbourhoodDescription = neighbourhoodQuery.getNeighbourhoodDescription();
//    int numCats = neighbourhoodQuery.getNumCats();
//    int numDogs = neighbourhoodQuery.getNumDogs();
    /**
     * This function returns a description of the neighbourhood  uses neighbourhood_number as a parameter
     * @param neighbourhood_number the neighbourhood ID
     * @return
     */
    @Deprecated
    private String getNeighbourhoodDescription(int neighbourhood_number){

        String type = "neighbourhood_number=";
        String source = "https://data.edmonton.ca/resource/65fr-66s6.json?"+ type + neighbourhood_number;
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder().uri(URI.create(source)).GET().build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            String jsonString =  response.body();

            JsonParser parser = new JsonParser();
            JsonArray array = parser.parse(jsonString).getAsJsonArray();

            return array.get(0).getAsJsonObject().get("description").getAsString();

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
//
//    /**
//     * this function returns how many dogs and cats there are
//     * @param neighbourhood_number
//     * @return
//     */
//    @Deprecated
//    private int[] getNeighbourhoodPets(int neighbourhood_number){
//        int[] pets = new int[2];
//        String cats = "https://data.edmonton.ca/resource/5squ-mg4w.json?$where=&year=2021&pet_type=Cat&neighbourhood_id=" + neighbourhood_number;
//        //String type = "+AND+neighbourhood_id=";
//        String dogs = "https://data.edmonton.ca/resource/5squ-mg4w.json?$where=&year=2021&pet_type=Dog&neighbourhood_id="+ neighbourhood_number;
//
//
//
//        HttpClient client = HttpClient.newHttpClient();
//        HttpRequest requestCats = HttpRequest.newBuilder().uri(URI.create(cats)).GET().build();
//        HttpRequest requestDogs = HttpRequest.newBuilder().uri(URI.create(dogs)).GET().build();
//
//        try {
//            HttpResponse<String> responseCats = client.send(requestCats, HttpResponse.BodyHandlers.ofString());
//            String jsonStringCats =  responseCats.body();
//
//            HttpResponse<String> responseDogs = client.send(requestDogs, HttpResponse.BodyHandlers.ofString());
//            String jsonStringDogs =  responseDogs.body();
//
//            JsonParser parser = new JsonParser();
//            JsonArray arrayCats = parser.parse(jsonStringCats).getAsJsonArray();
//            JsonArray arrayDogs = parser.parse(jsonStringDogs).getAsJsonArray();
//
//            pets[0] = arrayCats.size();
//            pets[1] = arrayDogs.size();
//
//            return pets;
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            return null;
//        }
//    }

}
