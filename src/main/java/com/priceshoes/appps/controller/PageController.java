package com.priceshoes.appps.controller;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.google.gson.Gson;
import com.priceshoes.appps.request.AppRequest;
import com.priceshoes.appps.request.PageRequest;
import com.priceshoes.appps.response.AppResponse;
import com.priceshoes.appps.service.AppService;
import com.priceshoes.appps.util.AppInfo;

@Controller
@RequestMapping(method = RequestMethod.GET,produces=MediaType.APPLICATION_JSON_UTF8_VALUE)
public class PageController 
{
	private static final Logger log = Logger.getLogger(PageController.class);
	
	@Autowired	private AppInfo 	appInfo;
	@Autowired	private	AppService  appService;
	
	@RequestMapping(value="/")
	public String index(ModelMap map, @ModelAttribute PageRequest request )
	{
		log.debug("home");
		log.debug("Probando el servicio de test desde el home ");
		try
		{
			AppResponse response =  appService.testConexion(new AppRequest());
			map.put("id",			appInfo.getId());
			map.put("name",			appInfo.getName());
			map.put("desc",			appInfo.getDesc() );
			map.put("version",		appInfo.getVersion() );
			map.put("release",		appInfo.getRelease() );
			map.put("profile",		appInfo.getProfile() );
			map.put("developer",	appInfo.getDeveloper() );
			map.put("status", 		response.getStatus());
			map.put("message", 		response.getMessage());
		}
		catch(Exception e)
		{
			log.error(e.getMessage());
		}
		
		return "home";
	}
	
	@RequestMapping(value="/page/resources")
	public String resources(ModelMap map, @ModelAttribute PageRequest request )
	{
		map.addAttribute("resourcesUrl", appInfo.getResources());
		return "resources";
	}
	
	@RequestMapping(value="/page/{page}")
	public String aplicativos(@PathVariable("page") String page, @ModelAttribute PageRequest request, ModelMap map )
	{
		log.debug(new Gson().toJson(request));
		log.debug(" Pagina " +  page);
		
		map.addAttribute("resourcesUrl", appInfo.getResources());
		return page+"/"+page;
	}
	
	@RequestMapping(value="/page/form/{menu}/{form}",method = RequestMethod.POST)
	public String form(	@PathVariable("form") String  form, 
						@PathVariable("menu") String  menu, 
						@RequestBody PageRequest request,
						ModelMap map)
	{
		log.debug("Menu " + menu +  " Form: "+ form +" : " +new Gson().toJson(request));
		
		map.addAttribute("resourcesUrl", appInfo.getResources());
		map.addAttribute("object",request);
		
		return menu+"/"+form;
	}
	@RequestMapping(value="/page/form/{menu}/{grupo}/{form}",method = RequestMethod.POST)
	public String groupForm(
			@PathVariable("form")  String form, 
			@PathVariable("menu")  String menu, 
			@PathVariable("grupo") String grupo,
			ModelMap map, @RequestBody PageRequest request)
	{
		log.debug("Menu " + menu +  " Grupo: "+grupo+"  Form: "+ form +" : " +new Gson().toJson(request));
		
		map.addAttribute("resourcesUrl", appInfo.getResources());
		map.addAttribute("object",request);
		
		return menu+"/"+grupo+"/"+form;
	}
}
