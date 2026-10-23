package com.swieczorek.receipts.lib

import java.time.LocalDate

data class Product(val name: String, val price: Float)

data class Receipt(
    val total: Float,
    val products: List<Product>,
    val shop: String = "noname",
    val date: LocalDate = LocalDate.now()
) {
    fun productsToString(): String {
        val builder = StringBuilder()
        products.forEachIndexed { idx, it ->
            builder.append("$idx. ")
            builder.append(it.name)
            builder.append(" - $${it.price}")
            builder.append("\n")
        }
        return builder.toString()
    }
}