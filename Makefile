PRELUDE_PATH := vendor/prelude/java/src/prelude.clj
OUT_DIR := .github/bin
SRC_DIRS := main test

.PHONY: test
test: build
	@ clear && java -cp .github/bin/out test.Test

.PHONY: build
build:
	@ .github/build.generated.sh
	@ cd .github/bin && javac -d out **/*.java

.PHONY: clean
clean:
	@ rm -rf $(OUT_DIR)

.PHONY: init
init:
	@ rm -rf .git && rm -rf vendor && rm -f .gitmodules && git init .
	@ git submodule add git@github.com:y2k/prelude.git vendor/prelude
	@ git submodule add git@github.com:y2k/packages.git vendor/packages
