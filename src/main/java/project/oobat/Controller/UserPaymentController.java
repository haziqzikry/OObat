package project.oobat.Controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import project.oobat.Model.Order;
import project.oobat.Model.Payment;
import project.oobat.Model.Payment.Status;
import project.oobat.Service.OrderService;
import project.oobat.Service.PaymentService;

@Controller
@RequestMapping("/user/payment")
public class UserPaymentController {
    
    @Autowired
    private PaymentService paymentService;

    @Autowired
    private OrderService orderService;

    @GetMapping("/checkout")
    public String checkout(Model model, Principal principal) {
        Order order = orderService.getCartByUsername(principal.getName());
        orderService.placeOrder(order);
        Payment payment = order.getPayment();
        model.addAttribute("payment", payment);
        return "user/payment";
    }

    @GetMapping("/view")
    public String completePayment(Model model, Principal principal) {
        Payment payment = paymentService.getUnpaidPayment(principal.getName());
        model.addAttribute("payment", payment);
        return "user/payment";
    }

    @PostMapping("/confirm")
    public String confirmPayment(Payment payment, Principal principal) {
        Payment paymentToConfirm = paymentService.getUnpaidPayment(principal.getName());
        paymentToConfirm.setMethod(payment.getMethod());
        String today_date = java.time.LocalDate.now().toString();
        String current_time = java.time.LocalTime.now().toString();
        paymentToConfirm.setDate(today_date);
        paymentToConfirm.setTime(current_time);
        paymentToConfirm.setStatus(Status.COMPLETED);
        paymentService.savePayment(paymentToConfirm);
        paymentToConfirm.getOrder().setStatus(Order.Status.COMPLETED);
        paymentToConfirm.getOrder().setDate(today_date);
        orderService.saveOrder(paymentToConfirm.getOrder());
        return "redirect:/user/payment/view";
    }
}
