OUT_DIR := .github/bin
SRC_DIRS := main test

.PHONY: test
test: build
	@ clear && java -cp .github/bin/out test.test

.PHONY: build
build:
	@ rm -rf test/samples/out/
	@ mkdir -p $(OUT_DIR)/y2k
	@ export OCAMLRUNPARAM=b && clj2js compile gen -target java > $(OUT_DIR)/y2k/RT.java
	@ export OCAMLRUNPARAM=b && clj2js compile -target repl -src build.clj > $(OUT_DIR)/Makefile
	@ $(MAKE) -f $(OUT_DIR)/Makefile
	@ cd .github/bin && javac -d out **/*.java

.PHONY: clean
clean:
	@ rm -rf $(OUT_DIR)
