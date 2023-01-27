package project.oobat.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import project.oobat.Model.Order;
import project.oobat.Model.Payment;
import project.oobat.Model.Product;
import project.oobat.Repository.OrderRepository;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private PaymentService paymentService;

    // ORDER CRUD

    public void saveOrder(Order order) {
        orderRepository.save(order);
    }

    public Order saveOrderAndFlush(Order order) {
        return orderRepository.saveAndFlush(order);
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).get();
    }

    public Iterable<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public void deleteOrder(Order order) {
        orderRepository.delete(order);
    }

    public void deleteOrderById(Long id) {
        orderRepository.deleteById(id);
    }

    public Iterable<Order> getAllOrder() {
        return orderRepository.findAll();
    }

    // CART METHODS

    public void newCart(Order order) {
        order.setStatus(Order.Status.CART);
        Order newOrder = orderRepository.saveAndFlush(order);
        paymentService.newPayment(newOrder);
    }

    public void addProductToCart(Product product, Long id) {
        Order order = getCartByUserId(id);
        // order.getProducts().add(product);
        orderRepository.save(order);
    }

    public void removeProductFromCart(Product product, String user) {
        Order order = getCartByUsername(user);
        order.getProducts().remove(product);
        orderRepository.save(order);
    }

    public void placeOrder(Order order) {
        order.setStatus(Order.Status.PENDING);
        orderRepository.save(order);
    }

    public Order getCartByUserId(Long id) {
        return orderRepository.findByUserIdAndStatus(id, Order.Status.CART);
    }

    public Order getCartByUsername(String username) {
        return orderRepository.findByUsernameAndStatus(username, Order.Status.CART);
    }

}
