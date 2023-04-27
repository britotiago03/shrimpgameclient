package org.example.ui.controllers;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import javafx.stage.FileChooser;
import org.example.ShrimpGameApp;
import org.example.model.GameResult;

public class DownloadGameDataScreenController {
  private final ShrimpGameApp shrimpGameApp;

  /**
   * Creates a new instance of the {@code JoinGameScreenController} class.
   *
   * @param shrimpGameApp the instance of the main application class
   */
  public DownloadGameDataScreenController(ShrimpGameApp shrimpGameApp) {
    this.shrimpGameApp = shrimpGameApp;
  }

  /**
   * Handles the "Join" button being pressed.
   * Sends a join lobby request to the server and updates the UI accordingly.
   *
   */
  public void handleDownloadGameButton(GameResult gameResult) {
    // Construct the CSV file name using the game name and time finished
    String fileName = gameResult.getName() + ".csv";

    // Prompt the user to select a location to save the CSV file
    FileChooser fileChooser = new FileChooser();
    fileChooser.setTitle("Save CSV File");
    fileChooser.setInitialFileName(fileName);
    File file = fileChooser.showSaveDialog(null);

    if (file != null) {
      try {
        // Open an OutputStreamWriter to write the CSV data to the file using UTF-8 encoding
        OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);

        // Write the CSV data to the file
        for (String line : gameResult.getCsvData()) {
          writer.write(line + "\n");
        }

        // Close the OutputStreamWriter
        writer.close();

        // Open the file explorer to show the saved CSV file
        Desktop.getDesktop().open(file.getParentFile());
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
