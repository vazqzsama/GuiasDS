<%@ page language="java" contentType="text/html; charset=UTF-8"    pageEncoding="UTF-8"%>
<style>
table.tableAyuda
{
	border: solid 1px black;
	width: 100%;
}

td.tableAyuda
{
	border: solid 1px black;
	padding: 5px;
}
.lbl-0
{
 	color: #004B85; 
 	font-weight: bold; 
}
.help-title 
{
   color: #004B85;
   font-size:1.1em;
   font-weight: bold;
   background-color: #E0E0E0;
   padding: 3px 0px 2px 10px;
}
.current > a
{
	font-weight: bold;
	color:#DA0080;
	font-size: 1.0em;
}
.temario-nav > li > a
{
	padding-top: 4px;
	padding-bottom: 4px;
	outline: none;
}
.temario-nav > li > a:FOCUS
{
	background-color: #f9f9f9;
}
.divHelpContent
{
	padding: 5px 5px 5px 10px;
}
.bs-sidebar.affix
{
	position: fixed;
	top:75px;
	width: 21%;
}
.hr-help-divider
{
	margin: 7px 0px;
}
.help-icon
{
	font-weight: normal;
	margin-right: 5px;
}
.p-justify
{
	text-align: justify;
	text-indent: 15px;
}
.help-img
{
	max-width: 100%; 
	border: solid 1px #DA0080;
}
.help-div-img-container
{
	overflow: auto;
	text-align: center;
}
</style>
<script type="text/javascript">
var help={};

(function()
{
	var navTopHeight = 50;
	var $temarioNav = $("#temarioNav");
	var $container = $("#divHelpMainContainer");
	var baseDir="ayuda/";
	var baseId="entry-";
	function renderStickyHeader(event)
	{
		try
		{
			var div_id = "divTemario";
    		var div = event.data;
	         	         
	        if (div.length < 1 || $('#'+div_id).length < 1)
	        {
                $(window).off('scroll.'+div_id);
                return;
            }
	        
	        var top = $(window).scrollTop();
	        
	        if( top > navTopHeight )   	
	        	div.addClass("affix");
	        else        	
	        	div.removeClass("affix");
	        
		}
		catch(e){}
	}
	
	function setOnePageNave(config)
	{
		$temarioNav.onePageNav
		({
			scrollSpeed:250
		});
		
		if(config.collapsible)
		{
			$("[id^='link-help-']").click(function()
			{
				$this = $(this);
				var bloque = $this.data("bloque");
				if( !$("#bloque-"+bloque+"-container").hasClass("collapse in") )
				{
					$("#bloque-"+bloque+"-container").collapse("show");
					
					if( $this.data("level") > 0 )
					{
						$this.parent().removeClass("current");
						$this[0].click();
						$this.parent().addClass("current");
					}
				}	
			});			
		}		
	}
	
	function loadPages(config)
	{
		var bloque = 0;
		var entrada = 0;
		
		$.each(config.items,function(i,item)
		{
			if(item!=null)
			{
				if(item.level==0) bloque++;
				else entrada++;	
				
				try{
					var ref = item.level==0 ? 'bloque-'+bloque : baseId+entrada;
					var loadFromSystem = item.system!=undefined ? item.system : false;  
						
					if( item.form!=null && item.form!=undefined && item.form.length>0)
					return system.getForm
						({
							url:baseDir+item.form,
							system:loadFromSystem,
							defaultFail:false
						}).done(function(content)
						{ 
							$("#"+ref+"-contenido").prepend(content); 
						}).fail(function()
						{
							$("#"+ref+"-contenido").prepend(config.onLoadPageErrorText);
						});
				}catch(e){}
			}
		});
	}
	
	function setTemplate(config)
	{
		$container.html("");
		$temarioNav.html("");
		
		var bloque = 0;
		var entrada = 0;
		var lastBloque = 0;		
		var lastOwner=null;
		
		$.each(config.items,function(i,item)
		{
			if(item!=null)
			{
				if(item.level==0) bloque++;
				else entrada++;
				
				var ref = item.level==0 ? 'bloque-'+bloque : baseId+entrada;
				var indent = config.indent * item.level;
				var icon = item.cssIcon!=undefined && item.cssIcon.length>0 ? '<i class="help-icon '+item.cssIcon+'"></i>':'';
				var preTitle = item.preTitle != undefined ? item.preTitle :'';
				var itemClass= item.cssClass!=undefined ? item.cssClass : "";
				var entry = "<a id='link-help-"+ref+"' data-bloque='"+bloque+"' class='"+itemClass+"' style='text-indent:"+indent+"px' href='#"+ref+"'>"+icon+preTitle+item.title+"</a>";
				
				$temarioNav.append(" <li>"+entry+"</li> ");
				
				if(item.level==0)
				{
					if(config.collapsible)
					{
						$container.append(
						$('<div class="panel panel-blue">'+
								'<div id="'+ref+'" class="panel-heading clearfix" style="padding: 7px 10px 2px 15px;">'+
									'<div class="panel-title"  ><label>'+icon+item.title+'</label>'+
									'<div class="btn-group pull-right"><a data-toggle="collapse" href="#'+ref+'-container" style="outline: none;" >'+
										'<i class="fa fa-chevron-circle-down fa-lg" style="color:#FFF;"></i>'+
									'</a></div>'+
									'</div>'+
								'</div>'+
								'<div id="'+ref+'-container"class="panel-body panel-collapse collapse in" ><div id="'+ref+'-contenido"></div>'+
							'</div>'));	
					}	
					else
					{
						$container.append(
						$('<div class="panel panel-blue">'+
								'<div id="'+ref+'" class="panel-heading" style="padding: 7px 10px 2px 15px;">'+
									'<div class="panel-title"  ><label>'+icon+item.title+'</label></div>'+
								'</div>'+
								'<div id="'+ref+'-container"class="panel-body panel-collapse collapse in"><div id="'+ref+'-contenido"></div>'+
							'</div>'));
					}	
					lastBloque = ref+'-container';
				}	
				else if(item.level==1)
				{
					$("#"+lastBloque).append(
						$('<div id="'+ref+'" class="help-title">'+icon+item.title+'</div>'+
						  '<div id="'+ref+'-contenido" class="divHelpContent"></div>'+
						  '<hr class="hr-help-divider">')
					 );
					lastOwner=ref;
				}
				else
				{
					$("#"+lastOwner+"-contenido").append(
							$('<div id="'+ref+'" class="help-title">'+icon+item.title+'</div>'+
							  '<div id="'+ref+'-contenido" class="divHelpContent"></div>'+
							  '<hr class="hr-help-divider">')
						 );
				}
			}	
		});
		
		return loadPages(config);
	}
	
	this.load = function(config)
	{
		var loading = psDialog.loading().open();
		
		$(window).on('scroll.divTemario', $("#divTemario"), renderStickyHeader);
		$.when( setTemplate(config) )
		.then(function()
		{
			setOnePageNave(config);	
		}).always(function(){ loading.close(); });
	};
	
}).apply(help);



