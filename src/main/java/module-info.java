module com.khabibullina.snake {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    opens com.khabibullina.snake to javafx.controls;
    exports com.khabibullina.snake;

    opens com.khabibullina.fx.bot to javafx.controls;
    exports com.khabibullina.fx.bot;
}