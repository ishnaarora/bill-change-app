package org.adp.service;

import com.tngtech.java.junit.dataprovider.DataProvider;
import com.tngtech.java.junit.dataprovider.DataProviderRunner;
import com.tngtech.java.junit.dataprovider.UseDataProvider;
import org.adp.domain.Coin;
import org.adp.service.impl.BillChangeService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.emptyList;

@RunWith(DataProviderRunner.class)
public class BillChangeServiceTest {

    @DataProvider
    public static Object[][] dataProviderFieldIsEqualTo() {

        return new Object[][] {
                { 1, Arrays.asList(new Coin(0.25, 4))},
                { 2, Arrays.asList(new Coin(0.25, 8))},
                { 5, Arrays.asList(new Coin(0.25, 20))},
                { 10, Arrays.asList(new Coin(0.25, 40))},
                { 20, Arrays.asList(new Coin(0.25, 80))},
                { 50, emptyList()},
                { 100, emptyList()}
                //{ 70, Arrays.asList(new Coin(0.3, 100), new Coin(0.25, 100), new Coin(0.1, 100), new Coin(0.05, 100))}
        };
    }

    @UseDataProvider("dataProviderFieldIsEqualTo")
    @Test
    public void calculateChangeSuccessfullyForSingleTransactions(int billAmount, List<Coin> expectedCoins){
        BillChangeService billChangeService = new BillChangeService();
        List<Coin> actualCoins = billChangeService.calculateChange(billAmount);
        Assert.assertEquals(expectedCoins, actualCoins);
    }

    @Test
    public void calculateChangeSuccessfullyForMutlipleTransactions(){
        BillChangeService billChangeService = new BillChangeService();
        List<Coin> actualCoinsTransaction1 = billChangeService.calculateChange(20);
        List<Coin> actualCoinsTransaction2 = billChangeService.calculateChange(10);
        Assert.assertEquals(Arrays.asList(new Coin(0.25, 80)), actualCoinsTransaction1);
        Assert.assertEquals(Arrays.asList(new Coin(0.25, 20), new Coin(0.1, 50)), actualCoinsTransaction2);
    }

    @Test
    public void returnEmptyListWhenChangeNotPossible(){
        BillChangeService billChangeService = new BillChangeService();
        List<Coin> actualCoinsTransaction1 = billChangeService.calculateChange(120);
        Assert.assertEquals(Collections.emptyList(), actualCoinsTransaction1);
    }
}

