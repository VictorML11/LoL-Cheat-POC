#ifndef VMASTER_LEAGUEMEMORYREADER_H
#define VMASTER_LEAGUEMEMORYREADER_H

#include "windows.h"
#include "MemSnapshot.h"

class LeagueMemoryReader {

public:
    LeagueMemoryReader();

    /// Checks if leagues window is still active
    bool isLeagueWindowActive();

    /// Checks to see if we have a league window stored
    bool isHookedToProcess();

    /// Finds leagues window and stores it
    void hookToProcess();

    /// Creates an object with everything of iterest from the game
    void makeSnapshot(MemSnapshot& ms);

private:

    // Process related
    HANDLE                      hProcess = NULL;
    DWORD                       pid      = 0;
    HWND                        hWindow  = NULL;

    // Memory related
    DWORD_PTR                   moduleBaseAddr    = 0;
    DWORD                       moduleSize        = 0;
    BOOL                        is64Bit           = FALSE;

    void readRenderer(MemSnapshot& snapshot);
    void readHeroes(MemSnapshot& snapshot);
};


#endif //VMASTER_LEAGUEMEMORYREADER_H
