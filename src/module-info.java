module Movie_Browser {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive java.sql;  // Added 'transitive' here
    
    opens application to javafx.fxml;
    exports application;
}