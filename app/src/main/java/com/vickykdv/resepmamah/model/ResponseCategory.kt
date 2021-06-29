package com.vickykdv.resepmamah.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ResponseCategory(

	@field:SerializedName("method")
	val method: String? = null,

	@field:SerializedName("results")
	val results: List<ResultsItemCategory>,

	@field:SerializedName("status")
	val status: Boolean? = null
) : Parcelable

@Parcelize
data class ResultsItemCategory(

	@field:SerializedName("category")
	val category: String? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("key")
	val key: String? = null,

	val bgColor:Int?=null
) : Parcelable
