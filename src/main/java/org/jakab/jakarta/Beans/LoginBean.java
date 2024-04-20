package org.jakab.jakarta.Beans;

import jakarta.faces.annotation.ManagedProperty;
import jakarta.faces.context.ExternalContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;

import java.io.Serializable;

@Named
@RequestScoped
public class LoginBean implements Serializable {

    private String username;
    private String password;
    private String pageId;

    // Getters and setters for username and password

    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        ExternalContext externalContext = context.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        String viewId = request.getRequestURI();
        System.out.println(viewId);
        setPageId(viewId);
        try {
            request.login(this.username, this.password);
            if (pageId.contains("result")){
                return "result?faces-redirect=true";
            } else if (pageId.contains("processorders")) {
                return "processorders?faces-redirect=true";
            }else{
                return "index?faces-redirect=true";
            }

        } catch (ServletException e) {
            context.addMessage(null, new FacesMessage("Login failed."));
            return "Error";
        }

    }

    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            request.logout();
            return "/index?faces-redirect=true";
        } catch (ServletException e) {
            context.addMessage(null, new FacesMessage("Logout failed."));
            return "error";
        }
    }
    public String getPageId() {
        return pageId;
    }

    public void setPageId(String pageId) {
        this.pageId = pageId;
    }


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
