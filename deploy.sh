#!/bin/bash

# Build the project
lein clean
lein uberjar

# Get the name of the standalone JAR file from project.clj
JAR_NAME="pelada-bin"

# Create a directory for the deployment
DEPLOY_DIR="/usr/local/bin/pelada"
mkdir -p "$DEPLOY_DIR"

# Copy the binary to the deployment directory
cp "target/$JAR_NAME.jar" "$DEPLOY_DIR"
sudo chmod +x "$DEPLOY_DIR/$JAR_NAME.jar"

PROFILE_FILE="$HOME/.zshrc"

if ! echo "$PATH" | /usr/bin/grep -q "$DEPLOY_DIR"; then
    echo "# Pelada Project" >> "$PROFILE_FILE"
    echo "export PATH=\"$DEPLOY_DIR:\$PATH\"" >> "$PROFILE_FILE"
    echo "alias pelada='java -jar $DEPLOY_DIR/$JAR_NAME.jar'" >> "$PROFILE_FILE"
    echo "Added $DEPLOY_DIR to PATH in $PROFILE_FILE"
else
  echo "$DEPLOY_DIR is already in PATH"
fi

# Source the profile file to apply the changes to the current shell
if [[ -f "$PROFILE_FILE" ]]; then
    if [ "$PROFILE_FILE" == "$HOME/.zshrc" ]; then
        echo "Sourcing $PROFILE_FILE"
        zsh -c "source $PROFILE_FILE"
    else
        source "$PROFILE_FILE"
    fi
else
  echo "$PROFILE_FILE does not exist"
fi
