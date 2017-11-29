package org.sid.proxyservice.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collection;


@RestController
public class BourseGatewayRestService {

    @Autowired
    private RestTemplate restTemplate;

    //ça n'exite pas dans les videos : https://gist.github.com/RealDeanZhao/38821bc1efeb7e2a9bcd554cc06cdf96
    @Bean
    @LoadBalanced
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        // Do any additional configuration here
        return builder.build();
    }

    @RequestMapping(value = "/names")
    public Collection<Societe> listSocietes(){
    ParameterizedTypeReference<Resources<Societe>> responseType=
            new ParameterizedTypeReference<Resources<Societe>>() {  };
     return restTemplate
             .exchange("http://societe-service/societes",
                        HttpMethod.GET,null,responseType)
             .getBody()
             .getContent();
    }

}


class Societe{
    private Long id;
    private String nomSociete;

    public Long getId() {
        return id;
    }

    public String getNomSociete() {
        return nomSociete;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNomSociete(String nomSociete) {
        this.nomSociete = nomSociete;
    }
}