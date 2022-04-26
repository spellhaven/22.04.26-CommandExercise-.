package com.gyojincompany.ex;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface MCommand { // 인터페이스
	
	void execute(HttpServletRequest request, HttpServletResponse response);
	// 인터페이스 안의 추상메소드.
	// <아무리 망국의 홀로 남은 왕자라 하더라도 무조건 오버라이드 당해야 하는 이 몸은 억울해!!!>
	
}
