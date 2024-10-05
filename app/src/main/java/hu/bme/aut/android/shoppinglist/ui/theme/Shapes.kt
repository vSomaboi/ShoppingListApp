package hu.bme.aut.android.shoppinglist.ui.theme

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class FlagShape: Shape{
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(
            path = drawFlagPath(size)
        )
    }

}

private fun drawFlagPath(size: Size): Path{
    return Path().apply {
        val width = size.width
        val height = size.height

        //Starting point
        moveTo(x = 0f, y = height/5)

        //Left upper curve
        cubicTo(
            x1 = width/6, y1 = height/10,
            x2 = 2*width/6, y2 = height/10,
            x3 = width/2, y3 = height/5
        )
        //Right upper curve
        cubicTo(
            x1 = 4*width/6, y1 = 3*height/10,
            x2 = 5*width/6, y2 = 3*height/10,
            x3 = width, y3 = height/5
        )
        //Right side
        lineTo(x = width, y = 4*height/5)
        //Right lower curve
        cubicTo(
            x1 = 5*width/6, y1 = 9*height/10,
            x2 = 4*width/6, y2 = 9*height/10,
            x3 = width/2, y3 = 4*height/5
        )
        //Left lower curve
        cubicTo(
            x1 = 2*width/6, y1 = 7*height/10,
            x2 = width/6, y2 = 7*height/10,
            x3 = 0f, y3 = 4*height/5
        )
        //Left side
        lineTo(x = 0f, y = height/5)

        close()
    }
}

class ConcaveCurveShape: Shape{
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(
            path = drawConcaveCurvePath(size)
        )
    }
}

private fun drawConcaveCurvePath(size: Size): Path{
    return Path().apply {
        val width = size.width
        val height = size.height

        //Starting point
        moveTo(x = 0f, y = 0f)
        //Top line
        lineTo(x = width, y = 0f)
        //Right side
        lineTo(x = width, y = height)
        //Bottom curve
        cubicTo(
            x1 = 2*width/3, y1 = 3*height/4,
            x2 = width/3, y2 = 3*height/4,
            x3 = 0f, y3 = height
        )
        //Left side
        lineTo(x = 0f, y = 0f)

        close()
    }
}

class TicketShape: Shape{
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(
            path = drawTicketPath(size)
        )
    }

}

private fun drawTicketPath(size: Size): Path{
    return Path().apply {
        val width = size.width
        val height = size.height

        //Starting point
        moveTo(x = 0f, y = height/8)
        //Top left corner curve
        cubicTo(
            x1 = width/48, y1 = 2*height/24,
            x2 = 2*width/48, y2 = height/24,
            x3 = width/16, y3 = 0f
        )
        //Top line
        lineTo(x = 15*width/16, y = 0f)
        //Top right corner curve
        cubicTo(
            x1 = 46*width/48, y1 = height/24,
            x2 = 47*width/48, y2 = 2*height/24,
            x3 = width, y3 = height/8
        )
        //Right line
        lineTo(x = width, y = 7*height/8)
        //Bottom right corner curve
        cubicTo(
            x1 = 47*width/48, y1 = 22*height/24,
            x2 = 46*width/48, y2 = 23*height/24,
            x3 = 15*width/16, y3 = height
        )
        //Bottom line
        lineTo(x = width/16, y = height)
        //Bottom left corner curve
        cubicTo(
            x1 = 2*width/48, y1 = 23*height/24,
            x2 = width/48, y2 = 22*height/24,
            x3 = 0f, y3 = 7*height/8
        )
        //Right line
        lineTo(x = 0f, y = height/8)

        close()
    }
}

