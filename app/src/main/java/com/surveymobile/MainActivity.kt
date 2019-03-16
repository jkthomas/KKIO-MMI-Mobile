package com.surveymobile

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.ContextCompat
import android.support.v7.app.ActionBar
import android.support.v7.app.AlertDialog
import com.surveymobile.fragments.InstructionsFragment
import com.surveymobile.fragments.StatisticsFragment
import com.surveymobile.fragments.StatisticsFragment.OnFragmentInteractionListener
import com.surveymobile.fragments.SurveyFragment
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), OnFragmentInteractionListener {
    lateinit var navigationBar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        //TODO: Implement runtime theme switch
        setTheme(R.style.DarkTheme)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        checkPermissions()
    }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            //permission is not granted - request permisions
            setInternetPermissions()
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            //permission is not granted - request permisions
            setStoragePermissions()
        }


        //permission granted already - check connection
        if (isOnline()) {
            initializeNavigationBar()
        } else {
            shutdownApplication("No connection with server", "Connection with serwer couldn't be established. Application will shut down.")
        }
    }

    private fun setInternetPermissions() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.INTERNET), 1)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED) {
            shutdownApplication("Permissions not provided", "Permission not granted. Shutting down...")
        }
    }

    private fun setStoragePermissions() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            shutdownApplication("Permissions not provided", "Permission not granted. Shutting down...")
        }
    }

    private fun initializeNavigationBar() {
        navigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.instructions -> {
                    val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
                    ft.replace(R.id.container, InstructionsFragment())
                    ft.commit()
                    true
                }
                R.id.statistics -> {
                    val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
                    ft.replace(R.id.container, StatisticsFragment())
                    ft.commit()
                    true
                }
                R.id.survey -> {
                    val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
                    ft.replace(R.id.container, SurveyFragment())
                    ft.commit()
                    true
                }
                else -> false
            }
        }
        navigationView.selectedItemId = R.id.instructions
    }

    private fun shutdownApplication(shutdownAlertTitle: String, shutdownAlertMessage: String) {
        val shutdownAlertBuilder = AlertDialog.Builder(this)
        shutdownAlertBuilder.setTitle(shutdownAlertTitle)
        shutdownAlertBuilder.setMessage(shutdownAlertMessage)
        shutdownAlertBuilder.setPositiveButton("Shutdown") { _, _ ->
            finish()
        }
        val shutdownDialog: AlertDialog = shutdownAlertBuilder.create()
        shutdownDialog.show()
    }

    private fun isOnline(): Boolean {
        val runtime = Runtime.getRuntime()
        try {
            val ipProcess = runtime.exec("/system/bin/ping -c 1 8.8.8.8")
            val exitValue = ipProcess.waitFor()
            return exitValue == 0
        } catch (e: Exception) {
            e.printStackTrace()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        return false
    }

    override fun onFragmentInteraction(uri: Uri) {
        TODO("not implemented")
    }
}
