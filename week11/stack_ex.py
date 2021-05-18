# 실습문제 9번 클래스

class Stack:
    s_list = []
    def __init__(self):
        self.s_list = []
    def push(self, a):
        self.s_list.append(a)
    def pop(self):
        return self.s_list.pop()
    def top(self):
        return self.s_list[-1]
    def length(self):
        return len(self.s_list)
