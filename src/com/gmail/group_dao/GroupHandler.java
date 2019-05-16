package com.gmail.group_dao;

import java.io.File;
import javax.swing.JFileChooser;
import com.gmail.students.Group;

public class GroupHandler implements GroupDao {

	private File filePath;
	private Group group;
	
		
	public GroupHandler() {
		this.group = null;
	}

	@Override
	public Group getObject() {
		if (group == null) {
			createGroup();
		}
		return group;
	}

	@Override
	public void updateObject() {
		if (group != null) {
			group.saveToFile(filePath);
		}
	}
	
	private void createGroup() {
		JFileChooser dfc = new JFileChooser();
		if (dfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			String name = dfc.getSelectedFile().getName();
			this.filePath = dfc.getSelectedFile();
			group = new Group(name);
			group.readFromFile(filePath);
		}
	}

}
