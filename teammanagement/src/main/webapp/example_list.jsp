<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge"> 
  <!--<link rel="shortcut icon" href="../../docs-assets/ico/favicon.png">-->

    <meta name="mobile-web-app-capable" content="yes" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0, user-scalable=no">
	<!-- Icons -->  
  <link rel="shortcut icon" sizes="196x196" href="/bootcards-demo-app/images/apple-touch-icon-144x144.png">
  <link rel="shortcut icon" sizes="128x128" href="/bootcards-demo-app/images/apple-touch-icon-120x120.png">
	<!-- For iPhone with high-resolution Retina display running iOS ≥ 7: -->
	<link rel="apple-touch-icon" sizes="120x120" href="/bootcards-demo-app/images/apple-touch-icon-120x120.png">
	<!-- For iPad with high-resolution Retina display running iOS ≤ 6: -->
	<link rel="apple-touch-icon" sizes="144x144" href="/bootcards-demo-app/images/apple-touch-icon-144x144.png">

	<!-- Splash Screen Images -->
    <!-- iOS 6 & 7 iPad (retina, portrait) -->
    <link href="/bootcards-demo-app/images/bootcards-splash-768x1004@2x.png"
          media="(device-width: 768px) and (device-height: 1024px)
             and (orientation: portrait)
             and (-webkit-device-pixel-ratio: 2)"
          rel="apple-touch-startup-image">

    <!-- iOS 6 & 7 iPad (retina, landscape) -->
    <link href="/bootcards-demo-app/images/bootcards-splash-1024x748@2x.png"
          media="(device-width: 768px) and (device-height: 1024px)
             and (orientation: landscape)
             and (-webkit-device-pixel-ratio: 2)"
          rel="apple-touch-startup-image">

    <!-- iOS 6 iPad (portrait) -->
    <link href="/bootcards-demo-app/images/bootcards-splash-1024x748.png"
          media="(device-width: 768px) and (device-height: 1024px)
             and (orientation: portrait)
             and (-webkit-device-pixel-ratio: 1)"
          rel="apple-touch-startup-image">

    <!-- iOS 6 iPad (landscape) -->
    <link href="/bootcards-demo-app/images/bootcards-splash-768x1004.png"
          media="(device-width: 768px) and (device-height: 1024px)
             and (orientation: landscape)
             and (-webkit-device-pixel-ratio: 1)"
          rel="apple-touch-startup-image">

    <!-- iOS 6 & 7 iPhone 5 -->
    <link href="/bootcards-demo-app/images/bootcards-splash-320x548@2x.png"
          media="(device-width: 320px) and (device-height: 568px)
             and (-webkit-device-pixel-ratio: 2)"
          rel="apple-touch-startup-image">

    <!-- iOS 6 & 7 iPhone (retina) -->
    <link href="/bootcards-demo-app/images/bootcards-splash-320x460@2x.png"
          media="(device-width: 320px) and (device-height: 480px)
             and (-webkit-device-pixel-ratio: 2)"
          rel="apple-touch-startup-image">

    <!-- iOS 6 iPhone -->
    <link href="/bootcards-demo-app/images/bootcards-splash-320x460.png"
          media="(device-width: 320px) and (device-height: 480px)
             and (-webkit-device-pixel-ratio: 1)"
          rel="apple-touch-startup-image">	


  <title>Customers</title>
  <!-- 1.1.2 -->
  
  <!-- Load the required CSS libraries -->

  <!-- bootstrap -->
  <link href="//maxcdn.bootstrapcdn.com/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">

   <!-- Bootcards CSS file (different for desktop, iOS and Android, included Bootstrap CSS) -->
  <link href="//cdnjs.cloudflare.com/ajax/libs/bootcards/1.1.2/css/bootcards-desktop.min.css" rel="stylesheet" type="text/css" />

  <link href="//cdnjs.cloudflare.com/ajax/libs/morris.js/0.5.1/morris.css" rel="stylesheet" />

  <link href="//netdna.bootstrapcdn.com/font-awesome/4.2.0/css/font-awesome.min.css" rel="stylesheet" />
 <link rel="stylesheet" type="text/css" media="all" href="<c:url value="/resources/css/styles-responsive.css" context="/teammanagement"/>">

