package generator

import generator.TypeInference._

class DidNotMatchPattern(message: String, cause: Throwable = null) extends RuntimeException(message, cause) {}

object DataPattern {

    import IntegerType._
    import IntegerFormat._
    import RationalType._
    import RationalFormat._

    abstract class Pattern {
        def toString: String
        def doesHold(str: String): Boolean
        def genMock(id: String): String
    }

    abstract case class Typed extends Pattern {
        def underlyingType: String
    }

    class IntegerNumber(basicT: IntegerType, format: IntegerFormat) extends Typed {
        val underlyingType = "Long"

        override def toString: String = {
            return basicT.toString + format.toString
        }

        override def doesHold(str: String): Boolean = {
            val unified = if (format == Hexadecimal) convertHex2Dec(str) else str
            try {
                basicT match {
                    case IntegerType._Byte => {unified.toByte; true}
                    case IntegerType._Short => {unified.toShort; true}
                    case IntegerType._Int => {unified.toInt; true}
                    case IntegerType._Long => {unified.toLong; true}
                }
            } catch {
                case _: java.lang.NumberFormatException => throw new DidNotMatchPattern("Error: " + str + " was not a " + this.toString)
            }
        }

        def genMock(id: String): String = {
            return basicT.toString.toLowerCase+" mock_"+id+"""(String record) {
        try{ 
            return """+getFullType(basicT.toString)+".parse"+basicT.toString+"""(record);
        } catch (NumberFormatException e) { return """+getReturnFalse(basicT.toString)+""";}
    }
    """
        }
    }

    class RationalNumber(basicT: RationalType, format: RationalFormat) extends Typed {
        val underlyingType = "Double"
        override def toString: String = {
            return basicT.toString + format.toString
        }

        def doesHold(str: String): Boolean = {
            try {
                basicT match {
                    case RationalType._Float => {str.toFloat; true}
                    case RationalType._Double => {str.toDouble; true}
                }
            } catch {
                case _: java.lang.NumberFormatException => throw new DidNotMatchPattern("Error: " + str + " was not a " + this.toString)
            }
        }

        def genMock(id: String): String = {
            return basicT.toString.toLowerCase+" mock_"+id+"""(String record) {
        try{ 
            return """+getFullType(basicT.toString)+".parse"+basicT.toString+"""(record);
        } catch (NumberFormatException e) { return new """+getFullType(basicT.toString)+"("+getReturnFalse(basicT.toString)+""");}
    }
    """
        }
    }

    class BooleanLiteral extends Typed {
        val underlyingType = "Boolean"
        override def toString: String = {
            return "Boolean"
        }

        def doesHold(str: String): Boolean = {
            if (isBoolean(str))
                str.toBoolean
            else throw new DidNotMatchPattern("Error: " + str + " was not a Boolean")
        }

        def genMock(id: String): String = {
            return "boolean mock_"+id+"""(String record) {
        return Boolean.parseBoolean(record);
    }
    """
        }
    }

    class StringLiteral extends Typed {
        val underlyingType = "String"
        override def toString: String = {
            return "String"
        }

        def doesHold(str: String): Boolean = true

        def genMock(id: String): String = {
            return "int mock_"+id+"""(String record) {return record.length();}
    """
        }
    }

    case class TupleLiteral(delim: String, first: Pattern, second: Pattern) extends Pattern with Serializable {
        override def toString: String = {
            return first.toString + delim + second.toString
        }

        override def doesHold(str: String): Boolean = {
            val subparts = str.split(delim)
            if (subparts.length != 2)
                throw new DidNotMatchPattern("Error: " + str + " was not a paired variable")
            first.doesHold(subparts(0)) && second.doesHold(subparts(1))
        }

        def genMock(id: String): String = {
            var result = ""
            result += first.genMock(id+"1") + second.genMock(id+"2")
            result += "void mock_"+id+"""(String record) {
        String[] parts = record.split(""""+delim+"""");
        if(parts.length == 2) {
            """+genInnerStmt(id+"1", first, 0)+"""
            """+genInnerStmt(id+"2", second, 1)+"""
        } else System.out.println("Expected to be in the format of ***"""+delim+"""*** -> "+record);
    }
    """
        result 
        }
    }

