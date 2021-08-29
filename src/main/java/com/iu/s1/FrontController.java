package com.iu.s1;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.iu.s1.bankbook.BankBookController;
import com.iu.s1.member.MemberController;

/**
 * Servlet implementation class FrontController
 */
@WebServlet("/FrontController")
public class FrontController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private MemberController memberController;
	private BankBookController bankbookController;
	
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FrontController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	System.out.println("Front Controller 실행");
		memberController = new MemberController();
		bankbookController = new BankBookController();
		
		String uri = request.getRequestURI();
		//String url = request.getRequestURL().toString();
		
		String path = "";
		
		int startIndex = request.getContextPath().length();
		int lastIndex = uri.lastIndexOf("/");
		path = uri.substring(startIndex,lastIndex);
		
		System.out.println("path : "+ path);
		
		if(path.equals("/member")) {
			try {
				memberController.start(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else if(path.equals("/bankbook")) {
			bankbookController.start(request,response);
		}else {
			System.out.println("없는 url");
			RequestDispatcher view = request.getRequestDispatcher("/index.jsp");
			view.forward(request, response);
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
