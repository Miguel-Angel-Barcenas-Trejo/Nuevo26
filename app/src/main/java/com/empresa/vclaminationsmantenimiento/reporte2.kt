package com.empresa.vclaminationsmantenimiento

import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore

class reporte2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reporte2)

        val db = FirebaseFirestore.getInstance()

        // EditTexts
        val etFecha = findViewById<EditText>(R.id.etFecha)
        val etReporte = findViewById<EditText>(R.id.etReporte)
        val etTurno = findViewById<EditText>(R.id.etTurno)
        val etMaquina = findViewById<EditText>(R.id.etMaquina)
        val etResponsable = findViewById<EditText>(R.id.etResponsable)
        val etTipoReporte = findViewById<EditText>(R.id.etTipoReporte)
        val etParoMaquina = findViewById<EditText>(R.id.etParoMaquina)
        val etTipoFalla = findViewById<EditText>(R.id.etTipoFalla)
        val etCausaRaiz = findViewById<EditText>(R.id.etCausaRaiz)
        val etActividad = findViewById<EditText>(R.id.etActividad)
        val etHoraReporte = findViewById<EditText>(R.id.etHoraReporte)
        val etHoraInicio = findViewById<EditText>(R.id.etHoraInicio)
        val etHoraFinal = findViewById<EditText>(R.id.etHoraFinal)
        val etTiempoTotal = findViewById<EditText>(R.id.etTiempoTotal)
        val etRequerimientos = findViewById<EditText>(R.id.etRequerimientos)
        val etObservaciones = findViewById<EditText>(R.id.etObservaciones)

        // Spinners y contraseñas
        val spinnerSolicitado = findViewById<Spinner>(R.id.spinnerSolicitado)
        val spinnerVoBo = findViewById<Spinner>(R.id.spinnerVoBo)
        val spinnerJefe = findViewById<Spinner>(R.id.spinnerJefe)

        val etContrasenaSolicitado = findViewById<EditText>(R.id.etContrasenaSolicitado)
        val etContrasenaVoBo = findViewById<EditText>(R.id.etContrasenaVoBo)
        val etContrasenaJefe = findViewById<EditText>(R.id.etContrasenaJefe)
        val spinnerVoTecnico = findViewById<Spinner>(R.id.spinnerVoTecnico)
        val etContrasenaVoTecnico = findViewById<EditText>(R.id.etContrasenaVoTecnico)


        val btnEnviarCorreo = findViewById<Button>(R.id.btnDescargar)

        // Nombre de la máquina
        val buttonText = intent.getStringExtra("buttonText") ?: "Sin título"
        etMaquina.setText(buttonText)

        // Listas personalizadas de firmas
        val firmasSolicitado = mapOf(
            "Firma de 'Solicitado'" to "Firma de Solicitado.png",
            "Eduardo Mendoza" to "Eduardo_mendoza.png",
            "Irvin Cano" to "Irvin_cano.png",
            "Gabriel Trejo" to "Gabriel_trejo.png",
            "Alexis Reyes" to "Alexis_reyes.png",
            "Cecilia Hernandez" to "Cecilia_hernandez2.png",
            "Jose Alberto" to "Jose_alberto.png"
        )

        val firmasVoBo = mapOf(
            "Firma de 'Recibo'" to "Firma de Solicitado.png",
            "Eduardo Mendoza" to "Eduardo_mendoza.png",
            "Irvin Cano" to "Irvin_cano.png",
            "Gabriel Trejo" to "Gabriel_trejo.png",
            "Alexis Reyes" to "Alexis_reyes.png",
            "Cecilia Hernandez" to "Cecilia_hernandez2.png",
            "Jose Alberto" to "Jose_alberto.png"
        )

        val firmasJefe = mapOf(
            "Firma de 'Realizado'" to "Firma de Solicitado.png",
            "Eduardo Mendoza" to "Eduardo_mendoza.png",
            "Irvin Cano" to "Irvin_cano.png",
            "Gabriel Trejo" to "Gabriel_trejo.png",
            "Alexis Reyes" to "Alexis_reyes.png",
            "Cecilia Hernandez" to "Cecilia_hernandez2.png",
            "Jose Alberto" to "Jose_alberto.png"
        )

        val firmasVoTecnico = mapOf(
            "Firma de 'Conformidad'" to "Firma de Solicitado.png",
            "Eduardo Mendoza" to "Eduardo_mendoza.png",
            "Irvin Cano" to "Irvin_cano.png",
            "Gabriel Trejo" to "Gabriel_trejo.png",
            "Alexis Reyes" to "Alexis_reyes.png",
            "Cecilia Hernandez" to "Cecilia_hernandez2.png",
            "Jose Alberto" to "Jose_alberto.png"
        )

        spinnerSolicitado.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, firmasSolicitado.keys.toList()).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        spinnerVoBo.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, firmasVoBo.keys.toList()).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        spinnerJefe.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, firmasJefe.keys.toList()).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        spinnerVoTecnico.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, firmasVoTecnico.keys.toList()).apply {
            setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }


        // Contraseñas por firma
        val contrasenasFirmas = mapOf(
            "Eduardo_mendoza.png" to "1234",
            "Irvin_cano.png" to "abcd",
            "Gabriel_trejo.png" to "12345",
            "Alexis_reyes.png" to "123456789",
            "Cecilia_hernandez2.png" to "4321",
            "Jose_alberto.png" to "abc321"
        )

        //fun requiereValidarContrasena(nombre: String): Boolean {
        // return nombre != "Firma de 'Solicitado'" &&
        //   nombre != "Firma de 'Recibo'" &&
        //   nombre != "Firma de 'Realizado'" &&
        //    nombre != "Firma de 'Conformidad'"
        // }

        fun requiereValidarContrasena(nombre: String): Boolean {
            val noValidar = listOf("Solicitado", "Recibo", "Realizado", "Conformidad")
            return noValidar.none { nombre.contains(it) }
        }

        btnEnviarCorreo.setOnClickListener {
            val seleccionadoSolicitado = spinnerSolicitado.selectedItem.toString()
            val seleccionadoVoBo = spinnerVoBo.selectedItem.toString()
            val seleccionadoJefe = spinnerJefe.selectedItem.toString()

            val passSolicitado = etContrasenaSolicitado.text.toString()
            val passVoBo = etContrasenaVoBo.text.toString()
            val passJefe = etContrasenaJefe.text.toString()
            val seleccionadoVoTecnico = spinnerVoTecnico.selectedItem.toString()
            val passVoTecnico = etContrasenaVoTecnico.text.toString()

            // Validación de selección y contraseña con excepción para "Firma de ..."
            if (requiereValidarContrasena(seleccionadoSolicitado)) {
                val archivoSolicitado = firmasSolicitado[seleccionadoSolicitado] ?: ""
                if (!contrasenasFirmas.containsKey(archivoSolicitado) || passSolicitado != contrasenasFirmas[archivoSolicitado]) {
                    Toast.makeText(this, "Contraseña incorrecta para 'Solicitado'", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }

            if (requiereValidarContrasena(seleccionadoVoBo)) {
                val archivoVoBo = firmasVoBo[seleccionadoVoBo] ?: ""
                if (!contrasenasFirmas.containsKey(archivoVoBo) || passVoBo != contrasenasFirmas[archivoVoBo]) {
                    Toast.makeText(this, "Contraseña incorrecta para 'Recibo'", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }

            if (requiereValidarContrasena(seleccionadoJefe)) {
                val archivoJefe = firmasJefe[seleccionadoJefe] ?: ""
                if (!contrasenasFirmas.containsKey(archivoJefe) || passJefe != contrasenasFirmas[archivoJefe]) {
                    Toast.makeText(this, "Contraseña incorrecta para 'Realizado'", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }

            if (requiereValidarContrasena(seleccionadoVoTecnico)) {
                val archivoVoTecnico = firmasVoTecnico[seleccionadoVoTecnico] ?: ""
                if (!contrasenasFirmas.containsKey(archivoVoTecnico) || passVoTecnico != contrasenasFirmas[archivoVoTecnico]) {
                    Toast.makeText(this, "Contraseña incorrecta para 'Conformidad'", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
            }

            // Cargar imágenes de firma
            val archivoSolicitado = firmasSolicitado[seleccionadoSolicitado] ?: ""
            val firmaSolicitado = BitmapFactory.decodeStream(assets.open(archivoSolicitado))

            val archivoVoBo = firmasVoBo[seleccionadoVoBo] ?: ""
            val firmaVoBo = BitmapFactory.decodeStream(assets.open(archivoVoBo))

            val archivoJefe = firmasJefe[seleccionadoJefe] ?: ""
            val firmaJefe = BitmapFactory.decodeStream(assets.open(archivoJefe))

            val archivoVoTecnico = firmasVoTecnico[seleccionadoVoTecnico] ?: ""
            val firmaVoTecnico = BitmapFactory.decodeStream(assets.open(archivoVoTecnico))

            val datos = mapOf(
                "fecha" to etFecha.text.toString(),
                "reporte" to etReporte.text.toString(),
                "turno" to etTurno.text.toString(),
                "maquina" to etMaquina.text.toString(),
                "responsable" to etResponsable.text.toString(),
                "tipoReporte" to etTipoReporte.text.toString(),
                "paroMaquina" to etParoMaquina.text.toString(),
                "tipoFalla" to etTipoFalla.text.toString(),
                "causaRaiz" to etCausaRaiz.text.toString(),
                "actividad" to etActividad.text.toString(),
                "horaReporte" to etHoraReporte.text.toString(),
                "horaInicio" to etHoraInicio.text.toString(),
                "horaFinal" to etHoraFinal.text.toString(),
                "tiempoTotal" to etTiempoTotal.text.toString(),
                "requerimientos" to etRequerimientos.text.toString(),
                "observaciones" to etObservaciones.text.toString()
            )

            // Guardar en Firestore
            val nombreColeccion = etMaquina.text.toString().trim()
            if (nombreColeccion.isNotEmpty()) {
                db.collection(nombreColeccion)
                    .add(datos)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Datos guardados en: $nombreColeccion", Toast.LENGTH_SHORT).show()
                        enviarDatosAGoogleSheets(datos)
                    }
            }

            // Generar PDF y enviar por correo
            //val pdfFile = PdfHelper.generatePdf(this, datos, firmaSolicitado, firmaVoBo, firmaJefe)
            val pdfFile = PdfHelper.generatePdf(this, datos, firmaSolicitado, firmaVoBo, firmaJefe, firmaVoTecnico)

            if (pdfFile.exists()) {
                val destinatarios = listOf("barcenasm342@gmail.com", "auxiliar.mantenimiento@vclaminations.com", "supervisores.prensas@vclaminations.com", "jmantenimiento@vclaminations.com")
                EmailHelper.sendEmail(this, destinatarios, pdfFile)
                Toast.makeText(this, "Correo enviado con el PDF adjunto", Toast.LENGTH_SHORT).show()
            } else {
                Log.e("PDF", "El archivo no se generó correctamente")
                Toast.makeText(this, "Error al generar el archivo PDF", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun enviarDatosAGoogleSheets(datos: Map<String, String>) {
        val url = "https://script.google.com/macros/s/AKfycbxalYAZ9hoaoVowvK2SPyaxR1bxdVv7EQ7xPbqo17XP5FVzoPUvWZre_pnZXRidQyL7ag/exec"
        val queue = com.android.volley.toolbox.Volley.newRequestQueue(this)

        val request = object : com.android.volley.toolbox.StringRequest(
            com.android.volley.Request.Method.POST, url,
            { response ->
                Toast.makeText(this, "Datos enviados a Google Sheets", Toast.LENGTH_SHORT).show()
                Toast.makeText(this, "Respuesta: $response", Toast.LENGTH_LONG).show()
            },
            { error ->
                Toast.makeText(this, "Error al enviar a Sheets", Toast.LENGTH_SHORT).show()
                error.printStackTrace()
            }
        ) {
            override fun getParams(): Map<String, String> = datos
            override fun getBodyContentType(): String = "application/x-www-form-urlencoded; charset=UTF-8"
        }

        queue.add(request)
    }
}

