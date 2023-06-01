package org.adp.controller;

import org.adp.domain.Coin;
import org.adp.service.impl.BillChangeService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class BillChangeControllerTest {

    @InjectMocks
    BillChangeController controller;

    @Mock
    BillChangeService changeService;

    @Test
    public void shouldReturnSuccessStatusWhenChangePossible() {
        List<Coin> expected = Arrays.asList(new Coin(10, 100));
        Mockito.when(changeService.calculateChange(10)).thenReturn(expected);

        ResponseEntity response = controller.getChange(10);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.OK, response.getStatusCode());
        Assert.assertEquals(expected, response.getBody());
    }

    @Test
    public void shouldReturnFailureStatusWhenNoChangePossible() {
        List<Coin> expected = Collections.emptyList();
        Mockito.when(changeService.calculateChange(10)).thenReturn(expected);

        ResponseEntity response = controller.getChange(10);
        Assert.assertNotNull(response);
        Assert.assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        Assert.assertEquals("No change possible for given bill.", response.getBody());
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldReturnBadRequestWhenBadInputProvided() {
        controller.getChange(-10);
    }
}
