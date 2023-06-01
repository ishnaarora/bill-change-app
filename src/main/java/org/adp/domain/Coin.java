package org.adp.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class Coin {
    @NotNull
    private double value;
    @NotNull
    private int quantity;
}
