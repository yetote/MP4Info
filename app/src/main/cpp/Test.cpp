//
// Created by ether on 2019/11/22.
//

#include "Test.h"

int Test::guessNumber(int n) {
    if(n==1){ return n;}
    if(max==0){
        if(guess(n)==0){ return n;}
        max=n;
        n/=2;
    }
    if(guess(n)>0){
        //正确的数字比预计的大
        min=n;
    }else if(guess(n)<0){
        //正确的数字比预计的小
        max=n;
    }else{
        return n;
    }



    return guessNumber((int)(max/2+min/2));
}
