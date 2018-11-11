package fr.amu.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.amu.models.Product;
import fr.amu.models.ProductDAO;

@Service
public class ProductService
{

	@Autowired
	ProductDAO productDAO;
	
	public Integer addProduct(Product product)
	{
		return productDAO.add(product);
	}
	
	public Integer addProduct(String title, String category, String description)
	{
		Product p = new Product();
		p.setProductTitle(title);
		p.setCategory(category);
		p.setDescription(description);
		return productDAO.add(p);
	}

	public Product getProduct(int id)
	{
		return productDAO.findById(id);
	}

	public void removeProduct(int id)
	{
		productDAO.delete(getProduct(id));
	}

	public List<Product> getProducts()
	{
		return productDAO.findAll();
	}

	public List<Product> getCategoryProducts(String category)
	{
		return productDAO.findByCategory(category);
	}
}