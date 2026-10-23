package com.swieczorek.receipts

import net.sourceforge.tess4j.Tesseract1
import net.sourceforge.tess4j.TesseractException
import net.sourceforge.tess4j.util.LoadLibs
import net.sourceforge.tess4j.util.PdfBoxUtilities
import org.apache.pdfbox.Loader
import org.apache.pdfbox.pdmodel.PDDocument
import org.apache.pdfbox.text.PDFTextStripper
import java.io.File
import java.io.IOException

class Ocr {
    val instance = Tesseract1() // JNA Direct Mapping (faster)
//    val instance = Tesseract() // JNA Mapping

    fun ocrImage(pathname: String): String? {
        var imageFile = File(pathname)
        App.LOG.debug("path: $pathname")
        val isPdf = isPdf(imageFile)

        val tessDataFolder: File = tessData("tessdata")
        instance.setDatapath(tessDataFolder.path)
        instance.setLanguage("pol")

        try {
            if (isPdf) {
                imageFile = PdfBoxUtilities.convertPdf2Tiff(imageFile)
            }

            val result: String = instance.doOCR(imageFile)
            return result

        } catch (e: TesseractException) {
            println(e.message)
        }

        return null
    }

    fun isPdf(imageFile: File): Boolean {
       return imageFile.extension == "pdf"
    }

    private fun tessData(file: String): File {
        return LoadLibs.extractTessResources(file)
    }
}


fun extractText(pathname: String) {
    val file = File(pathname)
    App.LOG.info("pathname: $pathname")

    var document: PDDocument
    try {
        document = Loader.loadPDF(file)
//        val access = document.currentAccessPermission
//        if (!access.canExtractContent()) { throw IOException("No perms")
//        }

        val stripper = PDFTextStripper()
        stripper.sortByPosition = true

        for (p in 1..document.numberOfPages) {
            // Set the page interval to extract. If you don't, then all pages would be extracted.
            stripper.startPage = p
            stripper.endPage = p

            // let the magic happen
            val text: String = stripper.getText(document)

            // do some nice output with a header
            val pageStr: String = String.format("page %d:", p)
            println(pageStr)

            for (i in 0..pageStr.length) {
                print("-")
            }
            println()
            println(text.trim())
            println()
        }
    } catch (e: IOException) {
        e.printStackTrace()
    }
}