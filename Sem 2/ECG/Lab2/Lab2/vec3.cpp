#include<iostream>
#include "vec2.h"
#include "vec3.h"
#include "vec4.h"

namespace egc {
	vec3& vec3::operator =(const vec3& src)
	{
		this->x = src.x;
		this->y = src.y;
		this->z = src.z;
		return *this;
	}
	vec3 vec3::operator +(const vec3& srcVector) const
	{
		vec3 v = vec3(*this);
		v.x += srcVector.x;
		v.y += srcVector.y;
		v.z += srcVector.z;
		return v;
	}
	vec3& vec3::operator +=(const vec3& srcVector)
	{
		this->x += srcVector.x;
		this->y += srcVector.y;
		this->z += srcVector.z;
		return *this;
	}
	vec3 vec3::operator *(float scalarValue) const
	{
		vec3 v = vec3(*this);
		v.x *= scalarValue;
		v.y *= scalarValue;
		v.z *= scalarValue;
		return v;
	}
	vec3 vec3::operator -(const vec3& srcVector) const
	{
		vec3 v = vec3(*this);
		v.x -= srcVector.x;
		v.y -= srcVector.y;
		v.z -= srcVector.z;
		return v;
	}
	vec3& vec3::operator -=(const vec3& srcVector)
	{
		this->x -= srcVector.x;
		this->y -= srcVector.y;
		this->z -= srcVector.z;
		return *this;
	}
	vec3& vec3::operator -()
	{
		this->x = -this->x;
		this->y = -this->y;
		this->z = -this->z;
		return *this;
	}
	float vec3::length() const
	{
		return sqrt(this->x*this->x + this->y*this->y + this->z*this->z);
	}
	vec3& vec3::normalize()
	{
		*this = *this * (1.0 / this->length());
		return *this;
	}
	float dotProduct(const vec3& v1, const vec3& v2)
	{
		return v1.x*v2.x + v1.y*v2.y + v1.z*v2.z;
	}
	vec3 crossProduct(const vec3& v1, const vec3& v2)
	{
		vec3 v = vec3(v1.y*v2.z-v1.z*v2.y,v1.z*v2.x-v1.x*v2.z,v1.x*v2.y-v1.y*v2.x);
		return v;
	}
}
