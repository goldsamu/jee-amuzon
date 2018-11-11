package fr.amu.services;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import fr.amu.models.Product;
import fr.amu.models.ProductDAO;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTests
{
	@Autowired
	ProductService ps;
	@Autowired
	ProductDAO prdao;

	@Test
	@Rollback(true)
	public void addProduct()
	{
		Product p = new Product();
		p.setProductTitle("test");
		p.setCategory("test");
		p.setDescription("test");
		Integer generatedId = ps.addProduct(p);
		List<Product> products = ps.getProducts();
		Assert.assertEquals(generatedId, Integer.valueOf(products.get(products.size() - 1).getId()));
		Integer generatedId2 = ps.addProduct("test2", "test", "test");
		products = ps.getProducts();
		Assert.assertEquals(generatedId2, Integer.valueOf(products.get(products.size() - 1).getId()));
	}
	
	@Test
	public void getProduct()
	{
		Product product = ps.getProduct(0);
		Assert.assertTrue(ps.getProduct(0) instanceof Product);
		Assert.assertTrue(product.getId() == 0);
	}
	
	@Test
	public void getProducts()
	{
		List<Product> listProducts = ps.getProducts();
		Assert.assertEquals(listProducts.size(), 2);
		Assert.assertEquals(listProducts.get(0).getProductTitle(), "shirt");
	}
}