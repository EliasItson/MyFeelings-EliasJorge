package elias.jorge.myfeelings

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import elias.jorge.myfeelings.utilities.CustomBarDrawable
import elias.jorge.myfeelings.utilities.CustomCircleDrawable
import elias.jorge.myfeelings.utilities.Emocion
import elias.jorge.myfeelings.utilities.JSONFile
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    var jsonFile: JSONFile? = null
    var veryHappy = 0.0f
    var happy = 0.0f
    var neutral = 0.0f
    var sad = 0.0f
    var verySad = 0.0f
    var data: Boolean = false
    var lista = ArrayList<Emocion>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val graph = findViewById<View>(R.id.graph)
        val graphVeryHappy = findViewById<View>(R.id.graphVeryHappy)
        val graphHappy = findViewById<View>(R.id.graphHappy)
        val graphNeutral = findViewById<View>(R.id.graphNeutral)
        val graphSad = findViewById<View>(R.id.graphSad)
        val graphVerySad = findViewById<View>(R.id.graphVerySad)
        val saveButton = findViewById<View>(R.id.guardarButton)
        val veryHappyButton = findViewById<View>(R.id.veryHappyButton)
        val happyButton = findViewById<View>(R.id.happyButton)
        val neutralButton = findViewById<View>(R.id.neutralButton)
        val sadButton = findViewById<View>(R.id.sadButton)
        val verySadButton = findViewById<View>(R.id.verySadButton)

        lista=ArrayList()

        jsonFile=JSONFile()
        fetchingData()
        if(!data){
            var emociones=ArrayList<Emocion>()
            val fondo=CustomCircleDrawable(this,emociones)
            graph.background=fondo
            graphVeryHappy.background=CustomBarDrawable(this, Emocion("Muy feliz",0.0f,R.color.mustard,veryHappy))
            graphHappy.background=CustomBarDrawable(this, Emocion("Feliz",0.0f,R.color.orange,happy))
            graphNeutral.background=CustomBarDrawable(this, Emocion("Neutral",0.0f,R.color.greenie,neutral))
            graphSad.background=CustomBarDrawable(this, Emocion("Triste",0.0f,R.color.blue,sad))
            graphVerySad.background=CustomBarDrawable(this, Emocion("Muy Triste",0.0f,R.color.deepBlue,verySad))
        }else{
            actualizarGrafica()
            iconoMayoria()
        }

        saveButton.setOnClickListener{
            guardar()
        }

        veryHappyButton.setOnClickListener {
            veryHappy ++
            iconoMayoria()
            actualizarGrafica()
        }

        happyButton.setOnClickListener {
            happy ++
            iconoMayoria()
            actualizarGrafica()
        }

        neutralButton.setOnClickListener {
            neutral ++
            iconoMayoria()
            actualizarGrafica()
        }

        sadButton.setOnClickListener {
            sad ++
            iconoMayoria()
            actualizarGrafica()
        }

        verySadButton.setOnClickListener {
            verySad ++
            iconoMayoria()
            actualizarGrafica()
        }
    }

    fun fetchingData(){
        try{
            var json:String=jsonFile?.getData(this)?:""
            if(json!=""){
                this.data=true
                var jsonArray:JSONArray=JSONArray(json)

                this.lista=parseJson(jsonArray)

                for(i in lista){
                    when(i.nombre){
                        "Muy feliz"->{veryHappy=i.total}
                        "Feliz"->{happy=i.total}
                        "Neutral"->{neutral=i.total}
                        "Triste"->{sad=i.total}
                        "Muy triste"->{verySad=i.total}
                    }
                }
            }else{
                this.data=false
            }
        }catch(exception:Exception){
            exception.printStackTrace()
        }
    }
    fun iconoMayoria(){

        val icon = findViewById<ImageView>(R.id.icon)

        when {
            happy > veryHappy && happy > neutral && happy > sad && happy > verySad -> {
                icon.setImageDrawable(resources?.getDrawable(R.drawable.ic_happy))
            }
            veryHappy > happy && veryHappy > neutral && veryHappy > sad && veryHappy > verySad -> {
                icon.setImageDrawable(resources?.getDrawable(R.drawable.ic_veryhappy))
            }
            neutral > happy && neutral > veryHappy && neutral > sad && neutral > verySad -> {
                icon.setImageDrawable(resources?.getDrawable(R.drawable.ic_neutral))
            }
            sad > happy && sad > veryHappy && sad > neutral && sad > verySad -> {
                icon.setImageDrawable(resources?.getDrawable( R.drawable.ic_sad))
            }
            verySad > happy && verySad > veryHappy && verySad > neutral && verySad > sad -> {
                icon.setImageDrawable(resources?.getDrawable(R.drawable.ic_verysad))
            }
        }
    }
    fun actualizarGrafica(){
        val total=veryHappy+happy+neutral+verySad+sad

        var pVhappy:Float=(veryHappy*100/total).toFloat()
        var pHappy:Float=(happy*100/total).toFloat()
        var pNeutral:Float=(neutral*100/total).toFloat()
        var pSad:Float=(neutral*100/total).toFloat()
        var pVerySad:Float=(verySad*100/total).toFloat()

        Log.d("porcentajes","very happy"+pVhappy)
        Log.d("porcentajes","happy"+pHappy)
        Log.d("porcentajes","neutral"+pNeutral)
        Log.d("porcentajes","sad"+pSad)
        Log.d("porcentajes","very sad"+pVerySad)

        lista.clear()
        lista.add(Emocion("Muy feliz",pVhappy,R.color.mustard,veryHappy))
        lista.add(Emocion("Feliz",pHappy,R.color.orange,happy))
        lista.add(Emocion("Neutral",pNeutral,R.color.greenie,neutral))
        lista.add(Emocion("Triste",pVerySad,R.color.blue,sad))
        lista.add(Emocion("Muy Triste",pVerySad,R.color.deepBlue,verySad))

        val fondo=CustomCircleDrawable(this,lista)

        val graph = findViewById<View>(R.id.graph)
        val graphVeryHappy = findViewById<View>(R.id.graphVeryHappy)
        val graphHappy = findViewById<View>(R.id.graphHappy)
        val graphNeutral = findViewById<View>(R.id.graphNeutral)
        val graphSad = findViewById<View>(R.id.graphSad)
        val graphVerySad = findViewById<View>(R.id.graphVerySad)

        graphVeryHappy.background=CustomBarDrawable(this,Emocion("Muy feliz",pVhappy,R.color.mustard,veryHappy))
        graphHappy.background=CustomBarDrawable(this,Emocion("Feliz",pHappy,R.color.orange,happy))
        graphNeutral.background=CustomBarDrawable(this,Emocion("Neutral",pNeutral,R.color.greenie,neutral))
        graphSad.background=CustomBarDrawable(this,Emocion("Triste",pSad,R.color.blue,sad))
        graphVerySad.background=CustomBarDrawable(this,Emocion("Muy Triste",pVerySad,R.color.deepBlue,verySad))

        graph.background=fondo
    }
    fun parseJson(jsonArray: JSONArray): ArrayList<Emocion>{
        var lista=ArrayList<Emocion>()

        for (i in 0..jsonArray.length()){
            try{
                val nombre=jsonArray.getJSONObject(i).getString("nombre")
                val porcentaje=jsonArray.getJSONObject(i).getString("porcentaje").toFloat()
                val color=jsonArray.getJSONObject(i).getInt("color")
                val total=jsonArray.getJSONObject(i).getString("total").toFloat()
                var emocion=Emocion(nombre,porcentaje,color,total)
                lista.add(emocion)
            }catch(exception: JSONException){
                exception.printStackTrace()
            }
        }
        return lista
    }
    fun guardar(){
        var jsonArray= JSONArray()
        var o:Int=0
        for(i in lista){
            Log.d("objetos",i.toString())
            var j: JSONObject = JSONObject()
            j.put("nombre",i.nombre)
            j.put("porcentaje",i.porcentaje)
            j.put("color",i.color)
            j.put("total",i.total)

            jsonArray.put(o,j)
            o++
        }
        jsonFile?.saveData(this,jsonArray.toString())
        Toast.makeText(this,"Datos guardados", Toast.LENGTH_SHORT).show()

    }
}