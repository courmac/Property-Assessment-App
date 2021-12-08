/**
 * Student's Name: Orest Dushkevich
 * Milestone #2
 * CMPT 305 LAB X02L Fall 2021
 * Instructor's Name: Indratmo Indratmo
 *
 * Purpose:
 * This Application is the launch window that allows the user to view and search the city of edmonton property assessments,
 * either by viewing a downloaded csv file or directly from the city of edmonton API
 *
 */
package ca.macewan.milestone_3;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;



public class PropertyAssessmentApplication extends Application {

    /**
     * creates the application window and uses the .fxml layout file to draw the application window
     * @param stage
     * @throws IOException
     */
    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(PropertyAssessmentApplication.class.getResource("property-assessment-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1500, 1000);
        stage.setTitle("Edmonton Property Assessments");
        stage.setScene(scene);
        stage.show();


    }

    public static void main(String[] args) {
        launch();
    }

}