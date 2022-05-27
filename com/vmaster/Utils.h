#pragma once

#include <string>
#include <stdexcept>
#include "windows.h"

class WinApiException : public std::runtime_error {

public:
    WinApiException(const char* message)
            :std::runtime_error(message){

        errorCode = GetLastError();
        this->message = message;
    }

    std::string GetErrorMessage() {
        std::string msg = std::string(message);

        if (errorCode > 0) {
            char winapiError[255];
            FormatMessageA(
                    FORMAT_MESSAGE_FROM_SYSTEM | FORMAT_MESSAGE_IGNORE_INSERTS,
                    NULL,
                    errorCode,
                    MAKELANGID(LANG_NEUTRAL, SUBLANG_DEFAULT),
                    winapiError,
                    (sizeof(winapiError) / sizeof(wchar_t)),
                    NULL);

            msg.append("(");
            msg.append(winapiError);
            msg.append(")");
        }

        return msg;
    }

private:
    const char*    message = nullptr;
    int            errorCode = 0;
};

/// Utilities used for realing process memory
namespace Mem {

    /// Reads a DWORD at the specified memory location
    DWORD          ReadDWORD(HANDLE hProcess, DWORD addr);

    /// Reads an arbitrary struct at the specified memory location
    void           Read(HANDLE hProcess, DWORD addr, void* structure, int size);

    /// Reads a DWORD at the specified location in a given buffer
    DWORD          ReadDWORDFromBuffer(void* buff, int position);
};

/// WINAPI process utilities
namespace Process {
    BOOL           IsProcessRunning(DWORD pid);
};

/// String related algorithms
namespace Character {
    bool        ContainsOnlyASCII(const char* buff, int maxSize);
    std::string ToLower(std::string str);
    std::string RandomString(const int len);
    std::string Format(const char* c, const char* args...);
}

