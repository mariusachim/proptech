package com.indent.proptech.proptech.property

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*

@Entity
@Table(name = "properties")
data class Property(

    @Id @GeneratedValue(strategy = GenerationType.UUID) val id: UUID? = null,

    val name: String,

    val address: String
) {
    constructor() : this(null, "", "")
}