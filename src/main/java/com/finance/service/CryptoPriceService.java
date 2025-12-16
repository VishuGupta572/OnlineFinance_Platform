package com.finance.service;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CryptoPriceService {

    private static final String API_URL = "https://api.coingecko.com/api/v3/simple/price";

    public Map<String, Double> getRealTimePrices(List<String> symbols) {
        Map<String, Double> prices = new HashMap<>();
        if (symbols == null || symbols.isEmpty()) return prices;

        try {
            // CoinGecko expects 'ids' (e.g., bitcoin, ethereum). We assume symbols passed are valid IDs.
            String ids = String.join(",", symbols).toLowerCase();
            String urlString = API_URL + "?ids=" + ids + "&vs_currencies=inr";
            
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                System.err.println("Failed to fetch prices: HTTP " + conn.getResponseCode());
                return prices;
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                response.append(line);
            }
            conn.disconnect();

            // Parse JSON
            // Response format: {"bitcoin": {"inr": 5000000}, "ethereum": {"inr": 300000}}
            JsonObject jsonObject = JsonParser.parseString(response.toString()).getAsJsonObject();
            
            for (String symbol : symbols) {
                String key = symbol.toLowerCase();
                if (jsonObject.has(key)) {
                    JsonObject priceObj = jsonObject.getAsJsonObject(key);
                    if (priceObj.has("inr")) {
                        prices.put(key, priceObj.get("inr").getAsDouble());
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prices;
    }
}
