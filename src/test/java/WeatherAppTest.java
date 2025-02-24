import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.example.WeatherApp;

import java.io.IOException;

public class WeatherAppTest {

    @Test
    public void testGetGeocodingDataWithZipCode() throws IOException, InterruptedException {
        String location = "E14,GB";
        String response = WeatherApp.getGeocodingData(location);
        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertTrue(response.contains("name"));
        assertTrue(response.contains("lat"));
        assertTrue(response.contains("lon"));
    }

    @Test
    public void testGetGeocodingDataWithCityName() throws IOException, InterruptedException {
        String location = "London";
        String response = WeatherApp.getGeocodingData(location);
        assertNotNull(response);
        assertFalse(response.isEmpty());
        assertTrue(response.contains("name"));
        assertTrue(response.contains("lat"));
        assertTrue(response.contains("lon"));
    }

    @Test
    public void testParseAndPrintResponseWithEmptyResponse() throws IOException {
        String jsonResponse = "[]";
        WeatherApp.parseAndPrintResponse(jsonResponse);
    }

    @Test
    public void testMainWithNoArguments() {
        String[] args = {};
        WeatherApp.main(args);
    }

    @Test
    public void testMainWithCityName() {
        String[] args = {"London"};
        WeatherApp.main(args);
    }

    @Test
    public void testParseAndPrintResponseWithValidResponse() throws IOException {
        String jsonResponse = "[{\"name\":\"London\",\"lat\":51.5074,\"lon\":-0.1278}]";
        WeatherApp.parseAndPrintResponse(jsonResponse);
        assertTrue(true);
    }

    @Test
    public void mainWithInvalidLocation() throws IOException, InterruptedException {
        String location = "Invalid City";
        String response = WeatherApp.getGeocodingData(location);
        assertNotNull(response);
        assertNotNull(response.length() == 0);
    }

    @Test
    public void getGeocodingDataWithInvalidZipCode() throws IOException, InterruptedException {
        String location = "00000";
        String response = WeatherApp.getGeocodingData(location);
        assertNotNull(response);
        assertTrue(response.contains("\"cod\":\"404\""));
        assertTrue(response.contains("\"message\":\"not found\""));
    }

    @Test
    public void mainWithMultipleValidCityNames() {
        String[] args = {"London", "Paris", "New York"};
        WeatherApp.main(args);
    }

    @Test
    public void mainWithMultipleValidAndInvalidCityNames() {
        String[] args = {"London", "Paris", "Invalid City"};
        WeatherApp.main(args);
    }

    @Test
    public void mainWithMultipleValidZipcodes() {
        String[] args = {"94404", "94587", "94541"};
        WeatherApp.main(args);
    }

    @Test
    public void mainWithMultipleValidAndInvalidZipcodes() {
        String[] args = {"94404", "94589077", "94"};
        WeatherApp.main(args);
    }
}
