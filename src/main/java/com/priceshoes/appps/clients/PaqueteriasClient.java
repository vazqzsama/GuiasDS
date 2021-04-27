package com.priceshoes.appps.clients;

import java.text.Normalizer;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.priceshoes.appps.request.PaqueteriasRequest;
import com.priceshoes.appps.response.PaqueteriasReponse;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource.Builder;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;

@Service
public class PaqueteriasClient implements WsClient {

	private static Logger log = Logger.getLogger(PaqueteriasClient.class);
	@Value("${wsPaq.url}")
	private String wsUrl;
	@Value("${wsPaq.user}")
	private String wsUser;
	@Value("${wsPaq.pass}")
	private String wsPass;
	
	@Override
	public String getAuthorization() {
		return new StringBuilder("Basic ").append(Base64.getEncoder().encodeToString(
			new StringBuilder(wsUser).append(":").append(wsPass).toString().getBytes())).toString();
	}

	@Override
	public String getEndPoint() {
		return wsUrl;
	}

	@Override
	public PaqueteriasReponse send(PaqueteriasRequest request) {
		ClientConfig clientConfig = new DefaultClientConfig();
		Client client = Client.create(clientConfig);
		try {
            Builder webResource = client.resource(this.getEndPoint()).header("Authorization",this.getAuthorization());
            ClientResponse response = webResource.type("application/json").post(ClientResponse.class,
            		Normalizer.normalize(new Gson().toJson( request ), Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", ""));
            
            log.info("Status response: "+response.getStatus());
            if (HttpStatus.valueOf(response.getStatus()).is3xxRedirection())
            	throw new Error("Ocurrio un error (Redireccion): "+response.getStatus()+" -> "+response.getEntity(String.class));
            else if (HttpStatus.valueOf(response.getStatus()).is4xxClientError())
            	throw new Error("Ocurrio un error (Cliente): "+response.getStatus()+" -> "+response.getEntity(String.class));
            else if (HttpStatus.valueOf(response.getStatus()).is5xxServerError())
            	throw new Error("Ocurrio un error en el servidor: "+response.getStatus()+" -> "+response.getEntity(String.class));
            
        	return new Gson().fromJson(response.getEntity(String.class),PaqueteriasReponse.class);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return null;
		} finally {
			client.destroy();
		}
		
	}

}
