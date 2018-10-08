package dataAccessLayer;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import model.Order;

public class OrderDAO extends AbstractDAO<Order> {
	public OrderDAO() {
	}

	public List<Order> findAll() {
		return super.findAll();
	}

	public Order findById(int id) {
		return super.findById(id);
	}

	public List<Order> createObjects(ResultSet resultSet) {
		return super.createObjects(resultSet);
	}

	public void insertObjects(Order instance) throws SQLException, SecurityException, IllegalArgumentException,
			InstantiationException, IllegalAccessException, IntrospectionException, InvocationTargetException {
		super.insertObject(instance);
	}

	public void update(Order instance) {
		super.update(instance.getId(), instance);
	}
}
