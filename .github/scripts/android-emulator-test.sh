#!/usr/bin/env bash
set -e
pwd
ls -l

nohup appium --log appium.log &
sleep 10
adb wait-for-device

# Wait for emulator to fully boot (up to 3 minutes)
timeout=180
elapsed=0
while [ "$(adb shell getprop sys.boot_completed | tr -d '\r')" != "1" ] && [ $elapsed -lt $timeout ]; do
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

allure generate allure-results --clean -o allure-report || echo "Allure generation failed"

echo "Killing emulator and Appium server..."
adb -s emulator-5554 emu kill || echo "Emulator not running"
pkill -f appium || echo "Appium not running"
exit $TEST_EXIT_CODE