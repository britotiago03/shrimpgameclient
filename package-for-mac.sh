#!/bin/bash

if [[ "$(uname)" == "Darwin" ]]; then
  echo "Running on macOS, using hdutil"
else
  echo "You must run this on a Mac, otherwise the wrong version of Java will be included and it won't work!"
  exit
fi

# This script is based on a tutorial:
# https://cuneyt.aliustaoglu.biz/en/creating-standalone-macos-app-dmg-from-javafx/

echo "Generate all Java and JavaFX files, those will be in the folder target/app"
mvn clean install
mvn javafx:jlink

echo "Copy the necessary files into target"
cp src/main/resources/packaging/macos/run.command target
cp src/main/resources/packaging/macos/Info.plist target
cp src/main/resources/packaging/shrimp_logo.icns target

# Work inside the target directory from now on
cd target

# Define constants
RUN_COMMAND=run.command
APPNAME=ShrimpGame
CONTENTS_DIR="$APPNAME.app/Contents"
MAIN_APP_DIR="$CONTENTS_DIR/MacOS"
ICON_DIR="$CONTENTS_DIR/Resources"

if [ -a "$APPNAME.app" ]; then
	echo "$PWD/$APPNAME.app already exists :("
	exit 1
fi

echo "Create necessary directories"
mkdir -p "$MAIN_APP_DIR"
mkdir -p "$ICON_DIR"

echo "Copy the launcher file"
cp "${RUN_COMMAND}" "$MAIN_APP_DIR/$APPNAME"
chmod +x "$MAIN_APP_DIR/$APPNAME"

echo "Copy all the java files inside the app folder"
cp -r app/* $MAIN_APP_DIR

echo "Set up the logo"
cp shrimp_logo.icns $ICON_DIR
cp Info.plist $CONTENTS_DIR

echo "Create .dmg file"
DMG_DIR=ShrimpGame
mkdir "$DMG_DIR"
mv "${APPNAME}.app" $DMG_DIR
hdiutil create -volname "$APPNAME" -srcfolder $DMG_DIR -ov -format UDZO ${APPNAME}.dmg

echo "Done creating the MacOS app, see target folder"