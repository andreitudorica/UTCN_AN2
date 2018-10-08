#include<iostream>
#include "vec2.h"
#include "vec3.h"
#include "vec4.h"

namespace egc {
	vec2& vec2::operator =(const vec2& src)
	{
		this->x = src.x;
		this->y = src.y;
		return *this;
	}
	vec2 vec2::operator +(const vec2& srcVector) const
	{
		vec2 v= vec2(*this);
		v.x += srcVector.x;
		v.y += srcVector.y;
		return v;
	}
	vec2& vec2::operator +=(const vec2& srcVector)
	{
		this->x += srcVector.x;
		this->y += srcVector.y;
		return *this;
	}
	vec2 vec2::operator *(float scalarValue) const
	{
		vec2 v = vec2(*this);
		v.x *= scalarValue;
		v.y *= scalarValue;
		return v;
	}
	vec2 vec2::operator -(const vec2& srcVector) const
	{
		vec2 v = vec2(*this);
		v.x -= srcVector.x;
		v.y -= srcVector.y;
		return v;
	}
	vec2& vec2::operator -=(const vec2& srcVector)
	{
		this->x -= srcVector.x;
		this->y -= srcVector.y;
		return *this;
	}
	vec2& vec2::operator -()
	{
		this->x = -this->x;
		this->y = -this->y;
		return *this;
	}
	float vec2::length() const
	{
		return sqrt(this->x*this->x+ this->y*this->y);
	}
	vec2& vec2::normalize()
	{
		*this = *this * (1.0 / this->length());
		return *this;
	}
	float dotProduct(const vec2& v1, const vec2& v2)
	{
		return v1.x*v2.x + v1.y*v2.y;
	}

}
