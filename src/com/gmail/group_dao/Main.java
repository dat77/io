package com.gmail.group_dao;

import java.time.LocalDate;

import com.gmail.students.CapacityLimitExceedException;
import com.gmail.students.Group;
import com.gmail.students.Student;

public class Main {

	public static void main(String[] args) {
		
		GroupDao groupHandler = new GroupHandler();
		Group group = groupHandler.getObject();
		
		group.getInfo("Name");
		
		Student newStudent = new Student("Oscar", "Wilde", LocalDate.of(1999, 9, 9), "male", 4.5);
		try {
			group.addStudent(newStudent);
		} catch (CapacityLimitExceedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		group.getInfo("Grade");
		Student nonAchieverSudent = group.getStudents()[9];
		group.removeStudent(nonAchieverSudent);
		group.getInfo("Name");
		
		groupHandler.updateObject();
		

	}

}
