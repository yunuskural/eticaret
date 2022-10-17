package com.metric.eticaret.order.service;

import com.metric.eticaret.exception.domain.NotFoundException;
import com.metric.eticaret.order.model.shopcard.ShopCard;
import com.metric.eticaret.order.model.shopcard.ShopCardDTO;
import com.metric.eticaret.order.model.shopcard.ShopCardMapper;
import com.metric.eticaret.order.repository.ShopCardRepository;
import com.metric.eticaret.product.model.product.Product;
import com.metric.eticaret.product.repository.ProductRepository;
import com.metric.eticaret.user.model.user.User;
import com.metric.eticaret.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class ShopCardServiceImpl implements ShopCardService {

    private final ShopCardRepository shopCardRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public ShopCardDTO addProductInShopCard(Long productId, String username) throws NotFoundException {
        ShopCard shopCard = new ShopCard();
        User user = userRepository.findByUsername(username);
        if (user != null && user.getShopCard() == null) {
            shopCard.setUser(user);
        } else {
            shopCard = shopCardRepository.findByUserUsername(username);
        }
        Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Product not found"));
        if (product != null && product.getStockQuantity() != 0) {
            if (shopCard.getProducts().contains(product)) {
                product.setShopCardQuantity(product.getShopCardQuantity() + 1);
            } else {
                shopCard.getProducts().add(product);
                product.setShopCardQuantity(1);
                product.setShopCard(shopCard);
            }
            product.setStockQuantity(product.getStockQuantity() - 1);
            shopCardRepository.save(shopCard);
            productRepository.save(product);
        } else {
            throw new NotFoundException("This product is out of stock..");
        }
        return ShopCardMapper.INSTANCE.toDTO(shopCard);
    }

    @Override
    public ShopCardDTO retrieveShopCardProducts(String username) throws NotFoundException {
        ShopCard shopCard = shopCardRepository.findByUserUsername(username);
        if (shopCard.getProducts() == null) {
            throw new NotFoundException("Your shop card is empty now.. You can explorer the products and continue shopping");
        }
        return ShopCardMapper.INSTANCE.toDTO(shopCard);
    }

    @Override
    public ShopCardDTO deleteProductInShopCard(Long productId, String username) throws NotFoundException {
        Product product = productRepository.findById(productId).orElseThrow(() -> new NotFoundException("Product not found"));
        ShopCard shopCard = shopCardRepository.findByUserUsername(username);
        if (product != null && shopCard.getProducts().contains(product)) {
            if (product.getShopCardQuantity() == 1) {
                shopCard.getProducts().remove(product);
                product.setShopCard(null);
            } else {
                product.setShopCardQuantity(product.getShopCardQuantity() - 1);
            }
            product.setStockQuantity(product.getStockQuantity() + 1);
            productRepository.save(product);
            shopCardRepository.save(shopCard);
        }
        return ShopCardMapper.INSTANCE.toDTO(shopCard);
    }
}
