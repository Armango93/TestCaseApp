package com.nikolaev.testcaseapp.model

import com.google.gson.annotations.SerializedName
import io.realm.RealmList
import io.realm.RealmModel
import io.realm.annotations.RealmClass
import java.io.Serializable

@RealmClass
open class Employee(): RealmModel, Serializable {

        @SerializedName("avatr_url")
        var avatarUrl: String? = null

        @SerializedName("birthday")
        var birthday: String? = null

        @SerializedName("f_name")
        var firstName: String? = null

        @SerializedName("l_name")
        var lastName: String? = null

        @SerializedName("specialty")
        var specialtyList: RealmList<Specialty>? = null
}