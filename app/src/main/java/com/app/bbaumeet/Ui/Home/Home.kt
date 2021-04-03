package com.app.bbaumeet.Ui.Home

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.app.bbaumeet.R
import org.jitsi.meet.sdk.JitsiMeetActivity
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions
import org.jitsi.meet.sdk.JitsiMeetUserInfo
import soup.neumorphism.NeumorphButton
import java.net.MalformedURLException
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class Home : Fragment() {

    companion object {
        fun newInstance() = Home()
    }
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v =  inflater.inflate(R.layout.home_fragment, container, false)
        val calendar = Calendar.getInstance()
        val date = calendar.time
        val dayte = v.findViewById<TextView>(R.id.day)
        dayte?.text = SimpleDateFormat("EEE", Locale.ENGLISH).format(date.time)
        val serverURL: URL = try {
            URL("https://meet.jit.si")
        } catch (e: MalformedURLException) {
            e.printStackTrace()
            throw RuntimeException("Invalid server URL!")
        }
        val join = v.findViewById<NeumorphButton>(R.id.joinbut)
        val create = v.findViewById<NeumorphButton>(R.id.createroom)
        join?.setOnClickListener {
            Handler(Looper.getMainLooper()).post {
                join()
            }
        }
        create?.setOnClickListener {
            Handler(Looper.getMainLooper()).post {
                create(serverURL)
            }
        }
        return v
    }

    private fun create(url: URL){
        val editText = view?.findViewById<EditText>(R.id.meetingurl)
        val text = editText?.text.toString()
        if (text.isNotEmpty()) {
            val options = JitsiMeetConferenceOptions.Builder()
                .setServerURL(url)
                .setRoom(text)
                .build()
            JitsiMeetActivity.launch(requireActivity(), options)
        }
        else{
            Toast.makeText(requireActivity(), "Please Create Meeting Code To Begin", Toast.LENGTH_SHORT).show()
        }
    }

    private fun join(){
        val b = JitsiMeetUserInfo()
        b.displayName="Bishal"
        b.email="bishal.singh.3572@gmail.com"
        val editText = view?.findViewById<EditText>(R.id.meetingurl)
        val text = editText?.text.toString()
        if (text.isNotEmpty()) {
            val options = JitsiMeetConferenceOptions.Builder()
                .setRoom(text)
                .setFeatureFlag("Hello", true)
                .setAudioMuted(true)
                .setUserInfo(b)
                .setVideoMuted(true)
                .setWelcomePageEnabled(false)
                .build()
            JitsiMeetActivity.launch(requireActivity(), options)
        }
        else{
            Toast.makeText(requireActivity(), "Please Enter Meeting Code To Join", Toast.LENGTH_SHORT).show()
        }
    }

}