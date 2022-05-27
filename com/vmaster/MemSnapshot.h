//
// Created by VICTOR on 26/05/2022.
//

#ifndef VMASTER_MEMSNAPSHOT_H
#define VMASTER_MEMSNAPSHOT_H


#include "ScreenData.h"
#include "Hero.h"
#include <vector>

using namespace std;

struct MemSnapshot {

    /* How many seconds have elapsed since the game started */
    float gameTime = 0.f;

    ScreenData screenData = ScreenData();

    vector<Hero> heroes;
};


#endif //VMASTER_MEMSNAPSHOT_H
