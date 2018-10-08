package GUIPackage;

import java.util.ArrayList;
import java.util.Vector;

import javax.swing.table.DefaultTableModel;

import java.lang.reflect.*;

public class TableModel {
	public TableModel() {
	}

	public <T> DefaultTableModel CreateModel(ArrayList<T> ObjectsList) {
		ArrayList<String> columns = new ArrayList<String>();

		if (ObjectsList.size() > 0) {
			for (Field field : ObjectsList.get(0).getClass().getDeclaredFields())
				if (!field.getName().equals("isDeleted") && !field.getName().equals("serialVersionUID"))
					columns.add(field.getName());
		}
		DefaultTableModel model = new DefaultTableModel(columns.toArray(), 0);

		for (Object obj : ObjectsList) {
			int deleted = 0;

			Vector<String> values = new Vector<String>();
			for (Field field : obj.getClass().getDeclaredFields()) {
				field.setAccessible(true);
				try {
					String val = field.get(obj).toString();
					if (field.getName().equals("isDeleted")) {
						if (field.get(obj).toString().equals("true"))
							deleted = 1;
					} else if (!field.getName().equals("serialVersionUID"))
						values.add(val);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			if (deleted == 0)
				model.addRow(values);
		}

		return model;

	}
}
