package lk.royal.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.royal.dto.BankAccountDTO;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AccountDetailsController implements Initializable {

    @FXML
    private AnchorPane root;

    @FXML
    private Label lblName;

    @FXML
    private Label lblAccountType;

    @FXML
    private Label lblAmount;

    @FXML
    private Label lblAccno;

    @FXML
    private JFXButton btnReturnCard;

    @FXML
    void btnHomeOnAction(ActionEvent event) throws IOException {
        redirect("/lk/royal/view/DashBoard.fxml");
    }

    @FXML
    void btnReturnCardOnAction(ActionEvent event) throws IOException {
        redirect("/lk/royal/view/login.fxml");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        BankAccountDTO userAccount = LoginController.userAccount;

        String fullname = userAccount.getClientDTO().getFname() + " " + userAccount.getClientDTO().getMname() + " " + userAccount.getClientDTO().getLname();
        String accountType = userAccount.getAccountType();
        double amount = userAccount.getAmount();
        String accountNumber = userAccount.getAccountNumber();

        lblName.setText("Mr " + fullname);
        lblAccountType.setText(accountType);
        lblAccno.setText(accountNumber);
        lblAmount.setText("Rs " + amount + "0");
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
}