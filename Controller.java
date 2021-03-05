package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField loginLoginFld;

    @FXML
    private TextField loginPasswordFld;

    @FXML
    private Button loginOkBtn;

    @FXML
    void initialize() throws SQLException, ClassNotFoundException {
        DBAccessor accessor = new DBAccessor("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/pets", "root", "");
        EventHandler<ActionEvent> buttonHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                String login = loginLoginFld.getText();
                String password = loginPasswordFld.getText();
                String role = null;
                try {
                    role = accessor.login(login, password);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                if (role != null){
                    try {
                        accessor.setOSData();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    switch (role){
                        case "директор":
                            Parent root;
                            try {
                                root = FXMLLoader.load(getClass().getResource("director.fxml"));
                                Stage stage = new Stage();
                                stage.setTitle("Директор");
                                stage.setScene(new Scene(root, 800, 700));
                                stage.show();
                                ((Node)(event.getSource())).getScene().getWindow().hide();
                            }
                            catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case "менеджер":
                            try {
                                root = FXMLLoader.load(getClass().getResource("manager.fxml"));
                                Stage stage = new Stage();
                                stage.setTitle("Менеджер");
                                stage.setScene(new Scene(root, 800, 700));
                                stage.show();
                                ((Node)(event.getSource())).getScene().getWindow().hide();
                            }
                            catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case "главврач":
                        case "врач":
                            try {
                                root = FXMLLoader.load(getClass().getResource("doctor.fxml"));
                                Stage stage = new Stage();
                                stage.setTitle("Доктор");
                                stage.setScene(new Scene(root, 800, 700));
                                stage.show();
                                ((Node)(event.getSource())).getScene().getWindow().hide();
                            }
                            catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                        case "уборщик":
                            try {
                                root = FXMLLoader.load(getClass().getResource("cleaner.fxml"));
                                Stage stage = new Stage();
                                stage.setTitle("Уборщик");
                                stage.setScene(new Scene(root, 800, 500));
                                stage.show();
                                ((Node)(event.getSource())).getScene().getWindow().hide();
                            }
                            catch (IOException e) {
                                e.printStackTrace();
                            }
                            break;
                    }
                }
                event.consume();
            }
        };
        loginOkBtn.setOnAction(buttonHandler);
    }
}