package com.example;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class WeatherApp {
    private static final String API_KEY = "622622fce820ed62a9e93d621e1f142d";
    private static final String GEOCODING_API_URL = "http://api.openweathermap.org/geo/1.0/";

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please provide a city, state, or zip code.");
            return;
        }

        String allLocations = String.join(" ", args);
            List<String> locations = List.of(allLocations.split(" "));
            for(int i=0; i<locations.size(); i++) {
                try {
                    String response = getGeocodingData(locations.get(i));
                    parseAndPrintResponse(response);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
    }

    public static String getGeocodingData(String location) throws IOException, InterruptedException {
        HttpClient client = HttpClient.newHttpClient();
        String url = "";
        if (location.matches(".*\\d.*")) {
            url = String.format("%szip?zip=%s&appid=%s", GEOCODING_API_URL, location, API_KEY);
        } else {
            String encodedLocation = URLEncoder.encode(location, StandardCharsets.UTF_8);
            url = String.format("%sdirect?q=%s&limit=5&appid=%s", GEOCODING_API_URL, encodedLocation, API_KEY);
        }
        System.out.println("****** "+url);
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("Response Body: " + response.body());
        return response.body();
    }

    public static void parseAndPrintResponse(String response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readTree(response);
        if (rootNode.isArray() && rootNode.size() > 0) {
            JsonNode locationNode = rootNode.get(0);
            printLocationDetails(locationNode);
        }
        else if(rootNode.isObject()) {
            printLocationDetails(rootNode);
        }
        else {
            System.out.println("No results found for the given location.");
        }
    }

    private static void printLocationDetails(JsonNode locationNode) {
        String name = locationNode.path("name").asText();
        double lat = locationNode.path("lat").asDouble();
        double lon = locationNode.path("lon").asDouble();
        System.out.printf("Place: %s, Latitude: %f, Longitude: %f%n", name, lat, lon);
    }
}
