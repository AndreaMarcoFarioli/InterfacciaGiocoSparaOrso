package sample;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import packetManager.Request;
import packetManager.Response;
import socketManager.EventSocketOriented;
import socketManager.SocketManager;
import socketManager.utilities.Tuple;

import java.awt.*;
import java.io.IOException;
import java.net.Socket;
import java.util.Arrays;

public class Controller {

    private EventSocketOriented eventSocketOriented;

    public Controller(EventSocketOriented eventSocketOriented){
        try {
            this.eventSocketOriented = eventSocketOriented;
            this.eventSocketOriented.on("endGame", (request, response) -> {
                if(request.getBody("win").equals("true")) {
                    win.setVisible(true);
                }else{
                    lose.setVisible(true);
                }
                System.out.println("xd");
            });
            this.eventSocketOriented.on("connected", (request, response) -> { Platform.runLater(()->{
                nMoveRem.setText("10");
                try {
                    eventSocketOriented.emit64("randomStartPosition");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });});

            setEvents();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // region JavaFX
    @FXML
    private Button up;

    @FXML
    private Button right;

    @FXML
    private Button left;

    @FXML
    private Button down;

    @FXML
    private Button shoot;

    @FXML
    private Pane container;

    @FXML
    private SplitPane tabd1;

    @FXML
    private SplitPane tabd2;

    @FXML
    private SplitPane tabd3;

    @FXML
    private SplitPane tabd4;

    @FXML
    private SplitPane tabc1;

    @FXML
    private SplitPane tabc2;

    @FXML
    private SplitPane tabc3;

    @FXML
    private SplitPane tabc4;

    @FXML
    private SplitPane tabb1;

    @FXML
    private SplitPane tabb2;

    @FXML
    private SplitPane tabb3;

    @FXML
    private SplitPane tabb4;

    @FXML
    private SplitPane taba1;

    @FXML
    private SplitPane taba2;

    @FXML
    private SplitPane taba3;

    @FXML
    private SplitPane taba4;

    @FXML
    private Label munizioni;

    @FXML
    private ImageView player;

    @FXML
    private Label win;

    @FXML
    private Label lose;

    @FXML
    private Label nMoveRem;


    @FXML
    void moveDown(ActionEvent event) {
        try {
            eventSocketOriented.emit64("move", new Tuple<>("direction", "d"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("muovi giu");
    }

    @FXML
    void moveLeft(ActionEvent event) {
        try {
            eventSocketOriented.emit64("move", new Tuple<>("direction", "l"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("muovi sinistra");
    }

    @FXML
    void moveRight(ActionEvent event) {
        try {
            eventSocketOriented.emit64("move", new Tuple<>("direction", "r"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("muovi destra");
    }

    @FXML
    void moveUp(ActionEvent event) {
        try {
            eventSocketOriented.emit64("move", new Tuple<>("direction", "u"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("muovi sopra");
    }

    @FXML
    void takeShot(ActionEvent event) {
        try {
            eventSocketOriented.emit64("shoot");
        } catch (Exception e) {
            e.printStackTrace();
        }



    }

    @FXML
    void closeButton(ActionEvent event){
        try {
            eventSocketOriented.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.exit(1);
    }

    SplitPane setPlayerCell(int x, int y){
        Node node = container.getChildren().get(y*4+x);
        SplitPane pane = (SplitPane)node;
        player.setFitHeight(pane.getHeight());
        player.setFitWidth(pane.getHeight());
        Bounds bounds = node.localToScene(node.getBoundsInLocal());
        player.setX((bounds.getMinX() + bounds.getMaxX())/2 - player.getFitWidth()/2);
        player.setY((bounds.getMinY() + bounds.getMaxY())/2 - player.getFitHeight()/2);
        return pane;
    }





    // endregion

    public void setEvents() throws Exception {
        eventSocketOriented.on("didMove", this::didMove);
        eventSocketOriented.on("didShoot", this::didShoot);
        eventSocketOriented.on("didWin", this::didWin);
    }

    public void didMove(Request request, Response response){
        Platform.runLater(()->{
            String did = request.getBody("did");
            if(did != null){
                if(did.equals("true")){
                    System.out.println(nMoveRem.getText());
                    setPlayerCell(Integer.parseInt(request.getBody("x")), Integer.parseInt(request.getBody("y")));
                    int n = Integer.parseInt(nMoveRem.getText());
                    n--;
                    nMoveRem.setText(Integer.toString(n));
                }
            }
        });
    }

    public void didShoot(Request request, Response response){
        String did = request.getBody("did");
        if(did != null){
            if(did.equals("true")){

            }
        }
    }

    public void didWin(Request request, Response response){
        String did = request.getBody("did");
        if(did != null){
            if(did.equals("true")){

            }
        }
    }



}