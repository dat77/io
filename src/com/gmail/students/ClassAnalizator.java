package com.gmail.students;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Comparator;

public class ClassAnalizator {
	
	public static boolean isComparator(Class<?> exClass) {
		Class<?>[] interfaces =  exClass.getInterfaces();
		for (Class<?> interface1 : interfaces) {
			if (interface1.getName().contentEquals("java.util.Comparator")) {
				return true;
			}
		}
		return false;
	}

	public static Class<?> getInnerClassByName(Class<?> exClass, String partOfName) {
		Class<?>[] innerClassesGroup = exClass.getDeclaredClasses();
		for (Class<?> innerClass : innerClassesGroup) {
			if (innerClass.getName().contains(partOfName)) return innerClass;
		}
		return null;
	}
	
	public static Comparator getComparatorInstance(Class<?> exClass, Object outerObject) {
		if (!isComparator(exClass)) {
			return null;	
		}
		Comparator comparator = null;
		Constructor<?>[] constructors = exClass.getDeclaredConstructors();
		for (Constructor<?> constructor : constructors) {
			if (constructor.getParameterCount() == 1) {
				constructor.setAccessible(true);
				try {
					comparator = (Comparator) constructor.newInstance(outerObject);
					break;
				} catch (InstantiationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
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
		}
		return comparator;
	}
	

}
