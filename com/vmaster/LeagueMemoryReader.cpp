#include <iostream>
#include "LeagueMemoryReader.h"
#include "Utils.h"
#include "psapi.h"
#include "Offsets.h"
#include <chrono>
using namespace std::chrono;
LeagueMemoryReader::LeagueMemoryReader() {

}


bool LeagueMemoryReader::isLeagueWindowActive() {
    HWND handle = GetForegroundWindow();

    DWORD h;
    GetWindowThreadProcessId(handle, &h);
    return pid == h;
}

bool LeagueMemoryReader::isHookedToProcess() {
    return Process::IsProcessRunning(pid);
}

void LeagueMemoryReader::hookToProcess() {

    // Find the window
    hWindow = FindWindowA("RiotWindowClass", NULL);
    if (hWindow == NULL) {
        throw WinApiException("League window not found");
    }

    // Get the process ID
    GetWindowThreadProcessId(hWindow, &pid);
    if (pid == NULL) {
        throw WinApiException("Couldn't retrieve league process id");
    }

    // Open the process
    hProcess = OpenProcess(PROCESS_ALL_ACCESS, FALSE, pid);
    if (hProcess == NULL) {
        throw WinApiException("Couldn't open league process");
    }

    // Check architecture
    if (0 == IsWow64Process(hProcess, &is64Bit)) {
        throw WinApiException("Failed to identify if process has 32 or 64 bit architecture");
    }

    HMODULE hMods[1024];
    DWORD cbNeeded;
    if (EnumProcessModules(hProcess, hMods, sizeof(hMods), &cbNeeded)) {
        moduleBaseAddr = (DWORD_PTR)hMods[0];
    }
    else {
        throw WinApiException("Couldn't retrieve league base address");
    }


}

void LeagueMemoryReader::readRenderer(MemSnapshot &snapshot) {
    snapshot.screenData.update(moduleBaseAddr, hProcess);
}

void LeagueMemoryReader::readHeroes(MemSnapshot &snapshot) {
    // Clear heroes
    snapshot.heroes.clear();

    using clock = std::chrono::system_clock;
    using ms = std::chrono::duration<double, std::milli>;

    const auto before = clock::now();

    int heroListStructurePtr = Mem::ReadDWORD(hProcess, moduleBaseAddr + Offsets::heroList);
    int size = 0;
    Mem::Read(hProcess, heroListStructurePtr + 8, &size, sizeof(int));

    int heroListPtr = Mem::ReadDWORD(hProcess, heroListStructurePtr + 4);

    for(int i=0; i < size * 4; i+=4){
        int heroPtr = Mem::ReadDWORD(hProcess, heroListPtr + i);
        Hero hero = Hero(heroPtr);
        hero.update(hProcess);
        snapshot.heroes.push_back(hero);


    }
    const ms duration = clock::now() - before;
    //std::cout << "It took " << duration.count() << "ms" << std::endl;



}


void LeagueMemoryReader::makeSnapshot(MemSnapshot &ms) {
    Mem::Read(hProcess, moduleBaseAddr + Offsets::GameTime, &ms.gameTime, sizeof(float));
    if (ms.gameTime > 2) {
        readRenderer(ms);
        readHeroes(ms);
    }

}





