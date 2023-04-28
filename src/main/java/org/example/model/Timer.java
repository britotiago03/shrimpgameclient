package org.example.model;

import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.util.Duration;
import org.example.ShrimpGameApp;

/**
 * Represents a timer for the application that counts down seconds during the current round of
 * the game.
 */
public class Timer {
  private int secondsLeft;
  private final ShrimpGameApp shrimpGameApp;
  private Timeline timeline;
  private final int roundNum;

  /**
   * Creates a new instance of {@code Timer}.
   *
   * @param shrimpGameApp the {@code ShrimpGameApp} object used to get the game information.
   * @param timeLabels    a list of labels for displaying time.
   */
  public Timer(ShrimpGameApp shrimpGameApp, List<Label> timeLabels) {
    this.shrimpGameApp = shrimpGameApp;
    this.secondsLeft = shrimpGameApp.getGame().getSettings().getRoundTime();
    this.roundNum = shrimpGameApp.getGame().getCurrentRoundNum();
    this.timeline = new Timeline(new KeyFrame(Duration.seconds(1), e ->
    {
      // Update the time left
      this.secondsLeft--;

      // Update the label with the new time left
      int minutes = this.secondsLeft / 60;
      int seconds = this.secondsLeft % 60;
      String timeLeftStr = String.format("%02d:%02d", minutes, seconds);
      for (Label timeLeftLbl : timeLabels) {
        timeLeftLbl.setText(timeLeftStr);
      }

      if (this.secondsLeft == 30) {
        for (Label timeLeftLbl : timeLabels) {
          timeLeftLbl.getStyleClass().add("low-time-label");
        }
      }
      else if (this.secondsLeft == 20 && !this.shrimpGameApp.getGame().getPlayers().get(
          this.shrimpGameApp.getUser().getName()).hasCaughtShrimp()) {
        this.shrimpGameApp.setScene(this.shrimpGameApp.getCatchShrimpScreen());
      }

      // If the time is up, stop the timeline
      if (this.secondsLeft == 0) {
        this.timeline.stop();
        if (shrimpGameApp.getGame().getCurrentRoundNum() == this.roundNum) {
          try {
            if (!this.shrimpGameApp.getGame().getPlayers().get(
                this.shrimpGameApp.getUser().getName()).hasCaughtShrimp()) {
              int minShrimpKg = this.shrimpGameApp.getGame().getSettings().getMinShrimpKilograms();
              int serverPacketsSize =
                  this.shrimpGameApp.getServerConnection().getServerPackets().size();
              this.shrimpGameApp.getServerConnection().sendCatchShrimpRequest(minShrimpKg);
              String response = "";
              synchronized (this.shrimpGameApp.getServerConnection().getServerPackets()) {
                while (this.shrimpGameApp.getServerConnection().getServerPackets().size()
                       == serverPacketsSize) {
                  try {
                    this.shrimpGameApp.getServerConnection().getServerPackets().wait();
                  }
                  catch (InterruptedException exception) {
                    throw new RuntimeException("Thread was interrupted");
                  }
                }
                response = this.shrimpGameApp.getServerConnection().getNextServerPacket();
              }
              if (!response.equals("CAUGHT_SUCCESSFULLY")) {
                throw new RuntimeException("Failed to catch shrimp.");
              }
            }
          }
          catch (RuntimeException exception) {
            Alert errorDialog = new Alert(Alert.AlertType.ERROR);
            errorDialog.setTitle("Error");
            errorDialog.setHeaderText(null);
            errorDialog.setContentText(exception.getMessage());
            this.shrimpGameApp.addIconToDialog(errorDialog);
            errorDialog.showAndWait();
          }
        }
      }
    }));
    this.timeline.setCycleCount(Timeline.INDEFINITE);
  }

  /**
   * Starts the timeline.
   */
  public void start() {
    this.timeline.play();
  }

  /**
   * Stops the timeline.
   */
  public void stop() {
    this.timeline.stop();
  }

  /**
   * Gets the remaining seconds.
   *
   * @return the seconds remaining.
   */
  public int getSecondsLeft() {
    return secondsLeft;
  }

  /**
   * Sets the remaining seconds.
   *
   * @param secondsLeft the remaining seconds to assign to the timer.
   */
  public void setSecondsLeft(int secondsLeft) {
    this.secondsLeft = secondsLeft;
  }

  /**
   * Checks if the timer is finished counting down.
   *
   * @return {@code true} if there are no seconds remaining, or {@code false} if there is.
   */
  public boolean isFinished() {
    boolean isFinished = false;
    if (this.secondsLeft == 0) {
      isFinished = true;
    }
    return isFinished;
  }
}