package com.swieczorek.receipts.test

import com.swieczorek.receipts.lib.findPrice
import com.swieczorek.receipts.lib.parseDate
import java.time.Month
import kotlin.test.Test
import kotlin.test.assertEquals

class UtilTest {

    @Test
    fun negativePriceHandled() {
        val sale = "Lidl Plus oferta -3,50"
        assertEquals(-3.5f, findPrice(sale))

        val positive = "Lidl Plus oferta 6,50"
        assertEquals(6.5f, findPrice(positive))
    }

    @Test
    fun priceIsReturned() {
        val priceA = findPrice("Red Bull Class 1 * 8.98 8.98 A")
        assertEquals(8.98f, priceA)

        val priceB = findPrice("Trybunał Pils 6,0% 0,51 4,49")
        assertEquals(4.49f, priceB)

        val priceC = findPrice("HtKajzerka pszenna 55g 0,35b")
        assertEquals(0.35f, priceC)
    }

    @Test
    fun simpleDateIsParsed() {
        val d = parseDate("05.12.2027 22:30")
        assertEquals(5, d.dayOfMonth)
        assertEquals(Month.DECEMBER, d.month)
        assertEquals(2027, d.year)
    }

    @Test
    fun extractedDateIsParsed() {
        val kaufland = parseDate("Date: 03.03.2026 - Time: 1821")
        assertEquals(3, kaufland.dayOfMonth)
        assertEquals(Month.MARCH, kaufland.month)
        assertEquals(2026, kaufland.year)

        val lidl = parseDate("2025-01-13")
        assertEquals(13, lidl.dayOfMonth)
        assertEquals(Month.JANUARY, lidl.month)
        assertEquals(2025, lidl.year)
    }
}