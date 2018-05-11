package com.ss.test.util;

import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;

public class Util {
	private static Gson gson = null;

	public static Gson getGson() {
		if (gson == null) {
			JsonSerializer<Date> ser = new JsonSerializer<Date>() {
				@Override
				public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
					return src == null ? null : new JsonPrimitive(src.getTime());
				}
			};

			JsonDeserializer<Date> deser = new JsonDeserializer<Date>() {
				@Override
				public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
						throws JsonParseException {
					return json == null ? null : new Date(json.getAsLong());
				}
			};

			gson = new GsonBuilder().registerTypeAdapter(Date.class, ser).registerTypeAdapter(Date.class, deser)
					.disableHtmlEscaping().create();
		}
		return gson;
	}

	public static String getCharEncoding() {
		return "UTF-8";
	}
}