package wad.ordering.controller;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import wad.ordering.domain.Order;
import wad.ordering.service.OrderService;

@Controller
@RequestMapping("order")
public class OrderController {

    private OrderService orderService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @ModelAttribute("items")
    public List<String> createItemList() {

        // tilauspalvelusta voi tilata vain kolme kirjaa
        // -- tietokannallisessa sovelluksessa nämä haettaisiin luonnollisesti
        // tietokannasta
        List<String> items = new ArrayList<String>();
        items.add("A Time to Kill");
        items.add("The Firm");
        items.add("The Pelican Brief");
        return items;
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.POST)
    public String submitOrder(
            RedirectAttributes redirectAttributes,
            @Valid @ModelAttribute("order") Order order,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "form";
        }
       

        order = orderService.placeOrder(order);
        redirectAttributes.addAttribute("orderId", order.getId());
        redirectAttributes.addFlashAttribute("message", "Order placed!");


        return "redirect:/app/order/{orderId}";
    }

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String showOrderForm(@ModelAttribute("order") Order order,
            @ModelAttribute("items") List<String> items) {
        return "form";
    }

    @RequestMapping(value = "{orderId}", method = RequestMethod.GET)
    public String showOrder(Model model, @PathVariable String orderId) {
        model.addAttribute("order", orderService.getOrder(orderId));
        return "vieworder";
    }
}