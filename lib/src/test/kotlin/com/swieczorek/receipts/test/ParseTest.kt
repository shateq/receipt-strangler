package com.swieczorek.receipts.test

import com.swieczorek.receipts.lib.parseProduct
import com.swieczorek.receipts.lib.parseReceipt
import kotlin.test.Test
import kotlin.test.assertEquals

class ParseTest {
    @Test
    fun parseReceiptKaufland() {
        val lines = object {}.javaClass.getResourceAsStream("/kaufland.txt")?.bufferedReader()?.lineSequence()
        val r = parseReceipt(lines!!)

        assertEquals(41.28f, r.total)
        assertEquals(5, r.products.size)
//        assertEquals(LocalDate.of(2026, 3, 3), r.date)
//        assertEquals(Shops.Kaufland.name, r.shop)
    }

    @Test
    fun parseReceiptLidl() {
        val lines = object {}.javaClass.getResourceAsStream("/lidl.txt")?.bufferedReader()?.lineSequence()
        val r = parseReceipt(lines!!)

        assertEquals(25.01f, r.total)
        assertEquals(4, r.products.size)
//        Razem 78,09
    }

    @Test
    fun productSimpleString() {
        val p = parseProduct("BarillaMakaron500g 5,99")!!
        assertEquals("BarillaMakaron500g", p.name)
        assertEquals(5.99f, p.price)
    }

    @Test
    fun productSpacedString() {
        val p = parseProduct("Vegan Croissant Morela 85g 1,99")!!
        assertEquals(p.name, "Vegan Croissant Morela 85g")
        assertEquals(p.price, 1.99f)
    }

    @Test
    fun productPollutedString() {
        val p = parseProduct("HtKajzerka pszenna 55g 0,35b")!!
        assertEquals("HtKajzerka pszenna 55g", p.name)
        assertEquals(0.35f,p.price)
    }
    /* DawtonaKoncentrat190 3,45
    BarillaMakaron500g 5,99
    DijoTortilla250G 4,19
    HtKajzerka pszenna 55g 0,35b
    HKajzerka pszenna 559 0,35b
    HtKajzerka pszenna 55g 0,35b
    Vegan Croissant Morela 85g 1,99
    BevChustOśwież1 5szt. 2,39
    ColgateSzczoteczka/1s 4,79 */
}