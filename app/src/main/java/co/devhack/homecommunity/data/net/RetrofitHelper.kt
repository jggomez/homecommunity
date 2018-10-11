package co.devhack.homecommunity.data.net

import co.devhack.homecommunity.util.Constant
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitHelper private constructor() {

    companion object {

        private var retrofit: Retrofit? = null

        fun getRetrofit(): Retrofit {

            if (retrofit != null) {
                return retrofit!!
            }

            val builder = GsonBuilder()
            builder.excludeFieldsWithoutExposeAnnotation()
            val gson = builder.create()

            retrofit = Retrofit.Builder()
                    .baseUrl(Constant.baseURL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()

            return retrofit!!

        }
    }

}