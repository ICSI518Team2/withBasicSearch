package com.hemanshu;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.hemanshu.ItemService;

import java.util.List;

@Controller
public class LoginController {
	@Autowired
	UserRepo ur;
	
	@Autowired
	UserService userService;
	
	@Autowired
	ItemService itemservice;



    public Item getProducts(String key){
        List<Item> itemList = itemservice.showAllitems();
        List<Item> resultSet = null;
        for(Item item: itemList){
        	System.out.println(item.product_name + " "+ key);
            if (item.product_name.equals(key) ){
                return item;
            }
            System.out.println(item.description.indexOf(key));
            if (item.description.indexOf(key)!=-1){
                return item;
            }

        }
        return null;
    }

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

	@RequestMapping("/search")
	public String searchProducts(@RequestParam String key, HttpServletRequest request) {
		System.out.println("Yo asshole, you typed "+ key);
		String[] pusher = key.split(",");
		Item a = getProducts(pusher[1]);
		if (a == null)
		{
			request.setAttribute("mode", "NO_PRODUCT");
			return "allproducts";
		}
		else {
		request.setAttribute("item", a);
		request.setAttribute("mode", "SEARCH");
		return "allproducts";
		}
	}

	@RequestMapping("/delete-user")
	public String deleteUser(@RequestParam int id, HttpServletRequest request) {
		userService.deleteMyUser(id);
		request.setAttribute("users", userService.showAllUsers());
		request.setAttribute("mode", "ALL_USERS");
		return "adminpage";
	}
	public Item findByIDnum(int id){
		for(Item item: itemservice.showAllitems()){
			if (item.id == id) {
				return item;
			};
		}
		return null;
	}



	@RequestMapping("/buy-item")
	public String renderBuyPage(@RequestParam int id, HttpServletRequest request) {
		Item a= findByIDnum(id);
		request.setAttribute("item", a);
		request.setAttribute("mode", "ALL_USERS");
		return "buypage";
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
