package lk.royal.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.jfoenix.controls.JFXButton;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.GetRequest;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.royal.dto.BankAccountDTO;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {


    @FXML
    private AnchorPane root;

    @FXML
    private PasswordField txtPin;

    @FXML
    private JFXButton btnLogin;

    public static BankAccountDTO userAccount;


    private String PIN = "";

    @FXML
    void btnLoginAction(ActionEvent event) throws IOException, UnirestException {
        configure();
        PIN = txtPin.getText();

        try {

            BankAccountDTO bankAccountDTO = Unirest.get("http://192.168.1.101:8083/api/v1/atmcards/login/" + PIN).asObject(BankAccountDTO.class).getBody();
            userAccount = bankAccountDTO;
            redirect();
        } catch (Exception e) {

            try {
                BankAccountDTO bankAccountDTO = Unirest.get("http://192.168.1.101:8080/api/v1/atmcards/login/" + PIN).asObject(BankAccountDTO.class).getBody();
                userAccount = bankAccountDTO;
                redirect();
            } catch (Exception e1) {
                e1.printStackTrace();
                new Alert(Alert.AlertType.ERROR, "Authentication Failed !").show();

            }
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }


    private void redirect() throws IOException {
        Parent root = null;
        root = FXMLLoader.load(this.getClass().getResource("/lk/royal/view/DashBoard.fxml"));
        if (root != null) {
            Scene subScene = new Scene(root);
            Stage primaryStage = (Stage) this.root.getScene().getWindow();
            primaryStage.setScene(subScene);
            primaryStage.centerOnScreen();

        }
    }

//    void checkServer(String URL) {
//        try {
//            GetRequest response = Unirest.get(URL + PIN);
//            response.asJson().getStatus();
//        } catch (Exception e) {
//            loginATMURL = "http://192.168.1.101:8080/api/v1/atmcards/login/";
//        }
//    }


    void configure() {
        Unirest.setObjectMapper(new ObjectMapper() {
            private com.fasterxml.jackson.databind.ObjectMapper jacksonObjectMapper
                    = new com.fasterxml.jackson.databind.ObjectMapper();

            public <T> T readValue(String value, Class<T> valueType) {
                try {
                    return jacksonObjectMapper.readValue(value, valueType);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }

            public String writeValue(Object value) {
                try {
                    return jacksonObjectMapper.writeValueAsString(value);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

}
