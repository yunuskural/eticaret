package com.metric.eticaret.order.service;


import com.metric.eticaret.exception.domain.NotFoundException;
import com.metric.eticaret.order.model.order.Order;
import com.metric.eticaret.order.model.order.OrderDTO;
import com.metric.eticaret.order.model.order.OrderMapper;
import com.metric.eticaret.order.repository.OrderRepository;
import com.metric.eticaret.user.model.user.User;
import com.metric.eticaret.user.model.user.UserDTO;
import com.metric.eticaret.user.model.user.UserMapper;
import com.metric.eticaret.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ShopCardService shopCardService;

    @Transactional
    @Override
    public OrderDTO save(OrderDTO orderDTO, String username) throws NotFoundException {
        if (orderDTO != null && orderDTO.getProducts().size() != 0) {
            Order order = OrderMapper.INSTANCE.toEntity(orderDTO);
            order.setOrderDate(new Date().getTime());
            UserDTO userDTO = findByUsername(username);
            order.setUser(UserMapper.INSTANCE.toEntity(userDTO));
            order.setInvoiceFullAddress(order.getUser().getAddress());
            Random random = new Random();
            order.setOrderNumber(random.nextInt(100000));
            order = orderRepository.save(order);
            removeAllProductsInShopCard(order);
            return OrderMapper.INSTANCE.toDTO(order);

        } else {
            throw new NotFoundException("You can add at least one product");
        }
    }

    private void removeAllProductsInShopCard(Order order) {
        order.getProducts().forEach(product ->
        {
            product.setShopCard(null);
            product.setShopCardQuantity(0);
            product.setStockQuantity(product.getStockQuantity() - product.getShopCardQuantity());
        });
    }

    private UserDTO findByUsername(String username) throws NotFoundException {
        if (userRepository.findByUsername(username) != null) {
            User user = userRepository.findByUsername(username);
            return UserMapper.INSTANCE.toDTO(user);
        } else {
            throw new NotFoundException("user not found");
        }
    }

  /*  private List<Product> decreaseStockQuantity(OrderDTO orderDTO){
        List<Product> products = new ArrayList<>();
        if(orderDTO.getProducts() != null){
            orderDTO.getProducts().forEach(product ->
                    products.replaceAll(product.getStockQuantity()--));
        }
    }*/

    @Override
    public List<OrderDTO> retrieveAllOrders(String username) throws NotFoundException {
        List<Order> orders = orderRepository.getAllByUserUsername(username);
        if (orders.size() == 0) {
            throw new NotFoundException("You have not had any orders yet");
        }
        return orders.stream().map(OrderMapper.INSTANCE::toDTO).collect(Collectors.toList());
    }

    @Override
    public OrderDTO getOrderById(Long id) throws NotFoundException {
        Order order = orderRepository.findById(id).orElseThrow(() -> new NotFoundException("Order not found"));
        return OrderMapper.INSTANCE.toDTO(order);
    }

    @Override
    public void deleteOrderById(Long id) {
        Optional<Order> orderOptional = orderRepository.findById(id);
        if (orderOptional.isPresent()) {
            orderRepository.deleteById(id);
        }
    }

}
