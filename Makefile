OUT_DIR := .github/bin

.PHONE: release
release: test
	@ cp src/interpreter.clj $(LY2K_PACKAGES_DIR)/interpreter/0.5.0

.PHONY: test
test: build
	@ cd data && \
		rm -f *.txt && \
		ly2k -target sexp -src sample.clj -output .
	@ java -cp $(OUT_DIR)/out 'y2k.interpreter_test$$App'

.PHONY: build
build:
	@ mkdir -p $(OUT_DIR)/y2k
	@ ly2k generate -target java_prelude_v2 > $(OUT_DIR)/y2k/prelude_java_v2.java
	@ ly2k generate -target java > $(OUT_DIR)/y2k/RT.java
	@ ly2k compile -target eval -src build.clj > $(OUT_DIR)/Makefile
	@ $(MAKE) -f $(OUT_DIR)/Makefile
	@ cd $(OUT_DIR) && javac -d out **/*.java

.PHONY: clean
clean:
	@ rm -rf test/samples/out/
	@ rm -rf $(OUT_DIR)
