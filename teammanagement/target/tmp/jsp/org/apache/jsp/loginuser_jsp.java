/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: jetty/9.4.2.v20170220
 * Generated at: 2018-01-16 19:54:49 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import es.ligasnba.app.util.constants.Constants;

public final class loginuser_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(4);
    _jspx_dependants.put("jar:file:/C:/Users/Sir/.m2/repository/org/springframework/spring-webmvc/3.2.18.RELEASE/spring-webmvc-3.2.18.RELEASE.jar!/META-INF/spring-form.tld", Long.valueOf(1482338760000L));
    _jspx_dependants.put("file:/C:/Users/Sir/.m2/repository/org/springframework/spring-webmvc/3.2.18.RELEASE/spring-webmvc-3.2.18.RELEASE.jar", Long.valueOf(1505150641460L));
    _jspx_dependants.put("jar:file:/C:/Users/Sir/.m2/repository/org/apache/taglibs/taglibs-standard-impl/1.2.5/taglibs-standard-impl-1.2.5.jar!/META-INF/c.tld", Long.valueOf(1425975070000L));
    _jspx_dependants.put("file:/C:/Users/Sir/.m2/repository/org/apache/taglibs/taglibs-standard-impl/1.2.5/taglibs-standard-impl-1.2.5.jar", Long.valueOf(1505151312971L));
  }

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("es.ligasnba.app.util.constants.Constants");
  }

  private volatile javax.el.ExpressionFactory _el_expressionfactory;
  private volatile org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public javax.el.ExpressionFactory _jsp_getExpressionFactory() {
    if (_el_expressionfactory == null) {
      synchronized (this) {
        if (_el_expressionfactory == null) {
          _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
        }
      }
    }
    return _el_expressionfactory;
  }

  public org.apache.tomcat.InstanceManager _jsp_getInstanceManager() {
    if (_jsp_instancemanager == null) {
      synchronized (this) {
        if (_jsp_instancemanager == null) {
          _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
        }
      }
    }
    return _jsp_instancemanager;
  }

  public void _jspInit() {
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
      throws java.io.IOException, javax.servlet.ServletException {

    final java.lang.String _jspx_method = request.getMethod();
    if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method) && !javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
      response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET POST or HEAD");
      return;
    }

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write(" \n");
      out.write("\n");
      out.write("\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">\n");
      out.write("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"> \n");
      out.write("<meta name=\"mobile-web-app-capable\" content=\"yes\" />\n");
      out.write("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no\">\n");
      out.write("\n");
      out.write("\n");
      out.write("<html>\n");
      out.write("\n");
      out.write("<head>\n");
      org.apache.jasper.runtime.JspRuntimeLibrary.include(request, response, "menu.jsp", out, false);
      out.write("\n");
      out.write("\n");
      out.write("<script type='text/javascript' src='");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("/resources/js/jquery-3.1.0.min.js'></script>\n");
      out.write("<script type='text/javascript' src='");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("/resources/js/jquery-ui.js'></script>\n");
      out.write("<script type='text/javascript' src='");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("/resources/validation/dist/jquery.validate.min.js'></script>\n");
      out.write("\n");
      out.write("<link href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("/resources/css/styles.css\" rel=\"stylesheet\" >\n");
      out.write("<link href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("/resources/css/pure-min.css\" rel=\"stylesheet\" >\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">\n");
      out.write("<script>\n");
      out.write("function submitLogin(){\n");
      out.write("\n");
      out.write("\t\t\n");
      out.write("\tvar validator = $(\"#login-form\").validate({ \n");
      out.write("\t\trules: { \n");
      out.write("\t\t\tj_username: {\n");
      out.write("\t\t\t\trequired: true,\n");
      out.write("\t\t\t\tmaxlength: ");
      out.print( Constants.cUserNameMaxLength );
      out.write(",\n");
      out.write("\t\t\t\tminlength: ");
      out.print( Constants.cUserNameMinLength );
      out.write("\n");
      out.write("\t\t\t},\n");
      out.write("\t\t\tj_password: {\n");
      out.write("\t\t\t\trequired: true,\n");
      out.write("\t\t\t\tmaxlength: ");
      out.print( Constants.cUserPassMaxLength );
      out.write(",\n");
      out.write("\t\t\t\tminlength: ");
      out.print( Constants.cUserPassMinLength );
      out.write("\n");
      out.write("\n");
      out.write("\t\t\t}\n");
      out.write("\t\t}, \n");
      out.write("\t\terrorElement: \"span\" , \n");
      out.write("\t\tmessages: { \n");
      out.write("\t\t\tj_username: {\n");
      out.write("\t\t\t\trequired: \"<font color='red'>· El nombre de usuario no puede estar vacío</font>\",\n");
      out.write("\t\t\t\tmaxlength: \"<font color='red'>· El nombre de usuario debe contener como máximo \"+ ");
      out.print( Constants.cUserNameMaxLength );
      out.write(" +\" caracteres<br></font>\",\n");
      out.write("\t\t\t\tminlength: \"<font color='red'>· El nombre de usuario debe contener al menos \"+ ");
      out.print( Constants.cUserNameMinLength );
      out.write(" +\" caracteres<br></font>\"\n");
      out.write("\t\t\t},\n");
      out.write("\t\t\tj_password: {\n");
      out.write("\t\t\t\trequired: \"<font color='red'>· La contraseña no puede estar vacía</font>\",\n");
      out.write("\t\t\t\tmaxlength: \"<font color='red'>· La contraseña debe contener como máximo \"+ ");
      out.print( Constants.cUserPassMaxLength );
      out.write(" +\" caracteres<br></font>\",\n");
      out.write("\t\t\t\tminlength: \"<font color='red'>· La contraseña debe contener al menos \"+ ");
      out.print( Constants.cUserPassMinLength );
      out.write(" +\" caracteres<br></font>\"\n");
      out.write("\t\t\t\n");
      out.write("\t\t\t}\n");
      out.write("\t\t}\n");
      out.write(" \n");
      out.write("\t}); \n");
      out.write("\tif(validator.form()){\n");
      out.write("\n");
      out.write("\t\t$('#login-form').submit(); \n");
      out.write("\t}\n");
      out.write("}\n");
      out.write("</script>\n");
      out.write("\n");
      out.write("<title>Login</title>\n");
      out.write("</head>\n");
      out.write(" \n");
      out.write("<body>\n");
      out.write("<div class=\"loginForm\">\n");
      out.write("\t<form class=\"pure-form pure-form-stacked\" id=\"login-form\" action=\"/j_spring_security_check\" method=\"post\" >\n");
      out.write("\n");
      out.write("\t<fieldset>\n");
      out.write("\t<legend>Login</legend>\n");
      out.write("\n");
      out.write("\t<label for=\"j_username\">Nombre de Usuario </label>\n");
      out.write("\t<input id=\"j_username\" name=\"j_username\" size=\"20\" maxlength=\"50\" type=\"text\"/>\n");
      out.write("\n");
      out.write("\n");
      out.write("\t<label for=\"j_password\">Contraseña</label>\n");
      out.write("\t<input id=\"j_password\" name=\"j_password\" size=\"20\" maxlength=\"50\" type=\"password\"/>\n");
      out.write("\t<p class=\"messageOK\">");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${message}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("</p>\n");
      out.write("\t<input class=\"pure-button pure-button-primary\" type=\"button\" onclick=\"submitLogin();\" value=\"Login\"/>\n");
      out.write("\t</fieldset>\n");
      out.write("\t<p><a href=\"/remember\"><font size=2> No recuerdo la contraseña </font></a></p>\n");
      out.write("\t</form>\n");
      out.write("</div>\n");
      out.write("</body>\n");
      out.write("</html>\n");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}
