#!/bin/bash

export        JAVA_HOME="/usr/local/buildtools/java/jdk8"
export        JDK_16="/usr/local/buildtools/java/jdk8"
export        JDK_17="/usr/local/buildtools/java/jdk8"
export        JDK_18="/usr/local/buildtools/java/jdk8"
export        JDK_9="/usr/local/buildtools/java/jdk9"

./gradlew ideaPlugin -Pteamcity=true -Pbuild.number=1.4.10 -PdeployVersion=1.4.10 -PpluginVersion=1.4.10-release-Studio4.2-2 && (

    cp $HOME/kotlin/dist/artifacts/ideaPlugin/Kotlin -r -t $HOME/smd/prebuilts/studio/intellij-sdk/AI-202/linux/android-studio/plugins/

    cp $HOME/kotlin/dist/artifacts/ideaPlugin/Kotlin -r  $HOME/studio-sdk/prebuilts/tools/common/kotlin-plugin-ij
)

