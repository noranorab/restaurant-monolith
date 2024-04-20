package org.jakab.jakarta.Beans;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.jakab.jakarta.service.OrderService;

import java.util.HashMap;
import java.util.Map;

@Named("orderReceived")
@RequestScoped
public class OrderReceivedBean {

    private Map<Integer, String> itemQuantities;

    @Inject
    private OrderService service;

    @PostConstruct
    public void init() {

        itemQuantities = new HashMap<>();
    }



    public String placeOrder() {
        // Call the service method to add the order to the database
        service.placeOrder(itemQuantities);
        return "thankYou?faces-redirect=true";
    }

    public Map<Integer, String> getItemQuantities() {
        return itemQuantities;
    }

    public void setItemQuantities(Map<Integer, String> itemQuantities) {
        this.itemQuantities = itemQuantities;
    }
}
