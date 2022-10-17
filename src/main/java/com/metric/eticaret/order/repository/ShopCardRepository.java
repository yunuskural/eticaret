package com.metric.eticaret.order.repository;

import com.metric.eticaret.order.model.shopcard.ShopCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ShopCardRepository extends JpaRepository<ShopCard, Integer> {

    ShopCard getAllByUserUsername(String username);

    ShopCard findByUserUsername(String username);
}
