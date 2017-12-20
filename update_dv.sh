#!/bin/sh

VERSION="0.0.1"

# Create and move into working directory
echo "Creating libs directory..."
mkdir libs
cd libs

# Pull in latest version
echo "Pulling in latest version of DemonVision..."
wget https://github.com/KHS-Robotics/DemonVision/archive/v$VERSION.zip

# Unzip retrieved artifact and remove zip
echo "Unzipping DemonVision artifact..."
unzip v$VERSION.zip
rm -rf v$VERSION.zip

# Extract DemonVision.jar
echo "Extracting demon_vision.jar..."
mv ./DemonVision-$VERSION/dist/demon_vision.jar ./
rm -rf DemonVision-$VERSION

# Back out one level
cd ..
echo "Done."
