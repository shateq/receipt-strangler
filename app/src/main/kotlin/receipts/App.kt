package com.swieczorek.receipts

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.io.File
import kotlin.system.exitProcess

// Look for line
// Price in PLN
// End with line Total none %d

object App {
    val LOG: Logger = LoggerFactory.getLogger(this::class.java)
//    val verbose = false
//    val out: File = File("assets/out1.log")

    @JvmStatic
    fun main(args: Array<String>) {
        if (args.isEmpty() || args.contains("--help")) {
            printUsage(args[0])
            exitProcess(2)
        }

        println(args.toString())
        LOG.debug("PWD: ${File("").canonicalPath}")
        val file = File(args[1])

        // important check
        exitOnBadFile(file)

        val ocr = Ocr()
        val lines = ocr.ocrImage("assets/lidl-out.jpg")

        if (lines != null) {
            LOG.info("Printing result")
            println(lines)

        } else {
            LOG.error("Unexpected, no lines returned")
            exitProcess(1)
        }
    }

    private fun exitOnBadFile(file: File) {
        if (!file.exists()) {
            println("error: No file provided.")
            exitProcess(1)
        }
    }

    private fun printUsage(program: String) {
        println("Receipt Strangler")
        println("Supported filetypes: image/*, application/pdf")
        println("Usage: $program <file>")
    }
}