package org.adp.controller;

import org.adp.domain.Coin;
import org.adp.service.impl.BillChangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/api/bill/change")
public class BillChangeController {

    private final BillChangeService billChangeService;

    private static final List<Integer> SUPPORTED_BILLS = Arrays.asList(1, 2, 5, 10, 20, 50, 100);

    @Autowired
    public BillChangeController(BillChangeService billChangeService) {
        this.billChangeService = billChangeService;
    }

    @GetMapping("/{billAmount}")
    public ResponseEntity<List<Coin>> getChange(@NotNull @PathVariable int billAmount) {
        if (billAmount <= 0 || !SUPPORTED_BILLS.contains(billAmount)) {
            throw new IllegalArgumentException(String.format("Invalid bill amount %s",billAmount));
        }

        List<Coin> change = billChangeService.calculateChange(billAmount);

        if (change.isEmpty()) {
            throw new IllegalStateException("No change possible for given bill.");
        }

        return ResponseEntity.ok(change);
    }
}