<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/resources/css/bootstrap.min.css" context="/teammanagement"/>">
<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/resources/css/bootcards-desktop.min.css" context="/teammanagement"/>">
<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/resources/css/bootcards-demo.css" context="/teammanagement"/>">
<link rel="stylesheet" type="text/css" media="all" href="<c:url value="/resources/css/font-awesome.min.css" context="/teammanagement"/>">
<script type='text/javascript' src='<c:url value="/resources/js/fastclick.min.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/js/raphael-min.js"/>'></script>
<%-- <script type='text/javascript' src='<c:url value="/resources/js/morris.min.js"/>'></script> --%>
<script type='text/javascript' src='<c:url value="/resources/js/jquery-3.1.0.min.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/js/bootstrap.min.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/js/bootcards.min.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/js/jquery.pjax.js"/>'></script>
<script type='text/javascript' src='<c:url value="/resources/js/bootcards-demo-app.js"/>'></script>
  <script>var isDesktop = true;</script>
  <!-- Custom CSS for the demo app -->
  <link href="/bootcards-demo-app/css/bootcards-demo-app.css" rel="stylesheet" />
  
  <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/libs/html5shiv/3.7.0/html5shiv.js"></script>
      <script src="https://oss.maxcdn.com/libs/respond.js/1.3.0/respond.min.js"></script>
      <![endif]-->

    <!-- <script src="//ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script> -->
    <script src="/bower_components/jquery/dist/jquery.min.js"></script>
