package fr.amu.models;

import java.sql.PreparedStatement;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Transactional // évite d'avoir à gérer les transactions
@Repository // indique à spring qu'il s'agit d'un accès BDD
public class ProductDAOImpl implements ProductDAO
{
	@Autowired
	JdbcTemplate jdbc;

	@Override
	public Integer add(Product product)
	{
		String sql = "INSERT INTO products(category,productTitle,img,description,date) values(?,?,?,?,?)";
		GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
		// nom de la column générée
		String id_column = "ID";
		// the update method takes an implementation of PreparedStatementCreator which
		// could be a lambda, la méthode update a besoin d'un preparedSatementCreator
		// pour modifier sa valeur de sortie
		// 'con' est la connection JDBC cachée par JDBCTemplate
		jdbc.update(con -> {
			PreparedStatement ps = con.prepareStatement(sql,
					// comme avant on suit l'ordre des ? dans la requete SQL
					new String[] { id_column }); // cet array de String correspond aux clés utilisées pour récupérer les
													// valeur
			// (ordre important, ici en premier car ID est en premier dans CREATE TABLE)
			ps.setString(1, product.getCategory());
			ps.setString(2, product.getProductTitle());
			ps.setString(3, "none");
			ps.setString(4, product.getDescription());
			ps.setString(5, "20180912");
			return ps;
		}, keyHolder); // ceci va capturer les valeurs de sortie voulues dans une hashmap
		// after the update executed we can now get the value of the generated ID
		// une fois l'update faite on récupère les valeurs grâce àla hashmap de
		// .getKeys().
		Integer id = (Integer) keyHolder.getKeys().get(id_column);
		return id.intValue();
	}

	@Override
	public void update(Product product)
	{
		String sql = "UPDATE products SET category = ?, productTitle = ?, description = ?";
		jdbc.update(sql, product.getCategory(), product.getProductTitle(), product.getDescription());
	}

	@Override
	public void delete(Product product)
	{
		String sql = "DELETE FROM products WHERE id = ?";
		jdbc.update(sql, product.getId());
	}

	@Override
	public List<Product> findAll()
	{
		String sql = "SELECT * FROM products";

		RowMapper<Product> rowMapper = new BeanPropertyRowMapper<Product>(Product.class);

		return jdbc.query(sql, rowMapper);
	}

	@Override
	public Product findById(Integer id)
	{
		String sql = "SELECT * FROM products WHERE id = ?";

		RowMapper<Product> rowMapper = new BeanPropertyRowMapper<Product>(Product.class);

		try
		{
			return jdbc.queryForObject(sql, rowMapper, id);
		}
		catch(EmptyResultDataAccessException e)
		{
			System.out.println("EmptyResultDataAccessException : Aucun résultat.");
			return null;
		}
	}

	@Override
	public List<Product> findByCategory(String category)
	{
		String sql = "SELECT * FROM products WHERE category = ?";

		RowMapper<Product> rowMapper = new BeanPropertyRowMapper<Product>(Product.class);

		return jdbc.query(sql, rowMapper, category);
	}

}