PROJ=$1
TASK=$2
DIR=/opt/$PROJ/$TASK
TASKDIR=/tmp/env/$PROJ
sudo rm -rf $DIR
sudo mkdir -p $DIR
[ -d $TASKDIR ] || mkdir -p $TASKDIR

sleep 1

cat <<EOF > $TASKDIR/$TASK.inc
# setup global env variables for $TASK of $PROJ
PROJ=$1
TASK=$2
EOF

