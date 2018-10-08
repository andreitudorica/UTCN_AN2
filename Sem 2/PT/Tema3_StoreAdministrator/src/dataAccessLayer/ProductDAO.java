package dataAccessLayer;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.Product;

public class ProductDAO extends AbstractDAO<Product> {
	public ProductDAO() {
	}

	public List<Product> findAll() {
		return super.findAll();
	}

	public Product findById(int id) {
		return super.findById(id);
	}

	public List<Product> createObjects(ResultSet resultSet) {
		return super.createObjects(resultSet);
	}

	public void insertObjects(Product instance) throws SQLException, SecurityException, IllegalArgumentException,
			InstantiationException, IllegalAccessException, IntrospectionException, InvocationTargetException {
		super.insertObject(instance);
	}

	public void update(Product instance) {
		super.update(instance.getId(), instance);
	}
}
