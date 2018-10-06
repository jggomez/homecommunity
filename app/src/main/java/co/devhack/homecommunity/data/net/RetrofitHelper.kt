package co.devhack.homecommunity.data.net

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitHelper {

    companion object {

        private var retrofit: Retrofit? = null

        fun getRetrofit(): Retrofit? {

            if (retrofit != null) {
                return retrofit
            }

            val builder = GsonBuilder()
            builder.excludeFieldsWithoutExposeAnnotation()
            val gson = builder.create()

            retrofit = Retrofit.Builder()
                    .baseUrl("https://us-central1-appecommerceworkshop.cloudfunctions.net/")
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()

            return retrofit

        }
    }

}