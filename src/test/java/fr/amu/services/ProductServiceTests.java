package fr.amu.services;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import fr.amu.models.Category;
import fr.amu.models.Product;
import fr.amu.models.ProductRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceTests
{
	@Autowired
	ProductService ps;
	@Autowired
	CategoryService cs;
	@Autowired
	ProductRepository pr;

	@Test
	@Transactional
	@Rollback(true)
	public void addProduct()
	{
		Product p = new Product();
		p.setProductTitle("test");
		p.setCategory(new Category("test"));
		p.setDescription("test");
		Long generatedId = ps.addProduct(p);
		List<Product> products = ps.getProducts();
		Assert.assertEquals(generatedId, Long.valueOf(products.get(products.size() - 1).getId()));
		Long generatedId2 = ps.addProduct("test2", new Category("test"), "test");
		products = ps.getProducts();
		Assert.assertEquals(generatedId2, Long.valueOf(products.get(products.size() - 1).getId()));
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void getProduct()
	{
		ps.addProduct("test2", new Category("test"), "test");
		Assert.assertNull(ps.getProduct(0));
		Assert.assertTrue(ps.getProduct(1) instanceof Product);
	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void getProducts()
	{
		ps.addProduct("shirt", new Category("test"), "test");
		ps.addProduct("test2", new Category("test"), "test");
		List<Product> listProducts = ps.getProducts();
		Assert.assertEquals(listProducts.size(), 2);
		Assert.assertEquals(listProducts.get(0).getProductTitle(), "shirt");
	}
}