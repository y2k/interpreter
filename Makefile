OUT_DIR := .github/bin

.PHONY: test
test: build
	java -cp .github/bin/out 'interpreter.test$$App'

.PHONY: build
build:
	@ rm -rf test/samples/out/
	@ mkdir -p $(OUT_DIR)/y2k
	@ ly2k generate -target java > $(OUT_DIR)/y2k/RT.java
	@ ly2k compile -target eval -src build.clj > $(OUT_DIR)/Makefile
	@ $(MAKE) -f $(OUT_DIR)/Makefile
	@ cd .github/bin && javac -d out **/*.java

.PHONY: clean
clean:
	@ rm -rf $(OUT_DIR)
