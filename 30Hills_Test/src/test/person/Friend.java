package test.person;

import com.google.gson.JsonArray;

public class Friend {

	private int id;
	private String firstName;
	private String surname;
	private int age;
	private String gender;
	private JsonArray friends;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		if (id > 0)
			this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		if (!firstName.equals(""))
			this.firstName = firstName;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		if (age > 0)
			this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		if (gender.equals("male") || gender.equals("female"))
			this.gender = gender;
	}

	public JsonArray getFriends() {
		return friends;
	}

	public void setFriends(JsonArray friends) {
		this.friends = friends;
	}

	@Override
	public String toString() {
		return firstName + " " + surname;
	}
}
