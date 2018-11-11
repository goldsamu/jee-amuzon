package fr.amu.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fr.amu.models.Category;
import fr.amu.models.Product;
import fr.amu.models.ProductRepository;

@Service
public class ProductService
{

	@Autowired
	ProductRepository pr;

	public Long addProduct(Product product)
	{
		Product p = pr.save(product);
		return p.getId();
	}

	public Long addProduct(String title, Category category, String description)
	{
		Product p = new Product();
		p.setProductTitle(title);
		p.setCategory(category);
		p.setDescription(description);
		Product product = pr.save(p);
		return product.getId();
	}

	public Product getProduct(long id)
	{
		Optional<Product> op = pr.findById(id);
		try
		{
			return op.get();
		} catch (NoSuchElementException e)
		{
			e.getStackTrace();
			return null;
		}
	}

	public void removeProduct(long id)
	{
		pr.delete(getProduct(id));
	}

	public List<Product> getProducts()
	{
		return pr.findAll();
	}

	public List<Product> getCategoryProducts(Category category)
	{
		return pr.findByCategory(category);
	}
}