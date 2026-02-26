# Directories
SRC_DIR=src
BIN_DIR=bin
LIB_DIR=lib

# All Java source files
SOURCES=$(shell find $(SRC_DIR) -name "*.java")

# All JAR files in lib/
CLASSPATH=$(shell find $(LIB_DIR) -name "*.jar" | tr '\n' ':')

# Main class to run
MAIN_CLASS=FeedReaderMain

# Target
all: $(BIN_DIR)/$(MAIN_CLASS).class

$(BIN_DIR)/$(MAIN_CLASS).class: $(SOURCES)
	mkdir -p $(BIN_DIR)
	javac -d $(BIN_DIR) -cp "$(CLASSPATH)" $(SOURCES)

run: all
	java -cp "$(BIN_DIR):$(CLASSPATH)" $(MAIN_CLASS) $(ARGS)

clean:
	rm -rf $(BIN_DIR)