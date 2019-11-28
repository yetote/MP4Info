//
// Created by ether on 2019/11/22.
//

#ifndef MP4INFO_TEST_H
#define MP4INFO_TEST_H

#include <vector>

class Test {
public:
    int max = 0;
    int min = 0;
    int temp = 0;

    std::vector<double> medianSlidingWindow(std::vector<int> &nums, int k);
};


#endif //MP4INFO_TEST_H