</head>
<body id="bootcards">

  <!-- fixed top navbar -->
  <div class="navbar navbar-default navbar-fixed-top" role="navigation">
    <div class="container">
      <div class="navbar-header">
        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
          <span class="sr-only">Toggle navigation</span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
          <span class="icon-bar"></span>
        </button>
      </div>

      <button type="button" class="btn btn-default btn-back navbar-left pull-left hidden" onclick="history.back()">
        <i class="fa fa-lg fa-chevron-left"></i><span>Back</span>
      </button>
      <!-- menu button to show/ hide the off canvas slider -->
      <button type="button" class="btn btn-default btn-menu navbar-left pull-left offCanvasToggle" data-toggle="offcanvas">
        <i class="fa fa-lg fa-bars"></i><span>Menu</span>
      </button>  

      <a class="navbar-brand no-break-out" title="Customers" href="/">Customers</a>  
      
      <div class="navbar-collapse collapse">
        <ul class="nav navbar-nav">
          <li>
            <a href="/dashboard" data-pjax="#main" data-title="Customers"
              <i class="fa fa-dashboard"></i>
              Dashboard
            </a>          
          </li>
          <li>
            <a href="/companies" data-pjax="#main" data-title="Companies"
              <i class="fa fa-building-o"></i>
              Companies
            </a>          
          </li>
          <li class="active">
            <a href="/contacts" data-pjax="#main" data-title="Contacts"
              <i class="fa fa-users"></i>
              Contacts
            </a>          
          </li>
          <li>
            <a href="/notes" data-pjax="#main" data-title="Notes"
              <i class="fa fa-clipboard"></i>
              Notes
            </a>          
          </li>
          <li>
            <a href="/charts" data-pjax="#main" data-title="Charts"
              <i class="fa fa-bar-chart-o"></i>
              Charts
            </a>          
          </li>
          <li>
            <a href="/settings" data-pjax="#main" data-title="Settings"
              <i class="fa fa-gears"></i>
              Settings
            </a>          
          </li>
        </ul>
      </div>          
    </div>
  </div>   

  <!-- slide in menu (mobile only) -->
  <nav id="offCanvasMenu" class="navmenu offcanvas offcanvas-left">
    <ul class="nav">
      <li>
        <a href="/dashboard" data-pjax="#main" data-title="Customers">
          <i class="fa fa-lg fa-dashboard"></i>
          Dashboard
        </a>          
      </li>
      <li>
        <a href="/companies" data-pjax="#main" data-title="Companies">
          <i class="fa fa-lg fa-building-o"></i>
          Companies
        </a>          
      </li>
      <li class="active">
        <a href="/contacts" data-pjax="#main" data-title="Contacts">
          <i class="fa fa-lg fa-users"></i>
          Contacts
        </a>          
      </li>
      <li>
        <a href="/notes" data-pjax="#main" data-title="Notes">
          <i class="fa fa-lg fa-clipboard"></i>
          Notes
        </a>          
      </li>
      <li>
        <a href="/charts" data-pjax="#main" data-title="Charts">
          <i class="fa fa-lg fa-bar-chart-o"></i>
          Charts
        </a>          
      </li>
      <li>
        <a href="/settings" data-pjax="#main" data-title="Settings">
          <i class="fa fa-lg fa-gears"></i>
          Settings
        </a>          
      </li>
    </ul>

    <div style="margin-top:20px; padding-left: 20px; font-size: 12px; color: #777">v1.1.2</div>
  </nav>

  <div class="container bootcards-container" id="main">
    
   
		<div class="row">
			
			<div class="col-sm-5 bootcards-list" id="list" data-title="Contacts">
				<div class="panel panel-default">				
					<div class="panel-body">
						<div class="search-form">
							<div class="row">
							    <div class="col-xs-9">
							      <div class="form-group">
								      <input type="text" class="form-control" placeholder="Search Contacts...">
								      <i class="fa fa-search"></i>
							      </div>
							    </div>
							    <div class="col-xs-3">
									<a class="btn btn-primary btn-block" href="/contacts/add"
										data-toggle="modal"
										data-target="#editModal">
										<i class="fa fa-plus"></i> 
										<span>Add</span>
									</a>
							    </div>
							</div>						    
						</div>					
					</div>
					<div class="list-group">
	
							<a class="list-group-item pjax" 
					 			href="example_card">
								<img src="/images/Sofia Acey.jpg" class="img-rounded pull-left"/>
								<h4 class="list-group-item-heading">Acey, Sofia</h4>
								<p class="list-group-item-text">Masung Corp.</p>
							</a>
							<a class="list-group-item pjax" 
					 			href="/contacts/sH5PxrdzkZ3T">
								<img src="/images/Joseph Barish.jpg" class="img-rounded pull-left"/>
								<h4 class="list-group-item-heading">Barish, Joseph</h4>
								<p class="list-group-item-text">Masung Corp.</p>
							</a>
							<a class="list-group-item pjax" 
					 			href="/contacts/s9RSWs87B4qv">
								<img src="/images/Jerry Bess.jpg" class="img-rounded pull-left"/>
								<h4 class="list-group-item-heading">Bess, Jerry</h4>
								<p class="list-group-item-text">ZetaComm</p>
							</a>
							<a class="list-group-item pjax" 
					 			href="/contacts/eNK2QXWJa8E3">
								<img src="/images/Jamie Biddy.jpg" class="img-rounded pull-left"/>
								<h4 class="list-group-item-heading">Biddy, Jamie</h4>
								<p class="list-group-item-text">Masung Corp.</p>
							</a>
							<a class="list-group-item pjax" 
					 			href="/contacts/kNQv7Urv7AzX">
								<img src="/images/Mark Booth.jpg" class="img-rounded pull-left"/>
								<h4 class="list-group-item-heading">Booth, Mark</h4>
								<p class="list-group-item-text">Burning Core</p>
							</a>
							<a class="list-group-item pjax" 
					 			href="/contacts/HZTBeGFQCzPs">
								<img src="/images/Sharon Burns.jpg" class="img-rounded pull-left"/>
								<h4 class="list-group-item-heading">Burns, Sharon</h4>
								<p class="list-group-item-text">Derenden Systems</p>
							</a>
							<a class="list-group-item pjax" 
					 			href="/contacts/3D6vqH8yZrD4">
								<img src="/images/Terri Donner.jpg" class="img-rounded pull-left"/>
								<h4 class="list-group-item-heading">Donner, Terri</h4>
								<p class="list-group-item-text">Elparvis</p>
							</a>
							<a class="list-group-item pjax" 
					 			href="/contacts/rvQv8GcxZFaN">
								<img src="/images/Josephine Driscoll.jpg" class="img-rounded pull-left"/>
								<h4 class="list-group-item-heading">Driscoll, Josephine</h4>
								<p class="list-group-item-text">Enmaris Mobile</p>
							</a>
							<a class="list-group-item pjax" 
					 			href="/contacts/K4NWfXGSPPWk">
								<img src="/images/Evelyn Dwyer.jpg" class="img-rounded pull-left"/>
								<h4 class="list-group-item-heading">Dwyer, Evelyn</h4>
								<p class="list-group-item-text">Entekra Inc.</p>
							</a>
							<a class="list-group-item pjax" 
					 			href="/contacts/FpTLcabz7TFs">
								<img src="/images/Jack Floyd.jpg" class="img-rounded pull-left"/>
								<h4 class="list-group-item-heading">Floyd, Jack</h4>
								<p class="list-group-item-text">Ferakon</p>
							</a>
							<a class="list-group-item pjax" 
					 			href="/contacts/RpGBhrAXGP8S">
								<img src="/images/Chris Grocott.jpg" class="img-rounded pull-left"/>
								<h4 class="list-group-item-heading">Grocott, Chris</h4>
								<p class="list-group-item-text">Ganonite Corp.</p>
							</a>
							<a class="list-group-item pjax" 
					 			href="/contacts/Gv8h6f4UDL84">
								<img src="/images/Fred Hafer.jpg" class="img-rounded pull-left"/>
								<h4 class="list-group-item-heading">Hafer, Fred</h4>
								<p class="list-group-item-text">GVSB Corp.</p>
							</a>
							<a class="list-group-item pjax" 
					 			href="/contacts/6BegTWK928p1">
								<img src="/images/Harry Harkins.jpg" class="img-rounded pull-left"/>
								<h4 class="list-group-item-heading">Harkins, Harry</h4>
								<p class="list-group-item-text">Haven Inc.</p>
							</a>
							<a class="list-group-item pjax" 
					 			href="/contacts/cNqW3fUXdrhB">
								<img src="/images/Robert Jarrell.jpg" class="img-rounded pull-left"/>
								<h4 class="list-group-item-heading">Jarrell, Robert</h4>
								<p class="list-group-item-text">Lossless Systems</p>
							</a>
							<a class="list-group-item pjax" 
					 			href="/contacts/cA2vBwEZmx88">
								<img src="/images/Laura Johnson.jpg" class="img-rounded pull-left"/>
								<h4 class="list-group-item-heading">Johnson, Laura</h4>
								<p class="list-group-item-text">Lossless Systems</p>
							</a>
							<a class="list-group-item pjax" 
					 			href="/contacts/zBeGXPFzns4a">
								<img src="/images/Stacy Korner.jpg" class="img-rounded pull-left"/>
								<h4 class="list-group-item-heading">Korner, Stacy</h4>
								<p class="list-group-item-text">Mecharta Corp.</p>
							</a>
							<a class="list-group-item pjax" 
					 			href="/contacts/C7eztra8Hb5x">
								<img src="/images/Ricky Lee.jpg" class="img-rounded pull-left"/>
								<h4 class="list-group-item-heading">Lee, Ricky</h4>
								<p class="list-group-item-text">NoHi Corp.</p>
							</a>
							<a class="list-group-item pjax" 
					 			href="/contacts/fAZM3gmKtw9T">
								<img src="/images/Annabelle Malcomb.jpg" class="img-rounded pull-left"/>
								<h4 class="list-group-item-heading">Malcomb, Annabelle</h4>
								<p class="list-group-item-text">Peranos Inc.</p>
							</a>
							<a class="list-group-item pjax" 
					 			href="/contacts/yV6ZbrhAGpfZ">
								<img src="/images/Chris Massie.jpg" class="img-rounded pull-left"/>
								<h4 class="list-group-item-heading">Massie, Chris</h4>
								<p class="list-group-item-text">Sayad Mobile</p>
							</a>
							<a class="list-group-item pjax" 
					 			href="/contacts/Jdacn3J62x73">
								<img src="/images/Jack Ortiz.jpg" class="img-rounded pull-left"/>
								<h4 class="list-group-item-heading">Ortiz, Jack</h4>
								<p class="list-group-item-text">Silver Rocket Inc.</p>
							</a>
							<a class="list-group-item pjax" 
					 			href="/contacts/M2B8Nd3rMey5">
								<img src="/images/David Peters.jpg" class="img-rounded pull-left"/>
								<h4 class="list-group-item-heading">Peters, David</h4>
								<p class="list-group-item-text">Techtractus Inc.</p>
							</a>
							<a class="list-group-item pjax" 
					 			href="/contacts/3DTWzkqktNSG">
								<img src="/images/Kelly Podesta.jpg" class="img-rounded pull-left"/>
								<h4 class="list-group-item-heading">Podesta, Kelly</h4>
								<p class="list-group-item-text">Telkon Corp.</p>
							</a>
							<a class="list-group-item pjax" 
					 			href="/contacts/zPBpZLWrbSq8">
								<img src="/images/Simon Primm.jpg" class="img-rounded pull-left"/>
								<h4 class="list-group-item-heading">Primm, Simon</h4>
								<p class="list-group-item-text">Telkon Corp.</p>
							</a>
							<a class="list-group-item pjax" 
					 			href="/contacts/MgGUaVg4Jy41">
								<img src="/images/Clinton Quincy.jpg" class="img-rounded pull-left"/>
								<h4 class="list-group-item-heading">Quincy, Clinton</h4>
								<p class="list-group-item-text">Terrestria Inc.</p>
							</a>
							<a class="list-group-item pjax" 
					 			href="/contacts/RmNmSEeDWaUK">
								<img src="/images/Nelson Raia.jpg" class="img-rounded pull-left"/>
								<h4 class="list-group-item-heading">Raia, Nelson</h4>
								<p class="list-group-item-text">Tidus International</p>
							</a>
							<a class="list-group-item pjax" 
					 			href="/contacts/vMUT6KnAq3Lt">
								<img src="/images/Kristin Rayner.jpg" class="img-rounded pull-left"/>
								<h4 class="list-group-item-heading">Rayner, Kristin</h4>
								<p class="list-group-item-text">Trantion Systems</p>
							</a>
							<a class="list-group-item pjax" 
					 			href="/contacts/bAvdNCMLFDwP">
								<img src="/images/Curtis Rowland.jpg" class="img-rounded pull-left"/>
								<h4 class="list-group-item-heading">Rowland, Curtis</h4>
								<p class="list-group-item-text">Travanus</p>
							</a>
							<a class="list-group-item pjax" 
					 			href="/contacts/1H4bs81t1mBp">
								<img src="/images/Mathew Salone.jpg" class="img-rounded pull-left"/>
								<h4 class="list-group-item-heading">Salone, Mathew</h4>
								<p class="list-group-item-text">Yoracle Inc.</p>
							</a>
							<a class="list-group-item pjax" 
					 			href="/contacts/nuer1hQbtEwr">
								<img src="/images/Chiam See Tong.jpg" class="img-rounded pull-left"/>
								<h4 class="list-group-item-heading">See Tong, Chiam</h4>
								<p class="list-group-item-text">Yunalesca</p>
							</a>
							<a class="list-group-item pjax" 
					 			href="/contacts/Zum5QaksnT38">
								<img src="/images/Wayne Sherman.jpg" class="img-rounded pull-left"/>
								<h4 class="list-group-item-heading">Sherman, Wayne</h4>
								<p class="list-group-item-text">Burning Core</p>
							</a>
							<a class="list-group-item pjax" 
					 			href="/contacts/ftBZ9u64tAgq">
								<img src="/images/Danny Shin.jpg" class="img-rounded pull-left"/>
								<h4 class="list-group-item-heading">Shin, Danny</h4>
								<p class="list-group-item-text">Burning Core</p>
							</a>
							<a class="list-group-item pjax" 
					 			href="/contacts/puSBydW5xgup">
								<img src="/images/Matthew Shipster.jpg" class="img-rounded pull-left"/>
								<h4 class="list-group-item-heading">Shipster, Matthew</h4>
								<p class="list-group-item-text">Materella Inc.</p>
							</a>
							<a class="list-group-item pjax" 
					 			href="/contacts/QQkRbSVxfARv">
								<img src="/images/Darren Sizelove.jpg" class="img-rounded pull-left"/>
								<h4 class="list-group-item-heading">Sizelove, Darren</h4>
								<p class="list-group-item-text">Camion Corp.</p>
							</a>
							<a class="list-group-item pjax" 
					 			href="/contacts/BHTmv2NpG3eU">
								<img src="/images/Teddy Skolnik.jpg" class="img-rounded pull-left"/>
								<h4 class="list-group-item-heading">Skolnik, Teddy</h4>
								<p class="list-group-item-text">Camion Corp.</p>
							</a>
							<a class="list-group-item pjax" 
					 			href="/contacts/nAhPG98D29yU">
								<img src="/images/James Smith.jpg" class="img-rounded pull-left"/>
								<h4 class="list-group-item-heading">Smith, James</h4>
								<p class="list-group-item-text">Derenden Systems</p>
							</a>
							<a class="list-group-item pjax" 
					 			href="/contacts/WJTQPvwJx8ZH">
								<img src="/images/Ryan Smith.jpg" class="img-rounded pull-left"/>
								<h4 class="list-group-item-heading">Smith, Ryan</h4>
								<p class="list-group-item-text">Elparvis</p>
							</a>
							<a class="list-group-item pjax" 
					 			href="/contacts/HTJey62NEsuR">
								<img src="/images/Cody Swinford.jpg" class="img-rounded pull-left"/>
								<h4 class="list-group-item-heading">Swinford, Cody</h4>
								<p class="list-group-item-text">Elparvis</p>
							</a>
							<a class="list-group-item pjax" 
					 			href="/contacts/tRzSwETzMVbT">
								<img src="/images/David Tay.jpg" class="img-rounded pull-left"/>
								<h4 class="list-group-item-heading">Tay, David</h4>
								<p class="list-group-item-text">Enmaris Mobile</p>
							</a>
							<a class="list-group-item pjax" 
					 			href="/contacts/rT3zaDLrzfSK">
								<img src="/images/Jerry Williamson.jpg" class="img-rounded pull-left"/>
								<h4 class="list-group-item-heading">Williamson, Jerry</h4>
								<p class="list-group-item-text">Ferakon</p>
							</a>
							<a class="list-group-item pjax" 
					 			href="/contacts/vhVqL5KRyhp2">
								<img src="/images/Lim Yew Jin.jpg" class="img-rounded pull-left"/>
								<h4 class="list-group-item-heading">Yew Jin, Lim</h4>
								<p class="list-group-item-text">Ferakon</p>
							</a>
						
					</div>
					<div class="panel-footer">
						<small class="pull-left">Built with Bootcards - List</small>
						<a class="btn btn-link btn-xs pull-right"
							href="/snippets/list-detailed"
							data-toggle="modal"
							data-target="#docsModal">
							View Source</a>
					</div>
				</div>
			</div>
		
			<div class="col-sm-7 bootcards-cards hidden-xs" id="listDetails">

				<!--contact details & notes-->
				
				<div id="contactCard">
					
					<div class="panel panel-default">
						<div class="panel-heading clearfix">
							<h3 class="panel-title pull-left">Contact Details</h3>
							<div class="btn-group pull-right visible-xs">
								<a class="btn btn-primary" href="/contacts/VBMJHwcLX3Vx/edit"
									data-toggle="modal"
									data-target="#editModal">
									<i class="fa fa-pencil"></i>
									<span>Edit</span>
								</a>
							</div>	
							<a class="btn btn-primary pull-right hidden-xs" href="/contacts/VBMJHwcLX3Vx/edit"
								data-toggle="modal"
								data-target="#editModal">
								<i class="fa fa-pencil"></i>
								<span>Edit</span>
							</a>
						</div>
						<div class="list-group">
							<div class="list-group-item">
								<img src="/images/Sofia Acey.jpg" class="img-rounded pull-left"/>
								<label>Name</label>
								<h4 class="list-group-item-heading">Acey, Sofia</h4>
							</div>
							<div class="list-group-item">
								<label>Company</label>
								<h4 class="list-group-item-heading">Masung Corp.</h4>
							</div>
							<div class="list-group-item">
								<label>Job Title</label>
								<h4 class="list-group-item-heading">Finance Director (ZCT)</h4>
							</div>
							<div class="list-group-item">
								<label>Department</label>
								<h4 class="list-group-item-heading">Finance</h4>
							</div>
							<a class="list-group-item" href="tel://+1 650-555-0055">
								<label>Phone</label>
								<h4 class="list-group-item-heading">+1 650-555-0055</h4>
							</a>
							<a class="list-group-item" href="mailto:Sofia.Acey@masung.com">
								<label>Email</label>
								<h4 class="list-group-item-heading">Sofia.Acey@masung.com</h4>
							</a>
						</div>
						<div class="panel-footer">
							<small class="pull-left">Built with Bootcards - Base Card</small>
							<a class="btn btn-link btn-xs pull-right"
											href="/snippets/base-card-form"
											data-toggle="modal"
											data-target="#docsModal">
											View Source</a>
						</div>		
					</div>
				
				</div>
				
				<!--list of notes-->
				
				<div>
				
					<div class="panel panel-default">
						<div class="panel-heading clearfix">
							<h3 class="panel-title pull-left">Notes</h3>
							<div class="btn-group pull-right">
								<a class="btn btn-primary" href="/contacts/VBMJHwcLX3Vx/notes/add"
									data-toggle="modal"
									data-target="#editModal">
										<i class="fa fa-plus"></i>
										<span>Add</span>
								</a>
							</div>							
						</div>					
						<div class="list-group">
								<a class="list-group-item pjax" 
										href="/contacts/VBMJHwcLX3Vx/notes/ucw9gtbjq7ls">
									<i class="fa fa-2x pull-left fa-check-square-o"></i>
									<h4 class="list-group-item-heading">Send tracking Number</h4>
									<p class="list-group-item-text">Mon 02 Dec 2013 17:27</p>
								</a>
								<a class="list-group-item pjax" 
										href="/contacts/VBMJHwcLX3Vx/notes/1rhen4xigta0w">
									<i class="fa fa-2x pull-left fa-file-text-o"></i>
									<h4 class="list-group-item-heading">Improved Load Time</h4>
									<p class="list-group-item-text">Mon 02 Dec 2013 17:26</p>
								</a>
								<a class="list-group-item pjax" 
										href="/contacts/VBMJHwcLX3Vx/notes/1rhen4xigta0z">
									<i class="fa fa-2x pull-left fa-paperclip"></i>
									<h4 class="list-group-item-heading">Stingray.pdf</h4>
									<p class="list-group-item-text">Mon 02 Dec 2013 17:26</p>
								</a>
								<a class="list-group-item pjax" 
										href="/contacts/VBMJHwcLX3Vx/notes/uywr1g2fnvuo">
									<i class="fa fa-2x pull-left fa-picture-o"></i>
									<h4 class="list-group-item-heading">Product image</h4>
									<p class="list-group-item-text">Mon 02 Dec 2013 17:26</p>
								</a>
						</div>
						<div class="panel-footer">
							<small class="pull-left">Built with Bootcards - List</small>
							<a class="btn btn-link btn-xs pull-right"
									href="/snippets/list-card"
									data-toggle="modal"
									data-target="#docsModal">
									View Source</a>
						</div>			
					</div>
				
				</div>

			</div>
		</div>


  </div>

	<!-- fixed tabbed footer -->
	<div class="navbar navbar-default navbar-fixed-bottom">
		<div class="container">
			<div class="bootcards-desktop-footer clearfix">
				<p class="pull-left">Bootcards v1.1.2</p>
			</div>
			<div class="btn-group">
				<a href="/dashboard" class="btn btn-default" data-pjax="#main" data-title="Customers">
					<i class="fa fa-2x fa-dashboard"></i>
					Dashboard
				</a>          
				<a href="/companies" class="btn btn-default" data-pjax="#main" data-title="Companies">
					<i class="fa fa-2x fa-building-o"></i>
					Companies
				</a>          
				<a href="/contacts" class="btn btn-default active" data-pjax="#main" data-title="Contacts">
					<i class="fa fa-2x fa-users"></i>
					Contacts
				</a>          
				<a href="/notes" class="btn btn-default" data-pjax="#main" data-title="Notes">
					<i class="fa fa-2x fa-clipboard"></i>
					Notes
				</a>          
				<a href="/charts" class="btn btn-default" data-pjax="#main" data-title="Charts">
					<i class="fa fa-2x fa-bar-chart-o"></i>
					Charts
				</a>          
				<a href="/settings" class="btn btn-default" data-pjax="#main" data-title="Settings">
					<i class="fa fa-2x fa-gears"></i>
					Settings
				</a>          
			</div>
		</div>
	</div> 

  <!-- Load the required JavaScript libraries -->
  <script src="//maxcdn.bootstrapcdn.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
  <script src="//cdnjs.cloudflare.com/ajax/libs/jqueryui/1.10.4/jquery-ui.min.js"></script>

  <script src="//cdnjs.cloudflare.com/ajax/libs/jquery.pjax/1.9.2/jquery.pjax.min.js"></script>

  <script src="//cdnjs.cloudflare.com/ajax/libs/fastclick/1.0.3/fastclick.min.js"></script>

  <script src="//cdnjs.cloudflare.com/ajax/libs/raphael/2.1.2/raphael-min.js"></script>
  <script src="//cdnjs.cloudflare.com/ajax/libs/morris.js/0.5.1/morris.min.js"></script>

  <!-- Bootcards JS file -->
  <script src="//cdnjs.cloudflare.com/ajax/libs/bootcards/1.1.2/js/bootcards.min.js"></script> 
  
  <script src="/bootcards-demo-app/js/bootcards-demo-app.js"></script> 

	<!--modals-->
	<div class="modal fade" id="editModal" tabindex="-1" role="dialog" aria-labelledby="editModal" aria-hidden="true">
		<div class="modal-dialog">
			<div class="modal-content"></div>
		</div>
	</div>
  <div class="modal fade" id="docsModal" tabindex="-1" role="dialog" aria-labelledby="docsModal" aria-hidden="true">
    <div class="modal-dialog">
      <div class="modal-content"></div>
    </div>
  </div>
  
  <script type="text/javascript">
    //highlight first list group option (if non active yet)
    if ( $('.list-group a.active').length === 0 ) {
      $('.list-group a').first().addClass('active');
    }

    bootcards.init( {
        offCanvasHideOnMainClick : true,
        offCanvasBackdrop : true,
        enableTabletPortraitMode : true,
        disableRubberBanding : true,
        disableBreakoutSelector : 'a.no-break-out'
      });

  </script>

</body>
</html>

