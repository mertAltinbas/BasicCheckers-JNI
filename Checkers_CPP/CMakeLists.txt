cmake_minimum_required(VERSION 3.28)
project(Checkers_CPP)

set(CMAKE_CXX_STANDARD 20)

add_library(Checkers_CPP SHARED Main.cpp)

target_include_directories(Checkers_CPP PRIVATE "C:/Program Files/Java/jdk-23/include")
target_include_directories(Checkers_CPP PRIVATE "C:/Program Files/Java/jdk-23/include/win32")