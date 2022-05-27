//
// Created by VICTOR on 26/05/2022.
//

#ifndef VMASTER_HERO_H
#define VMASTER_HERO_H

#include "windows.h"
#include "Vector.h"
class Hero {

public:
    Hero(int address);

    void update(HANDLE hProcess);

    Vector3 position;

    float health;

    bool visible;


private:

    //const SIZE_T   sizeBuff     = ;
    //static const SIZE_T   sizeBuffDeep = 0x1000;


    //static BYTE           buffDeep[sizeBuffDeep];

    int address;


};


#endif //VMASTER_HERO_H
