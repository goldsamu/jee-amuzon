package fr.amu.models;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductDAOTests
{
	// la base de données est auto remplie grâce àsrc/main/java/resources/data.sql
	@Autowired
	ProductDAO prdao;

	@Test // dire que c'est un test (annotation classique de JUnit)
	@Transactional // pour gérer les transactions
	@Rollback(true) // pour remettre la BDD dans son état initial
	public void add()
	{
		Integer generatedId = prdao.add(new Product());
		List<Product> products = prdao.findAll();
		Assert.assertEquals(generatedId, Integer.valueOf(products.get(products.size() - 1).getId()));
	}
	
	@Test // dire que c'est un test (annotation classique de JUnit)
	@Transactional // pour gérer les transactions
	@Rollback(true) // pour remettre la BDD dans son état initial
	public void update()
	{
		Product p = prdao.findById(0);
		p.setProductTitle("test");
		p.setCategory("test");
		p.setDescription("test");
		prdao.update(p);
		Product p2 = prdao.findById(0);
		Assert.assertEquals(p2.getProductTitle(), "test");
		Assert.assertEquals(p2.getCategory(), "test");
		Assert.assertEquals(p2.getDescription(), "test");
	}
	
	@Test // dire que c'est un test (annotation classique de JUnit)
	@Transactional // pour gérer les transactions
	@Rollback(true) // pour remettre la BDD dans son état initial
	public void delete()
	{
		Product p = prdao.findById(0);
		prdao.delete(p);
		Product p2 = prdao.findById(0);
		Assert.assertNull(p2);
	}
	
	@Test // dire que c'est un test (annotation classique de JUnit)
	@Transactional // pour gérer les transactions
	@Rollback(true) // pour remettre la BDD dans son état initial
	public void findAll()
	{
		List<Product> listProducts = prdao.findAll();
		Assert.assertEquals(listProducts.size(), 2);
		Assert.assertEquals(listProducts.get(0).getProductTitle(), "shirt");
	}
	
	@Test // dire que c'est un test (annotation classique de JUnit)
	@Transactional // pour gérer les transactions
	@Rollback(true) // pour remettre la BDD dans son état initial
	public void findById()
	{
		Product p = prdao.findById(0);
		Assert.assertEquals(p.getProductTitle(), "shirt");
	}
	
	
	@Test // dire que c'est un test (annotation classique de JUnit)
	@Transactional // pour gérer les transactions
	@Rollback(true) // pour remettre la BDD dans son état initial
	public void findByCategory()
	{
		List<Product> listProducts = prdao.findByCategory("test");
		Assert.assertEquals(listProducts.isEmpty(), true);
		listProducts = prdao.findByCategory("clothes");
		Assert.assertEquals(listProducts.size(), 1);
		Assert.assertEquals(listProducts.get(0).getProductTitle(), "shirt");
	}
	
	
	
	
	
}
