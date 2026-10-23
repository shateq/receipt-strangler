package com.swieczorek.receipts.test

import com.swieczorek.receipts.Ocr
import org.junit.jupiter.api.Test
import java.io.File

class AppTest {
    @Test
    fun isPdfCorrect() {
        val file = File("test.java.pdf")
        assert(Ocr().isPdf(file))
    }
}