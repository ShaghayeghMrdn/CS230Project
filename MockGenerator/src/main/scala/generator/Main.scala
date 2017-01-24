package generator

// import org.apache.spark.{SparkContext, SparkConf}
// import org.apache.spark.rdd._
// import at.loveoneanother.schale._
import java.io._
import java.lang.StringBuilder
import scala.collection.mutable.ArrayBuffer
import scala.collection.mutable.Map
import scala.sys.process._

import generator.StringPatternInference._

object Main {

    def main(args: Array[String]): Unit = {
        // val conf = new SparkConf()
        // conf.setMaster("local[4]")
        // conf.setAppName("Parallel Daikon")
        // val sc = new SparkContext(conf)

        var SRC_PATH = ""
        if(args.length != 1) {
            println("Provide the input file path as an argument!")
            exit(1)
        } else
            SRC_PATH = args(0)

        val tree = generateMock(SRC_PATH)
        // val result = ("/home/amytis/Projects/DaiMock/MockGenerator/runAuto.sh "+args(0)).!!
        // println(result)

        // val text = sc.textFile(SRC_PATH)
        // val records = text.flatMap(line => line.split("\n"))
        // records.saveAsTextFile("all")
        // val scriptPath = "/home/amytis/Projects/DaiMock/MockGenerator/runAuto.sh"
        // val result = records.pipe(scriptPath)
        // val result = records.mapPartitionsWithIndex{
        //     (index, iterator) => {
        //         println("Called in Partition -> "+index)
        //         val subpath = "part-0000"+index
        //         val path = "../WorkingDir/"+subpath
        //         ("mkdir -p "+path).!!
        //         ("cp Mock.java "+path).!!
        //         ("cp ../AutoDaikon/Main.java "+path).!!
        //         // ("cd ../WorkingDir/").!
        //         ("javac -g "+subpath+"/*.java").!!
        //         // ("java -cp \"../../daikon-5.3.0/daikon.jar:.\" daikon.Chicory --dtrace-file="+subpath+"/Main.dtrace.gz "+path+".Main ../MockGenerator/all/"+subpath).!!
        //         // var outputStream = ("cd "+subpath+" && java -cp \"../../../daikon-5.3.0/daikon.jar:.\" daikon.Daikon Main.dtrace.gz").!!

        //         // println(outputStream.split("\n").get(0))
                 
        //         // outputStream.split("\n").iterator
        //         iterator
        //     }
        // }
        // result.collect()
        // println(result.collect().mkString("\n"))
        println("Done!")
    }
}
