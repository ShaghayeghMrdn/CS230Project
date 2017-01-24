package generator

import org.apache.spark.{SparkContext, SparkConf}
import java.io._
import java.lang.StringBuilder
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Map
import scala.util.control.Breaks._

import generator.DataPattern._
import generator.TypeInference._

object StringPatternInference {

    val alphaNum = (('a' to 'z') ++ ('A' to 'Z') ++ ('0' to '9')).toSet
    
    def countDelimiters (line: String): Map[Char, Int] = {
        val delimiters = line.filter(!alphaNum.contains(_))
        val counts = Map[Char, Int]()
        for(c <- delimiters) {
            if(counts contains c)
                counts(c) = counts(c) + 1
            else counts += (c -> 1)
        }
        counts
    }

    def findPattern(line: String, notSeen: ArrayBuffer[Char]): Pattern = {
        var priority = 0
        var found: Pattern = new StringLiteral()
        //----------------------- multi part
        for(delim <- notSeen) {
            val parts = line.split(delim)
            if(parts.length > 2) { 
                var newFound = ""
                var child: Pattern = typeInfer(parts(0))
                if(child.isInstanceOf[StringLiteral])
                    child = findPattern(parts(0), notSeen)

                var result = true
                var part = ""
                try {
                    for(part <- parts)
                        child.doesHold(part)
                } catch {
                    case e: DidNotMatchPattern => result = false
                }
                      
                if(result) {
                    found = new ListLiteral(delim.toString, child)
                    priority = 6
                }
            }
        } 
      
        //----------------------- binary part
        for(delim <- notSeen){
            if(line.indexOf(delim) != -1) {
                var firstPart = line.substring(0, line.indexOf(delim))
                var secondPart = line.substring(line.indexOf(delim)+1)

                var first: Pattern = typeInfer(firstPart)
                var second: Pattern = typeInfer(secondPart)
                
                if(first.isInstanceOf[StringLiteral])
                    first = findPattern(firstPart, notSeen)
    
                if(second.isInstanceOf[StringLiteral])
                    second = findPattern(secondPart, notSeen)
                
                var newPriority = 1
                if(first.isInstanceOf[ListLiteral])
                    newPriority += 2
                else if(!first.isInstanceOf[StringLiteral])
                    newPriority += 1

                if(second.isInstanceOf[ListLiteral])
                    newPriority += 2
                else if(!second.isInstanceOf[StringLiteral])
                    newPriority += 1

                if(newPriority > priority){
                    found = new TupleLiteral(delim.toString, first, second)
                    priority = newPriority
                }
            }
        }
        found
    }

    def generateMock(SRC_PATH: String) : Pattern = {
        val lines = ArrayBuffer[String]()
        val source = scala.io.Source.fromFile(SRC_PATH)
        try source.getLines.copyToBuffer(lines) finally source.close()
        val line = lines(0)

        val delimitersCount = countDelimiters(line)

        val notSeen = ArrayBuffer[Char]()
        delimitersCount.keys.copyToBuffer(notSeen)
        var tree = findPattern(line, notSeen)
        println(tree)

        var result = ""
        result += 
"""//package AutoDaikon;
public class Mock {
    """ + tree.genMock("0") + """
}"""
        val writer = new PrintWriter("Mock.java", "UTF-8")
        writer.println(result)
        writer.close()
        tree
    }
}
