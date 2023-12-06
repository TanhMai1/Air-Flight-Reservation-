package com.example.finalcis;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label LoginLabel;

    @FXML
    private Label RegisterLabel;

    @FXML
    protected void onLoginClick() {LoginLabel.setText("This is a login button");
    }

    @FXML
    protected void onLRegisterClick() {RegisterLabel.setText("This is a register button");
    }
}


