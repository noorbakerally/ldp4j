#!/bin/bash

function analyzeBranch() {
  if [ "$1" != "porcelain" ];
  then
    echo "Checking SonarQube Server..."
    curl --head http://analysis.ldp4j1.org/sonar/ > /dev/null 2>&1
    if [ "$?" = "0" ];
    then
      echo "Executing SonarQube analysis (${TRAVIS_BRANCH})..."
      mvn sonar:sonar -B -Dsonar.branch=$TRAVIS_BRANCH -Dcoverage.reports.dir=$(pwd)/target/all --settings config/src/main/resources/ci/settings.xml
    else
      echo "Skipped SonarQube analysis (${TRAVIS_BRANCH}): Cannot connect to SonarQube Server (http://analysis.ldp4j1.org/sonar/)"
    fi
  else
    echo "Skipped SonarQube analysis (${TRAVIS_BRANCH}): Porcelain"
  fi
}

function skipBranchAnalysis() {
  echo "Skipped SonarQube analysis (${TRAVIS_BRANCH}): Non Q.A. branch"
}

function skipPullRequestAnalysis() {
  echo "Skipped SonarQube analysis (${TRAVIS_BRANCH}): Pull request"
}

mode=$1

if [ "${TRAVIS_PULL_REQUEST}" = "false" ];
then
  case "${TRAVIS_BRANCH}" in
    master)   analyzeBranch "$mode";;
    develop)  analyzeBranch "$mode";;
    feature*) skipBranchAnalysis ;;
    *)        skipBranchAnalysis ;;
  esac
else
  skipPullRequestAnalysis
fi
