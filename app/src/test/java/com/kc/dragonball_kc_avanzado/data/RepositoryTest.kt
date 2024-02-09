package com.kc.dragonball_kc_avanzado.data

import com.kc.dragonball_kc_avanzado.data.local.InMemoryDataSource
import com.kc.dragonball_kc_avanzado.data.local.LocalDataSource
import com.kc.dragonball_kc_avanzado.data.mappers.LocalToDetailMapper
import com.kc.dragonball_kc_avanzado.data.mappers.LocalToListMapper
import com.kc.dragonball_kc_avanzado.data.mappers.RemoteToLocalMapper
import com.kc.dragonball_kc_avanzado.data.remote.RemoteDataSource
import com.kc.dragonball_kc_avanzado.test_utils.generateHeroDetail
import com.kc.dragonball_kc_avanzado.test_utils.generateHeroLocal
import com.kc.dragonball_kc_avanzado.test_utils.generateHeroLocationsRemote
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class RepositoryTest {

    // SUT
    private lateinit var repository: Repository

    // Dependencies
    private val localDataSource: LocalDataSource = mockk()
    private val remoteDataSource: RemoteDataSource = mockk()
    private val inMemoryDataSource: InMemoryDataSource = mockk()
    private val localToListMapper = LocalToListMapper()
    private val remoteToLocalMapper = RemoteToLocalMapper()
    private val localToDetailMapper = LocalToDetailMapper()

    @Before
    fun setUp() {
        repository = Repository(
            localDataSource,
            remoteDataSource,
            localToListMapper,
            remoteToLocalMapper,
            inMemoryDataSource,
            localToDetailMapper,
        )
    }

    // getToken
    @Test
    fun `WHEN inMemoryToken THEN 42`() = runTest {
        every { inMemoryDataSource.getToken() } returns "42"

        val actual = repository.getToken()

        assertEquals("42", actual)
    }

    @Test
    fun `WHEN empty inMemoryToken THEN remote`() = runTest {
        every { inMemoryDataSource.getToken() } returns ""
        coEvery { localDataSource.getToken() } returns "remote"

        assertEquals("remote", repository.getToken())
        verify { inMemoryDataSource.getToken() }
        coVerify { localDataSource.getToken() }
    }

    // isPersistentSession
    @Test
    fun `WHEN token inMemory THEN isPersistentSession false`() {
        every { inMemoryDataSource.getToken() } returns "token"

        assertFalse(repository.isPersistentSession())
    }

    @Test
    fun `WHEN token NOT inMemory THEN isPersistentSession true`() {
        every { inMemoryDataSource.getToken() } returns ""

        assertTrue(repository.isPersistentSession())
    }

    //saveToken
    @Test
    fun `WHEN saveToken persist true THEN localDataSource-saveToken`() = runTest {
        coEvery { localDataSource.saveToken("token") } just runs

        repository.saveToken("token", true)

        coVerify { localDataSource.saveToken("token") }
    }

    @Test
    fun `WHEN saveToken persist false THEN inMemoryDataSource-saveToken`() = runTest {
        coEvery { inMemoryDataSource.saveToken("token") } just runs

        repository.saveToken("token", false)

        coVerify { inMemoryDataSource.saveToken("token") }
    }

    // deleteToken
    @Test
    fun deleteToken() = runTest {
        coEvery { localDataSource.deleteToken() } just runs

        repository.deleteToken()

        coVerify { localDataSource.deleteToken() }
    }

    // login
    @Test
    fun `WHEN login and token THEN true`() = runTest {
        coEvery { repository.saveToken("token", true) } just runs
        coEvery { remoteDataSource.login("foo", "bar") } returns "token"

        val actual = repository.login("foo", "bar", true)

        assertTrue(actual)
    }

    // getHeroes (The other path is in fakes)
    @Test
    fun `WHEN heroesLocal THEN return heroes from Local`() = runTest {
        coEvery { localDataSource.getHeroes() } returns generateHeroLocal(3)

        val actual = repository.getHeroes()

        assertEquals(3, actual.count())
        assertEquals("HeroLocal0", actual.first().name)
    }

    // getHeroDetail
    @Test
    fun `WHEN heroes cached THEN return hero by name`() = runTest {
        coEvery { localDataSource.getHeroes() } returns generateHeroLocal(7)

        val actual = repository.getHeroDetail("HeroLocal3")

        assertEquals("HeroLocal3", actual.name)
    }

    @Test
    fun `WHEN heroes not cached THEN return hero by name (from remote)`() = runTest {
        coEvery { localDataSource.getHeroes() } returns generateHeroLocal(0)
        coEvery { inMemoryDataSource.getToken() } returns "token"
        coEvery {
            remoteDataSource.getHeroDetail("HeroDetail", "Bearer token")
        } returns generateHeroDetail()

        val actual = repository.getHeroDetail("HeroDetail")

        assertEquals("HeroDetail", actual.name)
    }


    @Test
    fun toggleFavorite() = runTest {
        coEvery { inMemoryDataSource.getToken() } returns "token"
        coEvery { localDataSource.toggleFavorite("id") } just runs
        coEvery { remoteDataSource.toggleFavorite("id", "Bearer token") } just runs

        repository.toggleFavorite("id")

        coVerify(exactly = 1) { localDataSource.toggleFavorite("id") }
        coVerify(exactly = 1) { remoteDataSource.toggleFavorite("id", "Bearer token") }
    }

    @Test
    fun deleteLocalCache() = runTest {
        coEvery { localDataSource.deleteHeroes() } just runs

        repository.deleteLocalCache()

        coVerify(exactly = 1) { localDataSource.deleteHeroes() }
    }

    @Test
    fun getLocations() = runTest {
        coEvery { inMemoryDataSource.getToken() } returns "token"
        coEvery { remoteDataSource.getLocations("id", "Bearer token") } returns generateHeroLocationsRemote()

        val actual = repository.getLocations("id")

        assertEquals("-74.2605503", actual.last().longitud)
    }
}