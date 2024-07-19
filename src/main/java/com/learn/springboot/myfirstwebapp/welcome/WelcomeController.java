package com.learn.springboot.myfirstwebapp.welcome;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Controller
@SessionAttributes("username")
public class WelcomeController {

    // @GetMapping("/")
    // public String goToWelcomePage(ModelMap model) {
    // model.put("username", "Kalpataru");
    // return "welcome";
    // }
    @GetMapping("/")
    public String goToWelcomePage(ModelMap model) {
        model.put("username", getLoggedInUserName());
        return "welcome";
    }

    private String getLoggedInUserName() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	return authentication.getName();
    }
}
