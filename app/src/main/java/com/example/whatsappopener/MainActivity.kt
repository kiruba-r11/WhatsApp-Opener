package com.example.whatsappopener

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        numberEditText.addTextChangedListener {
            var number = numberEditText.text.toString()
            if(number.startsWith('+'))
                openWhatsAppBtn.isEnabled = number.length == 13
            else
                openWhatsAppBtn.isEnabled = number.length == 10
        }

        openWhatsAppBtn.setOnClickListener {
            var number = numberEditText.text.toString()
            if(validNumber(number)) {
                if (!number.startsWith('+'))
                    number = "+91$number"
                val i = Intent(Intent.ACTION_VIEW)
                i.data = Uri.parse("https://wa.me/$number")
                i.setPackage("com.whatsapp")
                if (i.resolveActivity(packageManager) != null) {
                    startActivity(i)
                } else {
                    Toast.makeText(this, "Please install WhatsApp", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this , "Please enter a valid number", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validNumber(number: String): Boolean {
        number.forEach {
            if(it !in "+1234567890")
                return false
        }
        if(number.length == 13 && !number.startsWith('+'))
            return false
        if(number.length == 10 && number.contains('+'))
            return false
        return true
    }
}