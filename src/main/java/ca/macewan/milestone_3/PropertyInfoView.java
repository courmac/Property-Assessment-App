/**
 * Student's Name: Orest Dushkevich and Courtney McNeilly
 * Milestone #2
 * CMPT 305 LAB X02L Fall 2021
 * Instructor's Name: Indratmo Indratmo
 *
 * Purpose:
 * This Class is for the popup window with the individual stats
 *
 *
 */
package ca.macewan.milestone_3;

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

        ApiNeighbourhoodQuery nhQuery = new ApiNeighbourhoodQuery(nhID);




        // set views
        nhTitle.setText(row.getAddress());
        nhNeighbourhood.setText(row.getNeighbourhood());nhDescription.setText(nhQuery.getNeighbourhoodDescription());
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

}
