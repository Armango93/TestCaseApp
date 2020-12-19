package com.nikolaev.testcaseapp.database

import com.nikolaev.testcaseapp.model.Employee
import com.nikolaev.testcaseapp.model.EmployeeFields
import com.nikolaev.testcaseapp.model.Specialty
import com.nikolaev.testcaseapp.model.SpecialtyFields
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.kotlin.where

class DataStorage : EmployeeRepo {

    private var realm: Realm? = null

    private val TAG = this::class.java.canonicalName

    private val configuration = RealmConfiguration
        .Builder()
        .name(TAG)
        .deleteRealmIfMigrationNeeded()
        .build()

    private fun ensureRealmCreated(): Realm {
        if (realm == null || realm!!.isClosed) {
            realm = Realm.getInstance(configuration)
        }
        return realm!!
    }

    private fun withAsyncTransaction(block: Realm.() -> Unit) {
        Realm.getInstance(configuration).use { r ->
            r.executeTransactionAsync { realm ->
                realm.block()
                realm.close()
            }
        }
    }

    override fun saveEmployeeList(list: List<Employee>) {
        withAsyncTransaction {
            val rmList = where<Employee>().findAll()
            rmList.deleteAllFromRealm()
            insertOrUpdate(list.toMutableList())
        }
    }

    override fun getAllUniqueSpecialties(): List<Specialty> {
        try {
            with(ensureRealmCreated()) {
                return copyFromRealm(
                    where<Specialty>().distinct(SpecialtyFields.SPECIALTY_ID).findAll()
                )
            }
        } finally {
            realm?.close()
        }
    }

    override fun getAllEmployeesBySpecialty(specialty: Specialty): List<Employee> {
        try {
        with(ensureRealmCreated()) {
            return copyFromRealm(
                where<Employee>()
                    .equalTo(EmployeeFields.SPECIALTY_LIST.SPECIALTY_ID, specialty.specialtyId)
                    .findAll()
            )
        }
        } finally {
            realm?.close()
        }
    }

    override fun deleteAllEmployees() {
        withAsyncTransaction {
            where<Employee>().findAll().deleteAllFromRealm()
        }
    }
}