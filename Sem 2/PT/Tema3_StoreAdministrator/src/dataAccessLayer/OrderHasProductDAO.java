package dataAccessLayer;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.Order;
import model.OrderHasProduct;
import model.Product;

public class OrderHasProductDAO extends AbstractDAO<OrderHasProduct> {
	public OrderHasProductDAO() {
	}

	public List<OrderHasProduct> findAll() {
		return super.findAll();
	}

	public OrderHasProduct findById(int id) {
		return super.findById(id);
	}

	public List<OrderHasProduct> createObjects(ResultSet resultSet) {
		return super.createObjects(resultSet);
	}

	public void insertObjects(OrderHasProduct instance) throws SQLException, SecurityException, IllegalArgumentException,
			InstantiationException, IllegalAccessException, IntrospectionException, InvocationTargetException {
		super.insertObject(instance);
	}
	
}
