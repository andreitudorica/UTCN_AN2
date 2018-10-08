package dataAccessLayer;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AbstractDAO<T> {
	protected static final Logger LOGGER = Logger.getLogger(AbstractDAO.class.getName());

	private final Class<T> type;

	@SuppressWarnings("unchecked")
	public AbstractDAO() {
		this.type = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];

	}

	private static boolean isNumeric(String s)
	{
		try{
			Double.parseDouble(s);
		}catch (Exception e) {
			return false;
		}
		return true;
	}

	private String generateUpdatePairs(int id, T ModifiedInstance) {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		/* Iterate the column-names */
		for (Field f : type.getDeclaredFields()) {

			if (first)
				first = false;
			else
				sb.append(", ");
			
			
			sb.append(f.getName());
			
			sb.append(" = ");
			
			PropertyDescriptor propertyDescriptor;
			try {
				propertyDescriptor = new PropertyDescriptor(f.getName(), type);
				Method method = propertyDescriptor.getReadMethod();
				Object value = method.invoke(ModifiedInstance);
				if(isNumeric(value.toString()))
					sb.append(value);
				else if(value.toString()=="false")
					sb.append('0');
				else if(value.toString()=="true")
					sb.append('1');
				else 
				{
					sb.append("'");
					sb.append(value);
					sb.append("'");
				}
			} catch (IntrospectionException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return sb.toString();

	}

	private String getColumns(boolean usePlaceHolders) {
		StringBuilder sb = new StringBuilder();
		boolean first = true;
		/* Iterate the column-names */
		for (Field f : type.getDeclaredFields()) {
			if (first)
				first = false;
			else
				sb.append(", ");
			if (usePlaceHolders)
				sb.append("?");
			else
				sb.append(f.getName());
		}
		return sb.toString();
	}

	public List<T> findAll() {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = "SELECT " + " * " + " FROM tema3db." + type.getSimpleName();
		System.out.println(query);
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();
			return createObjects(resultSet);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return null;
	}

	public T findById(int id) {
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		String query = "SELECT " + " * " + " FROM tema3db." + type.getSimpleName() + " WHERE " + "id" + " =" +id;
		System.out.println(query);
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			resultSet = statement.executeQuery();

			return createObjects(resultSet).get(0);
		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO:findById " + e.getMessage());
		} finally {
			ConnectionFactory.close(resultSet);
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
		return null;
	}

	public List<T> createObjects(ResultSet resultSet) {
		List<T> list = new ArrayList<T>();

		try {
			while (resultSet.next()) {
				T instance = type.newInstance();
				for (Field field : type.getDeclaredFields()) {
					Object value = resultSet.getObject(field.getName());
					PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
					Method method = propertyDescriptor.getWriteMethod();
					method.invoke(instance, value);
				}
				list.add(instance);
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IntrospectionException e) {
			e.printStackTrace();
		}
		return list;
	}

	public void insertObject(T instance) throws SQLException, SecurityException, IllegalArgumentException,
			InstantiationException, IllegalAccessException, IntrospectionException, InvocationTargetException {

		Connection connection = null;
		PreparedStatement statement = null;
		try {
			String query = "INSERT INTO tema3db." + type.getSimpleName() + "(" + getColumns(false) + ")" + " VALUES (" + getColumns(true)+ ")";
			System.out.println(query);
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			
			int i = 0;
			for (Field field : type.getDeclaredFields()) {
				PropertyDescriptor propertyDescriptor = new PropertyDescriptor(field.getName(), type);
				Method method = propertyDescriptor.getReadMethod();
				Object value = method.invoke(instance);
				statement.setObject(++i, value);
			}
			statement.addBatch();
			statement.executeBatch();
		} finally {
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
	}

	public void update(int id, T ModifiedInstance) {
		Connection connection = null;
		PreparedStatement statement = null;
		String query = "UPDATE tema3db." + type.getSimpleName() + " SET " + generateUpdatePairs(id, ModifiedInstance) + " WHERE ID=" + id;
		System.out.println(query);
		try {
			connection = ConnectionFactory.getConnection();
			statement = connection.prepareStatement(query);
			statement.executeUpdate();

		} catch (SQLException e) {
			LOGGER.log(Level.WARNING, type.getName() + "DAO: update " + e.getMessage());
		} finally {
			ConnectionFactory.close(statement);
			ConnectionFactory.close(connection);
		}
	}
}
