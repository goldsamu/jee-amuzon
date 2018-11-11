package fr.amu.models;

public class Product
{
	private int id;
	private String category;
	private String productTitle;
	private String description;

	public Product()
	{
	}
	
	public int getId()
	{
		return id;
	}
	
	public void setId(int id)
	{
		this.id = id;
	}

	public String getCategory()
	{
		return category;
	}

	public void setCategory(String category)
	{
		this.category = category;
	}

	public String getProductTitle()
	{
		return productTitle;
	}

	public void setProductTitle(String productTitle)
	{
		this.productTitle = productTitle;
	}

	@Override
	public String toString()
	{
		return String.format("category = %s, title = %s", category, productTitle);
		
	}
	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

}
