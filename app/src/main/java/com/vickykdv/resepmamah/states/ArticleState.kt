package com.vickykdv.resepmamah.states

import com.vickykdv.resepmamah.model.*


sealed class ArticleState{
    object Loading:ArticleState()
    data class Result(val data : ResponseArticle) :ArticleState()
    data class Error(val error : Throwable) :ArticleState()
}
