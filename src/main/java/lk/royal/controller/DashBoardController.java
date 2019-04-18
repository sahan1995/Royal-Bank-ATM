package lk.royal.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import lk.royal.dto.BankAccountDTO;

import java.io.IOException;

public class DashBoardController {

    @FXML
    private AnchorPane root;

    @FXML
    private JFXButton btnWidthDraw;


    @FXML
    void btnWidthDrawOnAction(ActionEvent event) {
        try {
            redirect("/lk/royal/view/Withdraw.fxml");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public DashBoardController() {
        BankAccountDTO accNo = LoginController.userAccount;
        System.out.println(accNo);
    }

    @FXML
    void btnRetunOnAction(ActionEvent event) throws IOException {
        redirect("/lk/royal/view/login.fxml");
    }
    @FXML
    void btnAccountDetailsOnAction(ActionEvent event) throws IOException {
        redirect("/lk/royal/view/AccountDetails.fxml");
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
