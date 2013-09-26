package wad.ordering;

import fi.helsinki.cs.tmc.edutestutils.Points;
import java.util.List;
import java.util.UUID;
import org.springframework.test.web.servlet.MockMvc;
import org.junit.Test;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;
import static org.junit.Assert.*;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import wad.ordering.controller.OrderController;
import wad.ordering.domain.Order;
import wad.ordering.service.InMemoryOrderService;
import wad.ordering.service.OrderService;

public class PostRedirectGetTest {

    @Test
    @Points("W4E05.1")
    public void formReturnedOnGetToOrder() throws Exception {
        createControllerWithViewResolver()
                .perform(get("/order"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/form.jsp"));
    }

    @Test
    @Points("W4E05.1")
    public void redirectToSuccessOnSuccessfulPostToOrder() throws Exception {
        InMemoryOrderService orderService = new InMemoryOrderService();

        String name = UUID.randomUUID().toString().substring(0, 10);
        String address = UUID.randomUUID().toString().substring(0, 16);

        MvcResult result = createControllerWithViewResolver(orderService)
                .perform(post("/order")
                .param("name", name)
                .param("address", address)
                .param("items", "The Firm")).andReturn();

        assertTrue("The response should contain the string \"order\"", result.getModelAndView().getViewName().contains("order"));
    }

    @Test
    @Points("W4E05.1")
    public void returnToFormOnTooShortName() throws Exception {
        InMemoryOrderService orderService = new InMemoryOrderService();

        String name = UUID.randomUUID().toString().substring(0, 3);
        String address = UUID.randomUUID().toString().substring(0, 16);

        try {
            createControllerWithViewResolver(orderService)
                    .perform(post("/order")
                    .param("name", name)
                    .param("address", address)
                    .param("items", "The Firm"))
                    .andExpect(status().isOk())
                    .andExpect(forwardedUrl("/WEB-INF/jsp/form.jsp"));
        } catch (Throwable t) {
            fail("When the name is too short, the user should be shown the form again with existing inputs and error messages. Error: " + t.getMessage());
        }
    }

    @Test
    @Points("W4E05.1")
    public void returnToFormOnTooLongName() throws Exception {
        InMemoryOrderService orderService = new InMemoryOrderService();

        String name = UUID.randomUUID().toString() + UUID.randomUUID().toString() + UUID.randomUUID().toString();
        name = name.substring(0, 31);
        String address = UUID.randomUUID().toString().substring(0, 16);

        try {
            createControllerWithViewResolver(orderService)
                    .perform(post("/order")
                    .param("name", name)
                    .param("address", address)
                    .param("items", "The Firm"))
                    .andExpect(status().isOk())
                    .andExpect(forwardedUrl("/WEB-INF/jsp/form.jsp"));
        } catch (Throwable t) {
            fail("When the name is too long, the user should be shown the form again with existing inputs and error messages. Error: " + t.getMessage());
        }
    }

    @Test
    @Points("W4E05.1")
    public void returnToFormOnTooShortAddress() throws Exception {
        InMemoryOrderService orderService = new InMemoryOrderService();

        String name = UUID.randomUUID().toString().substring(0, 6);
        String address = UUID.randomUUID().toString().substring(0, 3);

        try {
            createControllerWithViewResolver(orderService)
                    .perform(post("/order")
                    .param("name", name)
                    .param("address", address)
                    .param("items", "The Firm"))
                    .andExpect(status().isOk())
                    .andExpect(forwardedUrl("/WEB-INF/jsp/form.jsp"));
        } catch (Throwable t) {
            fail("When the address is too short, the user should be shown the form again with existing inputs and error messages. Error: " + t.getMessage());
        }

    }

    @Test
    @Points("W4E05.1")
    public void returnToFormOnTooLongAddress() throws Exception {
        InMemoryOrderService orderService = new InMemoryOrderService();

        String name = UUID.randomUUID().toString().substring(0, 6);
        String address = UUID.randomUUID().toString() + UUID.randomUUID().toString() + UUID.randomUUID().toString();
        address = address.substring(0, 51);

        try {
            createControllerWithViewResolver(orderService)
                    .perform(post("/order")
                    .param("name", name)
                    .param("address", address)
                    .param("items", "The Firm"))
                    .andExpect(status().isOk())
                    .andExpect(forwardedUrl("/WEB-INF/jsp/form.jsp"));
        } catch (Throwable t) {
            fail("When the address is too long, the user should be shown the form again with existing inputs and error messages. Error: " + t.getMessage());
        }
    }

    @Test
    @Points("W4E05.1")
    public void returnToFormWhenNoItems() throws Exception {
        InMemoryOrderService orderService = new InMemoryOrderService();

        String name = UUID.randomUUID().toString().substring(0, 6);
        String address = UUID.randomUUID().toString().substring(0, 6);

        try {
            createControllerWithViewResolver(orderService)
                    .perform(post("/order")
                    .param("name", name)
                    .param("address", address))
                    .andExpect(status().isOk())
                    .andExpect(forwardedUrl("/WEB-INF/jsp/form.jsp"));
        } catch (Throwable t) {
            fail("When no items are selected, the user should be shown the form again with existing inputs and error messages. Error: " + t.getMessage());
        }
    }

    @Test
    @Points("W4E05.2")
    public void hasFlashAttributeMessageOnSuccess() throws Exception {
        InMemoryOrderService orderService = new InMemoryOrderService();

        String name = UUID.randomUUID().toString().substring(0, 6);
        String address = UUID.randomUUID().toString().substring(0, 6);
        
        MvcResult result = null;
        try {
            result = createControllerWithViewResolver(orderService)
                    .perform(post("/order")
                    .param("name", name)
                    .param("address", address)
                    .param("items", "The Firm"))
                    .andExpect(flash().attributeCount(1))
                    .andExpect(flash().attribute("message", "Order placed!"))
                    .andReturn();
        } catch (Throwable t) {
            fail("Once the order has been placed, a flash attribute \"message\" with value \"Order placed!\" should be added to the redirect parameters.");
        }

        List<Order> orders = orderService.getOrders();
        assertTrue("After a single order has been submitted, exactly 1 order should be added to the service.", orders.size() == 1);
        String viewname = result.getModelAndView().getViewName();
        assertTrue("The user should be redirected to a new page after an order is successfully placed.", viewname.startsWith("redirect"));
    }

    @Test
    @Points("W4E05.2")
    public void hasModelAttributeOrderIdOnSuccess() throws Exception {
        InMemoryOrderService orderService = new InMemoryOrderService();

        String name = UUID.randomUUID().toString().substring(0, 6);
        String address = UUID.randomUUID().toString().substring(0, 6);

        MvcResult result = null;
        try {
            result = createControllerWithViewResolver(orderService)
                .perform(post("/order")
                .param("name", name)
                .param("address", address)
                .param("items", "The Firm"))
                .andExpect(model().attributeExists("orderId"))
                .andReturn();
        } catch (Throwable t) {
            fail("Once the order has been placed, an attribute \"orderId\" should be added to the redirect parameters.");
        }
        

        List<Order> orders = orderService.getOrders();
        assertTrue("After a single order has been submitted, exactly 1 order should be added to the service.", orders.size() == 1);
        String viewname = result.getModelAndView().getViewName();
        assertTrue("The user should be redirected to a new page after an order is successfully placed.", viewname.startsWith("redirect"));
        assertTrue("The returned redirectUrl should contain a string {orderId} for orderId-attribute.", viewname.contains("{orderId}"));
        
        try {
            model().attribute("orderId", orders.get(0).getId()).match(result);
        } catch (Throwable t) {
            fail("Attribute orderId should have the value of the id of the created order. Error: " + t.getMessage());
        }
        
    }
    
    
    @Test
    @Points("W4E05.2")
    public void finallyOrderDetailsAreShown() throws Exception {
        InMemoryOrderService orderService = new InMemoryOrderService();

        String name = UUID.randomUUID().toString().substring(0, 6);
        String address = UUID.randomUUID().toString().substring(0, 6);

        MvcResult result = null;
        try {
            result = createControllerWithViewResolver(orderService)
                .perform(post("/order")
                .param("name", name)
                .param("address", address)
                .param("items", "The Firm"))
                .andExpect(model().attributeExists("orderId"))
                .andReturn();
        } catch (Throwable t) {
            fail("Once the order has been placed, an attribute \"orderId\" should be added to the redirect parameters.");
        }

        List<Order> orders = orderService.getOrders();
        assertTrue("After a single order has been submitted, exactly 1 order should be added to the service.", orders.size() == 1);
        String viewname = result.getModelAndView().getViewName();
        assertTrue("The user should be redirected to a new page after an order is successfully placed.", viewname.startsWith("redirect"));
        assertTrue("The returned redirectUrl should contain a string {orderId} for orderId-attribute.", viewname.contains("{orderId}"));
        
        
    }

    private MockMvc createControllerWithViewResolver() {
        return createControllerWithViewResolver(null);
    }

    private MockMvc createControllerWithViewResolver(OrderService orderService) {
        InternalResourceViewResolver viewResolver = new InternalResourceViewResolver();
        viewResolver.setPrefix("/WEB-INF/jsp/");
        viewResolver.setSuffix(".jsp");

        OrderController orderController = new OrderController();
        if (orderService != null) {
            orderController.setOrderService(orderService);
        }

        return standaloneSetup(orderController)
                .setViewResolvers(viewResolver).build();
    }
}
