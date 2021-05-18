# 실습문제 8번 클래스

# -*- coding: cp949 -*-
class Calc:
    def sum(self, a, b):
        result = a + b
        print('{0:d} + {1:d} = {2:d} 입니다.'.format(a, b, result))
    def sub(self, a, b):
        result = a - b
        print('{0:d} - {1:d} = {2:d} 입니다.'.format(a, b, result))
    def multi(self, a, b):
        result = a * b
        print('{0:d} * {1:d} = {2:d} 입니다.'.format(a, b, result))
    def divi(self, a, b):
        result = a // b
        print('{0:d} / {1:d} = {2:d} 입니다.'.format(a, b, result))
