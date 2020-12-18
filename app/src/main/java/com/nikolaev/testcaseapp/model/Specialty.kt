package com.nikolaev.testcaseapp.model

import com.google.gson.annotations.SerializedName
import io.realm.RealmModel
import io.realm.annotations.RealmClass
import java.io.Serializable

@RealmClass
open class Specialty(): RealmModel, Serializable {
            @SerializedName("name")
            var specialtyName: String? = null

            @SerializedName("specialty_id")
            var specialtyId: Int? = null
}