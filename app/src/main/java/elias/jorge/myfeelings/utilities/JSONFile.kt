package elias.jorge.myfeelings.utilities

import android.content.Context
import android.util.Log
import java.io.File
import java.io.IOException

class JSONFile {

    private val MY_FEELINGS = "data.json"

    fun saveData(context: Context, jsonString: String) {
        try {
            context.openFileOutput(MY_FEELINGS, Context.MODE_PRIVATE).use {
                it.write(jsonString.toByteArray())
            }
        } catch (e: Exception) {
            Log.e("GUARDAR", "Error in writing: " + e.localizedMessage)
        }
    }

    fun getData(context: Context): String {
        try {
            val file = File(context.filesDir, MY_FEELINGS)
            if (!file.exists()) {
                Log.e("OBTENER", "El archivo $MY_FEELINGS no existe, creando uno vacío.")
                saveData(context, "{}") // Inicializa el archivo con un JSON vacío
            }
            return file.readText()
        } catch (e: IOException) {
            Log.e("OBTENER", "Error in fetching data: " + e.localizedMessage)
            return ""
        }
    }

}