#include<iostream>
#include "vec3.h"
#include "vec4.h"

namespace egc {
	vec4& vec4::operator =(const vec4& src)
	{
		this->x = src.x;
		this->y = src.y;
		this->z = src.z;
		this->w = src.w;
		return *this;
	}
	vec4 vec4::operator +(const vec4& srcVector) const
	{
		vec4 v = vec4(*this);
		v.x += srcVector.x;
		v.y += srcVector.y;
		v.z += srcVector.z;
		v.w += srcVector.w;
		return v;
	}
	vec4& vec4::operator +=(const vec4& srcVector)
	{
		this->x += srcVector.x;
		this->y += srcVector.y;
		this->z += srcVector.z;
		this->w += srcVector.w;
		return *this;
	}
	vec4 vec4::operator *(float scalarValue) const
	{
		vec4 v = vec4(*this);
		v.x *= scalarValue;
		v.y *= scalarValue;
		v.z *= scalarValue;
		v.w *= scalarValue;
		return v;
	}
	vec4 vec4::operator -(const vec4& srcVector) const
	{
		vec4 v = vec4(*this);
		v.x -= srcVector.x;
		v.y -= srcVector.y;
		v.z -= srcVector.z;
		v.w -= srcVector.w;
		return v;
	}
	vec4& vec4::operator -=(const vec4& srcVector)
	{
		this->x -= srcVector.x;
		this->y -= srcVector.y;
		this->z -= srcVector.z;
		this->w -= srcVector.w;
		return *this;
	}
	vec4& vec4::operator -()
	{
		this->x = -this->x;
		this->y = -this->y;
		this->z = -this->z;
		this->w = -this->w;
		return *this;
	}
	float vec4::length() const
	{
		return sqrt(this->x*this->x + this->y*this->y + this->z*this->z + this->w*this->w);
	}
	vec4& vec4::normalize()
	{
		*this = *this * (1.0 / this->length());
		return *this;
	}
	float dotProduct(const vec4& v1, const vec4& v2)
	{
		return v1.x*v2.x + v1.y*v2.y + v1.z*v2.z + v1.w*v2.w;
	}
}
