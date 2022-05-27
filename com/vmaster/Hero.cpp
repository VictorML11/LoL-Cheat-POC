//
// Created by VICTOR on 26/05/2022.
//

#include <iostream>
#include "Hero.h"
#include "Utils.h"
#include "Offsets.h"

Hero::Hero(int address) : address(address) {
    this->address = address;
}

void Hero::update(HANDLE hProcess) {
    char buff[4000];

    Mem::Read(hProcess, this->address, buff, 4000);
    memcpy(&position,&buff[Offsets::ObjPos],sizeof(Vector3));
    memcpy(&health,&buff[Offsets::ObjHealth],sizeof(float));
    memcpy(&visible,&buff[Offsets::ObjVisibility],sizeof(bool));



}
