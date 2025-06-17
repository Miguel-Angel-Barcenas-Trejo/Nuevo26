package com.empresa.vclaminationsmantenimiento

import android.content.Context
import android.graphics.Bitmap
import com.itextpdf.text.Image
import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.PdfStamper
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

object PdfHelper {
    fun generatePdf(
        context: Context,
        datos: Map<String, String>,
        firmaSolicitado: Bitmap,
        firmaVoTecnico: Bitmap,
        firmaJefe: Bitmap,
        firmaVoBo: Bitmap

    ): File {
        val inputStream = context.assets.open("base_template.pdf")
        val tempPdfFile = File(context.externalCacheDir, "Reporte.pdf")

        val reader = PdfReader(inputStream)
        val outputStream = FileOutputStream(tempPdfFile)
        val stamper = PdfStamper(reader, outputStream)
        val form = stamper.acroFields

        // Llenar campos de texto en el formulario PDF
        for ((clave, valor) in datos) {
            form.setField(clave, valor)
        }

        // Obtener la última página para poner las firmas
        val totalPages = reader.numberOfPages
        val content = stamper.getOverContent(totalPages)

        // Función para agregar una firma en coordenadas específicas
        fun agregarFirma(bitmap: Bitmap, x: Float, y: Float) {
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val imageBytes = stream.toByteArray()
            val imagenFirma = Image.getInstance(imageBytes)
            imagenFirma.scaleToFit(150f, 80f)
            imagenFirma.setAbsolutePosition(x, y)
            content.addImage(imagenFirma)
        }

        // Agregar las 4 firmas en diferentes posiciones (ajusta según tu plantilla PDF)
        agregarFirma(firmaSolicitado, 68f, 35f)      // Firma Solicitado por ---solicitado
        agregarFirma(firmaVoTecnico, 195f, 35f)      // Firma Vo. Técnico (posición superior) --- recibo
        agregarFirma(firmaJefe, 305f, 35f)           // Firma Jefe de mantenimiento ---realizado--bien
        agregarFirma(firmaVoBo, 470f, 35f)           // Firma VoBo ------conformidad


        // Aplanar el formulario para que no se pueda editar
        stamper.setFormFlattening(true)
        stamper.close()
        reader.close()

        return tempPdfFile
    }
}






