package com.gmail.students;

public class MilitaryDepartment {
	
	
	public void getInfo(MilitaryConscription students) {
		Student[] conscripts = students.getConscripts();
		StringBuilder stringBuilder = new StringBuilder("Conscripts:\n");
		for (Student student : conscripts) {
			if (student != null) {
				stringBuilder.append(student + "\n");
			}
		}
		System.out.println(stringBuilder.toString());
	}

}
