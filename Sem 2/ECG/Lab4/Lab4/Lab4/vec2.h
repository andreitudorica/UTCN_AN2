//
//  vec2.h
//  Lab2
//
//  Copyright © 2016 CGIS. All rights reserved.
//

#pragma once

#include <cmath>
#include <iostream>
#include "vec3.h"
#include "vec4.h"

namespace egc {

class vec2
{
public:
    union // shared memory
    {
        float x;
        float u;//ordonata de textura
    };
    
    union
    {
        float y;
        float v;//ordonata de textura
    };
    
    //=========================================
    //constructors
    //=========================================
    
    //default constructor creates a zero vector
    vec2()
    {
		x = 0.0f;
		y = 0.0f;
    }
    
    //creates a vector (fX, fY)
    vec2(float fX, float fY)
    {
		x = fX;
		y = fY;
    }
    
    //copy constructor
    vec2(const vec2 &srcVector)
    {
		x = srcVector.x;
		y = srcVector.y;
    }
    
    vec2(const egc::vec3 &srcVector) //referinta catre vec3
    {
		x = srcVector.x;
		y = srcVector.y;
    }
    
    vec2(const egc::vec4 &srcVector)
    {
		x = srcVector.x;
		y = srcVector.y;
    }
    
    //=========================================
    //
    //=========================================
    
    bool operator ==(const vec2& srcVector) const
    {
        return (std::abs(x - srcVector.x) < std::numeric_limits<float>::epsilon()) && (std::abs(y - srcVector.y) < std::numeric_limits<float>::epsilon());
    }
    
    friend std::ostream& operator<< (std::ostream &out, const vec2& srcVector);
    
    //=========================================
    //2D vector operations
    //TO BE IMPLEMENTED
    //=========================================

    //daca this e obiectul, *this dereferentiaza pointerul => obiectul nostru
    vec2& operator =(const vec2 &srcVector);
    vec2 operator +(const vec2& srcVector) const; //aduna pe this cu srcVector si il pune intr-o variabila pe care o apeleaza dupaia
    vec2& operator +=(const vec2& srcVector); //aduna pe this cu srcVector si pune rezultatul in this
    vec2 operator *(float scalarValue) const;//TOATE CARE AU CONST, E PENTRU CA NU MODIFICA THIS !!!
    vec2 operator -(const vec2& srcVector) const; //parametru de tip const = nu se poate modifica
    vec2& operator -=(const vec2& srcVector);
    vec2& operator -();
    float length() const;
    vec2& normalize();
};

    inline std::ostream& operator<< (std::ostream &out, const vec2& srcVector)
    {
        out << "(" << srcVector.x << ", " << srcVector.y << ")";
        return out;
    };

    
    float dotProduct(const vec2& v1, const vec2& v2);
}