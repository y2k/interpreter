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
	@ mkdir -p $(OUT_DIR)/y2k
	@ export OCAMLRUNPARAM=b && clj2js compile gen -target java > $(OUT_DIR)/y2k/RT.java
	@ export OCAMLRUNPARAM=b && clj2js compile -target repl -src build.clj > $(OUT_DIR)/Makefile
	@ $(MAKE) -f $(OUT_DIR)/Makefile
	@ cd .github/bin && javac -d out **/*.java

.PHONY: clean
clean:
	@ rm -rf $(OUT_DIR)
