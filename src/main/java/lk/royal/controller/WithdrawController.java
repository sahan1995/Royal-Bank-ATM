package lk.royal.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class WithdrawController {

    @FXML
    private AnchorPane root;
    @FXML
    private JFXButton btn500;

    @FXML
    private JFXButton btn1000;

    @FXML
    private JFXButton btn2000;

    @FXML
    private JFXButton btn4000;

    @FXML
    private JFXButton btn3000;

    @FXML
    private JFXButton btnEnterAmount;

    public static double withdrawamount;

    @FXML
    void btn1000Click(ActionEvent event) throws IOException {
            withdrawamount = 1000;

            redirect("/lk/royal/view/Withdrawprocess.fxml");
    }

    @FXML
    void btn2000Click(ActionEvent event) throws IOException {
        withdrawamount = 2000;
        redirect("/lk/royal/view/Withdrawprocess.fxml");
    }

    @FXML
    void btn3000Click(ActionEvent event) throws IOException {
        withdrawamount = 3000;
        redirect("/lk/royal/view/Withdrawprocess.fxml");
    }

    @FXML
    void btn500Click(ActionEvent event) throws IOException {
        withdrawamount = 500;
        redirect("/lk/royal/view/Withdrawprocess.fxml");
    }
    @FXML
    void btn4000Click(ActionEvent event) throws IOException {
        withdrawamount = 4000;
        redirect("/lk/royal/view/Withdrawprocess.fxml");
    }
    @FXML
    void btnEnterAmount(ActionEvent event) throws IOException {
        redirect("/lk/royal/view/WithdrawAmount.fxml");

    }


    @FXML
    void btnRetunOnAction(ActionEvent event) throws IOException {
        redirect("/lk/royal/view/login.fxml");
    }
    @FXML
    void btnHomeOnAction(ActionEvent event) throws IOException {
        redirect("/lk/royal/view/DashBoard.fxml");
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
