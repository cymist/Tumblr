package com.stitch.Tumblr.controller;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.stitch.Tumblr.command.CommandService;

//▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦ [ Servlet 설정 부분 ]
/**
* Servlet implementation class MessageController
* 
* ========================================================================================================
* 											<< urlPatterns >>
* 
* Web Browser에서의 url 주소 입력 form을 설정하는 것이다.
* http://IP주소:포트번호/프로젝트명/(지정한)urlPatterns 이 기본 브라우저의 주소가 된다.
* http://localhost:8080/MVCWithCommandPattern_RequestParameter/MessageController → 이 프로젝트의 기본 URL 주소이다.
* ========================================================================================================
* 
* ========================================================================================================
* 											<< initParams >>
* 
* @WebInitParam 어노테이션을 사용하여 서블릿의 초기화 매개변수를 설정한다.
* propertyConfig라는 name을 갖는 key의 value 값을 사용하겠다는 선언같은 거라고 생각하면 될 듯 하다.
* ========================================================================================================
*/
@WebServlet(
		urlPatterns = { 
				"/Controller", 
				"*.do"
		}, 
		initParams = { 
				@WebInitParam(name = "propertyConfig", value = "commandMapping.properties")
							// ----- propertyConfig 파라미터를 받으면 command.properties file을 사용 -----
})
//▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦ [ Servlet 설정 부분 ]
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	// ▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦ [ command.properties 파일 저장 부분 ]
	/* ========================================================================================================
	 * 명령어와 명령어 처리 클래스가 매핑되어 있는 commandMapping.properties 파일을 읽어서 Map 객체인 commandMap에 저장한다.
	 * ========================================================================================================
	 */
	// ---------- 명령어와 명령어 처리 클래스를 쌍으로 저장한다 ---------- [ 1 ]
	private Map<String, Object> commandMap = new HashMap<String, Object>();
	// ▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦ [ command.properties 파일 저장 부분 ] 
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Controller() {
        super();
        // TODO Auto-generated constructor stub
    }

    // ▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦ [ Servlet 초기화 설정 ]
  	/**
  	 * @see Servlet#init(ServletConfig)
  	 * 	
  	 * ========================================================================================================
  	 * doGet(), doPost() 메서드보다 먼저 호출되는 메서드로 초기화 할 때 사용하면 된다.	
  	 * ========================================================================================================
  	 * 
  	 * ========================================================================================================
  	 * 		<< ServletConfig config >>
  	 * 
  	 * config에는 @WebServlet()의 initParams{ }의 @WebInitParam(name, value)의 정보가 들어있다.
  	 * getInitParameter() 메서드를 이용하여 정보를 추출할 수 있는데, 이 때 name 값을 파라미터로 사용하면 value 값을 얻을 수 있다.
  	 * ========================================================================================================
  	 * 
  	 * ========================================================================================================
  	 * 		<< Iterator<?> keyIter = pr.keySet().iterator(); >>
  	 * 
  	 * Properties 객체의 키값들을 Set 자료 구조에 담아서 리턴한다.
  	 * Iterator 객체는 Enumeration 객체를 확장시킨 개념의 객체이다.
  	 * ========================================================================================================
  	 * 
  	 */
	public void init(ServletConfig config) throws ServletException {		// ----- config : servlet 설정에 관한 정보가 들어있다 -----
																			// ----- @WebServlet()의 initParams의 데이터를 가지고 있다 -----
		// ---------- initParams에서 propertyConfig의 값을 읽어온다 ---------- [ 1 ]
		String props = config.getInitParameter("propertyConfig");			// ----- value 값(command.properties)이 들어가며, 이 파일을 load 한다 -----
																			// ----- web.xml에서 propertyConfig에 해당하는 init-param의 값을 읽어온다 -----
		System.out.println("[ propertyConfig's value(props) ] : " + props);
		
		// ---------- commandMapping.properties 파일이 저장된 폴더의 경로를 저장 ---------- [ 2 ]
		String realFolder = "/property";
		System.out.println("[ properties 파일의 경로(realFolder) ] : " + realFolder);
		
		// ---------- Web App의 루트 경로를 추출하여 저장 ---------- [ 3 ]
		ServletContext context = config.getServletContext();				// ----- getServletContext() : 루트 파일의 경로를 가져온다 -----
																			// ----- http://localhost:8080/Tumblr
		System.out.println("[ Web App의 루트 경로(context) ] : " + context);
		
		// ---------- realFolder를 Web App 시스템상의 절대 경로로 변경 ---------- [ 4 ]
		String realPath = context.getRealPath(realFolder) + "\\" + props;
		System.out.println("[ Web App 시스템상의 절대 경로(realPath) ] : " + realPath);
		
		// ---------- 명령어와 명령어 처리 클래스의 Mapping 정보를 저장할 Properties 객체 생성 ---------- [ 5 ]
		Properties pr = new Properties();
		System.out.println("[ Properties 객체 초기값(pr) ] : " + pr);
		
		// ---------- commandMapping.properties 파일의 데이터를 불러올 파일스트림 객체 ---------- [ 6 ]
		FileInputStream f = null;
		System.out.println("[ FileInputStream 객체 초기값(f) ] : " + f);
		
		try{
			// ---------- commandMapping.properties 파일의 내용을 읽어온다 ---------- [ 7 ]
			f = new FileInputStream(realPath);		// ----- realPath(파일의 경로와 파일이 설정되어 있다) 변수를 이용하여 파일의 데이터를 추출한다 -----
													// ----- commandMapping.properties 파일의 내용을 읽어오는데,
													// ----- properties 파일은 String 형태를 받아들이지 못하기 때문에,
													// ----- FileInputStream 형태로 바꾸어 저장해야 한다
			System.out.println("[ FileInputStream 객체가 파일에서 데이터를 추출한 후의 값(f) ] : " + f);
			
			// ---------- commandMapping.properties 파일의 내용을 Properties 객체로 재정의하여 pr 변수에 저장 ---------- [ 8 ]
			pr.load(f);								// ----- 불러온 commandMapping 파일의 데이터를 Properties로 로드 -----
			System.out.println("[ Properties 객체가 file의 데이터를 로드한 후의 값(pr.load(f) ] : " + "이건 출력 불가능하구나...............");
			
		} catch(IOException e){
			e.printStackTrace();
		} finally{
			if(f != null) {
				try{
					f.close();
				} catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		/*
 		 * ====================================================================================
 		 * 								<< Iterator >>
 		 * 
 		 * An iterator over a collection. 
 		 * Iterator takes the place of Enumeration in the Java Collections Framework. 
 		 * Iterators differ from enumerations in two ways: 
 		 * 		• Iterators allow the caller to remove elements from the underlying collection 
 		 * 		  during the iteration with well-defined semantics.
 		 * 		• Method names have been improved.
 		 * 
 		 * This interface is a member of the  Java Collections Framework.
 		 * 
 		 * Since:1.2See 
 		 * 
 		 * Also:Collection, ListIterator, Iterable
 		 * ====================================================================================
 		 */
		
		// ---------- Set 객체(public Set<K> keySet())의 iterator() 메서드를 사용해 Iterator 객체를 얻어낸다 ---------- [ 9 ]
		Iterator<?> keyIter = pr.keySet().iterator();				// ----- commandMapping.properties 파일의 key, value를
																	// ----- iterator() 메서드를 이용해 반복해서 추출하고,
																	// ----- 추출한 데이터를 Iterator 컬렉션에 저장
		System.out.println("[ Iterator<?>에 저장된 데이터 값(keyIter) ] : " + keyIter);
		
		// ---------- Iterator 객체에 저장된 명령어와 명령어 처리 클래스를 commandMap에 저장한다 ---------- [ 10 ]
		System.out.println();
		System.out.println("---------- 명령어 및 명령어 처리 클래스 목록 시작 ----------");
		while (keyIter.hasNext()) {									// ----- key가 있으면, 객체를 하나씩 꺼내서 그 객체명으로 Properties 객체에 저장된 객체에 접근한다 -----
			String command = (String) keyIter.next();				// ----- commandMapping.properties 파일의 key=value에 접근하여
																	// ----- key에 해당하는 value 값을 가져온다
			String className = pr.getProperty(command);				// ----- command 변수에는 commandMapping.properites 파일의 키(예 : message)가 들어오고
																	// ----- Properties 객체에 의해서 재정의 되어 className에는 키에 해당하는
																	// ----- 값(예 : com.stitch.Tumblr.xxx)이 들어온다.
			System.out.println("[ 명령어(command) ] : " + command);
			System.out.println("[ 명령어 처리 클래스 이름(className) ] : " + className);
			
			try{
				/*
 				 * ============================================================================================
 				 *										 << Class >>
 				 * 
 				 * Instances of the class Class represent classes and interfaces in a running Java application. 
 				 * Every array also belongs to a class that is reflected as a Class object that is shared 
 				 * by all arrays with the same element type and number of dimensions.
 				 * 
 				 * Class의 class 인스턴스는 자바 프로그램에서 사용되는 클래스들과 인터페이스들을 나타낸다.
 				 * Class.class가 가지고 있는 api를 살펴보면, 일단 static으로 되어 있는 forName() 메서드가 있다.
 				 * jdbc 예제에서 많이 나오는 메서드이며, Class.forName("java.lang.String"); 으로 실행하면
 				 * 해당 이름을 갖는 클래스(String)의 정보를 담은 Class instance를 리턴해준다.
 				 * 
 				 * forName() 이라는 메서드는 해당 스트링(String)으로 class의 객체를 리턴합니다.
 				 * String s = "a문자열";				 → 문자열 s
 				 * Class<?> a = Class.forName(s);	 → 문자열 s를 Class 객체의 forName() 메서드를 사용해서 클래스(객체)화 시킨다.
 				 * Object b = a.newInstance()		 → 해당하는 클래스(a) 객체로 b라는 Instance를 생성한다.
 				 * 									       이때 생성되는 Instance는 Object 타입으로 리턴된다. (전략 패턴인가보다...)
 				 * ============================================================================================
 				 */
				Class<?> commandClass = Class.forName(className);		// ----- className(String)의 정보를 담은 Class Instance를 생성 -----
																		// ----- 즉, 해당 문자열을 클래스로 만들어 리턴해준다 -----
				System.out.println("[ className의 정보를 담은 Class Instance(commandClass) ] : " + commandClass);
				
				Object commandInstance = commandClass.newInstance();	// ----- commandClass 객체로 commandInstance라는 인스턴스를 생성
																		// ----- 즉, 해당 클래스의 객체를 생성하라는 코드이다
																		// ----- 이때 생성되는 인스턴스는 Object type으로 리턴된다
				System.out.println("[ commandClass 객체로 만든 Object type Instance(commandInstance) ] " + commandInstance);
				
				commandMap.put(command, commandInstance);				// ----- Map 개체인 commandMap에 key에 해당하는 value 값을 저장 -----
				
			} catch(ClassNotFoundException e) {
				throw new ServletException(e);
			} catch(InstantiationException e) {
				throw new ServletException(e);
			} catch(IllegalAccessException e) {
				throw new ServletException(e);
			}
		}
		System.out.println("---------- 명령어 및 명령어 처리 클래스 목록 끝 ----------");
		System.out.println();
	}
    // ▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦ [ Servlet 초기화 설정 ]

 	// ▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦ [ Get방식 서비스 메서드 ]
 	/**
 	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
 	 * 
 	 * << Get 방식의 서비스 메서드 >>
 	 * 
 	 * 웹 브라우저(클라이언트)가 Get방식 호출을 할 때 실행되는 doGet() 메서드 - 보통 return 값이 필요없는 경우에 사용되는 듯.
 	 */
 	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 		this.requestPro(request, response);		// ----- 요청 처리 메서드 호출 -----
 	}
 	// ▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦ [ Get방식 서비스 메서드 ]

 	
 	// ▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦ [ Post방식 서비스 메서드 ]
 	/**
 	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
 	 * 
 	 * << Post 방식의 서비스 메서드 >>
 	 * 
 	 * 웹 브라우저(클라이언트)가 Post방식 호출을 할 때 실행되는 doPost() 메서드 - 보통 return 값이 필요한 경우에 사용되는 듯.
 	 */
 	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 		this.requestPro(request, response);		// ----- 요청 처리 메서드 호출 -----
 	}
 	// ▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦ [ Post방식 서비스 메서드 ]

 	
 	// ▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦ [ 사용자의 요청을 분석해서 처리하는 메서드 ]	
 	// ---------- Get 방식, Post 방식에 상관없이 Web Browser(Client)의 요청을 처리하는 메서드 ---------- [ 1 ]
 	private void requestPro(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
 		
 		// ---------- 변수 선언 ---------- [ 2 ]
 		String view = null;
 		CommandService com = null;		// ----- Interface : 명령어 처리 클래스들의 인터페이스 -----
 		System.out.println("[ CommandService Interface의 Instance(com) ] : " + com);
 		
 		try{
 			// ---------- command uri 데이터 값(전체 경로)을 추출한다 ---------- [ 3 ]
 			String command = request.getRequestURI();	// ----- 전체 경로 추출 -----
 			System.out.println("[ command uri의 값 - 전체 경로 추출(command) - 요청 명령 ] : " + command);
 			
 			// ---------- 프로젝트 경로만 추출 ---------- [ 4 ]
 			String path = request.getContextPath();		// ----- 프로젝트 경로까지만 추출 -----
 			System.out.println("[ 프로젝트 경로까지만 추출(path) ] : " + path);
 			
 			if(command.indexOf(request.getContextPath()) == 0) {
 				
 				int length = path.length();	// ----- 프로젝트 경로의 길이를 얻는다 -----
 				System.out.println("[ path(경로)의 길이(length) ] : " + length);
 				
 				command = command.substring(length);	// ----- 전체경로 - 프로젝트 경로 = 페이지 추출 -----
 				System.out.println("[ 페이지 경로 추출 - 요청 명령(command) ] : " + command);
 				
 				com = (CommandService) commandMap.get(command);
 									// ----- commandMapping.properties 파일의 key에 대응되는 value 값이 들어오고
 									// ----- 해당하는 클래스의 주소가 service에 저장된다
 									// ----- 전략 패턴 : 인터페이스를 호출하면 인터페이스를 구현한 클래스와 연결 -----
 				System.out.println("[ 요청 처리 클래스(com) ] : " + com.toString());
 				
 				view = com.service(request, response);	// ----- CommandService.java 클래스의 service() 메서드의 결과 값을 view 에 저장한다 -----
 				System.out.println("[ 명령어 처리 클래스의 요청 처리 결과 뷰(view) ] : " + com.toString());
 				
 			}
 			
 		} catch(Throwable t) {
 			System.out.println("requestPro() 메서드에서 예외 발생!!!");
 			throw new ServletException(t);
 		}
 		/*
 		 * =============================================================================
 		 * 							<< RequestDispatcher >>
 		 * 
 		 * RequestDispatcher 란 인터페이스는 현재 request에 담긴 정보를 저장하고 있다가 그 다음 페이지, 
 		 * 그리고 그다음 페이지에서도 해당 정보를 볼 수 있도록 계속 저장하는 기능을 가지고 있다.
 		 * 다른 의미로 요청을 보내주는 인터페이스(클래스)라고 봐도 된다. 
 		 * 
 		 * 예를 들어, A.jsp에 담겨있는 param 이라는 이름의 파라미터를 B.jsp에서도, 그리고 C.jsp에서도
 		 * 받아 볼 수 있도록 해준다는 것이다. 물론 중간에 가공을 하지 않는다는 전제가 있다.
 		 *  
 		 * request - response 단계에서 forward와 sendRedirect 방식이 있는데,
 		 * forward 방식에서는 A.jsp → Servlet(Controller) → B.jsp로 넘어감에 따라 A.jsp가 
 		 * 가지고 있는 파라미터 정보를 가지고 B.jsp로 넘겨주는 반면에,
 		 * sendRedirect 방식에서는 모든 파라미터 정보를 제외하고 단순 페이지 호출만 하는 차이가 있다.
 		 *  
 		 * 하지만 RequestDispatcher 없이 forward를 하게 되면, A.jsp → Servlet → B.jsp까지는
 		 * 파라미터 정보가 넘어가지만 그 다음 단계에서 A.jsp의 파라미터를 별도로 저장하지 않는다면 파라미터 정보는 
 		 * 소실되게 된다.
 		 * =============================================================================
 		 * 
 		 * ============================================================================= 
 		 * 							<< RequestDispatcher를 얻는 방법 >>
 		 * 
 		 * 1. ServletRequest로부터 얻는 방법
 		 * 	RequestDispatcher dispatcher = request.getRequestDispatcher("result.jsp");
 		 * 	ServletRequest의 getRequestDispatcher() 메서드는 Request(요청)을 넘길 자원에 대한 경로를
 		 * 	인자로 한다. 경로는 String 타입이다. 경로가 "/"로 시작하는 경우 Web App의 루트로 인식하며,
 		 * 	"/"로 시작하지 않으면 Request의 경로에서 상대경로로 시작하는 것으로 인식한다. 하지만 Web App 밖의 경로로는
 		 * 	설정이 불가능하다. 예를들어 ../../.. 이런 식의 Web App의 상위로 이동하는 것이 불가능 하다는 말이다.
 		 * 
 		 * 2. ServletContext로부터 얻는 방법
 		 * 	RequestDispatcher dispatcher 
 		 * 					= getServletContext().getRequestDispatcher("result.jsp");
 		 * 	ServletRequest와 마찬가지로 getRequestDispatcher() 메서드를 사용한다. 또한 자원에 대한 경로를
 		 * 	인자로 한다. 
 		 * 	다른 점이 있다면 상대경로를 사용할 수 없다. 반드시 절대경로인 "/"로 시작해야 한다.
 		 * =============================================================================
 		 * 
 		 * =============================================================================
 		 * 							<< RequestDispatcher - 추가 >>
 		 * 
 		 * RequestDispatcher는 디스패치를 해주는 인터페이스이며, Tomcat(Server & Container)이 
 		 * 그 인터페이스를 구현한 클래스를 제공해준다.
 		 * 디스패치는 ( dispatch : (특히 특별한 목적을 위해)보내다 )라는 사전적인 뜻을 가지고 있다.
 		 * 즉, RequestDispatcher는 요청을 보내주는 클래스라고 볼 수 있다.
 		 * 따라서 현재의 요청을 다른 서블릿(궁극적으로 JSP도 서블릿의 하나이다)으로 보내야 할 때, 
 		 * 서블릿의 인스턴스는 하나만 생성되고, 이것이 멀티스레딩으로 돌아가기 때문에, 새로운 서블릿을 생성해서 
 		 * 그 서블릿을 실행하는 것이 아닌, 서블릿 인스턴스의 스레드를 하나 더 추가해줘야 한다.
 		 * 이것은 서블릿 개발자가 임의로 지정해줄 수 없기 때문에 Dispatcher가 대신해준다.
 		 * 하지만 Dispatcher는 HttpServletRequest, HttpServletResponse를 생성해주지 않는다.
 		 * 그렇기 때문에 Dispatcher가 만들어준 새로운 서블릿 스레드가 있는데, 이 스레드를 실행시키려면 
 		 * doGet() 메서드나 doPost() 메서드를 실행시켜야 한다. 그 결과 RequestDispatcher의 메서드에
 		 * HttpServletRequest, HttpServletResponse를 넘겨줄 수 있게 된다.
 		 * =============================================================================
 		 */
// 		request.setAttribute("cont", view);
 		
 		RequestDispatcher dispatcher = request.getRequestDispatcher(view);	// ----- view의 정보를 dispatcher를 이용하여 전달 -----
 		System.out.println("[ RequestDispatcher가 처리하는 정보(dispatcher) ] : " + dispatcher);
 		
 		dispatcher.forward(request, response);	// ----- 요청 정보와 응답 정보를 dispatcher를 이용하여 전달 -----
 		System.out.println();
 		
 	// ▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦▦ [ 사용자의 요청을 분석해서 처리하는 메서드 ]
 		
 	}
}























