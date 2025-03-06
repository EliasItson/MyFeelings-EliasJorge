package elias.jorge.myfeelings.utilities

import android.content.Context
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.RectF
import android.graphics.drawable.Drawable
import elias.jorge.myfeelings.R

class CustomCircleDrawable: Drawable {

    var coordenadas: RectF? = null
    var anguloBarrido: Float = 0.0F
    var anguloInicio: Float = 0.0F
    var grosorMetrica: Int = 0
    var grosorFondo: Int = 0
    var context: Context? = null
    var emociones: ArrayList<Emocion>

    constructor(context: Context, emociones: ArrayList<Emocion>){
        this.context=context
        grosorMetrica=context.resources.getDimensionPixelSize(R.dimen.graphWith) //10dp
        grosorFondo=context.resources.getDimensionPixelSize(R.dimen.graphWith) //15dp
        this.emociones=emociones
    }

    override fun draw(canvas: Canvas) {
        if(emociones.size!=0){
            for(e in emociones){
                val degree: Float=(e.porcentaje*360)/100
                this.anguloBarrido=degree
                val fondo: Paint = Paint()
                fondo.style=Paint.Style.STROKE
                fondo.strokeWidth=(this.grosorFondo).toFloat()
                fondo.isAntiAlias=true
                fondo.strokeCap=Paint.Cap.ROUND
                fondo.color = this.context?.resources?.getColor(R.color.gray)?:R.color.gray
                val ancho: Float=(canvas.width-25).toFloat()
                val alto: Float=(canvas.height-25).toFloat()

                coordenadas=RectF(25.0F, 25.0F, ancho, alto)

                canvas.drawArc(coordenadas!!,0.0f,360.0f,false,fondo)

                this.anguloInicio+=this.anguloBarrido
            }
        }
    }

    override fun setAlpha(alpha: Int) {

    }

    override fun setColorFilter(colorFilter: ColorFilter?) {

    }

    override fun getOpacity(): Int {
        return PixelFormat.OPAQUE
    }
}