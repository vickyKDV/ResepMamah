package com.vickykdv.resepmamah.network.repository

import com.vickykdv.resepmamah.model.ResponseArticle
import com.vickykdv.resepmamah.model.ResponseCategory
import com.vickykdv.resepmamah.model.ResponseDetailRecipe
import com.vickykdv.resepmamah.model.ResponseRecipes
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    /*Get Recipe By Page*/
    @GET("recipes/{page}")
    fun getRecipes(
        @Path("page") page:Int
    ):Single<ResponseRecipes>

    /*Get Categori*/
    @GET("categorys/recipes")
    fun getCategory():Single<ResponseCategory>

    /*Get Article*/
    @GET("articles/new")
    fun getArticle():Single<ResponseArticle>


    /*Get With Search*/
    @GET("search/")
    fun searchReceipe(
        @Query("q") find:String
    ):Single<ResponseRecipes>

//    /*Get Categori*/
//    @GET("articles/new")
//    fun getCategory():Single<ResponseCategory>

    /*Get Recipe with Limit*/
    @GET("recipes-length/")
    fun getRecipe(
      @Query("limit") limit:String
    ):Single<ResponseRecipes>

    /*Get Detail Recipe*/
    @GET("recipe/{key}")
    fun getDetailRecipe(
        @Path("key") keyname:String
    ):Single<ResponseDetailRecipe>
}