module ru.smackmbu.mynotepad {
    requires javafx.controls;
    requires javafx.fxml;


    opens ru.smackmbu.mynotepad to javafx.fxml;
    exports ru.smackmbu.mynotepad;
}