package TresEnLinea.controller;

import TresEnLinea.Jugador;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Paint;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML private ToggleButton casella00, casella10, casella20, casella01, casella11, casella21, casella02, casella12, casella22;

    @FXML private TextField nomJugador1, nomJugador2;

    @FXML private MenuItem menuItemReinciar, menuItemTancar, menuItemFosc, menuItemClar, menuItemClassificacio, menuItemGuia, menuItemSobre;

    @FXML private Label tornDisplay;

    @FXML private Button botoNovaPartida;

    private ArrayList<ToggleButton> llistaCaselles = new ArrayList<>();



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        nomJugador1.setText("Nom Jugador 1");
        nomJugador2.setText("Nom Jugador 2");

        llistaCaselles.add(casella00);
        llistaCaselles.add(casella10);
        llistaCaselles.add(casella20);
        llistaCaselles.add(casella01);
        llistaCaselles.add(casella11);
        llistaCaselles.add(casella21);
        llistaCaselles.add(casella02);
        llistaCaselles.add(casella12);
        llistaCaselles.add(casella22);

        netejarCaselles();
    }

    int torn = 0;
    int[][] casella = new int[3][3];
    String colorJ1 = "#00bf1d";
    Paint paintJ1 = Paint.valueOf(colorJ1);
    String colorJ2 = "#0095eb";
    Paint paintJ2 = Paint.valueOf(colorJ2);

    public void tirada(ActionEvent actionEvent) {
        ToggleButton b = (ToggleButton) actionEvent.getSource();

        int i = Integer.parseInt(b.getId().substring(7,8));
        int x = Integer.parseInt(b.getId().substring(8,9));

        if (torn %2 == 0){
            if (casella[i][x] == 1 || casella[i][x] == 2) {
                b.setSelected(true);
            } else {
                b.setText("O");
                casella[i][x] = 1;
            }
            tornDisplay.setText("Torn del jugador 1");
            tornDisplay.setTextFill(paintJ1);

        } else {
            if (casella[i][x] == 1 || casella[i][x] == 2) {
                b.setSelected(true);
            } else {
                b.setText("X");
                casella[i][x] = 2;
            }
            tornDisplay.setText("Torn del jugador 2");
            tornDisplay.setTextFill(paintJ2);
        }

        winCondition();
        torn++;
    }

    private void tiradaCPU() {
        boolean posat = false;

        while (!posat) {
            ToggleButton b = llistaCaselles.get((int) (Math.random() * 9));
            int i = Integer.parseInt(b.getId().substring(7,8));
            int x = Integer.parseInt(b.getId().substring(8,9));

            if (torn %2 == 0) {
                if (casella[i][x] == 1 || casella[i][x] == 2) {
                    b.setSelected(true);
                } else {
                    b.setText("O");
                    casella[i][x] = 1;
                }
                tornDisplay.setText("Torn del jugador 1");
                tornDisplay.setTextFill(paintJ1);
                posat = true;
            } else {
                if (casella[i][x] == 1 || casella[i][x] == 2) {
                    b.setSelected(true);
                } else {
                    b.setText("X");
                    casella[i][x] = 2;
                }
                tornDisplay.setText("Torn del jugador 2");
                tornDisplay.setTextFill(paintJ2);
            }
        }
    }

    public void startNovaPartida(ActionEvent actionEvent) {

        Jugador j1 = new Jugador(nomJugador1.toString());

        Jugador j2 = new Jugador(nomJugador2.toString());

        nomJugador1.setDisable(true);
        nomJugador2.setDisable(true);

        if (j1.equals("") || j2.equals("")) {
//            tiradaCPU();

        }

        torn = 0;

        decidirTorn();

        netejarCaselles();
    }

    public void winCondition() {
        boolean winJ1 = false;
        boolean winJ2 = false;
        boolean empat = false;

        if (casella[0][0] + casella[0][1] + casella[0][2] == 3 ||
                casella[1][0] + casella[1][1] + casella[1][2] == 3 ||
                casella[2][0] + casella[2][1] + casella[2][2] == 3 ||
                casella[0][0] + casella[1][0] + casella[2][0] == 3 ||
                casella[0][1] + casella[1][1] + casella[2][1] == 3 ||
                casella[0][2] + casella[1][2] + casella[2][2] == 3 ||
                casella[0][0] + casella[1][1] + casella[2][2] == 3 ||
                casella[0][2] + casella[1][1] + casella[2][0] == 3 ) {
            winJ1 = true;
        } else if (casella[0][0] + casella[0][1] + casella[0][2] == 6 ||
                casella[1][0] + casella[1][1] + casella[1][2] == 6 ||
                casella[2][0] + casella[2][1] + casella[2][2] == 6 ||
                casella[0][0] + casella[1][0] + casella[2][0] == 6 ||
                casella[0][1] + casella[1][1] + casella[2][1] == 6 ||
                casella[0][2] + casella[1][2] + casella[2][2] == 6 ||
                casella[0][0] + casella[1][1] + casella[2][2] == 6 ||
                casella[0][2] + casella[1][1] + casella[2][0] == 6 ) {
            winJ2 = true;
        }

        if (winJ1){
            tornDisplay.setText(nomJugador1.getText() + " ha guanyat!");
            nomJugador1.setDisable(false);
            nomJugador2.setDisable(false);
        } else if (winJ2) {
            tornDisplay.setText(nomJugador2.getText() + " ha guanyat!");
            nomJugador1.setDisable(false);
            nomJugador2.setDisable(false);
        }
    }

    public void decidirTorn() {
        int primer = (int) (Math.random() * 2 + 1);

        if (primer == 1) tornDisplay.setText("Torn del jugador 1");
        else if (primer == 2) tornDisplay.setText("Torn del jugador 2");
    }

    public void netejarCaselles() {

        for (int i = 0; i < casella.length; i++) {
            for (int j = 0; j < casella.length; j++) {
                casella[i][j] = 9;
            }
        }

        casella00.setText("");
        casella00.setSelected(false);
        casella10.setText("");
        casella10.setSelected(false);
        casella20.setText("");
        casella20.setSelected(false);
        casella01.setText("");
        casella01.setSelected(false);
        casella11.setText("");
        casella11.setSelected(false);
        casella21.setText("");
        casella21.setSelected(false);
        casella02.setText("");
        casella02.setSelected(false);
        casella12.setText("");
        casella12.setSelected(false);
        casella22.setText("");
        casella22.setSelected(false);
    }
}
