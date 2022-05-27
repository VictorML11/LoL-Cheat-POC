cmake_minimum_required(VERSION 3.22)
project(vmaster)

set(CMAKE_CXX_STANDARD 17)

include(${CMAKE_BINARY_DIR}/conanbuildinfo.cmake)
conan_basic_setup(NO_OUTPUT_DIRS)


include(FetchContent)
FetchContent_Declare(json URL https://github.com/nlohmann/json/releases/download/v3.10.5/json.tar.xz)
FetchContent_MakeAvailable(json)

set(LIBMCK_LIBRARY_DIR ${CMAKE_SOURCE_DIR}/libs/modern-cpp-kafka/include)
message(STATUS "modern-cpp-kafka include directory: ${LIBMCK_LIBRARY_DIR}")

add_executable(vmaster com/vmaster/main.cpp com/vmaster/Utils.cpp com/vmaster/Utils.h com/vmaster/LeagueMemoryReader.cpp com/vmaster/LeagueMemoryReader.h com/vmaster/MemSnapshot.h com/vmaster/Offsets.cpp com/vmaster/Offsets.h com/vmaster/ScreenData.cpp com/vmaster/ScreenData.h com/vmaster/Hero.cpp com/vmaster/Hero.h com/vmaster/Vector.h)

#Include for specific target
target_include_directories(vmaster
        PRIVATE
        ${LIBMCK_LIBRARY_DIR}
        )

target_link_libraries(vmaster nlohmann_json::nlohmann_json )

target_link_libraries(vmaster ${CONAN_LIBS}  nlohmann_json::nlohmann_json)

