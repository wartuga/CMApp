package com.cmapp.model.domain.database

import android.os.Parcelable
import com.google.firebase.database.IgnoreExtraProperties
import java.io.Serializable

@kotlinx.serialization.Serializable
@IgnoreExtraProperties
open class Spell (
    open var key: String? = null,
    open val image: String? = null,
    open val description: String? = null,
    open val movements: List<String> = emptyList(),
    open val name: String? = null,
    open val time: Int? = null
): Serializable
