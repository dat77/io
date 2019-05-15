package com.gmail.dao;

import com.gmail.students.CapacityLimitExceedException;
import com.gmail.students.Student;
import java.io.File;
import javax.swing.JFileChooser;
import com.gmail.io.FileHandler;

public class StudentsDao implements Dao {

	private Student[] students;
	private String name;
	private File filePath;

	public StudentsDao() {
		this.name = "unknown";
	}

	
	
	public String getName() {
		return name;
	}



	@Override
	public Student[] getAllObjects() {
		JFileChooser dfc = new JFileChooser();
		if (dfc.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
			name = dfc.getSelectedFile().getName();
			filePath = dfc.getSelectedFile();
			String[] studentsInfo = FileHandler.readFromFileLineByLine(dfc.getSelectedFile());
			students = new Student[studentsInfo.length + studentsInfo.length/2];
			for (int i = 0; i < studentsInfo.length; i++) {
				Student student = new Student();
				student.setObjectFromCommaStyle(studentsInfo[i]);
				students[i] = student;
			}
		}
		return students;
	}

	@Override
	public Student getObject(int id) {
		if (students == null) {
			System.out.println("Group has not been initialized");
			return null;
		}
		for (Student student : students) {
			if (student!=null && student.getId() == id) {
				return student;
			}
		}
		return null;
	}

	
	private int addStudent(Student student) throws CapacityLimitExceedException {
		if (students == null) {
			System.out.println("Group has not been initialized");
			return -1;
		}
		for (int i = 0; i < students.length; i++) {
			if (students[i] == null) {
				students[i] = student;
				return students[i].getId();
			}
		}
		throw new CapacityLimitExceedException();
	}

	
	@Override
	public int updateObject(Object obj) {
		if (students == null) {
			System.out.println("Group has not been initialized");
			return -1;
		}
		if (obj instanceof Student) {
			for (Student student : students) {
				if (student == obj) {
					String str = student.getInfoInCommaStyle();
					student.setObjectFromCommaStyle(str);
					return student.getId();
				}
			}
			try {
				return addStudent((Student)obj);
			} catch (CapacityLimitExceedException e) {
				e.printStackTrace();
				return -1;
			}
		} else if (obj instanceof Student[]) {
			StringBuilder stringBuilder = new StringBuilder();
			for (Student student : students) {
				if (student != null) {
					stringBuilder.append(student.getInfoInCommaStyle() + "\n");
				}
			}
			FileHandler.writeToFileLineByLine(filePath, stringBuilder.toString().split("\n"));
			return 0;
		}
		return -1;
	}

	@Override
	public void removeObject(Object obj) {
		if (students == null) {
			System.out.println("Group has not been initialized");
			return;
		}
		for (int i = 0; i < students.length; i++) {
			if (students[i]!= null && students[i].equals((Student) obj)) {
				students[i] = null;
			}
		}

	}

}
