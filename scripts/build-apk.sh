#!/bin/bash

# Build script for InstantKey
set -e

echo "Building InstantKey APK..."

# Clean previous builds
echo "Cleaning previous builds..."
./gradlew clean

# Build debug APK
echo "Building debug APK..."
./gradlew assembleDebug

# Check if build was successful
if [ -f "app/build/outputs/apk/debug/app-debug.apk" ]; then
    echo "✓ Build successful!"
    echo "APK location: app/build/outputs/apk/debug/app-debug.apk"
else
    echo "✗ Build failed - APK not found"
    exit 1
fi
