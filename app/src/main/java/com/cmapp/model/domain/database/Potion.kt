package com.cmapp.model.domain.database

import com.google.firebase.database.IgnoreExtraProperties
import java.io.Serializable

@kotlinx.serialization.Serializable
@IgnoreExtraProperties
open class Potion (
    open var key: String? = null,
    open val color: String? = null,
    open val description: String? = null,
    open val ingredients: String? = null,
    open val name: String? = null,
): Serializable
