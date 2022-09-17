echo "TRACING APPLICATION"
DIR_PATH=$(cd $(dirname "${BASH_SOURCE:-$0}") && pwd)
cd DIR_PATH
pwd=$(pwd)
echo "$pwd"
echo "$DIR_PATH"
TRACE_CLASS_PATH=../java/trace/CallTrace.java
#OUTPUT_PATH="$pwd/trace-output/$5"
OUTPUT_PATH=$5

echo "START TRACING /$2 ON PORT $1 and methods=/$3"
if [ $4 == "false" ]; then
  echo "Output will be written on the file $OUTPUT_PATH"
  btrace -o "$OUTPUT_PATH" "$1" "$TRACE_CLASS_PATH" packageName="/$2" methodName="/$3"
else
  echo "Output will be shown on Console"
  btrace "$1" "$TRACE_CLASS_PATH" packageName="/$2" methodName="/$3"
fi
