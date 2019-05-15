package com.gmail.dao;

import java.time.LocalDate;

import com.gmail.students.Student;

public class Main {

	public static void main(String[] args) {
		
		Dao daoInstance = new StudentsDao();
		Student[] group = (Student[]) daoInstance.getAllObjects();
		for (Student student : group) {
			System.out.println(student);
		}
		
		System.out.println();
		Student newStudent = new Student("Oscar", "Wilde", LocalDate.of(1999, 9, 9), "male", 4.5);
		int id = newStudent.getId();
		daoInstance.updateObject(newStudent);
		for (Student student : group) {
			System.out.println(student);
		}
		
		System.out.println();
		Student student1 = (Student) daoInstance.getObject(id);
		System.out.println(student1);
		
		System.out.println();
		student1.setAverageGrade(5.0);
		id = daoInstance.updateObject(student1);
		student1 = (Student) daoInstance.getObject(id);
		System.out.println(student1);
		
		System.out.println();
		double minGrade = 5.0;
		for (Student student : group) {
			if (student != null && student.getAverageGrade() <= minGrade) {
				minGrade = student.getAverageGrade();
				id = student.getId();
			}
		}
		student1 = (Student) daoInstance.getObject(id);
		System.out.println(student1);
		
		System.out.println();
		daoInstance.removeObject(student1);
		for (Student student : group) {
			System.out.println(student);
		}
		
		daoInstance.updateObject(group);

		
	}

}
