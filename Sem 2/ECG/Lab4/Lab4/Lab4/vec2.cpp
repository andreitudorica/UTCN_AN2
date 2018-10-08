#pragma once

#include <cmath>
#include <iostream>

#include "vec2.h"

using namespace std;

namespace egc {
    vec2& vec2::operator =(const vec2& srcVec) {
        this->x = srcVec.x;
        this->y = srcVec.y;
        return *this;
    }

    vec2 vec2::operator +(const vec2& srcVec) const{
        vec2 v = vec2(*this);
        v.x =  v.x + srcVec.x;
        v.y = v.y + srcVec.y;
        return v;
    }

    vec2& vec2::operator +=(const vec2& srcVec) {
        this->x += srcVec.x;
        this->y += srcVec.y;
        return *this;
    }

    vec2 vec2::operator *(float scalarValue) const {
        vec2 v = vec2(*this);
        v.x = v.x * scalarValue;
        v.y = v.y * scalarValue;
        return v;
    }

    vec2 vec2::operator -(const vec2& srcVec) const {
        vec2 v = vec2(*this);
        v.x -= srcVec.x;
        v.y -= srcVec.y;
        return v;
    }

    vec2& vec2::operator -=(const vec2& srcVector) {
        this->x -= srcVector.x;
        this->y -= srcVector.y;
        return *this;
    }

    vec2& vec2::operator -() {
        this->x = - this->x;
        this->y = - this->y;
        return *this;
    }

    float vec2::length() const {
        return sqrt(this->x * this->x + this->y * this->y);
    }

    vec2& vec2::normalize() {
       return *this = *this * (1.0 / (*this).length());
    }
}

