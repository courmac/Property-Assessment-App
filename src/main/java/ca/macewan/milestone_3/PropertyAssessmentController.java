/*
  Student's Name: Orest Dushkevich and Courtney McNeilly
  Milestone #2
  CMPT 305 LAB X02L Fall 2021
  Instructor's Name: Indratmo Indratmo

  Purpose:
  This is the controller for the application window for Milestone #2 and contains the functionality  of teh application

 */

package ca.macewan.milestone_3;

import com.opencsv.CSVWriter;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PropertyAssessmentController implements Initializable {
    @FXML    private ComboBox<String> sourceComboBox;
    @FXML    private Button readDataButton;
    @FXML    private TextField accountNumberTextField;
    @FXML    private TextField addressTextField;
    @FXML    private TextField neighbourhoodTextField;
    @FXML    private ComboBox<String> assessmentClassComboBox;
    @FXML    private TextField minValueTextField;
    @FXML    private Label search_results;
    @FXML    private TextField maxValueTextField, kilometer_text_field, lat_text_field, long_text_field;
    @FXML    private Button searchButton,resetButton, cancel_button, export_button;
    @FXML    private CheckBox has_garage;
    @FXML    private TableColumn<TableViewObject, Integer>  col_account;
    @FXML    private TableColumn<TableViewObject, Integer>  assessedValue;
    @FXML    private TableColumn<TableViewObject, String>  address;
    @FXML    private TableColumn<TableViewObject, String>  neighbourhood;
    @FXML    private TableColumn<TableViewObject, String>  assessmentClass;
    @FXML    private TableColumn<TableViewObject, String>  location;


    @FXML    private TableView<TableViewObject> tableViewItem;
    private final ObservableList<TableViewObject> propertyList = FXCollections.observableArrayList();
    private Boolean hasDataRead = false;
    private Boolean ifStopPressed = false;

    PropertyAssessments importedPropertyAssessments;
    ArrayList<PropertyAssessment> searchPropertyAssessments;  // search results



    /**
     * initializes the window and any of the parameters that can't be stored in a layout file
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sourceComboBox.getItems().add("Edmonton's Open Data Portal (First 1,000 entries)");
        sourceComboBox.getItems().add("Edmonton's Open Data Portal All Data (WARNING LONG LOAD TIME)");


        // TODO automate this later based on what was returned from the DAO
        assessmentClassComboBox.getItems().add("RESIDENTIAL");
        assessmentClassComboBox.getItems().add("OTHER RESIDENTIAL");
        assessmentClassComboBox.getItems().add("FARMLAND");
        assessmentClassComboBox.getItems().add("NONRES MUNICIPAL/RES EDUCATION");
        assessmentClassComboBox.getItems().add("COMMERCIAL");
        assessmentClassComboBox.getItems().add("none");
        cancel_button.setDisable(true); //hide cancel button
        export_button.setDisable(true);

        // format the tableView
        formatTable();
    }

    /**
     * sets ifStopPressed = true so that the backround thread stops running
     */
    @FXML
    protected void onStopButtonClick(){
        ifStopPressed = true;
    }


    /**
     * helper function used to format the tableView correctly
     */
    private void formatTable() {

        tableViewItem.setItems(propertyList);

        col_account.setCellValueFactory(new PropertyValueFactory<>("account"));
        assessedValue.setCellValueFactory(new PropertyValueFactory<>("assessedValue"));

        // set the format for the number
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        assessedValue.setStyle("-fx-alignment: CENTER-RIGHT;");
        assessedValue.setCellFactory(tc -> new TableCell<>() {
            protected void updateItem(Integer value, boolean empty) {
                super.updateItem(value, empty);
                currencyFormat.setMaximumFractionDigits(2);
                setText(empty ? "" : currencyFormat.format(value));
            }
        });

        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        assessmentClass.setCellValueFactory(new PropertyValueFactory<>("assessmentClass"));
        neighbourhood.setCellValueFactory(new PropertyValueFactory<>("neighbourhood"));
        location.setCellValueFactory(new PropertyValueFactory<>("location"));
    }

    /**
     * gathers row information when row is double-clicked and allows for the information to
     * be passed into the opened neighbourhood info window
     */
    private void onDoubleClickRow() {
        tableViewItem.setRowFactory(tv -> {
            TableRow<TableViewObject> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (! row.isEmpty() && event.getButton()== MouseButton.PRIMARY
                        && event.getClickCount() == 2) {

                    TableViewObject clickedRow = row.getItem();

                    // pass information to next stage with singleton pattern
                    PropertyHolder holder = PropertyHolder.getInstance();
                    holder.setProperty(clickedRow);
                    holder.setImportedPropertyAssessments(importedPropertyAssessments);
                    FXMLLoader fxmlLoader = new FXMLLoader(PropertyAssessmentApplication.class.getResource("property-info-view.fxml"));
                    Scene scene = null;
                    try {
                        scene = new Scene(fxmlLoader.load(), 528, 873);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Stage nhStage = new Stage();
                    nhStage.setTitle("Neighbourhood Information");
                    nhStage.setScene(scene);
                    nhStage.initModality(Modality.NONE);
                    nhStage.show();
                    printRow(clickedRow);
                }
            });
            return row;
        });
    }

    private void printRow(TableViewObject item) {
        System.out.println(item.getAddress());
    }

    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PropertyAssessmentApplication.class.getResource("property-info-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1500, 1000);
        stage.setTitle("Neighbourhood Information");
        stage.setScene(scene);
        stage.show();
    }



    /**
     * method for when Read Data button is clicked, chooses which data source to use for the application shows information
     * popup if no data was selected
     */
    @FXML
    protected void onReadDataButtonClick() {
        ifStopPressed = false;
        // clears what's currently in the tableView
        tableViewItem.getItems().clear();
        assessmentClassComboBox.getSelectionModel().clearSelection();
        accountNumberTextField.clear();
        addressTextField.clear();
        neighbourhoodTextField.clear();
        minValueTextField.clear();
        maxValueTextField.clear();
        onDoubleClickRow();

        // gets the index of the sourceComboBox
        int index = sourceComboBox.getSelectionModel().getSelectedIndex();


        importedPropertyAssessments = new PropertyAssessments();
        // CSV File
        if (index == 3) {
            PropertyAssessmentDAO propertyAssessmentsListDAO = new CsvPropertyAssessmentDAO();
            importedPropertyAssessments = propertyAssessmentsListDAO.getAll();
            populateTable();
            hasDataRead = true;
            searchPropertyAssessments = importedPropertyAssessments.getPropertyList();


        } else if (index == 0) {
            // Edmonton's Open Data Portal (First 1,000 entries)
            ApiPropertyAssessmentDAO propertyAssessmentsListDAO = new ApiPropertyAssessmentDAO();
            propertyAssessmentsListDAO.setOffsetSize(1000);
            importedPropertyAssessments =  propertyAssessmentsListDAO.getLess();
            populateTable();
            hasDataRead = true;
            searchPropertyAssessments = importedPropertyAssessments.getPropertyList();

        } else if (index == 1) {
            //Edmonton's Open Data Portal All Data (WARNING LONG LOAD TIME)
            // formats all the buttons
            readDataButton.setDisable(true);
            cancel_button.setDisable(false);
            cancel_button.setText("Stop");
            searchButton.setDisable(true);
            searchButton.setText("Search - disabled");
            resetButton.setDisable(true);
            LoadDataListener();
            hasDataRead = true;
            searchPropertyAssessments = importedPropertyAssessments.getPropertyList();

        } else {
            // displays info popup if no source was selected
            showPopup("Please select a source");
        }
    }

    private void LoadDataListener() {

            propertyList.clear();
            importedPropertyAssessments = new PropertyAssessments();

            // Create a background thread to run a long-running task of retrieving all data
            Thread retrieveThread = new Thread(new RetrieveTask());
            retrieveThread.setDaemon(true);
            retrieveThread.start();

    }

    /**
     * this class runs the API call in a parallel thread so that the application does not freeze
     */
    private class RetrieveTask extends Task<Void> {
        private final ApiPropertyAssessmentDAO dao;

        RetrieveTask() {
            dao = new ApiPropertyAssessmentDAO();
        }

        @Override
        protected Void call() {

            int limit = 10000;
            int offset = 0;
            PropertyAssessments assessedValueList = dao.getAllConcurrent(limit, offset);

            // Retrieve 1000 rows at a time until all rows are retrieved
            while (assessedValueList.getPropertyList().size() > 0) {
                if (isCancelled() ) break;
                PropertyAssessments propertyAssessmentsChunk = assessedValueList;
                Platform.runLater(() -> {

                    // We add subsets of data to the table (limit rows at a time)
                    // fills the tableView with the propertyList
                    for (PropertyAssessment prop : propertyAssessmentsChunk.getPropertyList()){
                        propertyList.add(new TableViewObject(prop));
                    }
                    importedPropertyAssessments.combine(propertyAssessmentsChunk);
                    readDataButton.setText("Read Data: Entries Read: " + importedPropertyAssessments.getPropertyList().size());
                });
                if (ifStopPressed) break;
                // Retrieve the next batch
                offset += limit;
                assessedValueList = dao.getAllConcurrent(limit, offset);
            }

            Platform.runLater(() -> readDataButton.setDisable(false));
            Platform.runLater(() -> cancel_button.setDisable(true));
            Platform.runLater(() -> searchButton.setDisable(false));
            Platform.runLater(() -> resetButton.setDisable(false));
            Platform.runLater(() -> searchButton.setText("Search"));
            Platform.runLater(() -> resetButton.setDisable(false));
            Platform.runLater(() -> cancel_button.setText("Finished"));
            return null;
        }
    }

    /**
     * method for when reset button is clicked
     * it resets all the selected search fields and repopulates that tableView with all the source data
     */
    @FXML
    protected void onResetButtonClick(){

        // information popup that states that there is no data to reset to and returns
        if (!hasDataRead){
            showPopup("No data read to reset");
            return;
        }

        // clears all the fields
        tableViewItem.getItems().clear();
        assessmentClassComboBox.getSelectionModel().clearSelection();
        accountNumberTextField.clear();
        addressTextField.clear();
        neighbourhoodTextField.clear();
        minValueTextField.clear();
        maxValueTextField.clear();
        kilometer_text_field.clear();
        lat_text_field.clear();
        long_text_field.clear();
        has_garage.setSelected(false);
        export_button.setDisable(true);
        searchPropertyAssessments = new ArrayList<>();

        // repopulates the table with the source data
        populateTable();
    }

    /**
     * helper function that populates the tableView with the source data
     */
    private void populateTable(){

        // fills the tableView with the propertyList
        for (PropertyAssessment prop : importedPropertyAssessments.getPropertyList()){
            propertyList.add(new TableViewObject(prop));
        }
    }

    /**
     * When the Export button is clicked it will launch this function that will export the search results to a scv file
     * in the application folder called searchResults.csv
     */
    @FXML
    protected void onExportClick() throws IOException {

        String CscFilename = "searchResults.csv";
        CSVWriter writer = new CSVWriter(new FileWriter(CscFilename));
        String[] str = {"Account Number", "Address", "Assessed Value", "Has Garage", "Location", "Neighbourhood", "Assessment Class"};
        writer.writeNext(str);

        for (PropertyAssessment prop : searchPropertyAssessments){
            str = new String[]{ Integer.toString(prop.getAccountNumber()),
            prop.getAddress().toString(),
            Integer.toString(prop.assessedValue()),
            Boolean.toString(prop.isHasGarage()),
            prop.getLocation().toString(),
            prop.getNeighbourhood().toString(),
            prop.getAssessmentClass().toString()};
            writer.writeNext(str);
        }
        writer.close();
    }

    /**
     * method for when search button is clicked
     * this method validates that the search parameters are valid and then searches the data source
     */
    @FXML
    protected void onSearchButtonClick(){
        // information popup that states that there is no data to search

        if (!hasDataRead){
            showPopup("Please select a source to search data");
            return;
        }

        // validates the user input for the search
        String[] validatedInput = validateSearch();
        if  (validatedInput[0] == null) return;

        // populates the tableView with the validates search parameters
        populateSearchResults(validatedInput);
        search_results.setText("There were: " + searchPropertyAssessments.size() + " Results");
    }

    /**
     * helper function for onSearchButtonClick that uses a validated string array as an input and populates the tableView
     * with the search results
     * returns a validated string array if valid and if
     * @param searchParameters validated sting array of user inputs
     */
    private void populateSearchResults(String[] searchParameters){
        //clears the current tableView
        tableViewItem.getItems().clear();

        Predicate<PropertyAssessment> predicateAccountNumber;
        Predicate<PropertyAssessment> predicateAddress;
        Predicate<PropertyAssessment> predicateNeighbourhood;
        Predicate<PropertyAssessment> predicateAssessmentClass;
        Predicate<PropertyAssessment> predicateMinValue;
        Predicate<PropertyAssessment> predicateMaxValue;
        Predicate<PropertyAssessment> predicateRadiusSearch;
        Predicate<PropertyAssessment> predicateHasGarage;


        if(searchParameters[1].equals("")) predicateAccountNumber = a -> true;
        else predicateAccountNumber = a -> String.valueOf(a.getAccountNumber()).contains(searchParameters[1]);

        if(searchParameters[2].equals("")) predicateAddress = a -> true;
        else predicateAddress = a -> a.getAddress().toString().toUpperCase().contains(searchParameters[2].toUpperCase());

        if(searchParameters[3].equals("")) predicateNeighbourhood = a -> true;
        else predicateNeighbourhood = a -> a.getNeighbourhood().toString().toUpperCase().contains(searchParameters[3].toUpperCase());

        if(searchParameters[4].equals("")) predicateAssessmentClass = a -> true;
        else predicateAssessmentClass = a -> a.getAssessmentClass().toString().toUpperCase().contains(searchParameters[4].toUpperCase());

        if(searchParameters[5].equals("")) predicateMinValue = a -> true;
        else predicateMinValue = a -> a.getAssessedValue() >= Integer.parseInt(searchParameters[5]);

        if(searchParameters[6].equals("")) predicateMaxValue = a -> true;
        else predicateMaxValue = a -> a.getAssessedValue() <= Integer.parseInt(searchParameters[6]);

        if(searchParameters[7].equals("")) predicateRadiusSearch = a -> true;
        else {
            predicateRadiusSearch = (a)->

                    distanceBetweenTwoPoints(Double.parseDouble(searchParameters[8]),
                            Double.parseDouble(searchParameters[9]),
                            a.getLat(),
                            a.getLong())
                      <= Double.parseDouble(searchParameters[7]);
        }
        if (!has_garage.isSelected()) predicateHasGarage = a -> true;
        else{
            predicateHasGarage = a -> a.isHasGarage();
        }

        ArrayList<PropertyAssessment> tempPropertyList = (ArrayList<PropertyAssessment>) importedPropertyAssessments.getPropertyList().stream()
                .filter(predicateAccountNumber)
                .filter(predicateAddress)
                .filter(predicateNeighbourhood)
                .filter(predicateAssessmentClass)
                .filter(predicateMinValue)
                .filter(predicateMaxValue)
                .filter(predicateRadiusSearch)
                .filter(predicateHasGarage)
                .collect(Collectors.toList());


        for (PropertyAssessment prop : tempPropertyList){
            this.propertyList.add(new TableViewObject(prop));
        }
        searchPropertyAssessments= tempPropertyList;
        export_button.setDisable(false);
    }

    /**
     * this helper function for onSearchButtonClick validates that the user input was incorrect and returns a
     * String array of the validated inputs, will return a nul in the 0 index if the
     * @return the validated string array
     */
    private String[] validateSearch(){

        String[] validatedInput = new String[10];
        validatedInput[0] = "empty search";

        // Validates that the Account number is an int, if not an int returns displays informative popup and returns null
        // if user field was empty saves it as ""
        try{
            if (accountNumberTextField.getText().equals("")) {validatedInput[1] = "";}
            else {
                validatedInput[1] = String.valueOf(Integer.parseInt(accountNumberTextField.getText()));
                validatedInput[0]="not null";
            }

        } catch (NumberFormatException e){
            validatedInput[0] = null;
            showPopup("Please enter valid account number");
            return validatedInput;
        }

        //saves the addressTextField into string array
        // if user field was empty saves it as ""
        if (addressTextField.getText().equals("")) {validatedInput[2] = "";}
        else {
            validatedInput[2] = addressTextField.getText();
            validatedInput[0]="not null";
        }

        //saves the neighbourhoodTextField into string array
        // if user field was empty saves it as ""
        if (neighbourhoodTextField.getText().equals("")) {validatedInput[3] = "";}
        else {
            validatedInput[3] = neighbourhoodTextField.getText();
            validatedInput[0]="not null";
        }

        // saves the selection of the assessmentClassComboBox and saves it to teh sting array
        if (assessmentClassComboBox.getSelectionModel().isEmpty()){validatedInput[4]= "";}
        else {
            if (assessmentClassComboBox.getValue().equals("none")){
                assessmentClassComboBox.getSelectionModel().clearSelection();
                validatedInput[4]= "";

            } else {
                validatedInput[4] = assessmentClassComboBox.getValue();
                validatedInput[0] = "not null";
            }
        }

        // Validates that the min value is an int, if not an int returns displays informative popup and returns null
        // if user field was empty saves it as ""
        try{
            if (minValueTextField.getText().equals("")) {validatedInput[5] = "";}
            else {
                validatedInput[5] = String.valueOf(Integer.parseInt(minValueTextField.getText()));
                validatedInput[0]="not null";
            }

        } catch (NumberFormatException e) {
            validatedInput[0] = null;

            showPopup("Please enter valid Min Value");
            return validatedInput;
        }

        // Validates that the max value is an int, if not an int returns displays informative popup and returns null
        // if user field was empty saves it as ""
        try{
            if (maxValueTextField.getText().equals("")) {validatedInput[6] = "";}
            else {
                validatedInput[6] = String.valueOf(Integer.parseInt(maxValueTextField.getText()));
                validatedInput[0]="not null";
            }

        } catch (NumberFormatException e) {
            validatedInput[0] = null;
            showPopup("Please enter valid Max Value");
            return validatedInput;
        }

       // validate the radius search class
        try{
            if(kilometer_text_field.getText().equals("")
                    && lat_text_field.getText().equals("")
                    && long_text_field.getText().equals("")){
                validatedInput[7] = "";
                validatedInput[8] = "";
                validatedInput[9] = "";

            } else if (kilometer_text_field.getText().equals("")
                    || lat_text_field.getText().equals("")
                    || long_text_field.getText().equals("")){
                validatedInput[0] = null;
                showPopup("Missing value for Km radius, or Longitude. or Latitude");
                return validatedInput;
            } else {
                validatedInput[7] = String.valueOf(Double.parseDouble(kilometer_text_field.getText()));
                validatedInput[8] = String.valueOf(Double.parseDouble(lat_text_field.getText()));
                validatedInput[9] = String.valueOf(Double.parseDouble(long_text_field.getText()));
                validatedInput[0]="not null";
            }
        } catch (NumberFormatException e){
            validatedInput[0] = null;
            showPopup("Please enter valid Km radius, or Longitude. or Latitude");
            return validatedInput;
        }

       // shows popup message if no search parameters were entered
       if (validatedInput[0].equals("empty search")) {
           validatedInput[0] = null;
           showPopup("No search parameters entered");
       }


        return validatedInput;
    }


    /**
     * this function returns the distance between two points on a map
     * @param lat1 latitude of point 1
     * @param long1 longitude of point 1
     * @param lat2 latitude of point 2
     * @param long2 longitude of point 2
     * @return the distance in KM
     */
    public static double distanceBetweenTwoPoints(double lat1, double long1, double lat2, double long2) {
        double deltaLat = Math.toRadians(lat2 - lat1);
        double deltaLong = Math.toRadians(long2 - long1);
        lat1 = Math.toRadians(lat1);
        lat2 = Math.toRadians(lat2);

        double a = Math.pow(Math.sin(deltaLat / 2),2) + Math.pow(Math.sin(deltaLong / 2),2) * Math.cos(lat1) * Math.cos(lat2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return 6372.8 * c;
    }

    /**
     * helper function that displays a popup information Alert
     * @param popupMessage string message to display
     */
    public void showPopup (String popupMessage){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(popupMessage);
        alert.show();
    }
}