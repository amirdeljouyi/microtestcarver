echo "TRACING APPLICATION"
DIR_PATH=$(cd $(dirname "${BASH_SOURCE:-$0}") && pwd)
echo "$DIR_PATH"
cd "$DIR_PATH"
pwd=$(pwd)
echo "$pwd"
TRACE_CLASS_PATH=../java/trace/CallTrace.java
source ./.env
#OUTPUT_PATH="$pwd/trace-output/$5"
OUTPUT_PATH=$5
PACKAGE_NAME=/$2

echo "START TRACING $PACKAGE_NAME ON PORT $1 and methods=/$3"
if [ $4 == "false" ]; then
  echo "Output will be written on the file $OUTPUT_PATH"
  btrace -u -v -d "./dumpClasses" -o "$OUTPUT_PATH" "$1" "$TRACE_CLASS_PATH"  packageName="$PACKAGE_NAME" methodName="/$3"
else
  echo "Output will be shown on Console"
  btrace -u -v -d "./dumpClasses" "$1" "$TRACE_CLASS_PATH"  packageName="$PACKAGE_NAME" methodName="/$3"
fi
