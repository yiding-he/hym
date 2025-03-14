package demo;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;

public class URLResourceTest {

  public static void main(String[] args) throws Exception {
    URL url = URI.create("http://localhost:5173/index.html").toURL();
    var connection = url.openConnection();
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
      StringBuilder response = new StringBuilder();
      String line;
      while ((line = reader.readLine()) != null) {
        response.append(line).append("\n");
      }
      System.out.println(response.toString());
    }
  }
}
