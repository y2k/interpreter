PRELUDE_PATH := vendor/prelude/java/src
OUT_DIR := .github/bin
SRC_DIRS := main test

.PHONY: test
test: build
	@ mkdir -p test/samples/out/
	@ clj2js bytecode test/samples/2.lisp $$PWD/vendor/prelude/bytecode/prelude.clj > test/samples/out/2.gen.lisp
	@ clj2js bytecode test/samples/3.lisp $$PWD/vendor/prelude/bytecode/prelude.clj > test/samples/out/3.gen.lisp
	@ clj2js bytecode test/samples/4.lisp $$PWD/vendor/prelude/bytecode/prelude.clj > test/samples/out/4.gen.lisp
	@ clj2js bytecode test/samples/5.lisp $$PWD/vendor/prelude/bytecode/prelude.clj > test/samples/out/5.gen.lisp
	@ clj2js bytecode test/samples/6.lisp $$PWD/vendor/prelude/bytecode/prelude.clj > test/samples/out/6.gen.lisp
	@ clj2js bytecode test/samples/7.lisp $$PWD/vendor/prelude/bytecode/prelude.clj > test/samples/out/7.gen.lisp
	@ clear && java -cp .github/bin/out test.test

.PHONY: build
build:
	@ mkdir -p $(OUT_DIR)/y2k && cp $(PRELUDE_PATH)/RT.java $(OUT_DIR)/y2k/
	@ PRELUDE_JAVA=$(PRELUDE_PATH) .github/build.gen.sh
	@ cd .github/bin && javac -d out **/*.java

.PHONY: gen_build
gen_build:
	@ export OCAMLRUNPARAM=b && clj2js make_build_script \
		-path src \
		-path test \
		-target .github/bin > .github/build.gen.sh
	@ chmod +x .github/build.gen.sh

.PHONY: clean
clean:
	@ rm -rf $(OUT_DIR)

.PHONY: init
init:
	@ rm -rf .git && rm -rf vendor && rm -f .gitmodules && git init .
	@ git submodule add git@github.com:y2k/prelude.git vendor/prelude
	@ git submodule add git@github.com:y2k/packages.git vendor/packages
