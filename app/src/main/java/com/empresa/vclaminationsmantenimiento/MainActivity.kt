package com.empresa.vclaminationsmantenimiento

import android.app.AlarmManager
import android.app.PendingIntent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.empresa.vclaminationsmantenimiento.databinding.ActivityMainBinding
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import android.content.Intent
import android.view.View
import androidx.core.view.isVisible
import com.google.firebase.auth.FirebaseAuth
import com.empresa.vclaminationsmantenimiento.ui.gallery.GalleryFragment
import androidx.appcompat.app.AppCompatDelegate
import android.content.Context
import android.util.Log


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Fuerza el modo claro, sin importar el modo del sistema
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        // Notificaciones mensajes
        val alarmIntent = Intent(this, ChatCheckAlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            this, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        //val interval = 10 * 60 * 1000L // cada 10 minutos
        val interval = 30 * 1000L // 30 segundos
        val startTime = System.currentTimeMillis() + 5000 // empieza en 5 segundos

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            startTime,
            interval,
            pendingIntent
        )
        // ⬇️ AQUI va el Log para saber que se programó la alarma
        Log.d("MainActivity", "Alarma configurada para cada 10 minutos")
        // -----------------------------------------------------------------------------------------

        binding.appBarMain.fab.setOnClickListener { view ->
            startActivity(Intent(this, ChatActivity::class.java))
            Snackbar.make(view, "Abriendo chat", Snackbar.LENGTH_LONG)
                .setAction("Action", null)
                .setAnchorView(R.id.fab).show()
        }

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    // login ----------------------------------------------------------------------------------------------------------
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this, LoginEmail::class.java))
                finish()
                true
            }
            R.id.action_settings -> {
                val intent = Intent(this, Ayuda::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
    // ------------------------------------------------------------------------------------------------------------------

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    // ======================================================
    // MANEJO DE SUBMENÚS (VERSIÓN OPTIMIZADA)
    // ======================================================

    /**
     * Función genérica para mostrar/ocultar cualquier submenú
     * @param viewId ID de la vista del submenú (ej: R.id.home_submenu)
     */
    private fun toggleSubmenu(viewId: Int) {
        val submenu = findViewById<View>(viewId) ?: return
        submenu.visibility = if (submenu.isVisible) View.GONE else View.VISIBLE
    }

    /**
     * Versiones específicas que llaman a la función genérica
     * (Mantienen la misma firma para compatibilidad con onClick en XML)
     */
    fun toggleHomeMenu(view: View) = toggleSubmenu(R.id.home_submenu)
    fun toggleHomeOption1Menu(view: View) = toggleSubmenu(R.id.home_option1_submenu)
    fun toggleHomeOption2Menu(view: View) = toggleSubmenu(R.id.home_option2_submenu)
    fun toggleHomeOption3Menu(view: View) = toggleSubmenu(R.id.home_option3_submenu)
    fun toggleHomeOption4Menu(view: View) = toggleSubmenu(R.id.home_option4_submenu)
    fun toggleGalleryMenu(view: View) = toggleSubmenu(R.id.gallery_submenu)
    fun toggleGalleryOption1Menu(view: View) = toggleSubmenu(R.id.gallery_option1_submenu)
    fun toggleGalleryOption2Menu(view: View) = toggleSubmenu(R.id.gallery_option2_submenu)
    fun toggleGalleryOption3Menu(view: View) = toggleSubmenu(R.id.gallery_option3_submenu)
    fun toggleGalleryOption4Menu(view: View) = toggleSubmenu(R.id.gallery_option4_submenu)
    //fun toggleSlideshowMenu(view: View) = toggleSubmenu(R.id.slideshow_submenu)
    //fun navigateToSlideshowOption1(view: View) = navigateToActivity(view)
    //fun navigateToSlideshowOption2(view: View) = navigateToActivity(view)


    // ======================================================
    // MANEJO DE NAVEGACIÓN (VERSIÓN UNIFICADA)
    // ======================================================

    /**
     * Función unificada para navegar a actividades
     * @param view Vista que dispara la navegación (se usa view.id para determinar destino)
     */
    fun navigateToActivity(view: View) {
        val destination = when (view.id) {
            R.id.btn_ticket -> CrearTicket::class.java  // <- línea que abre CrearTicket al presionar el botón
            // Home
            R.id.btn_home_option1_item1 -> reporte1prev::class.java
            R.id.btn_home_option1_item2 -> reporte1prev::class.java
            R.id.btn_home_option1_item3 -> reporte1prev::class.java
            R.id.btn_home_option1_item4 -> reporte1prev::class.java
            R.id.btn_home_option1_item5 -> reporte1prev::class.java
            R.id.btn_home_option1_item6 -> reporte1prev::class.java
            R.id.btn_home_option1_item7 -> reporte1prev::class.java
            R.id.btn_home_option1_item8 -> reporte1prev::class.java
            R.id.btn_home_option1_item9 -> reporte1prev::class.java
            R.id.btn_home_option1_item10 -> reporte1prev::class.java
            R.id.btn_home_option1_item11 -> reporte1prev::class.java
            R.id.btn_home_option1_item12 -> reporte1prev::class.java
            R.id.btn_home_option1_item13 -> reporte1prev::class.java
            R.id.btn_home_option1_item14 -> reporte1prev::class.java
            R.id.btn_home_option1_item15 -> reporte1prev::class.java
            R.id.btn_home_option1_item16 -> reporte1prev::class.java
            R.id.btn_home_option1_item17 -> reporte1prev::class.java
            R.id.btn_home_option2_item1 -> reporte1prev::class.java
            R.id.btn_home_option2_item2 -> reporte1prev::class.java
            R.id.btn_home_option2_item3 -> reporte1prev::class.java
            R.id.btn_home_option2_item4 -> reporte1prev::class.java
            R.id.btn_home_option2_item5 -> reporte1prev::class.java
            R.id.btn_home_option2_item6 -> reporte1prev::class.java
            R.id.btn_home_option2_item7 -> reporte1prev::class.java
            R.id.btn_home_option3_item1 -> reporte1prev::class.java
            R.id.btn_home_option3_item2 -> reporte1prev::class.java
            R.id.btn_home_option4_item1 -> reporte1prev::class.java
            R.id.btn_home_option4_item2 -> reporte1prev::class.java
            R.id.btn_home_option4_item3 -> reporte1prev::class.java
            // Gallery
            R.id.btn_gallery_option1_item1 -> reporte2::class.java
            // Slideshow
            //R.id.btn_slideshow_option1 -> SlideshowOption1Activity::class.java
            //R.id.btn_slideshow_option2 -> SlideshowOption2Activity::class.java
            // Default
            else -> reporte2::class.java
        }
        // Crear el Intent y pasar el texto del botón
        val intent = Intent(this, destination)
        intent.putExtra("buttonText", (view as Button).text.toString()) // Enviar el texto del botón

        startActivity(intent)
    }
    // ======================================================
    // FIN DE LAS SECCIONES DE MANEJO DE MENÚS Y NAVEGACIÓN
    // ======================================================
}




