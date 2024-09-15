#!/bin/bash

umask 077;

# add preconfigured module on CS-dept systems if present
source /etc/profile.d/modules.sh
module purge
module load courses/cs314
if [[ $? != 0 ]]; then
  echo "The '$0' script appears to have been invoked on a non-CS-dept machine."
  echo "You may be using an unsupported configuration, which is OK, but you may"
  echo "need to refer to the detail in LOCAL-SETUP.md and the guide wiki."
  echo
fi

export SERVER_PORT=$((0+${CS314_SERVER_BASE:-41300}+${CS314_TEAM:-0}))
export CLIENT_PORT=$((0+${CS314_CLIENT_BASE:-43100}+${CS314_TEAM:-0}))
echo Using client/server ports: $CLIENT_PORT / $SERVER_PORT

if [[ -z "$REVISION" ]]; then
  REVISION=local
fi

check_error() {
  if [ "$1" -ne 0 ]; then
    echo "Failed at step: ${FUNCNAME[1]}"
    exit "$1"
  fi
}

realpath() {
    [[ $1 = /* ]] && echo "$1" || echo "$PWD/${1#./}";
}

function usage {
  echo "usage: $0 [OPTIONS]";
  echo "Options:"
  echo "    -h, --help     :  Display this menu";
  echo "    -e, --env      :  Specify environment, possible choices: [dev,prod], (default, dev)";
  echo ""
}

function get_repo_root_dir {
  dir="$(realpath $1)";
  while [[ ! -d "$dir/.git" ]];
  do
    dir=`echo $dir | sed 's~\(.*\)/.*~\1~'`;
  done;

  export REPO_ROOT=$dir;
}

function get_nfs_mounted {
  OS=$(uname);
  if [[ "$OS" == "Darwin" ]]; then
    return;
  fi

  file_system="$(stat -f -L -c %T ${REPO_ROOT})";
  if [[ "$file_system" == "nfs" ]]; then
    export NFS_MOUNTED=1;
  fi
}

function get_default_prefix {
  if [[ -n "$NFS_MOUNTED" ]]; then
    export BUILD_DIRECTORY_PREFIX="/tmp/cs314-$(whoami)"
  else
    export BUILD_DIRECTORY_PREFIX="${REPO_ROOT}"
  fi
}

get_repo_root_dir $0;
get_nfs_mounted;
get_default_prefix;

function install_client_dependencies {
  if [[ -n "${NFS_MOUNTED}" ]]; then
    pushd ${REPO_ROOT}/client > /dev/null
    function _install_client_dependences {
      if [[ ! -e node_modules ]]; then
        mkdir -p ${BUILD_DIRECTORY_PREFIX}/client
        cp package.json ${BUILD_DIRECTORY_PREFIX}/client/
        npm install --prefix ${BUILD_DIRECTORY_PREFIX}/client
        ln -sf ${BUILD_DIRECTORY_PREFIX}/client/node_modules node_modules
      fi
    }
    _install_client_dependences
    check_error $?
    popd > /dev/null
  else
    if [[ ! -d "${REPO_ROOT}/client/node_modules" ]]; then
      npm install --prefix ${REPO_ROOT}/client
      check_error $?
    fi
  fi
}

function install_server_dependencies {
  if [[ -n "${NFS_MOUNTED}" ]]; then
    pushd ${REPO_ROOT}/server > /dev/null
    function _install_server_dependences {
      if [[ ! -e .m2/repository ]]; then
        mkdir -p ${BUILD_DIRECTORY_PREFIX}/server/.m2/repository
        mvn --global-settings .m2/settings.xml \
          -Dmaven.repo.local=${BUILD_DIRECTORY_PREFIX}/server/.m2/repository \
          -Drevision=${REVISION} \
          -Dbuild.directory.prefix=${BUILD_DIRECTORY_PREFIX} \
          install 2>&1 | grep '^Download'
        ln -sf ${BUILD_DIRECTORY_PREFIX}/server/.m2/repository .m2/repository
      fi
    }
    _install_server_dependences
    check_error $?
    popd > /dev/null
  else
    if [[ ! -d "${REPO_ROOT}/server/.m2/repository" ]]; then
      mvn -f ${REPO_ROOT}/server \
        --global-settings ${REPO_ROOT}/server/.m2/settings.xml \
        -Drevision=${REVISION} \
        -Dbuild.directory.prefix=${BUILD_DIRECTORY_PREFIX} \
        install 2>&1 | grep '^Download'
      check_error $?
    fi
  fi
}

function bundle_client {
  npm run prodClient --prefix ${REPO_ROOT}/client
  check_error $?
}

function build_server {
  mvn -f ${REPO_ROOT}/server --global-settings ${REPO_ROOT}/server/.m2/settings.xml -Drevision=${REVISION} -Dbuild.directory.prefix=${BUILD_DIRECTORY_PREFIX} package $@
  check_error $?
}

function run_client_tests {
  npm run test --prefix ${REPO_ROOT}/client
  check_error $?
}

function run_server_and_hotloader {
  npm run dev --prefix ${REPO_ROOT}/client
  check_error $?
}

function run_server {
  npm run server --prefix ${REPO_ROOT}/client
  check_error $?
}

function postman_tests {
  #Count Postman Collections
  count=`ls -1 ${REPO_ROOT}/Postman/*.json 2>/dev/null | wc -l`

  if [[ $count == 0 ]]; then
    echo =============================
    echo Postman Collections not found
    echo =============================
  else
    BASE_URL="http://localhost:"${SERVER_PORT}

    echo Starting server-local.jar on ${BASE_URL}
    echo "Port ${SERVER_PORT} status before starting sever (1 normal): " `nc -z localhost ${SERVER_PORT} ; echo $?`

    java -Dorg.slf4j.simpleLogger.log.com.tco=error -jar ${BUILD_DIRECTORY_PREFIX}/target/server-local.jar ${SERVER_PORT} &
    bg_pid=$!

    #Sleep to give time for the server to run
    sleep 5 
    echo "Port ${SERVER_PORT} status after starting sever (0 normal): " `nc -z localhost ${SERVER_PORT} ; echo $?`

    for filename in ${REPO_ROOT}/Postman/*.json; do
      echo
      echo ===============================================
      echo Running Collection: $filename
      echo ===============================================
      ${REPO_ROOT}/client/node_modules/newman/bin/newman.js run $filename --env-var "BASE_URL=${BASE_URL}"
      
      if [[ $? == 1 ]]; then
        echo
        echo Error occured while running Postman tests from $filename
        kill -9 ${bg_pid}
        exit 1
      fi
    done

    echo Successful
    echo
    kill -9 ${bg_pid}
    echo "Port ${SERVER_PORT} status after kill (1 normal): " `nc -z localhost ${SERVER_PORT} ; echo $?`
    sleep 2
  fi
}

# parse args
PARAMS=""
while (( "$#" )); do
  case "$1" in
    -h|--help)
      usage;
      exit 0;
      ;;
    -e|--env)
      if [ -n "$2" ] && [ ${2:0:1} != "-" ]; then
        if [[ "$2" != "dev" && "$2" != "prod" ]]; then
          echo "\"$2\" is not a valid environment choice";
          usage;
          exit;
        fi
        CS314_ENV=$2
        shift 2
      else
        echo "argument missing for -- $1" >&2
        exit 1
      fi
      ;;
    -*|--*=) # unsupported flags
      echo "unrecognized option -- $(echo $1 | sed 's~^-*~~')" >&2
      usage;
      exit 1
      ;;
    *) # preserve positional arguments
      PARAMS="$PARAMS $1"
      shift
      ;;
  esac
done

eval set -- "$PARAMS";

if [[ -z "$CS314_ENV" ]]; then
  CS314_ENV=dev;
fi;

echo "Building and Starting the Application in $([[ "$CS314_ENV" == "dev" ]] && echo 'DEVELOPMENT' || echo 'PRODUCTION' ) Mode."

# Remove target to avoid huge Maven shade warnings
rm -rf ${BUILD_DIRECTORY_PREFIX}/target

if [[ -n "${NFS_MOUNTED}" ]]; then
  mkdir -p ${BUILD_DIRECTORY_PREFIX}/target
  if [[ ! -L "${REPO_ROOT}/target" && -d "${REPO_ROOT}/target" ]]; then
    echo "Cleaning NFS mounted target"
    rm -rf ${REPO_ROOT}/target
  fi
  ln -sf ${BUILD_DIRECTORY_PREFIX}/target ${REPO_ROOT}/target
  if [[ ! "$CS314_ENV" == "dev" ]]; then
    mkdir -p ${BUILD_DIRECTORY_PREFIX}/client/dist
    if [[ ! -L "${REPO_ROOT}/client/dist" && -d "${REPO_ROOT}/client/dist" ]]; then
      echo "Cleaning NFS mounted client/dist"
      rm -rf ${REPO_ROOT}/client/dist
    fi
    ln -sf ${BUILD_DIRECTORY_PREFIX}/client/dist ${REPO_ROOT}/client/dist
  fi
fi

# install dependencies
install_client_dependencies
install_server_dependencies

# test client
run_client_tests

if [[ "$CS314_ENV" == "dev" ]]; then
  build_server
  postman_tests
  run_server_and_hotloader
else
  bundle_client
  build_server
  postman_tests
  run_server
fi