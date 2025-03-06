package elias.jorge.myfeelings.utilities

import android.content.Context
import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.RectF
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat
import elias.jorge.myfeelings.R

class CustomBarDrawable: Drawable {

    var coordenadas: RectF? = null
    var context: Context? = null
    var emocion: Emocion?

    constructor(context: Context, emocion: Emocion){
        this.context=context
        this.emocion=emocion
    }

    override fun draw(canvas: Canvas) {
        val fondo: Paint = Paint()
        fondo.style= Paint.Style.FILL
        fondo.isAntiAlias=true
        fondo.color = this.context?.resources?.getColor(R.color.gray)?:R.color.gray
        val ancho: Float=(canvas.width-10).toFloat()
        val alto: Float=(canvas.height-10).toFloat()

        coordenadas=RectF(0.0F, 0.0F, ancho, alto)

        canvas.drawRect(coordenadas!!,fondo)

        if(emocion!=null){
            val porcentaje: Float=emocion!!.porcentaje*(canvas.width-10)/100
            val coordenadas2:RectF= RectF(0.0f,0.0f,porcentaje,alto)

            val seccion: Paint=Paint()
            seccion.style= Paint.Style.FILL
            seccion.isAntiAlias=true
            seccion.color = ContextCompat.getColor(this.context!!,emocion!!.color)

            canvas.drawRect(coordenadas2!!,seccion)
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