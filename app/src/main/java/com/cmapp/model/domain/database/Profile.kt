package com.cmapp.model.domain.database

import com.google.firebase.database.IgnoreExtraProperties
import java.io.Serializable

@kotlinx.serialization.Serializable
@IgnoreExtraProperties
open class Profile (
    open var username: String? = null,
    open val photo: String? = null,
    open val wandFront: String? = null,
    open val wandSide: String? = null,
): Serializable