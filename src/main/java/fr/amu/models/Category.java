package fr.amu.models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Category
{
	@Id
	private String name;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Product> products;

	public Category()
	{
		products = new HashSet<Product>();
	}
	
	public Category(String name)
	{
		this.name = name;
		products = new HashSet<Product>();
	}

	public Category(String name, Set<Product> products)
	{
		this.name = name;
		this.products = products;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Set<Product> getProducts()
	{
		return products;
	}

	public void setProducts(Set<Product> products)
	{
		this.products = products;
	}

}
