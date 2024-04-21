package org.jakab.jakarta.Beans;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.servlet.http.HttpSession;
import org.jakab.jakarta.domain.Order;
import org.jakab.jakarta.service.OrderService;
import org.jakab.jakarta.service.SessionHandlerFactory;
import org.jakab.jakarta.service.SessionHandlerService;

import java.util.HashMap;
import java.util.Map;

@Named("orderReceived")
@RequestScoped
public class OrderReceivedBean {

    private Map<Integer, String> itemQuantities;

    @Inject
    private OrderService service;

    @Inject
    private SessionHandlerService handler;

    @PostConstruct
    public void init() {
        handler = SessionHandlerFactory.getHandler();
        itemQuantities = new HashMap<>();
    }



    public String placeOrder() {
        // Call the service method to add the order to the database
        Order order = service.placeOrder(itemQuantities);
        handler.newOrder(order);

        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession) facesContext.getExternalContext().getSession(true);
        session.setAttribute("orderId", order.getId());

        return "thankYou?faces-redirect=true";
    }

    public Map<Integer, String> getItemQuantities() {
        return itemQuantities;
    }

    public void setItemQuantities(Map<Integer, String> itemQuantities) {
        this.itemQuantities = itemQuantities;
    }
}
