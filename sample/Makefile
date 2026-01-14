OUT_DIR := bin
CODE_DIR := bin/android/app/src/main/java

.PHONE: hot-reload
hot-reload: build
	@ cd bin/remote && adb push --sync *.txt /sdcard/Android/data/im.y2k.fixme/files
	@ adb shell am start -S -n im.y2k.fixme/io.github.y2k.app\\\$$MainActivity

.PHONY: run
run: build_apk
	@ adb install -r $(OUT_DIR)/android/app/build/outputs/apk/debug/app-debug.apk
	@ adb shell "mkdir -p /sdcard/Android/data/im.y2k.fixme/files"
	@ cd bin/remote && adb push --sync *.txt /sdcard/Android/data/im.y2k.fixme/files
	@ adb shell am start -S -n im.y2k.fixme/io.github.y2k.app\\\$$MainActivity

.PHONY: build
build:
	@ mkdir -p $(CODE_DIR)/y2k
	@ ly2k compile -target eval -src build.clj > $(OUT_DIR)/Makefile
	@ ly2k generate -target java_prelude_v2 > $(CODE_DIR)/y2k/prelude_java_v2.java
	@ ly2k generate -target java > $(CODE_DIR)/y2k/RT.java
	@ $(MAKE) -f $(OUT_DIR)/Makefile

.PHONY: build_apk
build_apk: build
	@ docker run --rm \
		-v $(CURDIR)/$(OUT_DIR)/temp/android:/root/.android \
		-v $(CURDIR)/$(OUT_DIR)/temp/gradle:/root/.gradle \
		-v $(CURDIR)/$(OUT_DIR)/android:/target \
		y2khub/cljdroid build

.PHONY: log
log:
	@ adb logcat -c && adb logcat im.y2k.fixme:V AndroidRuntime:E *:S

.PHONY: clean
clean:
	@ rm -rf $(OUT_DIR)/remote
	@ rm -rf $(OUT_DIR)/android/app/src/main/java
