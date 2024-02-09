package com.kc.dragonball_kc_avanzado.data

import com.kc.dragonball_kc_avanzado.data.local.FakeLocalDataSource
import com.kc.dragonball_kc_avanzado.data.local.InMemoryDataSource
import com.kc.dragonball_kc_avanzado.data.mappers.LocalToDetailMapper
import com.kc.dragonball_kc_avanzado.data.mappers.LocalToListMapper
import com.kc.dragonball_kc_avanzado.data.mappers.RemoteToLocalMapper
import com.kc.dragonball_kc_avanzado.data.remote.RemoteDataSource
import com.kc.dragonball_kc_avanzado.test_utils.generateHeroRemote
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RepositoryTestWithFake {

    // SUT
    private lateinit var repository: Repository

    // Dependencies
    private val localDataSource = FakeLocalDataSource()
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

    // getHeroes when there's no heroes cached
    @Test
    fun `WHEN NO heroesLocal THEN return heroes from Remote`() = runTest {
        coEvery { repository.token() } returns "token"
        coEvery { remoteDataSource.getHeroes("Bearer token") } returns generateHeroRemote(2)

        val actual = repository.getHeroes()
        assertEquals(2, actual.count())
        assertEquals("HeroRemote0", actual.first().name)
    }

    // Test the token functions
    @Test
    fun `WHEN save token then delete token RETURN token then empty`() = runTest {
        coEvery { inMemoryDataSource.getToken() } returns ""
        //Get
        assertEquals("token", repository.getToken())
        // Remove
        repository.deleteToken()
        assertEquals("", repository.getToken())
        // Add from empty
        repository.saveToken("TEST", true)
        assertEquals("TEST", repository.getToken())
        // Update from existing value
        repository.saveToken("token", true)
        assertEquals("token", repository.getToken())
    }
}