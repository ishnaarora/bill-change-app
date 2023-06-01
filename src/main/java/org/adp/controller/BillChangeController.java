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
import java.util.List;

@RestController
@RequestMapping("/api/bill/change")
public class BillChangeController {

    private final BillChangeService billChangeService;

    @Autowired
    public BillChangeController(BillChangeService billChangeService) {
        this.billChangeService = billChangeService;
    }

    @GetMapping("/{billAmount}")
    public ResponseEntity getChange(@NotNull @PathVariable int billAmount) {
        if (billAmount <= 0) {
            return ResponseEntity.badRequest().body("Invalid bill amount.");
        }

        List<Coin> change = billChangeService.calculateChange(billAmount);

        if (change.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No change possible for given bill.");
        }

        return ResponseEntity.ok(change);
    }
}
