package fr.amu.controllers;

import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

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

	@PostMapping("/add") // raccourci pour @RequestMapping( value = "/", method = RequestMethod.GET)
	public String add(Map<String, Object> model, HttpServletRequest req)
	{
		ps.addProduct(req.getParameter("productTitle"), req.getParameter("category"), req.getParameter("description"));
		String sessionUser = (String) httpSession.getAttribute("user");
		model.put("products", ps.getProducts());
		System.out.println("session user = " + sessionUser);
		return "redirect:/";
	}

	@PostMapping("/remove") // raccourci pour @RequestMapping( value = "/", method = RequestMethod.GET)
	public String delete(Map<String, Object> model, HttpServletRequest req)
	{
		ps.removeProduct(Integer.parseInt(req.getParameter("productId")));
		String sessionUser = (String) httpSession.getAttribute("user");
		model.put("products", ps.getProducts());
		System.out.println("session user = " + sessionUser);
		return "redirect:/";
	}

	@PostMapping("/category") // raccourci pour @RequestMapping( value = "/", method = RequestMethod.GET)
	public String category(Map<String, Object> model, HttpServletRequest req)
	{
		String cat = req.getParameter("category");
		String sessionUser = (String) httpSession.getAttribute("user");
		if (cat.equals("allproducts"))
			model.put("products", ps.getProducts());
		else
			model.put("products", ps.getCategoryProducts(cat));
		System.out.println("session user = " + sessionUser);
		return "homepage";
	}

}