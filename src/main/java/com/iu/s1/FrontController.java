package com.iu.s1;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.StringTokenizer;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionContext;

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
    	ServletConfig sc = getServletConfig();
    	//sc.getHttpServletrequest 불가
    	HttpSession session = request.getSession();
    	//서버 내에서는 application 대신 ServletContext로 부름
    	ServletContext context = session.getServletContext(); 
    	//context.getSession은 불가능
    	context = getServletContext();
    	
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
			memberController.start(request);
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
