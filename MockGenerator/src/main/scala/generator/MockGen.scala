package generator

import org.apache.spark.{SparkContext, SparkConf}
import java.io._
import java.lang.StringBuilder
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Map
import generator.CodeGenerator._

object MockGen {

    val alphaNum = (('a' to 'z') ++ ('A' to 'Z') ++ ('0' to '9')).toSet
    val SRC_PATH = "../../SparkExamples/Classification/file1ss_s"
    // val SRC_PATH = "../../SparkExamples/MLlib/SVM/sample_small.txt"

    def countDelimiters (line: String): Map[Char, Int] = {
        val delimiters = line.filter(!alphaNum.contains(_))
        val counts = Map[Char, Int]()
        for(c <- delimiters) {
            if(counts contains c)
                counts(c) = counts(c) + 1
            else counts += (c -> 1)
        }
        println(counts)
        counts
    }

    def findPattern(/*sc: SparkContext, */line: String, notSeen: ArrayBuffer[Char]): Node = {
        var priority = 0
        var found: Node = new Typed("String")
        //----------------------- multi part
        for(delim <- notSeen) {
            val parts = line.split(delim)
            if(parts.length > 2) { 
                var newFound = ""
                var subpart = TypeInferrer.typeInfer(parts(0))
                var child = subpart match {
                    case "String" => findPattern(parts(0), notSeen)
                    case _ => new Typed(subpart)
                }

                for(i <- 1 to parts.length-1) {
                    subpart = TypeInferrer.typeInfer(parts(i))
                    var newChild = subpart match {
                        case "String" => findPattern(parts(i), notSeen)
                        case _ => new Typed(subpart)
                    }    
                    
                    if(newChild.toString != child.toString)
                        child = new Typed("String")    
                }

                if(child.toString != "String"){
                    found = new Multi(delim.toString, child)
                    priority = 6
                }
            }
        } 
      
        //----------------------- binary part
        for(delim <- notSeen){
            if(line.indexOf(delim) != -1) {
                var firstPart = line.substring(0, line.indexOf(delim))
                var secondPart = line.substring(line.indexOf(delim)+1)

                var first = TypeInferrer.typeInfer(firstPart)
                var second = TypeInferrer.typeInfer(secondPart)
                
                var firstChild = first match {
                    case "String" => findPattern(firstPart, notSeen)
                    case _ => new Typed(first)
                }

                var secondChild = second match {
                    case "String" => findPattern(secondPart, notSeen)
                    case _ => new Typed(second)
                }
                
                var newPriority = 1
                if(firstChild.isInstanceOf[Multi])
                    newPriority += 2
                else if(firstChild.toString != "String")
                    newPriority += 1

                if(secondChild.isInstanceOf[Multi])
                    newPriority += 2
                else if(secondChild.toString != "String")
                    newPriority += 1

                if(newPriority > priority){
                    found = new Binary(delim.toString, firstChild, secondChild)
                    priority = newPriority
                }
            }
        }
        found
    }

    def main(args: Array[String]): Unit = {
        val lines = ArrayBuffer[String]()
        val source = scala.io.Source.fromFile(SRC_PATH)
        try source.getLines.copyToBuffer(lines) finally source.close()
        val line = lines(0)

        val delimitersCount = countDelimiters(line)
        val min = delimitersCount.valuesIterator.min

        // val conf = new SparkConf()
        // conf.setMaster("local[4]")
        // conf.setAppName("MockGenerator")
        // val sc = new SparkContext(conf)

        val notSeen = ArrayBuffer[Char]()
        delimitersCount.keys.copyToBuffer(notSeen)
        var tree = findPattern(line, notSeen)
        println(tree)

        var result = ""
        result += 
"""package AutoDaikon;
public class Mock {
    """ + tree.genMock("0") + """
}"""
        //println(result)
        val writer = new PrintWriter("Mock.java", "UTF-8")
        writer.println(result)
        writer.close()

    }
}
