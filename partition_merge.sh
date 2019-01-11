#! /bin/sh
input_path=$1
echo "Running sequential daikon with merging functionality"
if ! [ -n "$input_path" ]; then
    echo "argument error"
    exit
fi

first=""
cd temp/
split -l 1000 -d ../$input_path
for f in x*
do
    echo "Processing $f file..."
    if [ ! "$first" ]; then
        # echo "first time only"
        cd ../MockGenerator/
        echo "path to first input file ../temp/$f"
        sbt "run ../temp/$f"
        cd ../WorkingDir
        cp ../MockGenerator/Mock.java .
        javac -g *.java
        first="true"
    fi
    cd ../WorkingDir
    # java -cp "../../daikon-5.3.0/daikon.jar:." daikon.Chicory --daikon Main "../temp/$f"
    java -cp "../../daikon-5.5.8/daikon.jar:." daikon.Chicory --daikon Main "../temp/$f"
    mv Main.inv.gz "$f.inv.gz"
done
java -cp "../../daikon-5.3.0/daikon.jar:." daikon.MergeInvariants -o merged.inv.gz x*.inv.gz
java -cp "../../daikon-5.3.0/daikon.jar:." daikon.PrintInvariants merged.inv.gz
echo "Done merging!"