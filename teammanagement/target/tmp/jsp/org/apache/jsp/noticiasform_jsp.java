/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: jetty/9.4.2.v20170220
 * Generated at: 2018-01-05 13:58:18 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class noticiasform_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(6);
    _jspx_dependants.put("jar:file:/C:/Users/Sir/.m2/repository/org/springframework/spring-webmvc/3.2.18.RELEASE/spring-webmvc-3.2.18.RELEASE.jar!/META-INF/spring-form.tld", Long.valueOf(1482338760000L));
    _jspx_dependants.put("file:/C:/Users/Sir/.m2/repository/org/springframework/spring-webmvc/3.2.18.RELEASE/spring-webmvc-3.2.18.RELEASE.jar", Long.valueOf(1505150641460L));
    _jspx_dependants.put("file:/C:/Users/Sir/.m2/repository/javax/servlet/jstl/1.2/jstl-1.2.jar", Long.valueOf(1467650168307L));
    _jspx_dependants.put("jar:file:/C:/Users/Sir/.m2/repository/org/apache/taglibs/taglibs-standard-impl/1.2.5/taglibs-standard-impl-1.2.5.jar!/META-INF/c.tld", Long.valueOf(1425975070000L));
    _jspx_dependants.put("file:/C:/Users/Sir/.m2/repository/org/apache/taglibs/taglibs-standard-impl/1.2.5/taglibs-standard-impl-1.2.5.jar", Long.valueOf(1505151312971L));
    _jspx_dependants.put("jar:file:/C:/Users/Sir/.m2/repository/javax/servlet/jstl/1.2/jstl-1.2.jar!/META-INF/fmt.tld", Long.valueOf(1153377882000L));
  }

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = null;
  }

  private org.apache.jasper.runtime.TagHandlerPool _005fjspx_005ftagPool_005fc_005furl_0026_005fvar_005fvalue_005fnobody;

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
    _005fjspx_005ftagPool_005fc_005furl_0026_005fvar_005fvalue_005fnobody = org.apache.jasper.runtime.TagHandlerPool.getTagHandlerPool(getServletConfig());
  }

  public void _jspDestroy() {
    _005fjspx_005ftagPool_005fc_005furl_0026_005fvar_005fvalue_005fnobody.release();
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

      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"> \n");
      out.write("<meta name=\"mobile-web-app-capable\" content=\"yes\" />\n");
      out.write("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no\">\n");
      out.write("<html>\n");
      out.write("\n");
      out.write("\n");
      out.write("\n");
      out.write("<head>\n");
      out.write("\n");
      out.write("\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" media=\"all\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("/resources/css/styles-responsive.css\"/>\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" media=\"all\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("/resources/css/bootstrap.min.css\"/>\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" media=\"all\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("/resources/css/bootcards-desktop.min.css\"/>\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" media=\"all\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("/resources/css/bootcards-demo.css\"/>\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" media=\"all\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("/resources/css/font-awesome.min.css\"/>\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" media=\"all\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("/resources/css/Animate.css\"/>\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" media=\"all\" href=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("/resources/css/star-rating.min.css\"/>\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=ISO-8859-1\">\n");
      out.write("<script type='text/javascript' src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("/resources/js/jquery-3.1.0.min.js\"/></script>\n");
      out.write("<script type='text/javascript' src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("/resources/js/jquery.number.min.js\"/></script>\n");
      out.write("<title>Noticias</title>\n");
      out.write("<script>var isDesktop = true;</script>\n");
      if (_jspx_meth_c_005furl_005f0(_jspx_page_context))
        return;
      out.write("\n");
      out.write("</head>\n");
      out.write("\n");
      out.write("<body>\n");
      out.write("\n");
      out.write("<nav class=\"navbar navbar-default navbar-fixed-top\" role=\"navigation\">\n");
      out.write("  <div class=\"container\">\n");
      out.write("    <!-- Brand and toggle get grouped for better mobile display -->\n");
      out.write("    <div class=\"navbar-header\">\n");
      out.write("      <button type=\"button\" id=\"botonAtras\" onClick=\"transitionDetailToList()\" class=\"hidden\" style=\"margin-top:10;margin-left:5;\">\n");
      out.write("      \tAtrás\n");
      out.write("      </button>\n");
      out.write(" \n");
      out.write("\t  <a class=\"navbar-brand no-break-out\"  title=\"TeamManagement\" href=\"/competition/");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${menuNavigationForm.idCompeticion}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("\">TeamManagement</a>\n");
      out.write("    </div>\n");
      out.write("  </div>\n");
      out.write("</nav>\n");
      out.write("\n");
      out.write("  <div class=\"container bootcards-container\" id=\"main\">\n");
      out.write("    \n");
      out.write("   <div class=\"col-sm-5 bootcards-list\" >\n");
      out.write("\t\t<div class=\"row\">\n");
      out.write("\t\t\t\n");
      out.write("\t\t\t<div class=\"bootcards-list\">\n");
      out.write("\t\t\t  <div class=\"panel panel-default\">\n");
      out.write("\t\t\t\t<div class=\"panel-heading clearfix\">\n");
      out.write("\t\t\t\t\t<h3 class=\"panel-title pull-left\"><b>Noticias</b></h3>\n");
      out.write("\t\t\t\t</div>\t\t\t\t  \n");
      out.write("\t\t\t    <div class=\"list-group\">\n");
      out.write("\t\t\t      <div class=\"list-group-item\" id=\"listaNoticias\">\n");
      out.write("\t\t\t      </div>\n");
      out.write("\t\t\t    </div>\n");
      out.write("\t\t    \n");
      out.write("\t\t\t  </div>\n");
      out.write("\t\t\t</div>\n");
      out.write("\n");
      out.write("  \t\t</div>\n");
      out.write("\t</div>\n");
      out.write("  </div>\n");
      out.write("\n");
      out.write("\n");
      out.write('\n');
      out.write("\n");
      out.write("<script type='text/javascript' src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("/resources/bootstrap-notify-master/bootstrap-notify.min.js\"/></script>\n");
      out.write("<script type='text/javascript' src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("/resources/js/bootstrap.min.js\"/></script>\n");
      out.write("<script type='text/javascript' src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("/resources/js/bootcards.min.js\"/></script>\n");
      out.write("<script type='text/javascript' src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("/resources/js/star-rating.min.js\"/></script>\n");
      out.write("<script type='text/javascript' src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("/resources/js/theme.min.js\"/></script>\n");
      out.write("<script type='text/javascript' src=\"");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${pageContext.request.contextPath}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write("/resources/js/es.js\"/></script>\n");
      out.write('\n');
      out.write("\n");
      out.write("<script type=\"text/javascript\">\n");
      out.write("    \n");
      out.write("//     if ( $('.list-group a.active').length === 0 ) {\n");
      out.write("//       $('.list-group a').first().addClass('active');\n");
      out.write("//     }\n");
      out.write("\n");
      out.write("    bootcards.init( {\n");
      out.write("        offCanvasHideOnMainClick : true,\n");
      out.write("        offCanvasBackdrop : true,\n");
      out.write("        enableTabletPortraitMode : true,\n");
      out.write("        disableRubberBanding : false,\n");
      out.write("        disableBreakoutSelector : 'a.no-break-out'\n");
      out.write("      });\n");
      out.write("    //fix for minimal-ui bug in Safari:\n");
      out.write("    //http://stackoverflow.com/questions/22391157/gray-area-visible-when-switching-from-portrait-to-landscape-using-ios-7-1-minima\n");
      out.write("    if (bootcards.isXS() ) {\n");
      out.write("      window.addEventListener(\"orientationchange\", function() {\n");
      out.write("        window.scrollTo(0,0);\n");
      out.write("      }, false);\n");
      out.write("\n");
      out.write("      //initial redraw - needed to fix an issue with the minimal-ui and foot location\n");
      out.write("      //when the page if first opened \n");
      out.write("      window.scrollTo(0,0);\n");
      out.write("    }    \n");
      out.write("\n");
      out.write("</script>\n");
      out.write("<script type=\"text/javascript\">\n");
      out.write("  \n");
      out.write("    \n");
      out.write("    $( document ).ready(function() {\n");
      out.write("    \tvar idTemporada = ");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${menuNavigationForm.idTemporada}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write(";\n");
      out.write("    \t$('select[name=seasonSelect]').val(idTemporada);\n");
      out.write("\t\tloadPartidos(idTemporada);\t\n");
      out.write("     });\n");
      out.write("    \n");
      out.write("    \n");
      out.write("    function notify(mensaje, tipo){\n");
      out.write("    \t$.notify({\n");
      out.write("    \t\ttitle: \"\",\n");
      out.write("    \t\tmessage: mensaje\n");
      out.write("    \t},{\n");
      out.write("    \t\ttype: tipo,\n");
      out.write("    \t\tdelay: 1000,\n");
      out.write("    \t\tplacement: {\n");
      out.write("    \t\t\talign : \"center\"\n");
      out.write("    \t\t},\n");
      out.write("    \t\tanimate:{\n");
      out.write("    \t\t\tenter: \"animated fadeInDown\",\n");
      out.write("    \t\t\texit: \"animated fadeOutDown\"\n");
      out.write("    \t\t}\n");
      out.write("    \t});    \t\n");
      out.write("    }\n");
      out.write("    \n");
      out.write("    function loadPartidos(idTemporada){\n");
      out.write("    \tvar idEquipo = ");
      out.write((java.lang.String) org.apache.jasper.runtime.PageContextImpl.proprietaryEvaluate("${menuNavigationForm.idEquipo}", java.lang.String.class, (javax.servlet.jsp.PageContext)_jspx_page_context, null));
      out.write(";\n");
      out.write("    \t \n");
      out.write("    \t$.ajax({\n");
      out.write("    \t\ttype:\"get\",\n");
      out.write("    \t\turl: \"/noticias/findUltimasNoticias\",\t\n");
      out.write("    \t\tdata: ({ idEquipo: idEquipo}),\n");
      out.write("    \t\tdataType: \"json\",\n");
      out.write("    \t\tsuccess: function(response){\n");
      out.write("    \t\t\t$( \"#listaNoticias\" ).empty();\n");
      out.write("    \t\t\t\n");
      out.write("    \t\t\tfor (var i in response){\n");
      out.write("    \t\t\n");
      out.write("    \t\t\t\tvar icono = '<i class=\"fa fa-3x fa-plus pull-left\" style=\"color:green\"></i>';\n");
      out.write("    \t\t\t\t\n");
      out.write("    \t\t\t\tvar date = new Date(response[i].fecha);\n");
      out.write("    \t\t\t\tvar dateFormatted = date.getDate() + '/'+(date.getMonth() + 1) + '/' +  +  date.getFullYear();\n");
      out.write("    \t\t\t\t\n");
      out.write("    \t\t\t\t$( \"#listaNoticias\" ).append('<div class=\"list-group-item\"><div class=\"row\"><div class=\"col-sm-12\">'+\t\t\t            \n");
      out.write("\t\t\t            '<h4 class=\"list-group-item-heading\">'+response[i].texto+'</h4>'+\n");
      out.write("\t\t\t            '<p class=\"list-group-item-text\"></p>'+\n");
      out.write("\t\t\t          '</div>'+\n");
      out.write("\t\t\t          '<div class=\"col-sm-6\">'+\n");
      out.write("\t\t\t            '<p class=\"list-group-item-text\">'+dateFormatted+'</p>'+\n");
      out.write("\t\t\t          '</div></div></div></div>');\n");
      out.write("    \t\t\t}\n");
      out.write("    \t\t\t   \t\t\t\n");
      out.write("    \t\t},\n");
      out.write("    \t\terror: function(){\n");
      out.write("    \t\t\talert('error');\n");
      out.write("    \t\t}\n");
      out.write("    \t});\t    \t\n");
      out.write("    }\n");
      out.write("    \n");
      out.write("    \n");
      out.write("</script>    \n");
      out.write("</body>\n");
      out.write("\n");
      out.write("</html>\n");
      out.write("\n");
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

  private boolean _jspx_meth_c_005furl_005f0(javax.servlet.jsp.PageContext _jspx_page_context)
          throws java.lang.Throwable {
    javax.servlet.jsp.PageContext pageContext = _jspx_page_context;
    javax.servlet.jsp.JspWriter out = _jspx_page_context.getOut();
    //  c:url
    org.apache.taglibs.standard.tag.rt.core.UrlTag _jspx_th_c_005furl_005f0 = (org.apache.taglibs.standard.tag.rt.core.UrlTag) _005fjspx_005ftagPool_005fc_005furl_0026_005fvar_005fvalue_005fnobody.get(org.apache.taglibs.standard.tag.rt.core.UrlTag.class);
    try {
      _jspx_th_c_005furl_005f0.setPageContext(_jspx_page_context);
      _jspx_th_c_005furl_005f0.setParent(null);
      // /noticiasform.jsp(27,0) name = value type = null reqTime = true required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_c_005furl_005f0.setValue("/");
      // /noticiasform.jsp(27,0) name = var type = java.lang.String reqTime = false required = false fragment = false deferredValue = false expectedTypeName = null deferredMethod = false methodSignature = null
      _jspx_th_c_005furl_005f0.setVar("homeUrl");
      int _jspx_eval_c_005furl_005f0 = _jspx_th_c_005furl_005f0.doStartTag();
      if (_jspx_th_c_005furl_005f0.doEndTag() == javax.servlet.jsp.tagext.Tag.SKIP_PAGE) {
        return true;
      }
    } finally {
      _005fjspx_005ftagPool_005fc_005furl_0026_005fvar_005fvalue_005fnobody.reuse(_jspx_th_c_005furl_005f0);
    }
    return false;
  }
}
