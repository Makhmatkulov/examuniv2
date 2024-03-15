package w4;

import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DataToDatabase {
    // OpenWeatherMap API key
    //private static final String API_KEY = "...";
    private static final String API_KEY = "a31e7a106635faa6152c41a052f1ab1b";

    // PostgreSQL database credentials
    //private static final String DB_URL = "jdbc:postgresql://localhost:5432/openweather";//name of db
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/weatherApp";
    private static final String USER = "postgres"; // owner of server(inside pg admin)
    private static final String PASSWORD = "Murod9059";// password of the server







    // Method to fetch weather data from OpenWeatherMap API
    private static JSONObject fetchWeatherData(String city) throws IOException {
        String apiUrl = "http://api.openweathermap.org/data/2.5/weather?q=" + city + "&appid=" + API_KEY;
        URL url = new URL(apiUrl);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        return new JSONObject(response.toString());
    }






    // Method to insert weather data into PostgreSQL database
    private static void insertWeatherData(JSONObject weatherData) throws SQLException {
        Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        String sql = "INSERT INTO weather_data(city, temperature, pressure) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = conn.prepareStatement(sql);
        preparedStatement.setString(1, weatherData.getString("name"));
        preparedStatement.setDouble(2, weatherData.getJSONObject("main").getDouble("temp"));
        preparedStatement.setDouble(3, weatherData.getJSONObject("main").getDouble("pressure"));
        preparedStatement.execute();
        conn.close();
    }







    public static void main(String[] args) throws IOException, SQLException {

        // Fetch weather data from the API
        String city = "Huddersfield"; // Change this to your desired city
        JSONObject weatherData = fetchWeatherData(city);
        System.out.println(weatherData);

        // Insert weather data into the database
        insertWeatherData(weatherData);
        System.out.println("Weather data inserted successfully!");

    }
}
