package demo;

import org.junit.jupiter.api.Test;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.nio.file.Path;

public class URLResourceTest {

  @Test
  public void testReadUrlLines() throws Exception {
    URL url = URI.create("http://localhost:5173/index.html").toURL();
    var connection = url.openConnection();
    try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
      StringBuilder response = new StringBuilder();
      String line;
      while ((line = reader.readLine()) != null) {
        response.append(line).append("\n");
      }
      System.out.println(response);
    }
  }

  @Test
  public void testGetFilesPath() throws Exception {
    var path = "file:../frontend-classic";
    Resource resource = new UrlResource(path);
    System.out.println(resource.getFile().toPath().toAbsolutePath());
  }
}
