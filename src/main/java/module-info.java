module com.example.milestone_2 {
//    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.net.http;
    requires com.opencsv;
    requires javafx.web;
    requires com.google.gson;

    opens ca.macewan.milestone_3 to javafx.fxml;
    exports ca.macewan.milestone_3;
}