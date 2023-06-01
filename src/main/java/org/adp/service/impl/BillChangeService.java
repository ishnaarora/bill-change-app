package org.adp.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.adp.domain.Coin;
import org.adp.service.IChangeService;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.springframework.util.CollectionUtils.isEmpty;

@Service
@Slf4j
public class BillChangeService implements IChangeService {
    private Map<Double, Coin> coins;

    public BillChangeService() {
        // Initialize the coins with their initial quantities
        coins = new HashMap<>();
        coins.put(0.01, new Coin(0.01, 100));
        coins.put(0.05, new Coin(0.05, 100));
        coins.put(0.10, new Coin(0.10, 100));
        coins.put(0.25, new Coin(0.25, 100));
    }

    @Override
    public List<Coin> calculateChange(double billAmount) {
        List<Coin> change = new ArrayList<>();
        double remainingAmount = billAmount;

        // Sort the coins in descending order of value
        List<Coin> sortedCoins = new ArrayList<>(coins.values());
        sortedCoins.sort(Comparator.comparingDouble(Coin::getValue).reversed());
        int totalNoOfCoins = sortedCoins.size();
        for (int i = 0; i < totalNoOfCoins; i++) {
            List<Coin> coinChange = extracted(remainingAmount, sortedCoins.subList(i, totalNoOfCoins));
            if (!isEmpty(coinChange)) {
                return coinChange;
            }
        }

        return change;
    }

    private List<Coin> extracted(double remainingAmount, List<Coin> sortedCoins) {
        // Iterate through the coins
        List<Coin> change = new ArrayList<>();
        for (Coin coin : sortedCoins) {
            int numCoins = Math.min((int) (remainingAmount / coin.getValue()), coin.getQuantity());

            if (numCoins > 0) {
                remainingAmount -= numCoins * coin.getValue();
                coin.setQuantity(coin.getQuantity() - numCoins);
                change.add(new Coin(coin.getValue(), numCoins));
            }
        }

        // If exact change is not possible, rollback the coin quantities and clear change list
        if (remainingAmount > 0) {
            rollbackCoinQuantities(change);
            change.clear();
        }

        return change;
    }

    private void rollbackCoinQuantities(List<Coin> change) {
        for (Coin coin : change) {
            Coin originalCoin = coins.get(coin.getValue());
            originalCoin.setQuantity(originalCoin.getQuantity() + coin.getQuantity());
        }
    }
}
