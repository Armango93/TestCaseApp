package com.nikolaev.testcaseapp.network

import com.google.gson.*
import com.nikolaev.testcaseapp.model.Specialty
import io.realm.RealmList
import java.lang.reflect.Type

class SpecialtyRealmListConverter : JsonSerializer<RealmList<Specialty?>>,
    JsonDeserializer<RealmList<Specialty>> {
    override fun serialize(
        src: RealmList<Specialty?>, typeOfSrc: Type,
        context: JsonSerializationContext
    ): JsonElement {
        val jsonArray = JsonArray()
        for (item in src) {
            jsonArray.add(context.serialize(item))
        }
        return jsonArray
    }

    @Throws(JsonParseException::class)
    override fun deserialize(
        json: JsonElement, typeOfT: Type,
        context: JsonDeserializationContext
    ): RealmList<Specialty> {
        val items = RealmList<Specialty>()
        val jsonArray = json.asJsonArray
        for (je in jsonArray) {
            items.add(context.deserialize<Any>(je, Specialty::class.java) as Specialty)
        }
        return items
    }
}