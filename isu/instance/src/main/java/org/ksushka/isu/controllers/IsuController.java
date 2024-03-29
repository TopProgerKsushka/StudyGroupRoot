package org.ksushka.isu.controllers;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.ksushka.isu.CheckService;
import org.ksushka.isu.messages.IntegerResponse;
import org.ksushka.isu.model.FormOfEducation;
import org.ksushka.isu.model.StudyGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;

import javax.net.ssl.SSLContext;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/isu")
public class IsuController {

    @Value("${config.group-service.port}")
    private int groupPort;

    @Value("${trust.store}")
    private Resource trustStore;
    @Value("${trust.store-password}")
    private String trustStorePassword;
    //SSLContexts.custom()

    @Autowired
    CheckService checkService;

    @GetMapping("hello")
    public String hello() {
        return "group-port: " + groupPort;
    }

    RestTemplate restTemplate() throws Exception {
        SSLContext sslContext = new SSLContextBuilder()
                .loadTrustMaterial(trustStore.getURL(), trustStorePassword.toCharArray(), new TrustSelfSignedStrategy())
                .build();
        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext);
        HttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(socketFactory)
                .build();
        HttpComponentsClientHttpRequestFactory factory =
                new HttpComponentsClientHttpRequestFactory(httpClient);
        RestTemplate restTemplate = new RestTemplate(factory);

//        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
//        interceptors.add(new LoggingRequestInterceptor());
//        restTemplate.setInterceptors(interceptors);

        return restTemplate;
    }

    @PutMapping(value = "group/{id}/form", produces = MediaType.APPLICATION_XML_VALUE)
    public ResponseEntity<StudyGroup> changeForm(@PathVariable Integer id,
                                                 @RequestParam FormOfEducation form) throws Exception {
        try {
            RestTemplate rt = restTemplate();
            String url = "https://localhost:" + groupPort + "/api/group/{id}";
            StudyGroup studyGroup = rt.getForObject(url, StudyGroup.class, id);
            if (studyGroup == null) return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

            studyGroup.setFormOfEducation(form);
            studyGroup.setCreationDate(null);
            studyGroup.setId(null);

            RestTemplate rt1 = restTemplate();
            url = "https://localhost:" + groupPort + "/api/group/{id}";
            HttpHeaders headers = new HttpHeaders();
            List<MediaType> mt = new ArrayList<>(1);
            mt.add(MediaType.APPLICATION_XML);
            headers.setAccept(mt);
            headers.setContentType(MediaType.APPLICATION_XML);
            HttpEntity<StudyGroup> entity = new HttpEntity<>(studyGroup, headers);
            ResponseEntity<StudyGroup> resp = rt1.exchange(url, HttpMethod.PUT, entity, StudyGroup.class, id);

            if (!resp.getStatusCode().equals(HttpStatus.OK)) return new ResponseEntity<>(resp.getStatusCode());
            if (resp.getBody() == null) return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);

            return new ResponseEntity<>(resp.getBody(), HttpStatus.OK);
        } catch (HttpClientErrorException ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (RestClientException ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("statistics/count_expelled")
    public ResponseEntity<Integer> countExpelled() throws Exception {
        try {
            RestTemplate rt = restTemplate();
            final String url = "https://localhost:" + groupPort + "/api/count_expelled";
            ResponseEntity<IntegerResponse> resp = rt.getForEntity(url, IntegerResponse.class);

            //if (resp == null) return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            if (!resp.getStatusCode().equals(HttpStatus.OK)) return new ResponseEntity<>(resp.getStatusCode());
            if (resp.getBody() == null) return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);

            return new ResponseEntity<>(resp.getBody().integer, HttpStatus.OK);
        } catch (HttpClientErrorException ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (RestClientException ex) {
            ex.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
