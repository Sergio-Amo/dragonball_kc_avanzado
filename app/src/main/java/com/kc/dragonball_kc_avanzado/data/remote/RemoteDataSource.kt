package com.kc.dragonball_kc_avanzado.data.remote

import android.content.Context
import android.net.http.NetworkException
import android.util.Log
import android.widget.Toast
import com.kc.dragonball_kc_avanzado.data.remote.request.FavoriteRequest
import com.kc.dragonball_kc_avanzado.data.remote.request.HeroRequest
import com.kc.dragonball_kc_avanzado.data.remote.request.LocationsRequest
import com.kc.dragonball_kc_avanzado.domain.model.HeroDetail
import com.kc.dragonball_kc_avanzado.domain.model.HeroLocation
import com.kc.dragonball_kc_avanzado.domain.model.HeroLocationsRemote
import com.kc.dragonball_kc_avanzado.domain.model.HeroRemote
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Credentials
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import kotlin.coroutines.coroutineContext

class RemoteDataSource @Inject constructor(private val api: DragonBallApi, @ApplicationContext private val context: Context) {
    suspend fun login(email: String, password: String): String {
        // I know I shouldn't be catching errors here, but wanted to try real quick
        // At some point I'll centralize all network errors in the same place, but I'm not
        // sure how, yet.
        try {
            return api.login(Credentials.basic(email, password))
        } catch (e: HttpException) {
            Log.d("WTF", e.localizedMessage as String)
            Toast.makeText(context, e.localizedMessage,Toast.LENGTH_LONG).show()
        } catch (e: IOException) {
            Log.d("WTF", e.toString())
        } catch (e: Exception) {
            Log.d("WTF", e.toString())
        }
        return ""
    }

    suspend fun getHeroes(token: String): List<HeroRemote> = api.getHeroes(HeroRequest(), token)

    suspend fun getHeroDetail(name: String, token: String): HeroDetail =
        api.getHeroesDetail(HeroRequest(name), token).first()

    suspend fun toggleFavorite(heroId: String, token: String) {
        api.toggleFavorite(FavoriteRequest(heroId), token)
    }

    suspend fun getLocations(heroId: String, token: String): List<HeroLocationsRemote> =
        api.getLocations(LocationsRequest(heroId), token)

}