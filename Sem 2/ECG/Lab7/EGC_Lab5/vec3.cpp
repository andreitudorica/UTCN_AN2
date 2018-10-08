#include<math.h>
#include<iostream>
#include"vec3.h"

using namespace std;

namespace egc{
	
	vec3& vec3:: operator =(const vec3 &srcVector)
	{
		this->x = srcVector.x;
		this->y = srcVector.y;
		this->z = srcVector.z;
		return *this;
	}

	vec3 vec3:: operator +(const vec3& srcVector) const
	{
		vec3 result;
		result.x = this->x + srcVector.x;
		result.y = this->y + srcVector.y;
		result.z = this->z + srcVector.z;
		return result;
	}

	vec3& vec3:: operator +=(const vec3& srcVector)
	{
		this->x = this->x + srcVector.x;
		this->y = this->y + srcVector.y;
		this->z = this->z + srcVector.z;
		return *this;
	}

	vec3 vec3:: operator *(float scalarValue) const
	{
		vec3 result;
		result.x = this->x * scalarValue;
		result.y = this->y * scalarValue;
		result.z = this->z * scalarValue;
		return result;
	}

	vec3 vec3:: operator -(const vec3& srcVector) const
	{
		vec3 result;
		result.x = this->x - srcVector.x;
		result.y = this->y - srcVector.y;
		result.z = this->z - srcVector.z;
		return result;
	}

	vec3& vec3:: operator -=(const vec3& srcVector)
	{
		this->x = this->x - srcVector.x;
		this->y = this->y - srcVector.y;
		this->z = this->z - srcVector.z;
		return *this;
	}

	vec3& vec3:: operator -()
	{
		this->x = this->x * -1;
		this->y = this->y * -1;
		this->z = this->z * -1;
		return *this;
	}

	float vec3:: length() const
	{
		float length;
		length = sqrt(pow(this->x,2) + pow(this->y,2) + pow(this->z,2));
		return length;
	}

	vec3& vec3:: normalize()
	{
		*this = *this * (1/ (*this).length());
		return *this;
	}

	float dotProduct(const vec3& v1, const vec3& v2)
	{
		float result;
		result = v1.x * v2.x + v1.y * v2.y + v1.z * v2.z;
		return result;
	}

	vec3 crossProduct(const vec3& v1, const vec3& v2)
	{
		vec3 result;
		result.x = v1.y*v2.z - v1.z*v2.y;
		result.y = v1.x*v2.z - v1.z*v2.x;
		result.z = v1.x*v2.y - v1.y*v2.x;
		return result;
	}
}


