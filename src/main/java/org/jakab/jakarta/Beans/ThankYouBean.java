package org.jakab.jakarta.Beans;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.security.DeclareRoles;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.jakab.jakarta.service.OrderService;

import java.text.DecimalFormat;
import java.util.Map;

@Named("thankYou")
@RequestScoped
public class ThankYouBean {
    private Long orderId = 1L;
    private String status;

    private Double total;

    @Inject
    private OrderService service;

    @PostConstruct
    public void init() {

        status = service.getStatus(orderId);
        total = service.getOrderTotal(orderId);
    }

    public String getStatus() {
        return status;
    }

    public Double getTotal() {
        return total;
    }

    public String getTotalFormatted() {
        DecimalFormat df = new DecimalFormat("#.##"); // Define the format to have two decimal places
        return df.format(total);
    }

    public String refreshStatus(){
        return service.getStatus(orderId);
    }

}
