//
// Created by ether on 2019/11/22.
//

#include "Test.h"

using namespace std;

std::vector<double> Test::medianSlidingWindow(std::vector<int> &nums, int k) {
    vector<int> tempvec;
    vector<double> result;
    int beginP, endP;
    beginP = 0;
    endP = k - 1;

    while (endP < nums.size()) {
        if (tempvec.size() < k) {
            for (int i = beginP; i < k; i++) {
                tempvec.push_back(nums[i]);
            }
        } else {
            auto it = find(tempvec.begin(), tempvec.end(), nums[beginP - 1]);
            tempvec[&*it - &tempvec[0]] = nums[endP];
        }
        sort(tempvec.begin(), tempvec.end(), less<int>());
        if (k % 2) {
            result.push_back(tempvec[k / 2]);
        } else {
            double res = ((double) tempvec[k / 2] + (double) tempvec[k / 2 - 1]) / 2.0;
            result.push_back(res);
        }

        beginP++;
        endP++;
    }

    return result;
}
