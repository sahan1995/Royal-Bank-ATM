package lk.royal.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.ObjectMapper;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.body.RequestBodyEntity;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.royal.dto.BankAccountDTO;
import lk.royal.dto.WidthdrawDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

public class WidthdrawprocessController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private Label lblmsg;


    @FXML
    void btnNoClick(ActionEvent event) throws IOException {
        redirect("/lk/royal/view/Withdraw.fxml");
    }

    @FXML
    void btnYesClick(ActionEvent event) throws UnirestException, IOException {
        double amount = WithdrawController.withdrawamount;;
        BankAccountDTO userAccount = LoginController.userAccount;

        if (amount > userAccount.getAmount()) {
            Optional<ButtonType> result = new Alert(Alert.AlertType.ERROR, "Insufficient Money").showAndWait();
            if (result.get() == ButtonType.OK) {
                redirect("/lk/royal/view/DashBoard.fxml");
                return;
            }

            return;
        }


        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
        Date date = new Date(System.currentTimeMillis());
        String dateandtime = formatter.format(date);
        String name = userAccount.getClientDTO().getFname() + " " + userAccount.getClientDTO().getLname();
        WidthdrawDTO widthdrawDTO = new WidthdrawDTO(dateandtime, amount, "ATM Withdraw", name);
        widthdrawDTO.setBankAccountDTO(userAccount);


        try{
            widthdraw(widthdrawDTO,amount,userAccount,"http://192.168.1.101:8082/api/v1/");
        }catch (Exception e){
            widthdraw(widthdrawDTO,amount,userAccount,"http://192.168.1.101:8083/api/v1/");
        }


//        RestTemplate restTemplate = new RestTemplate();
//        restTemplate.getMessageConverters().add(new MappingJacksonHttpMessageConverter());
//        MappingJacksonHttpMessageConverter mappingJacksonHttpMessageConverter = new MappingJacksonHttpMessageConverter();

//        HttpStatus statusCode = restTemplate.postForEntity("http://192.168.1.101:8082/api/v1/withdraw", widthdrawDTO, null).getStatusCode();
//        System.out.println(statusCode.toString());

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        double withdrawamount = WithdrawController.withdrawamount;
        BankAccountDTO userAccount = LoginController.userAccount;
        lblmsg.setText("Rs " + withdrawamount + " will be Withdraw from Account Number " + userAccount.getAccountNumber());
        configure();
    }

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

    private void redirect(String path) throws IOException {
        Parent root = null;
        root = FXMLLoader.load(this.getClass().getResource(path));
        if (root != null) {
            Scene subScene = new Scene(root);
            Stage primaryStage = (Stage) this.root.getScene().getWindow();
            primaryStage.setScene(subScene);
            primaryStage.centerOnScreen();

        }
    }
    @FXML
    void btnHomeOnAction(ActionEvent event) throws IOException {
        redirect("/lk/royal/view/DashBoard.fxml");
    }

    @FXML
    void btnRetunOnAction(ActionEvent event) throws IOException {
        redirect("/lk/royal/view/login.fxml");
    }
    private void widthdraw(WidthdrawDTO widthdrawDTO, Double amount, BankAccountDTO userAccount,String URL) throws IOException, UnirestException {

            Object body =
                    Unirest.post( URL+"withdraw")
                            .header("Content-Type", "application/json")
                            .body(widthdrawDTO).asString();
            Optional<ButtonType> result = new Alert(Alert.AlertType.INFORMATION, "Rs" + amount + " Successfully Withdraw from Account " + userAccount.getAccountNumber()).showAndWait();
            if (result.get() == ButtonType.OK) {
                redirect("/lk/royal/view/DashBoard.fxml");
                return;
            }

    }
}
