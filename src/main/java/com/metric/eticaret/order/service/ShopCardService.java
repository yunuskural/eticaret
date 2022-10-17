package com.metric.eticaret.order.service;

import com.metric.eticaret.exception.domain.NotFoundException;
import com.metric.eticaret.order.model.shopcard.ShopCardDTO;

public interface ShopCardService {

    ShopCardDTO addProductInShopCard(Long productId, String username) throws NotFoundException;

    ShopCardDTO retrieveShopCardProducts(String username) throws NotFoundException;

    ShopCardDTO deleteProductInShopCard(Long productId, String username) throws NotFoundException;
}
