/**
 * Student's Name: Orest Dushkevich
 * Milestone #2
 * CMPT 305 LAB X02L Fall 2021
 * Instructor's Name: Indratmo Indratmo
 *
 * Purpose:
 * This is the controller for the application window for Milestone #2 and contains the functionality  of teh application
 *
 */

package ca.macewan.milestone_3;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PropertyAssessmentController implements Initializable {
    @FXML    private ComboBox sourceComboBox;
    @FXML    private Button readDataButton;
    @FXML    private TextField accountNumberTextField;
    @FXML    private TextField addressTextField;
    @FXML    private TextField neighbourhoodTextField;
    @FXML    private ComboBox assessmentClassComboBox;
    @FXML    private TextField minValueTextField;
    @FXML    private TextField maxValueTextField;
    @FXML    private Button searchButton,resetButton, cancel_button;
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

    PropertyAssessments propertyAssessments;

    /**
     * initializes the window and any of the parameters that can't be stored in a layout file
     *
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        sourceComboBox.getItems().add("CSV File");
        sourceComboBox.getItems().add("Edmonton's Open Data Portal (First 1,000 entries)");
        sourceComboBox.getItems().add("Edmonton's Open Data Portal All Data (WARNING LONG LOAD TIME)");


        // TODO automate this later based on what was returned from the DAO
        assessmentClassComboBox.getItems().add("RESIDENTIAL");
        assessmentClassComboBox.getItems().add("OTHER RESIDENTIAL");
        assessmentClassComboBox.getItems().add("FARMLAND");
        assessmentClassComboBox.getItems().add("NONRES MUNICIPAL/RES EDUCATION");
        assessmentClassComboBox.getItems().add("COMMERCIAL");
        assessmentClassComboBox.getItems().add("none");

        // format the tableView
        formatTable();
    }

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

        // gets the index of the sourceComboBox
        int index = sourceComboBox.getSelectionModel().getSelectedIndex();


        propertyAssessments = new PropertyAssessments();
        // CSV File
        if (index == 0) {
            PropertyAssessmentDAO propertyAssessmentsListDAO = new CsvPropertyAssessmentDAO();
            propertyAssessments = propertyAssessmentsListDAO.getAll();
            populateTable();
            hasDataRead = true;

        } else if (index == 1) {
            // Edmonton's Open Data Portal (First 1,000 entries)
            ApiPropertyAssessmentDAO propertyAssessmentsListDAO = new ApiPropertyAssessmentDAO();
            propertyAssessmentsListDAO.setOffsetSize(1000);
            propertyAssessments =  propertyAssessmentsListDAO.getLess();
            populateTable();
            hasDataRead = true;

        } else if (index == 2) {
            //Edmonton's Open Data Portal All Data (WARNING LONG LOAD TIME)
            //PropertyAssessmentDAO propertyAssessmentsListDAO = new ApiPropertyAssessmentDAO();
            readDataButton.setDisable(true);
            LoadDataListener();
            //populateTable();
            hasDataRead = true;

        } else {
            // displays info popup if no source was selected
            showPopup("Please select a source");
        }
    }

    private void LoadDataListener() {

            propertyList.clear();
            propertyAssessments = new PropertyAssessments();

            // Create a background thread to run a long-running task of retrieving all data
            Thread retrieveThread = new Thread(new RetrieveTask());
            retrieveThread.setDaemon(true);
            retrieveThread.start();

    }

    /**
     * An example of a long-running task.
     * Here, we retrieve all data and update the table view when a subset of data is available.
     * Scroll down to the bottom of the table view to see the running task in the background thread.
     * https://openjfx.io/javadoc/17/javafx.graphics/javafx/concurrent/Task.html
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
            //propertyAssessments.combine(assessedValueList);
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
                    propertyAssessments.combine(propertyAssessmentsChunk);
                    readDataButton.setText("Read Data: Entries Read: " + propertyAssessments.getPropertyList().size());



                });
                if (ifStopPressed) break;
                // Retrieve the next batch
                offset += limit;
                assessedValueList = dao.getAllConcurrent(limit, offset);
            }

            Platform.runLater(() -> readDataButton.setDisable(false));

            //propertyAssessments = assessedValueList;

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

        // repopulates the table with the source data
        populateTable();
    }

    /**
     * helper function that populates the tableView with the source data
     */
    private void populateTable(){

        // fills the tableView with the propertyList
        for (PropertyAssessment prop : propertyAssessments.getPropertyList()){
            propertyList.add(new TableViewObject(prop));
        }
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

        ArrayList<PropertyAssessment> tempPropertyList = (ArrayList<PropertyAssessment>) propertyAssessments.getPropertyList().stream()
                .filter(predicateAccountNumber)
                .filter(predicateAddress)
                .filter(predicateNeighbourhood)
                .filter(predicateAssessmentClass)
                .filter(predicateMinValue)
                .filter(predicateMaxValue)
                .collect(Collectors.toList());


        for (PropertyAssessment prop : tempPropertyList){
            this.propertyList.add(new TableViewObject(prop));
        }
    }

    /**
     * this helper function for onSearchButtonClick validates that the user input was incorrect and returns a
     * String array of the validated inputs, will return a nul in the 0 index if the
     * @return the validated string array
     */
    private String[] validateSearch(){

        String[] validatedInput = new String[7];
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
            if (assessmentClassComboBox.getValue().toString().equals("none")){
                assessmentClassComboBox.getSelectionModel().clearSelection();
                validatedInput[4]= "";

            } else {
                validatedInput[4] = assessmentClassComboBox.getValue().toString();
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

       // shows popup message if no search parameters were entered
       if (validatedInput[0].equals("empty search")) {
           validatedInput[0] = null;
           showPopup("No search parameters entered");
       }


        return validatedInput;
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