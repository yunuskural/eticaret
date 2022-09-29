package com.metric.eticaret.order.service;

import com.metric.eticaret.exception.domain.NotFoundException;
import com.metric.eticaret.order.model.ShopCard;
import com.metric.eticaret.order.repository.ShopCardRepository;
import com.metric.eticaret.product.model.Product;
import com.metric.eticaret.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ShopCardServiceImpl implements ShopCardService {

    private final ShopCardRepository shopCardRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public ShopCard addProductToShopCard(Long productId) throws NotFoundException {
        Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Product not found"));
        ShopCard shopCard = shopCardRepository.findById(1).orElseThrow(() -> new NotFoundException("Shopcard not found"));
        if (product != null && product.getStockQuantity() != 0) {
            product.setShopCard(shopCard);
            shopCard.getProducts().add(product);
            productRepository.save(product);
            shopCardRepository.save(shopCard);
        }
        return shopCard;
    }

    @Override
    public ShopCard retrieveShopCard() throws NotFoundException {
        return shopCardRepository.findById(1).orElseThrow(() -> new NotFoundException("Shop card not found"));
    }

    @Override
    public ShopCard deleteProductInShopCard(Long productId) throws NotFoundException {
        Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Product not found"));
        ShopCard shopCard = shopCardRepository.findById(1).orElseThrow(() -> new NotFoundException("Shopcard not found"));
        if (product != null && shopCard.getProducts().contains(product)) {
            shopCard.getProducts().remove(product);
            product.setShopCard(null);
            productRepository.save(product);
            shopCardRepository.save(shopCard);
        }
        return shopCard;
    }


}
