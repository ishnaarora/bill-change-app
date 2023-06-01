package org.adp.service;

import org.adp.domain.Coin;

import java.util.List;

public interface IChangeService {
    List<Coin> calculateChange(double billAmount);
}
