package task4;
import java.util.Scanner;
import java.io.InputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class task4 {
    public static void main(String[] args) {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Base currency (USD Only)");     // Input base currency
            String base = "USD";
            System.out.print("Enter target currency (like: EUR): ");    // Input target currency
            String target = sc.nextLine();
            target=target.toUpperCase();
            System.out.print("Enter the amount to convert: ");
            double amount = Double.parseDouble(sc.next());               // Input amount

            String apiKey = "320a9bafa7b64c5e9a94f4924cd1b847";
            String apiUrl = "https://openexchangerates.org/api/latest.json?app_id=" + apiKey+"&base=USD";                
            URL url = new URL(apiUrl);
            HttpURLConnection connect = (HttpURLConnection) url.openConnection();
            connect.setRequestMethod("GET");

            int responseCode = connect.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream inputStream = connect.getInputStream();

                Scanner sc1 = new Scanner(inputStream);
                StringBuilder response = new StringBuilder();

                while (sc1.hasNextLine()) {
                    response.append(sc1.nextLine());
                }
                inputStream.close();
                
                JSONParser parser = new JSONParser();
                JSONObject exchangeRates = (JSONObject) parser.parse(response.toString());
                JSONObject rates = (JSONObject) exchangeRates.get("rates");

                double exchangeRate = (double) rates.get(target);
                double convertedAmount = amount * exchangeRate;

                System.out.println(amount + " " + base + " = " + convertedAmount + " " + target);
            } else {
                System.out.println("Failed. HTTP Error Code: " + responseCode);
            }

            connect.disconnect();
        } catch (IOException e) {
            System.out.println("Input Output Exception : " + e);
        } catch (Exception e) {
            System.out.println("Exception : " + e);
        }
    }
}

