package com.amanuel.loginRegistration.controllers;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.amanuel.loginRegistration.models.Role;
import com.amanuel.loginRegistration.models.User;
import com.amanuel.loginRegistration.services.UserService;
import com.amanuel.loginRegistration.validator.UserValidator;

@Controller
public class Users {
    
	 // NEW
    private UserValidator userValidator;
	private UserService userService;
    
    // NEW
    public Users(UserService userService, UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }
    
    @RequestMapping("/registration")
    public String registerForm(@Valid @ModelAttribute("user") User user) {
        return "loginPage";
    }
    
    @PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("user") User user,  BindingResult result,Principal pricipal, Model model, HttpSession session) {

        userValidator.validate(user, result);
    		if (result.hasErrors()) {
            return "loginPage";
        }
    		if(userService.findAll().size() == 0) {
    		userService.saveUserWithAdminRole(user);
    		return "redirect:/login";
    		}
    		else {
    		userService.saveWithUserRole(user);
    		return "redirect:/login";
    		}

    }
    
    
    @RequestMapping("/login")
    public String login(@ModelAttribute("user") User user, @RequestParam(value="error", required=false) String error, @RequestParam(value="logout", required=false) String logout, Model model) {
        if(error != null) {
            model.addAttribute("errorMessage", "Invalid Credentials, Please try again.");
        }
        
        if(logout != null) {
            model.addAttribute("logoutMessage", "Logout Successfull!");
        }
        return "loginPage";
    }
    @RequestMapping(value = {"/", "/home"})
    public String home(Principal principal, Model model) {
        String email = principal.getName();
        User user = userService.findByEmail(email);
        model.addAttribute("currentUser", user);
        
        
        List<Role> role = user.getRoles();
        for (int i = 0; i <= role.size(); i++) {
	        if(role.get(i).getName().equals("ROLE_ADMIN")) {
	        		
	        		return "redirect:/admin";
	        }else {
	        	model.addAttribute("currentUser", user);
	        return "homePage";
	        	}
	        
        }
        return "homePage";
    }
    @RequestMapping("/admin")
    public String show(Principal principal, Model model) {
    		String username = principal.getName();
    		
    		List<User> allusers = userService.findAll();
    		model.addAttribute("currentUsers", allusers);
    		model.addAttribute("currentUser", userService.findByEmail(username));
    		return "adminPage";
    }
    
    @RequestMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, Model model) {
    		userService.deleteUser(id);
    	
    		return "redirect:/admin";
    }
    @RequestMapping("/user/edit/{id}")
    public String makeNewAdmin(@PathVariable("id") Long id, Model model, Principal principal) {
	    	User user = userService.findById(id);
    		userService.saveUserWithAdminRole(user);
    		return "redirect:/admin";
    }
    
    
    
    
}
