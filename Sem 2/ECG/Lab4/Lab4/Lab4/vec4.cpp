#include<math.h>
#include<iostream>
#include"vec4.h"

using namespace std;

namespace egc{

	 vec4& vec4:: operator =(const vec4 &srcVector)
	 {
		 this->x = srcVector.x;
		 this->y = srcVector.y;
		 this->z = srcVector.z;
		 this->w = srcVector.w;
		 return *this;
	 }

	 vec4 vec4:: operator +(const vec4& srcVector) const
	 {
		 vec4 result;
		 result.x = this->x + srcVector.x;
		 result.y = this->y + srcVector.y;
		 result.z = this->z + srcVector.z;
		 result.w = this->w + srcVector.w;
		 return result;
	 }

	 vec4& vec4:: operator +=(const vec4& srcVector)
	 {
		 this->x = this->x + srcVector.x;
		 this->y = this->y + srcVector.y;
		 this->z = this->z + srcVector.z;
		 this->w = this->w + srcVector.w;
		 return *this;
	 }

	  vec4 vec4:: operator *(float scalarValue) const
	  {
		 vec4 result;
		 result.x = this->x * scalarValue;
		 result.y = this->y * scalarValue;
		 result.z = this->z * scalarValue;
		 result.w = this->w * scalarValue;
		 return result;
	  }

	  vec4 vec4:: operator -(const vec4& srcVector) const
	  {
		 vec4 result;
		 result.x = this->x - srcVector.x;
		 result.y = this->y - srcVector.y;
		 result.z = this->z - srcVector.z;
		 result.w = this->w - srcVector.w;
		 return result;	  
	  }

	  vec4& vec4:: operator -=(const vec4& srcVector)
	  {
		 this->x = this->x - srcVector.x;
		 this->y = this->y - srcVector.y;
		 this->z = this->z - srcVector.z;
		 this->w = this->w - srcVector.w;
		 return *this;
	  }

	  vec4& vec4:: operator -()
	  {
		 this->x = this->x * -1;
		 this->y = this->y * -1;
		 this->z = this->z * -1;
		 this->w = this->w * -1;
		 return *this;
	  }

	  float vec4:: length() const
	  {
		float length;
		length = sqrt(pow(this->x,2) + pow(this->y,2) + pow(this->z,2) + pow(this->w,2));
		return length;
	  }

	  vec4& vec4:: normalize()
	  {
		  *this = *this * (1 / (*this).length());
		  return *this;
	  }

}