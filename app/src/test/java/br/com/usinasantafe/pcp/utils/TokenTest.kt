package br.com.usinasantafe.pcp.utils

import org.junit.Assert.*

import org.junit.Test

class TokenTest {

    @Test
    fun `Test token`() {
        val result = token(16997417840, "6.00", 1)
        assertEquals(result, "Bearer E49AD0C7AAA85FA6AB01FFD4AF7205C7")
    }
}