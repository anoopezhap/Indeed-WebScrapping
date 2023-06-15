package indeed.PageObjects;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

import org.json.JSONObject;

public class UrlShortener {

    public static String urlShortener(String longUrl)
    {
        String accessToken ="2e2cd8679006353f52990a6c2e8c9c11221b727f";

        try {
            URL url = new URL("https://api-ssl.bitly.com/v4/shorten");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "Bearer " + accessToken);
            connection.setDoOutput(true);

            JSONObject jsonInput = new JSONObject();
            jsonInput.put("long_url", longUrl);

            byte[] requestBody = jsonInput.toString().getBytes(StandardCharsets.UTF_8);

            connection.getOutputStream().write(requestBody);

            int responseCode = connection.getResponseCode();

            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);

                }
                reader.close();

                JSONObject jsonResponse = new JSONObject(response.toString());
                String shortenedUrl = jsonResponse.getString("link");
                return shortenedUrl;
            } else if (responseCode == 429)
            {

                Thread.sleep(3000);
                urlShortener(longUrl);
                
            } else {
                System.out.println("Error occurred. Response Code: " + responseCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return "URL Not Shortened";
    }
}
