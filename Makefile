OUT_DIR := .github/bin
SRC_DIRS := main test

.PHONY: test
test: build
	@ mkdir -p test/samples/out/
	@ clj2js bytecode test/samples/2.lisp > test/samples/out/2.gen.lisp
	@ clj2js bytecode test/samples/3.lisp > test/samples/out/3.gen.lisp
	@ clj2js bytecode test/samples/4.lisp > test/samples/out/4.gen.lisp
	@ clj2js bytecode test/samples/5.lisp > test/samples/out/5.gen.lisp
	@ clj2js bytecode test/samples/6.lisp > test/samples/out/6.gen.lisp
	@ clj2js bytecode test/samples/7.lisp > test/samples/out/7.gen.lisp
	@ clj2js bytecode test/samples/8.lisp > test/samples/out/8.gen.lisp
	@ clj2js bytecode test/samples/9.lisp > test/samples/out/9.gen.lisp
	@ clear && java -cp .github/bin/out test.test

.PHONY: build
build:
	@ mkdir -p $(OUT_DIR)/y2k && clj2js gen -target java > $(OUT_DIR)/y2k/RT.java
	@ export PRELUDE_JAVA=0 && .github/build.gen.sh
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
