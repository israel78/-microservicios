package com.formacionbdispringboot.app.zuul.filters;

import javax.servlet.http.HttpServletRequest; 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

@Component
public class PostTiempoTranscurridoFilter extends ZuulFilter {
	
	private static Logger log = LoggerFactory.getLogger(PostTiempoTranscurridoFilter.class);

	@Override
	public boolean shouldFilter() {
		//Se puede establecer cuando se ejecuta el filtro dando true o false dependiendo de variables y demas...
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		//Logica del filtro
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest r = ctx.getRequest();
		log.info("Entrando a post filter");

		Long inicio =(Long) r.getAttribute("tiempoInicio");
		Long tiempoFinal = System.currentTimeMillis();
		Long tiempoTranscurrido =  tiempoFinal - inicio;
		
		log.info(String.format("Tiempo transcurrido en segundos %s", tiempoTranscurrido.doubleValue()/1000.00));
		log.info(String.format("Tiempo transcurrido en milisegundos %s", tiempoTranscurrido));
		return null;
	}

	@Override
	public String filterType() {
//		Los filtros pueden ser de tres tipos
//		pre filters run before the request is routed.
//		route filters can handle the actual routing of the request.
//		post filters run after the request has been routed.
//		error filters run if an error occurs in the course of handling the request.

		return "post";
	}

	@Override
	public int filterOrder() {
		return 1;
	}
	

}
