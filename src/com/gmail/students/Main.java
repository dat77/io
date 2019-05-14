package com.gmail.students;

import java.lang.reflect.Constructor;
import java.time.LocalDate;

public class Main {

	public static void main(String[] args) {

		Student student1 = new Student("Seva", "Primavera", LocalDate.of(2000, 5, 5), "male", 4.5);
		Student student2 = new Student("Eris", "Ok", LocalDate.of(2001, 2, 12), "female", 5.0);
		Student student3 = new Student("Denis", "Tkachuk", LocalDate.of(1977, 9, 14), "male", 4.3);
		Student student4 = new Student("Senia", "Igi", LocalDate.of(2000, 11, 22), "female", 3.9);
		Student student5 = new Student("Tiana", "Vankoff", LocalDate.of(2001, 12, 9), "female", 3.9);
		Student student6 = new Student("Mary", "Christmas", LocalDate.of(2000, 6, 1), "female", 3.4);
		Student student7 = new Student("Max", "Vostuk", LocalDate.of(2000, 5, 3), "male", 4.4);
		Student student8 = new Student("Sas", "Radvanskiy", LocalDate.of(1977, 7, 16), "male", 5.0);
		Group group = new Group("IV42");
		try {
			group.addStudent(student1);
			group.addStudent(student2);
			group.addStudent(student3);
			group.addStudent(student4);
			group.addStudent(student5);
			group.addStudent(student6);
			group.addStudent(student7);
			group.addStudent(student8);
		} catch (CapacityLimitExceedException e) {
			e.printStackTrace();
		}
		group.getInfo("Grade");
		group.addStudents();
		group.getInfo("Date");

		MilitaryDepartment militaryDepartment = new MilitaryDepartment();
		militaryDepartment.getInfo(group);
		/**
		 * 3. Усовершенствуйте класс, описывающий группу студентов, добавив возможность
		 * сохранения группы в файл.
		 */
		Archive archive = new Archive();
		archive.saveObjectToFile(group);
		/**
		 * 4. Реализовать обратный процесс. Т.е. считать данные о группе из файла, и на
		 * их основе создать объект типа группа.
		 */
		Group group_new = new Group("IV43");
		archive.readObjectFromFile(group_new);
		group_new.getInfo("Name");
	}

}
