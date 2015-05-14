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

import javax.crypto.NoSuchPaddingException;
import java.awt.event.ActionEvent;
import java.io.*;
import java.net.URL;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.ResourceBundle;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * Created by Niels on 27/03/2015.
 */
public class mainController implements Initializable {

    @FXML private TableView filesView;
    @FXML private Button addFiles;
    @FXML private Button upload;
    @FXML private Button cancel;


    private FServer myServer;
    private FClient myClient;
    private Key myPrivateKey = null;
    private Key myPublicKey = null;

    private ArrayList<FileFormaty> inFiles = new ArrayList<FileFormaty>();


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

        getKeys();

        try {
            myServer = new FServer(myPrivateKey,myPublicKey);
            myServer.start();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        }
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
                    inFiles.add(fileFormat);
                }
            }
        }
    }

    private boolean checkAlreadyInTable (FileFormaty fileFormaty) {
        ObservableList<FileFormaty> data = filesView.getItems();

        for (FileFormaty file : data) {
            if(file.getFile().equals(fileFormaty.getFile())){
                return false;
            }
        }
        return true;
    }

    public void handleUpload () {
        System.out.println("upload");
        if (inFiles.size() > 0 ) {
            byte[] input = null;

            input = convertFilesToBytes();


            myClient = new FClient("127.0.0.1", input ,"Encrypted.txt",myPrivateKey,myPublicKey);

            myClient.start();
        }
    }

    private byte[] convertFilesToBytes() {
        byte[] input = null;
        if (inFiles.size() == 1) {
            try {
                input = new byte[(int)inFiles.get(0).getFile().length()];
                FileInputStream fileInputStream = new FileInputStream(inFiles.get(0).getFile());

                fileInputStream.read(input);
                fileInputStream.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            File zip = zipFiles();
            input = new byte[(int)zip.length()];
            try {
                FileInputStream fileInputStream = new FileInputStream(zip);

                fileInputStream.read(input);
                fileInputStream.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }



        return input;
    }

    private File zipFiles() {
        System.out.println("start Zip");
        byte[] buffer = new byte[1024];
        try {
            FileOutputStream fos = new FileOutputStream("zip.zip");
            ZipOutputStream zos = new ZipOutputStream(fos);

            System.out.println("alle bestanden aan zip toevoegen");

            for (FileFormaty file : inFiles) {
                System.out.println(file.getFile().getAbsolutePath());
                ZipEntry ze = new ZipEntry(file.getFile().getAbsolutePath());
                zos.putNextEntry(ze);

                FileInputStream in = new FileInputStream(file.getFile().getAbsolutePath());

                int len;
                while ((len = in.read(buffer)) > 0) {
                    zos.write(buffer,0,len);
                }

                in.close();
            }

            System.out.println("einde zippen");

            zos.closeEntry();;
            zos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return new File("zip.zip");
    }

    private void getKeys() {
        KeyPairGenerator kpgA = null;
        try {
            kpgA = KeyPairGenerator.getInstance("RSA");
            kpgA.initialize(2048);
            KeyPair kpA = kpgA.genKeyPair();
            myPublicKey = kpA.getPublic();
            myPrivateKey = kpA.getPrivate();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

    }

    public void handleCancel () {
        System.out.println("cancel");
    }
}
