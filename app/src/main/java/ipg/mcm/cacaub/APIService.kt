package ipg.mcm.cacaub

import ipg.mcm.cacaub.arrayJSON.ArrayJSONModel
import ipg.mcm.cacaub.nestedJSON.NestedJSONModel
import ipg.mcm.cacaub.simpleJSON.SimpleJSONModel
import retrofit2.Response
import retrofit2.http.GET

interface APIService {
    /*
      Simple JSON
   */

    @GET("/johncodeos-blog/ParseJSONRetrofitConvertersExample/main/simple.json")
    suspend fun getEmployee(): Response<SimpleJSONModel>


    /*
       Array JSON
    */

    @GET("/johncodeos-blog/ParseJSONRetrofitConvertersExample/main/array.json")
    suspend fun getEmployees(): Response<List<ArrayJSONModel>>


    /*
       Nested JSON
    */

    @GET("/johncodeos-blog/ParseJSONRetrofitConvertersExample/main/nested.json")
    suspend fun getEmployeesNested(): Response<NestedJSONModel>
}