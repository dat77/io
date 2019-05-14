package com.gmail.students;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import com.gmail.io.*;

public class Group implements MilitaryConscription, Archivable {

	private String name;
	private Student[] students = new Student[10];

	public Group(String name) {
		this.name = name;
	}

	public Group() {
		this.name = "noname";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Student[] getStudents() {
		return students;
	}

	public int addStudent(Student student) throws CapacityLimitExceedException {
		for (int i = 0; i < students.length; i++) {
			if (students[i] == null) {
				students[i] = student;
				return i;
			}
		}
		throw new CapacityLimitExceedException();
	}

	public void addStudents() {

		while (true) {
			String input = null;
			input = JOptionPane.showInputDialog("Add student in format: Name Surname Birth sex grade"
					+ " \nName Surname YYYY-mm-dd male|female X.X");
			if (input == null) {
				break;
			}
			String[] fields = input.split(" ");
			try {
				String name = fields[0];
				String surname = fields[1];
				LocalDate birthDate = LocalDate.parse(fields[2]);
				String sex = fields[3];
				double averageGrade = Double.valueOf(fields[4]);
				Student student = new Student(name, surname, birthDate, sex, averageGrade);
				addStudent(student);
				System.out.println(student);
			} catch (IndexOutOfBoundsException e1) {
				JOptionPane.showMessageDialog(null, "Input data are incomplete");
				continue;
			} catch (DateTimeParseException e2) {
				JOptionPane.showMessageDialog(null, e2.getMessage());
				continue;
			} catch (NumberFormatException e3) {
				JOptionPane.showMessageDialog(null, e3.getMessage());
				continue;
			} catch (CapacityLimitExceedException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
				break;
			}
		}
	}

	public Student removeStudent(Student student) {
		for (int i = 0; i < students.length; i++) {
			if ((students[i] != null) && (students[i].equals(student))) {
				students[i] = null;
				return student;
			}
		}
		return null;
	}

	public Student removeStudent(int index) {
		Student student = null;
		if ((index < students.length) && (students[index] != null)) {
			student = students[index];
			students[index] = null;
		}
		return student;
	}

	public Student getStudent(String surname) {
		for (Student student : students) {
			if ((student != null) && (surname.compareToIgnoreCase(student.getSurname()) == 0)) {
				return student;
			}
		}
		return null;
	}

	public void getInfo(String sortByField) {
		Class<?> innerClass = ClassAnalizator.getInnerClassByName(this.getClass(), sortByField);
		if (innerClass != null) {
			Comparator comparator = ClassAnalizator.getComparatorInstance(innerClass, this);
			if (comparator != null) {
				Arrays.sort(students, comparator);
			} else {
				Arrays.sort(students, new SortBySurname());
			}
		} else {
			Arrays.sort(students, new SortBySurname());
		}
		System.out.println("Group " + name + " sorted by <" + sortByField + ">:");
		System.out.println(this);
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		for (Student student : students) {
			stringBuilder.append(student + "\n");
		}
		return stringBuilder.toString();
	}

	private class SortBySurname implements Comparator {
		@Override
		public int compare(Object o1, Object o2) {
			if ((o1 == null) & (o2 == null)) {
				return 0;
			}
			if ((o1 == null) & (o2 != null)) {
				return -1;
			}
			if ((o1 != null) & (o2 == null)) {
				return 1;
			}
			String s1 = ((Student) o1).getSurname() + ((Student) o1).getName();
			String s2 = ((Student) o2).getSurname() + ((Student) o2).getName();
			return s1.compareToIgnoreCase(s2);
		}
	}

	private class SortByBirthDate implements Comparator {
		@Override
		public int compare(Object o1, Object o2) {
			if ((o1 == null) & (o2 == null)) {
				return 0;
			}
			if ((o1 == null) & (o2 != null)) {
				return -1;
			}
			if ((o1 != null) & (o2 == null)) {
				return 1;
			}
			LocalDate d1 = ((Student) o1).getBirthDate();
			LocalDate d2 = ((Student) o2).getBirthDate();
			return d1.compareTo(d2);
		}
	}

	private class SortByAverageGrade implements Comparator {
		@Override
		public int compare(Object o1, Object o2) {
			if ((o1 == null) & (o2 == null)) {
				return 0;
			}
			if ((o1 == null) & (o2 != null)) {
				return -1;
			}
			if ((o1 != null) & (o2 == null)) {
				return 1;
			}
			double d1 = ((Student) o1).getAverageGrade();
			double d2 = ((Student) o2).getAverageGrade();
			return d1 > d2 ? -1 : 1;
		}
	}

	@Override
	public Student[] getConscripts() {
		Student[] conscripts = new Student[students.length];
		int i = 0;
		for (Student student : students) {
			if ((student != null) && (LocalDate.now().compareTo(student.getBirthDate()) >= MILITARY_AGE)
					&& ("male".equalsIgnoreCase(student.getSex()))) {
				conscripts[i] = student;
				i++;
			}
		}
		Arrays.sort(conscripts, new SortBySurname());
		return conscripts;
	}

	public String[] getInfoInCommaStyle() {
		StringBuilder stringBuilder = new StringBuilder();
		for (Student student : students) {
			if (student != null) {
				stringBuilder.append(student.getInfoInCommaStyle() + "\n");
			}
		}
		return stringBuilder.toString().split("\n");
	}


	@Override
	public void saveToFile(File file) {
		FileHandler.writeToFileLineByLine(file, getInfoInCommaStyle());
	}

	@Override
	public void readFromFile(File file) {
		String[] studentsInfo =  FileHandler.readFromFileLineByLine(file);
		for (int i = 0; i < students.length; i++) {
			students[i] = null;
		}
		for (String string : studentsInfo) {
			Student student = new Student();
			student.setObjectFromCommaStyle(string);
			try {
				addStudent(student);
			} catch (CapacityLimitExceedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				break;
			}
		}
		
	}

}
