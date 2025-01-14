package org.example.updatecheckeranddatadownloader;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.concurrent.Task;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.util.stream.Collectors;

import static jdk.xml.internal.SecuritySupport.getClassLoader;

public class MainController implements Initializable {
    
    private boolean hasDownloadedData = false;
    private String downloadUrl = "https://wahapedia.ru/wh40k10ed/Export%20Data%20Specs.xlsx";
    private String tempFolderPath = "src/main/resources/Temp";
    
    @FXML
    private Text lastUpdated;
    @FXML
    private ProgressIndicator progressIndicator;
    @FXML
    private Button checkForNewDataButton;
    @FXML
    private Button downloadNewestDataButton;
    @FXML
    private Text updateAvailabelText;
    @FXML
    private TextArea logTextArea;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            lastUpdated.setVisible(false);
            updateAvailabelText.setVisible(false);
            progressIndicator.setVisible(false);
            
        } catch (Exception e) {
            System.err.println("Error in: \"" + this.getClass().getSimpleName() + "\" in \"initialize\", message: " + e.getMessage());
        }
    }

    @FXML
    public void downloadNewestData(ActionEvent actionEvent) {
        String folderPath = "../Whapedia data";
        Task<Void> main = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try {
                    updateLog("Downloading newest data");
                    String mainCsvPath = downloadFile(downloadUrl, tempFolderPath);
                    updateLog("Downloaded main CSV: " + mainCsvPath);
                    List<String> links = extractLinksFromExcel(mainCsvPath);
                    updateLog("Found links: " + links);
                    for(String link : links) {
                        downloadFile(link, folderPath);
                    }
                    updateLog("Downloaded all files additional files");
                } catch (Exception ex) {
                    updateLog("Failed main task " + ex.getMessage());
                }
                
                return null;
            }
        };

        checkForNewDataButton.setDisable(true);
        downloadNewestDataButton.setDisable(true);

        progressIndicator.setProgress(-1);
        progressIndicator.setVisible(true);

        main.setOnSucceeded(e -> {
            progressIndicator.setVisible(false);
            checkForNewDataButton.setDisable(false);
            downloadNewestDataButton.setDisable(false);
        });

        main.setOnFailed(e -> {
            progressIndicator.setVisible(false);
            checkForNewDataButton.setDisable(false);
            downloadNewestDataButton.setDisable(false);
        });

        new Thread(main).start();
    }

    @FXML
    public void checkForNewData(ActionEvent actionEvent) throws InterruptedException {
        Task<Void> main = new Task<>() {
            @Override
            protected Void call() throws Exception {
                try {
                    updateLog("Checking for new data");
                    String mainCsvPath = downloadFile(downloadUrl, tempFolderPath);
                    updateLog("Downloaded main CSV: " + mainCsvPath);
                    List<String> links = extractLinksFromExcel(mainCsvPath);
                    updateLog("Found links: "+ links.size() +"\n" + links);
                    String lastUpdate = extractDateFromLastUpdate(links);
                    updateLog("Successfully extracted latest update date: " + lastUpdate);
                } catch (Exception ex) {
                    updateLog("Failed main task " + ex.getMessage());
                }
                return null;
            }
        };
        
        checkForNewDataButton.setDisable(true);
        downloadNewestDataButton.setDisable(true);

        progressIndicator.setProgress(-1);
        progressIndicator.setVisible(true);
        
        main.setOnSucceeded(e -> {
            progressIndicator.setVisible(false);
            checkForNewDataButton.setDisable(false);
            downloadNewestDataButton.setDisable(false);
        });
        
        main.setOnFailed(e -> {
            progressIndicator.setVisible(false);
            checkForNewDataButton.setDisable(false);
            downloadNewestDataButton.setDisable(false);
        });
        
        new Thread(main).start();
    }

    private String downloadFile(String fileUrl, String folderPath) throws IOException {
        updateLog("Downloading file: " + fileUrl);
        URL url = new URL(fileUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        connection.setInstanceFollowRedirects(false); // Manually follow redirects

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_MOVED_PERM || responseCode == HttpURLConnection.HTTP_MOVED_TEMP) {
            // Handle redirect manually
            String newUrl = connection.getHeaderField("Location");
            updateLog("Redirecting to: " + newUrl);
            return downloadFile(newUrl, folderPath); // Recursively follow the new URL
        }
        
        if (responseCode != HttpURLConnection.HTTP_OK) {
            updateLog("Failed to download file: " + fileUrl + "\n" + "Response code: " + connection.getResponseCode());
            throw new IOException("Failed to download file: " + fileUrl);
        }

        String fileName = fileUrl.substring(fileUrl.lastIndexOf('/') + 1);
        String outputPath = folderPath + "/" + fileName;

        try (InputStream inputStream = connection.getInputStream();
             FileOutputStream outputStream = new FileOutputStream(outputPath)) {

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
        }
        
        updateLog("Downloaded file: " + outputPath);
        return outputPath;
    }

    public static List<String> extractLinksFromExcel(String filePath) throws IOException {
        List<String> links = new ArrayList<>();
        try (FileInputStream fis = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fis)) {

            Sheet sheet = workbook.getSheetAt(0); // Get the first sheet
            for (Row row : sheet) {
                for (Cell cell : row) {
                    // Check if the cell contains a hyperlink
                    Hyperlink hyperlink = cell.getHyperlink();
                    if (hyperlink != null) {
                        links.add(hyperlink.getAddress()); // Extract the hyperlink address
                    }
                }
            }
        }
        return links;
    }

    private void openLocalCSV(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            try {
                List<String> lines = Files.readAllLines(selectedFile.toPath());
                updateLog("Loaded CSV: \n" + String.join("\n", lines));
            } catch (IOException e) {
                updateLog("Failed to open file: " + e.getMessage());
            }
        }
    }

    private void updateLog(String message) {
        Platform.runLater(() -> logTextArea.appendText(message + "\n\n"));
    }
    
    private URL getResourceFromTempFolder(String fileName) {
        ClassLoader classLoader = getClassLoader();
        return classLoader.getResource(tempFolderPath + "/" + fileName);
    }
    
    private String getLinkToLastUpdate (List <String> links) {
        return links.getLast();
    }
    
    private String extractDateFromLastUpdate(List<String> links) throws IOException {
        String lastLink = getLinkToLastUpdate(links);
        String lastUpdatePath = downloadFile(lastLink, tempFolderPath);
        int dateColumnIndex = 0;

        List<String> dates;

        try (BufferedReader reader = Files.newBufferedReader(Paths.get(lastUpdatePath))) {
            // Skip the header row and process the file
            dates = reader.lines()
                    .skip(1) // Skip header row
                    .map(line -> line.split("\\|")) // Split by pipe
                    .filter(parts -> parts.length > dateColumnIndex) // Ensure column exists
                    .map(parts -> parts[dateColumnIndex].trim()) // Extract date column and trim spaces
                    .collect(Collectors.toList());
        } catch (IOException e) {
            updateLog("Failed to extract date from last update: " + e.getMessage());
            throw e;
        }
        return dates.getFirst();
    }
}