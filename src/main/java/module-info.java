module com.example.socialnetworkui {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires java.sql;

    opens com.example.socialnetworkui to javafx.fxml;
    exports com.example.socialnetworkui;
    exports com.example.socialnetworkui.UI;
    opens com.example.socialnetworkui.UI to javafx.fxml;
}