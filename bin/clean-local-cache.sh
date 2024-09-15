#!/bin/bash

FOUND_ONE=

function clean_local_cache {
  f=$1
  if [[ -e "$f" ]]; then
    echo "found and removed $(hostname):$f"
    rm -rf $f
    FOUND_ONE=1
  fi
}

clean_local_cache target
clean_local_cache client/node_modules
clean_local_cache server/.m2/repository

clean_local_cache /tmp/cs314-${USER}/target
clean_local_cache /tmp/cs314-${USER}/client/node_modules
clean_local_cache /tmp/cs314-${USER}/server/.m2/repository

if [[ -n "$FOUND_ONE" ]]; then
  echo
  echo "successfully cleaned the local cache, npm and maven packages will be redownloaded the next time the application is launched with bin/run.sh"
else
  echo "nothing to do, local environment already clean"
fi
