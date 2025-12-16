package com.finance.test;

import com.finance.service.CryptoPriceService;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class TestCryptoService {

    @Test
    public void testFetchPrices() {
        CryptoPriceService service = new CryptoPriceService();
        List<String> symbols = Arrays.asList("bitcoin", "ethereum");
        
        System.out.println("Fetching prices for: " + symbols);
        Map<String, Double> prices = service.getRealTimePrices(symbols);
        
        System.out.println("Prices fetched: " + prices);
        
        assertNotNull(prices, "Prices map should not be null");
        assertTrue(prices.containsKey("bitcoin"), "Should contain bitcoin");
        assertTrue(prices.containsKey("ethereum"), "Should contain ethereum");
        
        Double btcPrice = prices.get("bitcoin");
        assertNotNull(btcPrice, "Bitcoin price should not be null");
        assertTrue(btcPrice > 0, "Bitcoin price should be positive");
    }
}
