package com.hemanshu;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class LoginController {
	@Autowired
	UserRepo ur;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ItemService itemservice;
	
	@RequestMapping("/admin")
	public String Welcome(HttpServletRequest request) {
		request.setAttribute("mode", "MODE_HOME");
		return "adminpage";
	}

	@RequestMapping("/welcome")
	public String homepage()
	{
		return "index";
	}

	@RequestMapping("/signup")
	public String signup()
	{
		return "Signup";
	}

	@RequestMapping("/contact")
	public String contact()
	{
		return "contact";
	}

	@RequestMapping("/saveuser")
	public String saveuser(@ModelAttribute("user") User user,BindingResult bindingResult,HttpServletRequest request)
	{
		user.setRole("user");
		ur.save(user);
		return "index";
	}

	@RequestMapping("/login")
	public String login(@ModelAttribute("user") User user,BindingResult bindingResult,HttpServletRequest request)
	{
	   System.out.println(user.getEmailID()+"    ####"+ user.getPassword());
		if(ur.findByEmailIDAndPassword(user.getEmailID(),user.getPassword())!=null)
		{
			User d=ur.findByEmailIDAndPassword(user.getEmailID(),user.getPassword() );
			if(d.getRole().equalsIgnoreCase("admin"))
			{
				request.setAttribute("mode", "MODE_HOME");
				return "adminpage";
			}
			else
				return "userpage";
		}
		return "index";
	}
	
	@GetMapping("/show-users")
	public String showAllUsers(HttpServletRequest request) {
		request.setAttribute("users", userService.showAllUsers());
		request.setAttribute("mode", "ALL_USERS");
		return "adminpage";
	}

	@RequestMapping("/delete-user")
	public String deleteUser(@RequestParam int id, HttpServletRequest request) {
		userService.deleteMyUser(id);
		request.setAttribute("users", userService.showAllUsers());
		request.setAttribute("mode", "ALL_USERS");
		return "adminpage";
	}

	@GetMapping("/all-items")
	public String showAllItems(HttpServletRequest request) {
		request.setAttribute("item", itemservice.showAllitems());
		request.setAttribute("mode", "All_items");
		return "adminpage";
	} 
	
	@RequestMapping("/delete-item")
	public String deleteItem(@RequestParam int ID, HttpServletRequest request) {
		itemservice.deleteMyitem(ID);
		request.setAttribute("item", itemservice.showAllitems());
		request.setAttribute("mode", "All_items");
		return "adminpage";
	}

	@RequestMapping("/allproducts")
	public String allproducts(HttpServletRequest request) {
		request.setAttribute("item", itemservice.showAllitems());
		request.setAttribute("mode", "All_Products");
		return "allproducts";
	}
	@GetMapping("/allproductslist")
	public @ResponseBody
	Iterable<Item> getAllProducts()
     {
		// This returns a JSON or XML with the items

		return itemservice.showAllitems();
	}
}
