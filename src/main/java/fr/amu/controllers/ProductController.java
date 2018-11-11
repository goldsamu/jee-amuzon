package fr.amu.controllers;

import java.util.Map;
import java.util.Optional;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import fr.amu.models.Category;
import fr.amu.services.CategoryService;
import fr.amu.services.ProductService;

@Controller
public class ProductController
{

	@Autowired
	private HttpSession httpSession;
	@Autowired
	ServletContext context;
	@Autowired
	ProductService ps;
	@Autowired
	CategoryService cs;

	@PostMapping("/add")
	public String add(Map<String, Object> model, HttpServletRequest req)
	{
		Optional<Category> op = cs.getCategory(req.getParameter("category"));
		Category cat;
		if(op.isPresent())
		{
			cat = op.get();
		}
		else
		{
			cat = new Category(req.getParameter("category"));
		}
		ps.addProduct(req.getParameter("productTitle"), cat, req.getParameter("description"));
		String sessionUser = (String) httpSession.getAttribute("user");
		model.put("products", ps.getProducts());
		System.out.println("session user = " + sessionUser);
		return "redirect:/";
	}

	@PostMapping("/remove") // raccourci pour @RequestMapping( value = "/",
							// method = RequestMethod.GET)
	public String delete(Map<String, Object> model, HttpServletRequest req)
	{
		ps.removeProduct(Long.parseLong(req.getParameter("productId")));
		String sessionUser = (String) httpSession.getAttribute("user");
		model.put("products", ps.getProducts());
		System.out.println("session user = " + sessionUser);
		return "redirect:/";
	}

	@PostMapping("/category") // raccourci pour @RequestMapping( value = "/",
								// method = RequestMethod.GET)
	public String category(Map<String, Object> model, HttpServletRequest req)
	{
		String cat = req.getParameter("category");
		Optional<Category> op = cs.getCategory(req.getParameter("category"));
		String sessionUser = (String) httpSession.getAttribute("user");
		if (cat.equals("allproducts"))
			model.put("products", ps.getProducts());
		else if (op.isPresent())
			model.put("products", ps.getCategoryProducts(op.get()));
		System.out.println("session user = " + sessionUser);
		return "homepage";
	}

}
