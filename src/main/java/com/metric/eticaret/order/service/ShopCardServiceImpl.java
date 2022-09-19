package com.metric.eticaret.order.service;

import com.metric.eticaret.exception.domain.NotFoundException;
import com.metric.eticaret.order.model.ShopCard;
import com.metric.eticaret.order.repository.ShopCardRepository;
import com.metric.eticaret.product.model.Product;
import com.metric.eticaret.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ShopCardServiceImpl implements ShopCardService {

    private final ShopCardRepository shopCardRepository;
    private final ProductRepository productRepository;

    @Override
    public void save(ShopCard shopCard) throws NotFoundException {
        if (shopCard.getId() != 0 && shopCard != null) {
            shopCardRepository.findById(shopCard.getId()).orElseThrow(() -> new NotFoundException("Shop card not found"));
        }
        Set<Product> products = new HashSet<>();
        shopCard.getProducts().forEach(product ->
                products.add(productRepository.findByProductName(product.getProductName())));
        shopCard.setProducts(products);
    }

    @Override
    public List<ShopCard> retrieveAllBasket() {
        return shopCardRepository.findAll();
    }


}