    case class ListLiteral(delim: String, child: Pattern) extends Pattern with Serializable {
        override def toString: String = { return child.toString + "(" + delim + child.toString + ")+" }

        override def doesHold(str: String): Boolean = {
            val subparts = str.split(delim)
            var result = true
            subparts.foreach(sub => result &= child.doesHold(sub))
            result
        }

        def genMock(id: String): String = {
            return child.genMock(id+"1") + "void mock_"+id+"""(String record) {
        String[] parts = record.split(""""+delim+"""");
        for(String part: parts)
            mock_"""+id+"1"+"""(part);
    }
    """
        }
    }

    def checkIntegerNumber(str: String): Typed = {
        var underlyingType = IntegerType._Long
        var underlyingFormat = IntegerFormat.Decimal
        var parsed: String = str
        var flag = false

        if (str.endsWith("l") || str.endsWith("L")) {
            parsed = str.substring(0, str.length - 1)
            flag = true
        }

        if (parsed.startsWith("0x") || parsed.startsWith("0X")) {
            underlyingFormat = IntegerFormat.Hexadecimal
            parsed = convertHex2Dec(parsed)
        }

        if (!flag && isByte(parsed))
            underlyingType = _Byte
        else if (!flag && isShort(parsed))
            underlyingType = _Short
        else if (!flag && isInt(parsed))
            underlyingType = _Int
        else if (isLong(parsed))
            underlyingType = _Long
        else throw new NotAnIntegerNumber()

        return new IntegerNumber(underlyingType, underlyingFormat)
    }

    def checkRationalNumber(str: String): Typed = {
        var underlyingType = RationalType._Double
        var underlyingFormat = RationalFormat.Scientific

        if (isFloat(str))
            underlyingType = _Float
        else if (isDouble(str))
            underlyingType = _Double
        else
            throw new NotARationalNumber()

        if (str.endsWith("D") || str.endsWith("d"))
            underlyingType = RationalType._Double

        if (str.indexOf('E') == -1 && str.indexOf('e') == -1)
            underlyingFormat = RationalFormat.DecimalPoint

        return new RationalNumber(underlyingType, underlyingFormat)
    }

    def typeInfer(str: String): Typed = {
        try {
            return checkIntegerNumber(str)
        } catch {
            case e: NotAnIntegerNumber =>
                try {
                    return checkRationalNumber(str)
                } catch {
                    case e: NotARationalNumber =>
                        if (isBoolean(str))
                            new BooleanLiteral()
                        else
                            return new StringLiteral()
                }
        }
    }

    def getFullType(t: String): String = t match {
        case "Int" => "Integer"
        case _ => t
    }

    def getReturnFalse(t: String): String = t match {
        case "Byte" => "-1"
        case "Short" => "-1"
        case "Int" => "-1"
        case "Long" => "-1"
        case "Float" => "-1.0"
        case "Double" => "-1.0"
        case "Boolean" => "fasle"
        case _ => ""
    }

    def genInnerStmt(id: String, child: Pattern, firstSecond: Int): String = child match {
        case b: BooleanLiteral => "if(mock_"+id+"(parts["+firstSecond.toString+"]) == false) System.out.println(\"Expected to be Boolean -> \"+parts["+firstSecond.toString+"]+\"["+id+"1]\");"
        case r: RationalNumber => "if(mock_"+id+"(parts["+firstSecond.toString+"]) < 0) System.out.println(\"Expected to be Double -> \"+parts["+firstSecond.toString+"]+\"["+id+"1]\");"
        case i: IntegerNumber => "if(mock_"+id+"(parts["+firstSecond.toString+"]) < 0) System.out.println(\"Expected to be Int/Long -> \"+parts["+firstSecond.toString+"]+\"["+id+"1]\");"
        case _ =>  "mock_"+id+"(parts["+firstSecond.toString+"]);"
    }

}