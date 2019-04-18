package lk.royal.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class WithdrawAmountController {

    @FXML
    private AnchorPane root;

    @FXML
    private TextField txtAmount;

    @FXML
    private JFXButton btnProcess;

    @FXML
    void btnProcessOnAction(ActionEvent event) throws IOException {

        String amounttxt = txtAmount.getText();
        Double amount = new Double(amounttxt);
        WithdrawController.withdrawamount = amount;
        redirect("/lk/royal/view/Withdrawprocess.fxml");

    }
    @FXML
    void btnHomeOnAction(ActionEvent event) throws IOException {
        redirect("/lk/royal/view/DashBoard.fxml");
    }

    @FXML
    void btnRetunOnAction(ActionEvent event) throws IOException {
        redirect("/lk/royal/view/login.fxml");
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
