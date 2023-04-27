package org.example.model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

/**
 * Represents a message comparator.
 */
public class MessageComparator implements Comparator<String> {
  private final SimpleDateFormat dateFormat;

  /**
   * Creates a new instance of {@code MessageComparator}.
   */
  public MessageComparator() {
    this.dateFormat = new SimpleDateFormat("HH:mm:ss");
  }

  @Override
  public int compare(String message1, String message2) {
    try {
      String[] parts1 = message1.split("\\.");
      String[] parts2 = message2.split("\\.");

      Date date1 = this.dateFormat.parse(parts1[2]);
      Date date2 = this.dateFormat.parse(parts2[2]);

      return date2.compareTo(date1);
    } catch (ParseException e) {
      // Handle parse error
      return 0;
    }
  }
}
