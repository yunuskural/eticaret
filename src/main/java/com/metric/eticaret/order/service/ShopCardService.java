package com.metric.eticaret.order.service;

import com.metric.eticaret.exception.domain.NotFoundException;
import com.metric.eticaret.order.model.ShopCard;

import java.util.List;

public interface ShopCardService {

    ShopCard addProductToShopCard(Long productId) throws NotFoundException;

    ShopCard retrieveShopCard() throws NotFoundException;

    ShopCard deleteProductInShopCard(Long productId) throws NotFoundException;
}
