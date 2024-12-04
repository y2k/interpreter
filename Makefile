PRELUDE_PATH := vendor/prelude/java/src/prelude.clj
OUT_DIR := .github/bin
SRC_DIRS := main test

.PHONY: test
test: build
	@ mkdir -p test/samples/out/
	@ clj2js bytecode test/samples/2.clj $$PWD/vendor/prelude/bytecode/prelude.clj > test/samples/out/2.gen.lisp
	@ clj2js bytecode test/samples/3.clj $$PWD/vendor/prelude/bytecode/prelude.clj > test/samples/out/3.gen.lisp
	@ clj2js bytecode test/samples/4.clj $$PWD/vendor/prelude/bytecode/prelude.clj > test/samples/out/4.gen.lisp
	@ clj2js bytecode test/samples/5.clj $$PWD/vendor/prelude/bytecode/prelude.clj > test/samples/out/5.gen.lisp
	@ clj2js bytecode test/samples/6.clj $$PWD/vendor/prelude/bytecode/prelude.clj > test/samples/out/6.gen.lisp
	@ clear && java -cp .github/bin/out test.Test

.PHONY: build
build:
	@ mkdir -p $(OUT_DIR)/y2k && cp vendor/prelude/java/src/RT.java $(OUT_DIR)/y2k/
	@ .github/build.gen.sh
	@ cd .github/bin && javac -d out **/*.java

.PHONY: gen_build
gen_build:
	@ clj2js make_build_script $$PWD $$PWD/.github/bin > .github/build.gen.sh

.PHONY: clean
clean:
	@ rm -rf $(OUT_DIR)

.PHONY: init
init:
	@ rm -rf .git && rm -rf vendor && rm -f .gitmodules && git init .
	@ git submodule add git@github.com:y2k/prelude.git vendor/prelude
	@ git submodule add git@github.com:y2k/packages.git vendor/packages
