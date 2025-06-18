#!/usr/bin/env bash
set -e
pwd
ls -l

# Start Appium in background
nohup appium --log appium.log &
# Wait for Appium to be ready (max 30s)
for i in {1..30}; do
  if nc -z localhost 4723; then
    echo "Appium started"
    break
  fi
  sleep 1
done

adb wait-for-device

# Wait for emulator to fully boot (up to 3 minutes)
timeout=180
elapsed=0
while [ "$(adb shell getprop sys.boot_completed | tr -d '\r')" != "1" ] && [ $elapsed -lt $timeout ]; do
  echo "Waiting for emulator to boot... ($elapsed/$timeout)"
  sleep 5
  elapsed=$((elapsed+5))
done

adb shell settings put global window_animation_scale 0.0
adb shell settings put global transition_animation_scale 0.0
adb shell settings put global animator_duration_scale 0.0
adb shell settings put secure spell_checker_enabled 0



cd mobile-tests && mvn clean test
TEST_EXIT_CODE=$?
echo "Tests completed with exit code $TEST_EXIT_CODE"

if [ -d allure-results ]; then
  allure generate allure-results --clean -o allure-report || echo "Allure generation failed"
else
  echo "No allure-results directory found"
fi

echo "Killing emulator and Appium server..."
adb -s emulator-5554 emu kill || echo "Emulator not running"
pkill -f appium || echo "Appium not running"
exit $TEST_EXIT_CODE