package gaur.himanshu.gpstracker.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.google.mlkit.nl.translate.TranslateLanguage
import com.google.mlkit.nl.translate.Translation
import com.google.mlkit.nl.translate.TranslatorOptions

class TranslatorService : Service() {

    val binder = TranslatorBinder()

    inner class TranslatorBinder : Binder() {
        fun getService() = this@TranslatorService
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun onBind(p0: Intent?): IBinder? {
        return binder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        return true
    }

    override fun onRebind(intent: Intent?) {
        super.onRebind(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    fun translate(text: String, onResult: (String) -> Unit, onFailure: () -> Unit) {
        val options = TranslatorOptions.Builder()
            .setSourceLanguage(TranslateLanguage.ENGLISH)
            .setTargetLanguage(TranslateLanguage.HINDI)
            .build()
        val client = Translation.getClient(options)

        client.downloadModelIfNeeded()
            .addOnSuccessListener {
                client.translate(text)
                    .addOnSuccessListener {
                        onResult.invoke(it)
                    }.addOnFailureListener {
                        onFailure.invoke()
                    }
            }.addOnFailureListener {
                println(it.toString())
            }
    }


}