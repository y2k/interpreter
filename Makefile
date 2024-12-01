PRELUDE_PATH := vendor/prelude/java/src/prelude.clj
OUT_DIR := .github/bin
SRC_DIRS := main test

.PHONY: test
test: build
	@ clj2js bytecode test/sample.1.lisp $$PWD/vendor/prelude/interpreter/src/prelude.clj > test/sample.1.gen.lisp
	@ clj2js bytecode test/sample.2.lisp $$PWD/vendor/prelude/interpreter/src/prelude.clj > test/sample.2.gen.lisp
	@ clj2js bytecode test/sample.3.lisp $$PWD/vendor/prelude/interpreter/src/prelude.clj > test/sample.3.gen.lisp
	@ clj2js bytecode test/sample.4.lisp $$PWD/vendor/prelude/interpreter/src/prelude.clj > test/sample.4.gen.lisp
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
