import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;

/**
 * Created by Niels on 27/03/2015.
 */
public class mainController implements Initializable {

    @FXML private RadioButton decrypt;
    @FXML private RadioButton encrypt;
    @FXML private TableView filesView;
    @FXML private Button addFiles;
    @FXML private Button upload;
    @FXML private Button cancel;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        addFiles.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                handleAddFiles();
            }
        });
        upload.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                handleUpload();
            }
        });
        cancel.setOnAction(new EventHandler<javafx.event.ActionEvent>() {
            @Override
            public void handle(javafx.event.ActionEvent event) {
                handleCancel();
            }
        });
    }

    public void handleAddFiles() {
        final FileChooser fileChooser = new FileChooser();
        System.out.println("addFiles");
        Stage stage = (Stage) addFiles.getScene().getWindow();
        List<File> list = fileChooser.showOpenMultipleDialog(stage);
        if (list !=null) {
            ObservableList<FileFormaty> data = filesView.getItems();

            for (File file : list) {
                FileFormaty fileFormat = new FileFormaty(file);
                if (checkAlreadyInTable(fileFormat)) {
                    data.add(fileFormat);
                }
            }
        }
    }

    private boolean checkAlreadyInTable (FileFormaty fileFormaty) {
        ObservableList<FileFormaty> data = filesView.getItems();

        for (FileFormaty file : data) {
            if(file.getFile().equals(fileFormaty.getFile())){
                System.out.println("same");
                return false;
            }
        }
        System.out.println("diff");
        return true;
    }

    public void handleUpload () {
        System.out.println("upload");
    }

    public void handleCancel () {
        System.out.println("cancel");
    }
}
