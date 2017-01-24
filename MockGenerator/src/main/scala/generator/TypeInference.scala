package generator

object TypeInference {

    class NotAnIntegerNumber(message: String = "", cause: Throwable = null) extends RuntimeException(message, cause) {}
    class NotARationalNumber(message: String = "", cause: Throwable = null) extends RuntimeException(message, cause) {}

    object IntegerType extends Enumeration {
        type IntegerType = Value
        val _Byte = Value("Byte")
        val _Short = Value("Short")
        val _Int = Value("Int")
        val _Long = Value("Long")
    }

    object IntegerFormat extends Enumeration {
        type IntegerFormat = Value
        val Decimal = Value("D")
        val Hexadecimal = Value("H")
    }

    object RationalType extends Enumeration {
        type RationalType = Value
        val _Float = Value("Float")
        val _Double = Value("Double")
    }

    object RationalFormat extends Enumeration {
        type RationalFormat = Value
        val Scientific = Value("Sc")
        val DecimalPoint = Value("Dp")
    }

    //-------------------------------------- helper functions --------------------------------------
    def convertHex2Dec(str: String): String = {
        var l: Long = convertHexChar(str(2));
        for (i <- str.substring(3))
            l = l * 16 + convertHexChar(i)
        l.toString
    }

    def convertHexChar(ch: Char): Int = {
        if (ch >= 'a' && ch <= 'f')
            return (ch - 'a') + 10
        else if (ch >= '0' && ch <= '9')
            return ch - '0'
        else throw new NotAnIntegerNumber()
    }

    def isByte(str: String): Boolean = {
        try {
            val v = str.toByte
            true
        } catch {
            case _: java.lang.NumberFormatException => false
        }
    }

    def isShort(str: String): Boolean = {
        try {
            val v = str.toShort
            true
        } catch {
            case _: java.lang.NumberFormatException => false
        }
    }

    def isInt(str: String): Boolean = {
        try {
            val v = str.toInt
            true
        } catch {
            case _: java.lang.NumberFormatException => false
        }
    }

    def isLong(str: String): Boolean = {
        try {
            val v = str.toLong
            true
        } catch {
            case _: java.lang.NumberFormatException => false
        }
    }

    def isFloat(str: String): Boolean = {
        try {
            val v = str.toFloat
            true
        } catch {
            case _: java.lang.NumberFormatException => false
        }
    }

    def isDouble(str: String): Boolean = {
        try {
            val v = str.toDouble
            true
        } catch {
            case _: java.lang.NumberFormatException => false
        }
    }

    def isBoolean(str: String): Boolean = {
        try {
            val v = str.toBoolean
            true
        } catch {
            case _: java.lang.NumberFormatException => false
            case _: java.lang.IllegalArgumentException => false
        }
    }

}