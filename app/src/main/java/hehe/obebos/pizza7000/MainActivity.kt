package hehe.obebos.pizza7000

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.yandex.mapkit.MapKitFactory
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var bottomNavigationView: BottomNavigationView

    fun onClickGoMain2(view: View) {
        val intent = Intent(this, MainActivity2::class.java)
        startActivity(intent)
    }

    companion object {
        const val MAPKIT_API_KEY = "aa08877f-d508-425c-9274-ed039a4ed158" // API-ключ
    }

    override fun onSaveInstanceState(outState: Bundle) { // Проверка на API-ключ перед инициализацией MapKitFactory
        super.onSaveInstanceState(outState)
        outState.putBoolean("haveApiKey", true)
    }

    private fun setApiKey(savedInstanceState: Bundle?) {
        val haveApiKey = savedInstanceState?.getBoolean("haveApiKey")
            ?: false // При первом запуске приложения всегда false
        if (!haveApiKey) {
            MapKitFactory.setApiKey(MAPKIT_API_KEY) // API-ключ должен быть задан единожды перед инициализацией MapKitFactory
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setApiKey(savedInstanceState)               // Задаем API-ключ
        MapKitFactory.initialize(this)      // Инициализируем MapKitFactory

        setContentView(R.layout.activity_main)

        drawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)
        bottomNavigationView = findViewById(R.id.bottom_navigation)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)  // Активация toolbar
        setSupportActionBar(toolbar)

        val navigationView =
            findViewById<NavigationView>(R.id.nav_view) // Активация NavigationDrawer
        navigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.open_nav,
            R.string.close_nav
        ) // Для слепых)

        drawerLayout.addDrawerListener(toggle) // Прослушка пролистываний
        toggle.syncState()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment())
                .commit()  // При любых проблемах отсылает на HomeFragment()
            navigationView.setCheckedItem(R.id.nav_home)
        }

        bottomNavigationView = findViewById(R.id.bottom_navigation)

        bottomNavigationView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.bottom_home -> {
                    replaceFragment(HomeFragment())
                    true
                }

                R.id.bottom_settings -> {
                    replaceFragment(SettingsFragment())
                    true
                }

                R.id.bottom_share -> {
                    replaceFragment(ShareFragment())
                    true
                }

                R.id.bottom_about -> {
                    replaceFragment(AboutFragment())
                    true
                }

                R.id.bottom_map -> {
                    replaceFragment(MapFragment())
                    true
                }

                else -> {false}
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {        // Тут слушаются нажатия в NavigationDrawer
        when (item.itemId) {
            R.id.nav_home -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment()).commit()

            R.id.nav_map -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, MapFragment()).commit()

            R.id.nav_settings -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SettingsFragment()).commit()

            R.id.nav_share -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, ShareFragment()).commit()

            R.id.nav_about -> supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AboutFragment()).commit()

            R.id.nav_logout -> {
                Toast.makeText(this, "Logout!", Toast.LENGTH_SHORT).show()  // Выход (Крутой)
                finishAndRemoveTask()
                exitProcess(0)
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START) // Закрывает NavigationDrawer после выполнения функции (Прослушки нажатий)
        return true
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
            .commit()
    }

    @Deprecated("Deprecated in Java")
    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) { // Штука для работы кнопки назад
            drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            onBackPressedDispatcher.onBackPressed()
        }
    }
}