$(document).ready(function()
{
	var config = 
	{
		indent:10,
		onLoadPageErrorText:"Contenido no disponible",
		collapsible:true,
		items:[]
	};
	
	var items= 
		{
			//<- paginas del adm (system) ->
	  		1:{level:0, system:true, cssIcon:"flaticon-config fa-lg", 		title:"Administración",		form:'help-administracion',	cssClass:"lbl-0"},
		  	2:{level:1, system:true, cssIcon:"fa fa-user-plus fa-lg",		title:"Perfiles",			form:"help-administracion-perfiles"},			  				
  			3:{level:1, system:true, cssIcon:"fa flaticon-user-key fa-lg",	title:"Permisos por perfil",form:"help-administracion-permisos-perfil"},	  		
			4:{level:1, system:true, cssIcon:"fa fa-users",					title:"Usuarios",			form:"help-administracion-usuarios"},		
			5:{level:1, system:true, cssIcon:"fa flaticon-buildings fa-lg",	title:"Sucursales",			form:"help-administracion-sucursales"},					
			//<- ------------------------------------------- ->
			10:{level:0, 	title:"Tema 1",		form:"ayuda-tema-1",cssClass:"lbl-0"},
			11:{level:1, 	preTitle:"&nbsp;&nbsp;-", title:"Subtema 1",	form:"ayuda-subtema-1"},
			12:{level:1, 	preTitle:"&nbsp;&nbsp;-", title:"Subtema 2",	form:""},			
	  };
	
	
	config.items.push(items[1],items[2],items[3],items[4],items[5],items[10],items[11],items[12]);
			
	help.load(config);
});
</script>

<div id="divHelpMainContainer" class="col-lg-9 col-md-12 col-sm-12 col-xs-12" 
	style="margin-bottom: 200px; font-size: 1.1em; padding-left: 0px; padding-right: 0px;">
	
</div>

<!-- barra de navegación -->
<div class="col-lg-3 padding-0">
	<div id="divTemario" class="bs-sidebar hidden-print hidden-sm hidden-xs hidden-md" style="height:83%; overflow: auto;">
		<ul id="temarioNav" class="temario-nav nav bs-sidenav">
		</ul>
	</div>
</div>

