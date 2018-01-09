package com.amanuel.dojoSubscription.controllers;

import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.amanuel.dojoSubscription.models.Pack;
import com.amanuel.dojoSubscription.models.Role;
import com.amanuel.dojoSubscription.models.User;
import com.amanuel.dojoSubscription.services.UserService;
import com.amanuel.dojoSubscription.validator.UserValidator;

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
    @InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		dateFormat.setLenient(false);
		binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
	}
    
    @RequestMapping("/registration")
    public String registerForm(@Valid @ModelAttribute("user") User user) {
        return "loginPage";
    }
    
    @PostMapping("/registration")
    public String registration(@Valid @ModelAttribute("user") User user,@ModelAttribute("selection") User selection, BindingResult result,Principal pricipal, Model model, HttpSession session) {
    		List<Pack> pack = userService.findAllPackages();
//    		Long userId = user.getId();
        userValidator.validate(user, result);
    		if (result.hasErrors()) {
            return "loginPage";
        }
    		if(userService.findAll().size() == 0) {
    		userService.saveUserWithAdminRole(user);
    		return "redirect:/login";
    		}
    		else {
    			model.addAttribute("packList",pack);
	    		userService.saveWithUserRole(user);
	    		User finduser = userService.findByEmail(user.getEmail());
	    		System.out.println("user Id: " + finduser.getId());
	    		System.out.println("user and registion :" + finduser );
	    		model.addAttribute("loggedInUser", finduser);
	    		return "selectionPage";
    		}

    }
    
    @PostMapping("/addscription")
    public String newScription(@RequestParam(value = "dueDate") Date dueDate,@RequestParam(value = "loggedInUser") Long loggedInUser, @RequestParam(value = "pack") Long packId,@ModelAttribute("selection") User user, BindingResult result, Principal principal, Model model) {
    		System.out.println("packId: " + packId);
    		System.out.println("helooo loggedInUser:" + loggedInUser);
    		Pack myPack = userService.findPackById(packId);
    		User myUser = userService.findById(loggedInUser);
    		myUser.setPack(myPack);
    		myUser.setDueDate(dueDate);
    		List<User> userlist = myPack.getUser();
    		userlist.add(myUser);
    		userService.saveUser(myUser);
    		userService.saveNewPackage(myPack);
    		return "redirect:/registration";
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
        for (int i = 0; i < role.size(); i++) {
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
    public String show(@ModelAttribute("pack") Pack pack,Principal principal, Model model) {
    		String username = principal.getName();
    		
    		List<User> allusers = userService.findAll();
    		List<Pack> allpackages = userService.findAllPackages();
    		model.addAttribute("currentUsers", allusers);
    		model.addAttribute("currentUser", userService.findByEmail(username));
    		model.addAttribute("allpackages", allpackages);

    		return "adminPage";
    }
    @PostMapping("/admin/newPackage")
    public String newPackage(@ModelAttribute("pack") Pack pack, BindingResult result, Principal principal, Model model) {
    		pack.setStatus(true);
    		userService.saveNewPackage(pack);
    		return "redirect:/admin";
    }
    @RequestMapping("/pack/delete/{id}")
    public String deletePack(@PathVariable("id") Long id, Model model) {
    		userService.deletePack(id);
    	
    		return "redirect:/admin";
    }
    @RequestMapping("/admin/activate/{id}")
    public String activatePack(@PathVariable("id") Long id, Model model) {
    		Pack pack = userService.findPackById(id);
    		
    		pack.setStatus(true);
    		userService.saveStatus(pack);
    		return "redirect:/admin";
    }
    @RequestMapping("/admin/deactivate/{id}")
    public String deactivatePack(@PathVariable("id") Long id, Model model) {
    		Pack pack = userService.findPackById(id);
    		pack.setStatus(false);
    		userService.saveStatus(pack);
    		
    		
    		return "redirect:/admin";
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
