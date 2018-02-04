package test.utils;

import java.util.LinkedList;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import test.person.Friend;

public class JSONUtils {

	public static LinkedList<Friend> parseOsoba(JsonArray data) {
		LinkedList<Friend> friendsJson = new LinkedList<>();

		try {
			for (int i = 0; i < data.size(); i++) {
				JsonObject obj = (JsonObject) data.get(i);

				Friend friend = new Friend();
				if (!obj.get("id").isJsonNull()) {
					friend.setId(obj.get("id").getAsInt());
				}
				if (!obj.get("firstName").isJsonNull()) {
					friend.setFirstName(obj.get("firstName").getAsString().toString());
				} else {
					friend.setFirstName("NEPOZNATO");
				}
				if (!obj.get("surname").isJsonNull()) {
					friend.setSurname(obj.get("surname").getAsString());
				} else {
					friend.setSurname("NEPOZNATO");
				}
				if (!obj.get("age").isJsonNull()) {
					friend.setAge(obj.get("age").getAsInt());
				}
				if (!obj.get("gender").isJsonNull()) {
					friend.setGender(obj.get("gender").getAsString());
				} else {
					friend.setGender("NEPOZNATO");
				}
				if (!obj.get("friends").isJsonNull()) {
					friend.setFriends(obj.get("friends").getAsJsonArray());
				}

				friendsJson.add(friend);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return friendsJson;
	}
}
