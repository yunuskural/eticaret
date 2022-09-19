package com.metric.eticaret.order.service;

import com.metric.eticaret.exception.domain.NotFoundException;
import com.metric.eticaret.order.model.ShopCard;

import java.util.List;

public interface ShopCardService {
    void save(ShopCard shopCard) throws NotFoundException;

    List<ShopCard> retrieveAllBasket();
}
