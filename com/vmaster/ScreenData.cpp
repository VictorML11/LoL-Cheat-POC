#include "ScreenData.h"
#include "Offsets.h"
#include "Utils.h"

void ScreenData::update(DWORD_PTR moduleBase, HANDLE hProcess) {
    char buff[128];

    Mem::Read(hProcess, moduleBase + Offsets::Renderer, buff, 128);
    memcpy(&width, &buff[Offsets::RendererWidth], sizeof(int));
    memcpy(&height, &buff[Offsets::RendererHeight], sizeof(int));

    Mem::Read(hProcess, moduleBase + Offsets::ViewProjMatrices, buff, 128);
    memcpy(viewMatrix, buff, 16 * sizeof(float));
    memcpy(projMatrix, &buff[16 * sizeof(float)], 16 * sizeof(float));
    multiplyMatrices(viewProjMatrix, viewMatrix, 4, 4, projMatrix, 4, 4);

}

void ScreenData::multiplyMatrices(float *out, float *a, int row1, int col1, float *b, int row2, int col2) {
    for (int i = 0; i < row1; i++) {
        for (int j = 0; j < col2; j++) {
            float sum = 0.f;
            for (int k = 0; k < col1; k++)
                sum = sum + a[i * col1 + k] * b[k * col2 + j];
            out[i * col2 + j] = sum;
        }
    }
}


