package generator

object TypeInferrer {

    // def isByte(str: String): Boolean = {
    //     try {
    //         val v = str.toByte
    //         true
    //     } catch {
    //         case _ : java.lang.NumberFormatException => false
    //     }
    // }

    // def isShort(str: String): Boolean = {
    //     try {
    //         val v = str.toShort
    //         true
    //     } catch {
    //         case _ : java.lang.NumberFormatException => false
    //     }
    // }

    def isInt(str: String): Boolean = {
        try {
            val v = str.toInt
            true
        } catch {
            case _ : java.lang.NumberFormatException => false
        }
    }

    def isLong(str: String): Boolean = {
        try {
            val v = str.toLong
            true
        } catch {
            case _ : java.lang.NumberFormatException => false
        }
    }

    // def isFloat(str: String): Boolean = {
    //     try {
    //         val v = str.toFloat
    //         true
    //     } catch {
    //         case _ : java.lang.NumberFormatException => false
    //     }
    // }

    def isDouble(str: String): Boolean = {
        try {
            val v = str.toDouble
            true
        } catch {
            case _ : java.lang.NumberFormatException => false
        }
    }

    def isBoolean(str: String): Boolean = {
        try {
            val v = str.toBoolean
            true
        } catch {
            case _ : java.lang.NumberFormatException => false
            case _ : java.lang.IllegalArgumentException => false
        }
    }

    def typeInfer(str: String): String = {
        if(isInt(str))
            "Int"
        else if(isLong(str))
            "Long"
        else if(isDouble(str))
            "Double"
        else if(str.length < 6 && isBoolean(str))
            "Boolean"
        else "String"
    }

}