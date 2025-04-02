package br.com.usinasantafe.pcp.external.sharedpreferences.datasource

import android.content.Context
import android.content.SharedPreferences
import androidx.test.core.app.ApplicationProvider
import br.com.usinasantafe.pcp.domain.entities.variable.Config
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Before

import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class IConfigSharedPreferencesDatasourceTest {

    private lateinit var context : Context
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var datasource : IConfigSharedPreferencesDatasource

    @Before
    fun before() {
        context = ApplicationProvider.getApplicationContext()
        sharedPreferences = context.getSharedPreferences("teste", Context.MODE_PRIVATE)
        datasource = IConfigSharedPreferencesDatasource(sharedPreferences)
    }

    @Test
    fun `Return false if don't have data in Config SharedPreferences internal`() = runTest {
        val result = datasource.has()
        assertEquals(
            result.getOrNull()!!,
            false
        )
    }

    @Test
    fun `Return true if have data in Config SharedPreferences internal`() = runTest {
        datasource.save(
            Config(password = "12345")
        )
        val result = datasource.has()
        assertEquals(
            result.getOrNull()!!,
            true
        )
    }

    @Test
    fun `Check return data correct the Config SharedPreferences internal`() = runTest {
        val data = Config(password = "12345")
        datasource.save(data)
        val result = datasource.get()
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull()!!,
            data
        )
    }

    @Test
    fun `Check return password correct the Config SharedPreferences internal`() = runTest {
        val data = Config(password = "12345")
        datasource.save(data)
        val result = datasource.get()
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull()!!.password,
            "12345"
        )
    }

    @Test
    fun `Check return true and data returned if clear execute successfully`() = runTest {
        val data = Config(password = "12345")
        datasource.save(data)
        val resultGetBefore = datasource.get()
        assertEquals(
            resultGetBefore.isSuccess,
            true
        )
        assertEquals(
            resultGetBefore.getOrNull()!!.password,
            "12345")

        val resultClear = datasource.clean()
        assertEquals(
            resultClear.isSuccess,
            true
        )
        val result = datasource.has()
        assertEquals(
            result.isSuccess,
            true
        )
        assertEquals(
            result.getOrNull()!!,
            false
        )
    }

}