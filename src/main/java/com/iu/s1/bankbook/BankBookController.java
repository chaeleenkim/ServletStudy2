package com.iu.s1.bankbook;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class BankBookController {
	private BankBookDAO bankBookDAO;

	public BankBookController() {
		bankBookDAO = new BankBookDAO();
	}

	public void start(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("BankBook Controller 실행");

		String uri = request.getRequestURI();

		int index = uri.lastIndexOf("/");

		String path = uri.substring(index + 1);

		if (path.equals("bankbookList.do")) {
			System.out.println("상품목록");
			ArrayList<BankBookDTO> ar = bankBookDAO.getList();

			// for(초기식;조건식;증감식)
			// for(모은타입명 변수명 :컬렉션참조변수명) - 향상된 for 문
			for (BankBookDTO bankBookDTO : ar) {
				System.out.println(bankBookDTO.getBookName());
			}

			request.setAttribute("list", ar);

			RequestDispatcher view = request.getRequestDispatcher("../WEB-INF/views/bankbook/bankbookList.jsp");
			try {
				view.forward(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/** -------------------------- Insert ------------------------- */
		} else if (path.equals("bankbookInsert.do")) {
			System.out.println("상품등록");

			String method = request.getMethod();
			System.out.println("Method : " + method);
			if (method.equals("POST")) {
				// 파라미터값 출력
				String bookName = request.getParameter("bookName");
				String bookRate = request.getParameter("bookRate");
				String bookSale = request.getParameter("bookSale");

				BankBookDTO bankBookDTO = new BankBookDTO();
				bankBookDTO.setBookName(bookName);
				bankBookDTO.setBookRate(Double.parseDouble(bookRate));
				bankBookDTO.setBookSale(Integer.parseInt(bookSale));

				int result = bankBookDAO.setInsert(bankBookDTO);
				System.out.println(result);
				if (result > 0) {
					System.out.println("Insert 성공");
				}

				
//				ArrayList<BankBookDTO> ar = bankBookDAO.getList();
//				request.setAttribute("list", ar);
				 

				//Redirect 방식
				try {
					response.sendRedirect("./bankbookList.do");
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
			//GET 방식	
			} else {

				RequestDispatcher view = request.getRequestDispatcher("../WEB-INF/views/bankbook/bankbookInsert.jsp");
				try {
					view.forward(request, response);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();

				}
			}

			/** --------------------------- Select ------------------------- */
		} else if (path.equals("bankbookSelect.do")) {
			System.out.println("상품상세조회");
			String num = request.getParameter("bookNumber");
			long num2 = Long.parseLong(num);
			BankBookDTO bankBookDTO = new BankBookDTO();
			bankBookDTO.setBookNumber(num2);

			bankBookDTO = bankBookDAO.getSelect(bankBookDTO);

			System.out.println(bankBookDTO.getBookName());

			request.setAttribute("dto", bankBookDTO);

			RequestDispatcher view = request.getRequestDispatcher("../WEB-INF/views/bankbook/bankbookSelect.jsp");
			try {
				view.forward(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}

		} else {
			System.out.println("그 외 나머지");
		}
	}
}
