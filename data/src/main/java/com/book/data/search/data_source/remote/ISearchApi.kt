package com.book.data.search.data_source.remote

import com.book.data.search.model.BookResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ISearchApi {

    companion object{
        private const val SEARCH ="/search"
        private const val BOOK ="${SEARCH}/book"
    }

    /**
     * query String	검색을 원하는 질의어
     * sort	String	결과 문서 정렬 방식, accuracy(정확도순) 또는 latest(발간일순), 기본값 accuracy
     * page	Integer	결과 페이지 번호, 1~50 사이의 값, 기본 값 1
     * size	Integer	한 페이지에 보여질 문서 수, 1~50 사이의 값, 기본 값 10
     * target	String	검색 필드 제한
     * 사용 가능한 값: title(제목), isbn (ISBN), publisher(출판사), person(인명)
     */
    @GET(BOOK)
    suspend fun getSearchBook(
        @Query("query") query : String,
        @Query("sort") sort : String?,
        @Query("page") page : Int?,
        @Query("size") size : Int?,
        @Query("target") target : String?,
    ) : BookResponse

}