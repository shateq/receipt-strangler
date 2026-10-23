package com.swieczorek.receipts.lib

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.format.DateTimeParseException

class ReceiptParser {

}

/**
 * Maps lines in sequence to List<Product>
 * Checks it total is equal to the items at the end
 * Removes latter items until it is
 */
fun parseReceipt(lines: Sequence<String>): Receipt {
    var total = 0.0f
    val products = ArrayList<Product>()
    // Windowed for checking if the next item is the end of the list
    lines.windowed(2).forEach { pair ->
        val fst = pair.first()
        if (fst.isEmpty()) {
            return@forEach
        }

        val p1 = parseProduct(fst) ?: return@forEach
        // Probably bad??
        if (p1.name.startsWith("PTU") || p1.name.startsWith("Kwota")) {
            return@forEach
        }

        val lst = pair.last()
        // Check if it's the end of listing
        // Null price is better than isEmpty
        if (parseProduct(lst) == null) {
            if (total == p1.price) {
                return Receipt(p1.price, products)
            }
//            else if (p1.price < total) {
//                val ult = products.last()
//                total -= ult.price
//
//                products.removeLast()
//                return Receipt(total, products)
//            }
//        } else if (total < p1.price) - uncatched
        }

        products.add(p1)
        total += p1.price
//        val p2 = parseProduct(lst) ?: return@forEach
    }

    return Receipt(total, products)
}


/**
 * Returns product object from a line with a price regex
 * Nullable
 */
fun parseProduct(string: String): Product? {
    val price = findPrice(string) ?: return null

    val spl = string.split(" ")
    val name = spl.subList(0, spl.size - 1).joinToString(" ")

//    val leaveDigits = Regex("[^0-9.,]")
//    val last = spl.last()
//    val price = leaveDigits.replace(last, "").replace(',','.').toFloatOrNull()
    return Product(name, price)
}

//private fun maybeTotal(s: String): Boolean {
//}

/**
 * Find total locale-PL
 */
internal fun findTotal(s: String): Float? {
    val re = Regex("^total |^razem", RegexOption.IGNORE_CASE)
    val all = re.findAll(s)
    if (all.none()) return null
    return findPrice(s)
}

/**
 * Return last price match on the current line
 * Non-nullable
 */
internal fun findPrice(s: String): Float? {
    val re = Regex("-?\\d+[,.]\\d{2}")
    val all = re.findAll(s)
    if (all.none()) return null

    val last = all.last().value.replace(",", ".")
    return last.toFloatOrNull() ?: 0.0f
}

private val rePolish = Regex("\\d+\\.\\d+\\.\\d{4}")
private val reStandard = Regex("\\d{4}-\\d+-\\d+")

/**
 * 2026-01-13 - lidl formatting
 * 13.01.2026 - kaufland formatting
 *
 * Use it on line or a whole result, probably should use it on result for performance
 */
internal fun parseDate(string: String): LocalDate {
    val formatter = DateTimeFormatterBuilder()
        .appendOptional(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
        .appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        .toFormatter()

    val match = rePolish.find(string) ?: reStandard.find(string)
    val ns = match?.value?.replace("/", ".")

    return try {
        LocalDate.parse(ns!!, formatter)
    } catch (_: DateTimeParseException) {
        LocalDate.now()
    }
}
//    Leave only the digits approach
//    val onlyDigits = Regex("[^0-9. ]")
//    val ns = onlyDigits.replace(string, "")