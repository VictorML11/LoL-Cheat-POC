//
// Created by VICTOR on 26/05/2022.
//

#ifndef VMASTER_SCREENDATA_H
#define VMASTER_SCREENDATA_H
#include "windows.h"

class ScreenData {

public:
    int height;
    int width;

    float viewMatrix[16];
    float projMatrix[16];
    float viewProjMatrix[16];

    void     update(DWORD_PTR moduleBase, HANDLE hProcess);

private:

    void     multiplyMatrices(float *out, float *a, int row1, int col1, float *b, int row2, int col2);

};


#endif //VMASTER_SCREENDATA_H
