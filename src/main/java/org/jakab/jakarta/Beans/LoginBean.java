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

    private String usernameInput;
    private String passwordInput;

    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        String isSuccessful = "";
        try {
            HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
            request.login(this.usernameInput, this.passwordInput);
            isSuccessful = "success";


        } catch (ServletException e) {
            context.addMessage(null, new FacesMessage("Login failed."));
            isSuccessful = "error";

        }finally {
            return isSuccessful;

        }

    }
    public String logout() {
        FacesContext context = FacesContext.getCurrentInstance();
        HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();
        try {
            request.logout();
            return "/index.xhtml?faces-redirect=true";
        } catch (ServletException e) {
            context.addMessage(null, new FacesMessage("Logout failed."));
            return "error";
        }
    }

    public String getUsernameInput() {
        return usernameInput;
    }

    public void setUsernameInput(String usernameInput) {
        this.usernameInput = usernameInput;
    }

    public String getPasswordInput() {
        return passwordInput;
    }

    public void setPasswordInput(String passwordInput) {
        this.passwordInput = passwordInput;
    }




}
