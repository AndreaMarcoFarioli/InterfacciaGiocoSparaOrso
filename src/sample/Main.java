package sample;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import packetManager.ContentTypes;
import socketManager.EventSocketOriented;
import socketManager.SocketManager;
import socketManager.extensions.MiddlewareExtensionBase64;

import java.io.File;
import java.net.Socket;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        try {
            Socket socket = new Socket("62.11.77.202", 5000);
            SocketManager socketManager = new SocketManager(socket, new MiddlewareExtensionBase64());
            EventSocketOriented eventSocketOriented = new EventSocketOriented(socketManager);
            eventSocketOriented.setContentType(ContentTypes.BASE64);
            socketManager.on((request, response) -> System.out.println(request));
            eventSocketOriented.emit64("connection");
            eventSocketOriented.on("connected", (request, response) -> {
                System.out.println(request);
            });

            FXMLLoader loader = new FXMLLoader(new File("sample.fxml").toURI().toURL());
            System.out.println(new File("sample.fxml").toURI().toURL());
            Controller controller = new Controller(eventSocketOriented);
            loader.setController(controller);
            Parent parent = loader.load();
            primaryStage.setTitle("Spara all'Orso");
            Scene scene = new Scene(parent, 1000, 1000);
            primaryStage.setScene(scene);
            scene.setFill(Color.TRANSPARENT);
            primaryStage.initStyle(StageStyle.TRANSPARENT);
            primaryStage.show();
            System.out.println(primaryStage.getWidth());

            eventSocketOriented.start();
        } catch (Exception e) {
            System.out.println(e);
        }

    }


    public static void main(String[] args) {
        launch(args);
    }
}
