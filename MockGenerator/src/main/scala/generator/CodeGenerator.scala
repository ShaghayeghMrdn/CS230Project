package generator

object CodeGenerator {

    abstract class Node {
        def genMock(id: String): String
        def toString: String
        def doesHold(str: String): Boolean
    }

    case class Typed(t: String) extends Node {
        //t = type : Int, Boolean, ....
        override def toString: String = {return t}

        def genMock(id: String): String = {
            if(t == "String")
                return genStringMock(id)
            else
                return t.toLowerCase+" mock_"+id+"""(String record) {
        try{ 
            return """+getFullType(t)+".parse"+t+"""(record);
        } catch (NumberFormatException e) { return """+getReturnFalse(t)+""";}
    }
    """
        }

        def doesHold(str: String) : Boolean = {    
            t match {
                case "Int" => TypeInferrer.isInt(str)
                case "Long" => TypeInferrer.isLong(str)
                case "Double" => TypeInferrer.isDouble(str)
                case "Boolean" => TypeInferrer.isBoolean(str)
                case "String" => true
                case _ =>
                    println("Underscore is catched!") 
                    false
            }
        }
    }

    case class Binary(delim: String, first: Node, second: Node) extends Node {

        override def toString: String = {return first.toString+delim+second.toString}

        def genInnerStmt(id: String, child: Node, firstSecond: Int): String = child match {
            case Typed("Boolean") => "if(mock_"+id+"(parts["+firstSecond.toString+"]) == false) System.out.println(\"Expected to be Boolean -> \"+parts["+firstSecond.toString+"]+\"["+id+"1]\");"
            case Typed(x) => "if(mock_"+id+"(parts["+firstSecond.toString+"]) < 0) System.out.println(\"Expected to be "+x+" -> \"+parts["+firstSecond.toString+"]+\"["+id+"1]\");"
            case _ =>  "mock_"+id+"(parts["+firstSecond.toString+"]);"  
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

        def doesHold(str: String): Boolean = {
            try {
                val subparts = str.split(delim)
                if(subparts.length != 2)
                    return false
                return first.doesHold(subparts(0)) && second.doesHold(subparts(1))
            } catch {
                case e: Exception => 
                    println("Caught Exception!")
                    return false
                case _: Throwable =>
                    println("Underscore is catched!") 
                    return false
            }
        }
    }

    case class Multi(delim: String, child: Node) extends Node {

        override def toString: String = {return child.toString+"("+delim+child.toString+")+"}

        def genMock(id: String): String = {
            return child.genMock(id+"1") + "void mock_"+id+"""(String record) {
        String[] parts = record.split(""""+delim+"""");
        for(String part: parts)
            mock_"""+id+"1"+"""(part);
    }
    """
        }

        def doesHold(str: String): Boolean = {
            try {
                val subparts = str.split(delim)
                var result = true
                subparts.foreach(sub => {result &= child.doesHold(sub)})
                result
            } catch {
                case e: Exception => 
                    println("Caught Exception!")
                    return false
                case _: Throwable =>
                    println("Underscore is catched!") 
                    return false
            }
        }
    }

    def getFullType(t: String): String = t match {
        case "Int" => "Integer"
        case _ => t
    }
    
    def getReturnFalse(t: String): String = t match {
        case "Int" => "-1"
        case "Double" => "-1.0"
        case "Long" => "-1"
        case "Boolean" => "fasle"
        case _ => ""
    }

    def genStringMock(id: String) : String = {
        return "void mock_"+id+"""(String record) {
    }
    """
    }


}