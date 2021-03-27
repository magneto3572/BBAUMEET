package com.app.bbaumeet

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jitsi.meet.sdk.JitsiMeetActivity
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions
import org.jitsi.meet.sdk.JitsiMeetUserInfo
import java.net.MalformedURLException
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class MainActivity :AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val actionBar=supportActionBar
        actionBar?.setDisplayShowTitleEnabled(false)
        actionBar?.elevation = 0f
        setContentView(R.layout.activity_main)
        val calendar = Calendar.getInstance()
        val date = calendar.time
        day.text = SimpleDateFormat("EEE", Locale.ENGLISH).format(date.time)
        val serverURL: URL = try {
            URL("https://meet.jit.si")
        } catch (e: MalformedURLException) {
            e.printStackTrace()
            throw RuntimeException("Invalid server URL!")
        }

        joinbut.setOnClickListener {
            Handler(Looper.getMainLooper()).post {
                join()
            }
        }
        createroom.setOnClickListener {
            Handler(Looper.getMainLooper()).post {
                create(serverURL)
            }
        }
    }

    private fun create(url: URL){
        val editText = findViewById<EditText>(R.id.meetingurl)
        val text = editText.text.toString()
        if (text.isNotEmpty()) {
            val options = JitsiMeetConferenceOptions.Builder()
                .setServerURL(url)
                .setRoom(text)
                .build()
            JitsiMeetActivity.launch(this, options)
        }
        else{
            Toast.makeText(this, "Please Create Meeting Code To Begin", Toast.LENGTH_SHORT).show()
        }
    }

    private fun join(){
        val b = JitsiMeetUserInfo()
        b.displayName="Bishal"
        b.email="bishal.singh.3572@gmail.com"
        val editText = findViewById<EditText>(R.id.meetingurl)
        val text = editText.text.toString()
        if (text.isNotEmpty()) {
            val options = JitsiMeetConferenceOptions.Builder()
                .setRoom(text)
                .setFeatureFlag("Hello", true)
                .setAudioMuted(true)
                .setUserInfo(b) 
                .setVideoMuted(true)
                .setWelcomePageEnabled(false)
                .build()
            JitsiMeetActivity.launch(this, options)
        }
        else{
            Toast.makeText(this, "Please Enter Meeting Code To Join", Toast.LENGTH_SHORT).show()
        }
    }
}