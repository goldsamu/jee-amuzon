package fr.amu.models;

import java.util.List;
import java.util.Optional;

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
public class ProductRepositoryTests
{
	// la base de données est auto remplie grâce àsrc/main/java/resources/data.sql
	@Autowired
	ProductRepository pr;

	@Test // dire que c'est un test (annotation classique de JUnit)
	@Transactional // pour gérer les transactions
	@Rollback(true) // pour remettre la BDD dans son état initial
	public void add()
	{
		Long generatedId = pr.save(new Product()).getId();
		List<Product> products = pr.findAll();
		Assert.assertEquals(generatedId, Long.valueOf(products.get(products.size() - 1).getId()));
	}
	
	@Test // dire que c'est un test (annotation classique de JUnit)
	@Transactional // pour gérer les transactions
	@Rollback(true) // pour remettre la BDD dans son état initial
	public void update()
	{
		System.out.println("Uuuuuuuuuuuuupdate");
		Product p = pr.save(new Product());
		System.out.println(p);
		p.setProductTitle("test");
		p.setCategory(new Category("test"));
		p.setDescription("test");
		System.out.println(pr.save(p));
		Optional<Product> op = pr.findById(p.getId());
		Product p2 = op.get();
		Assert.assertEquals(p2.getProductTitle(), "test");
		Assert.assertEquals(p2.getCategory(), "test");
		Assert.assertEquals(p2.getDescription(), "test");
		System.out.println("Uuuuuuuuuuuuupdateeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee" + p2);
	}
	
	@Test // dire que c'est un test (annotation classique de JUnit)
	@Transactional // pour gérer les transactions
	@Rollback(true) // pour remettre la BDD dans son état initial
	public void delete()
	{
		pr.save(new Product());
		Optional<Product> op = pr.findById(1l);
		Product p = op.get();
		Assert.assertTrue(op.isPresent());
		pr.delete(p);
		op = pr.findById(1l);
		Assert.assertFalse(op.isPresent());
	}
	
	@Test // dire que c'est un test (annotation classique de JUnit)
	@Transactional // pour gérer les transactions
	@Rollback(true) // pour remettre la BDD dans son état initial
	public void findAll()
	{
		pr.save(new Product());
		pr.save(new Product());
		List<Product> listProducts = pr.findAll();
		Assert.assertEquals(listProducts.size(), 2);
	}
	
	@Test // dire que c'est un test (annotation classique de JUnit)
	@Transactional // pour gérer les transactions
	@Rollback(true) // pour remettre la BDD dans son état initial
	public void findById()
	{
		pr.save(new Product());
		Optional<Product> op = pr.findById(1l);
		Product p = op.get();
		Assert.assertEquals(p.getId().longValue(), 1);
	}
	
	
	@Test // dire que c'est un test (annotation classique de JUnit)
	@Transactional // pour gérer les transactions
	@Rollback(true) // pour remettre la BDD dans son état initial
	public void findByCategory()
	{
		Product p = new Product();
		p.setProductTitle("shirt");
		p.setCategory(new Category("test"));
		p.setDescription("test");
		pr.save(p);
		List<Product> listProducts = pr.findByCategory(new Category("test"));
		Assert.assertEquals(listProducts.isEmpty(), true);
		listProducts = pr.findByCategory(new Category("test"));
		Assert.assertEquals(listProducts.size(), 1);
		Assert.assertEquals(listProducts.get(0).getProductTitle(), "shirt");
	}
	
	
	
	
}
