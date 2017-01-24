#! /bin/sh
echo "Running shell script"
path=$1

if ! [ -n "$path" ]; then
    echo "Invalid Input File!"
    exit
fi

cd MockGenerator/
sbt "run ../$path"

cd ../WorkingDir
cp ../MockGenerator/Mock.java .
#cp ../AutoDaikon/Main.java .
javac -g *.java
# while read LINE; do
#    echo ${LINE} >> input    
# done
java -cp "../../daikon-5.3.0/daikon.jar:." daikon.Chicory --daikon Main ../$path
