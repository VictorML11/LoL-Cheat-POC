# Distributed under the OSI-approved BSD 3-Clause License.  See accompanying
# file Copyright.txt or https://cmake.org/licensing for details.

cmake_minimum_required(VERSION 3.22.3)

# We name the project and the target for the ExternalProject_Add() call
# to something that will highlight to the user what we are working on if
# something goes wrong and an error message is produced.

project(modern-cpp-kafka-populate NONE)



include(ExternalProject)
ExternalProject_Add(modern-cpp-kafka-populate
                     "UPDATE_DISCONNECTED" "False" "URL" "https://github.com/morganstanley/modern-cpp-kafka/archive/refs/tags/v2022.04.11.tar.gz"
                    SOURCE_DIR          "C:/Users/VICTOR/CLionProjects/vmaster/cmake-build-debug/_deps/modern-cpp-kafka-src"
                    BINARY_DIR          "C:/Users/VICTOR/CLionProjects/vmaster/cmake-build-debug/_deps/modern-cpp-kafka-build"
                    CONFIGURE_COMMAND   ""
                    BUILD_COMMAND       ""
                    INSTALL_COMMAND     ""
                    TEST_COMMAND        ""
                    USES_TERMINAL_DOWNLOAD  YES
                    USES_TERMINAL_UPDATE    YES
)


