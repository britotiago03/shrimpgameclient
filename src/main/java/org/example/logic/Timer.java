package org.example.logic;

import java.util.List;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.util.Duration;
import org.example.ShrimpGameApp;

public class Timer {
  private int secondsLeft;
  private final ShrimpGameApp shrimpGameApp;
  private Timeline timeline;
  private final int roundNum;

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

      // If the time is up, stop the timeline
      if (this.secondsLeft == 0) {
        this.timeline.stop();
        if (shrimpGameApp.getGame().getCurrentRoundNum() == this.roundNum) {
          try {
            if (!this.shrimpGameApp.getGame().getPlayers().get(
                this.shrimpGameApp.getUser().getName()).hasCaughtShrimp()) {
              int minShrimpKg = this.shrimpGameApp.getGame().getSettings().getMinShrimpPounds();
              this.shrimpGameApp.getServerConnection().sendCatchShrimpRequest(minShrimpKg);
              this.shrimpGameApp.getGame().getPlayers().get(this.shrimpGameApp.getUser().getName())
                                .setShrimpCaught(minShrimpKg);
              this.shrimpGameApp.getServerConnection().getNextServerPacket();
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

  public void start() {
    this.timeline.play();
  }

  public void stop() {
    this.timeline.stop();
  }

  public int getSecondsLeft() {
    return secondsLeft;
  }

  public void setSecondsLeft(int secondsLeft) {
    this.secondsLeft = secondsLeft;
  }

  public boolean isFinished() {
    boolean isFinished = false;
    if (this.secondsLeft == 0) {
      isFinished = true;
    }
    return isFinished;
  }
}