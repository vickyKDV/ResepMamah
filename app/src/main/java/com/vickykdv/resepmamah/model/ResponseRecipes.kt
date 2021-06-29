package com.vickykdv.resepmamah.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ResponseRecipes(

	@field:SerializedName("method")
	val method: String,

	@field:SerializedName("results")
	val results: List<ResultsItem>,

	@field:SerializedName("status")
	val status: Boolean
) : Parcelable

@Parcelize
data class ResultsItem(

	@field:SerializedName("times")
	val times: String? = null,

	@field:SerializedName("thumb")
	val thumb: String? = null,

	@field:SerializedName("portion")
	val portion: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("key")
	val key: String?,

	@field:SerializedName("dificulty")
	val dificulty: String? = null
) : Parcelable
