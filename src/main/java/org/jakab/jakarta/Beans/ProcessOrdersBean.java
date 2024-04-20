package org.jakab.jakarta.Beans;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.security.DeclareRoles;
import jakarta.annotation.security.RolesAllowed;
import jakarta.ejb.Stateless;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import org.jakab.jakarta.domain.Order;
import org.jakab.jakarta.service.OrderService;
import org.jakab.jakarta.service.SessionHandlerFactory;
import org.jakab.jakarta.service.SessionHandlerService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named("processOrders")
@RequestScoped
public class ProcessOrdersBean {

    private List<Order> orders;

    private List<String> statuses;

    public Map<Long, String> getSelectedOrders() {
        return selectedOrders;
    }

    public void setSelectedOrders(Map<Long, String> selectedOrders) {
        this.selectedOrders = selectedOrders;
    }

    private Map<Long, String> selectedOrders;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Long id;

    private String status;

    @Inject
    private OrderService service;

    @PostConstruct
    public void init(){
        SessionHandlerService handler = SessionHandlerFactory.getHandler();
        orders = service.getAllOrders();
        statuses = new ArrayList<>();
        statuses.add("order accepted");
        statuses.add("payment received");
        statuses.add("being prepared");
        statuses.add("ready for collection");
        selectedOrders = new HashMap<>();

        handler.amendOrder(order);

    }

    public List<Order> getOrders() {
        return orders;
    }

    public List<String> getStatuses() {
        return statuses;
    }

    public void updateOrderStatus(){
        if (selectedOrders != null && !selectedOrders.isEmpty()) {
            for (Map.Entry<Long, String> entry : selectedOrders.entrySet()) {
                Long orderId = entry.getKey();
                String status = entry.getValue();
                service.updateOrderStatus(orderId, status);
            }
        }



    }

    public void setStatus(String status) {
        this.status = status;
    }


    public String getStatus() {
        return status;
    }
}
