package org.ksushka.client;

import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.ksushka.client.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class FrontendController {
    @Value("${group.port}")
    private int groupPort;

    @Value("${isu.port}")
    private int isuPort;

    @Autowired
    CheckService checkService;

    @Value("${trust.store}")
    private Resource trustStore;
    @Value("${trust.store.password}")
    private String trustStorePassword;

    RestTemplate restTemplate() throws Exception {
        SSLContext sslContext = new SSLContextBuilder()
                .loadTrustMaterial(trustStore.getURL(), trustStorePassword.toCharArray())
                .build();
        SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext);
        HttpClient httpClient = HttpClients.custom()
                .setSSLSocketFactory(socketFactory)
                .build();
        HttpComponentsClientHttpRequestFactory factory =
                new HttpComponentsClientHttpRequestFactory(httpClient);
        return new RestTemplate(factory);
    }

    @GetMapping("/")
    public String index(HttpSession s, ModelMap m, @RequestParam(required = false) Object skipsplash) {
        if (s.getAttribute("splashCanBeSkipped") != null) m.put("splashCanBeSkipped", true);
        m.put("skipsplash", skipsplash);
        return "index";
    }

    @PostMapping("/splash-can-be-skipped")
    public @ResponseBody String splashCanBeSkipped(HttpSession s) {
        s.setAttribute("splashCanBeSkipped", true);
        return "ok";
    }

    @PostMapping(value = "/get")
    public @ResponseBody FrontendResult get(@RequestParam(required = false) Integer id) throws Exception {
        if (id == null) {
            return FrontendResult.balloon("???????????????????? ?????????????? ID");
        }
        RestTemplate rt = restTemplate();
        final String url = "https://localhost:" + groupPort + "/api/group/get?id={id}";

        try {
            ResponseEntity<StudyGroup> resp = rt.getForEntity(url, StudyGroup.class, id);
            if (resp.getBody() == null) {
                return FrontendResult.balloon("???????????? ?????????? ???? API");
            }
            List<StudyGroup> groups = new ArrayList<>(1);
            groups.add(resp.getBody());
            return FrontendResult.show(groups);
        } catch (HttpClientErrorException ex) {
            return FrontendResult.balloon("???????????? ?? ?????????????????? ID ???? ????????????????????");
        } catch (RestClientException ex) {
            return FrontendResult.balloon("???? ?????????????? ???????????????? ???????????? ?? API");
        }
    }
    
    @PostMapping("/getAll")
    public @ResponseBody FrontendResult getAll(@RequestParam(required = false) Integer pageNumber,
                         @RequestParam(required = false) Integer pageSize,
                         @RequestParam(required = false) String sortType,
                         @RequestParam(required = false, defaultValue = "id") String orderBy,
                         @RequestParam(required = false) String filterBy) throws Exception {
        RestTemplate rt = restTemplate();
        final String url = "https://localhost:" + groupPort + "/api/group/getAll?" +
                "pageNumber={pageNumber}&pageSize={pageSize}&sortType={sortType}&orderBy={orderBy}&filterBy={filterBy}";
        try {
            ResponseEntity<StudyGroup[]> resp = rt.getForEntity(url, StudyGroup[].class, pageNumber, pageSize, sortType, orderBy, filterBy);
            if (resp.getBody() == null) {
                return FrontendResult.balloon("???????????? ?????????? ???? API");
            }

            return FrontendResult.show(Arrays.stream(resp.getBody()).toList());
        } catch (HttpClientErrorException ex) {
            return FrontendResult.balloon("??????-???? ???? ?????? ?? ?????????????? ??????????");
        } catch (RestClientException ex) {
            return FrontendResult.balloon("???? ?????????????? ???????????????? ???????????? ?? API");
        }
    }

    @PostMapping("/delete")
    public @ResponseBody FrontendResult delete(@RequestParam(required = false) Integer id) throws Exception {
        if (id == null) {
            return FrontendResult.balloon("???????????????????? ?????????????? ID");
        }
        RestTemplate rt = restTemplate();
        final String url = "https://localhost:" + groupPort + "/api/group/delete?id={id}";
        try {
            rt.delete(url, id);
            return FrontendResult.goHome();
        } catch (HttpClientErrorException ex) {
            System.out.println(ex.getStatusCode());
            return FrontendResult.balloon("???????????? ?? ?????????????????? ID ???? ????????????????????");
        } catch (RestClientException ex) {
            return FrontendResult.balloon("???? ?????????????? ???????????????? ???????????? ?? API");
        }
    }

    @PostMapping("/put")
    public @ResponseBody FrontendResult doPut(@RequestParam Integer id, @RequestParam String name,
                        @RequestParam (name = "coordinates.x") Integer coordinatesX,
                        @RequestParam (name = "coordinates.y") Float coordinatesY,
                        @RequestParam Integer studentsCount, @RequestParam FormOfEducation formOfEducation,
                        @RequestParam Semester semesterEnum,
                        @RequestParam (required = false, name = "groupAdmin.name") String groupAdminName,
                        @RequestParam (required = false, name = "groupAdmin.passportID") String groupAdminPassportID,
                        @RequestParam (required = false, name = "groupAdmin.eyeColor") Color groupAdminEyeColor,
                        @RequestParam (required = false, name = "groupAdmin.hairColor") Color groupAdminHairColor,
                        @RequestParam (required = false, name = "groupAdmin.nationality") Country groupAdminNationality
    ) throws Exception {
        if (id == null) {
            return FrontendResult.balloon("???????????????????? ?????????????? ID");
        }
        Coordinates coordinates = new Coordinates(coordinatesX, coordinatesY);
        Person groupAdmin = new Person(groupAdminName, groupAdminPassportID, groupAdminEyeColor,
                groupAdminHairColor, groupAdminNationality);
        StudyGroup group = new StudyGroup(name, coordinates, studentsCount, formOfEducation, semesterEnum);

        if (!checkService.studyGroup(group)) {
            return FrontendResult.balloon("??????-???? ???? ?????? ?? ?????????????? ??????????");
        }
        if (checkService.person(groupAdmin)) group.setGroupAdmin(groupAdmin);
        else group.setGroupAdmin(null);

        RestTemplate rt = restTemplate();
        final String url = "https://localhost:" + groupPort + "/api/group/put?id={id}";

        try {
            HttpHeaders headers = new HttpHeaders();
            List<MediaType> mt = new ArrayList<>(1);
            mt.add(MediaType.APPLICATION_XML);
            headers.setAccept(mt);
            headers.setContentType(MediaType.APPLICATION_XML);
            HttpEntity<StudyGroup> entity = new HttpEntity<>(group, headers);
            ResponseEntity<StudyGroup> resp = rt.exchange(url, HttpMethod.PUT, entity, StudyGroup.class, id);

            if (resp.getBody() == null) {
                return FrontendResult.balloon("???????????? ?????????? ???? API");
            }
            return FrontendResult.goHome();
        } catch (HttpClientErrorException ex) {
            return FrontendResult.balloon("?????????????????????? ???????????? API");
        } catch (RestClientException ex) {
            return FrontendResult.balloon("???? ?????????????? ???????????????? ???????????? ?? API");
        }
    }

    @PostMapping(value = "/add")
    public @ResponseBody FrontendResult doAdd(@RequestParam String name,
                        @RequestParam (required = false, name = "coordinates.x") Integer coordinatesX,
                        @RequestParam (required = false, name = "coordinates.y") Float coordinatesY,
                        @RequestParam (required = false) Integer studentsCount,
                        @RequestParam (required = false) FormOfEducation formOfEducation,
                        @RequestParam (required = false) Semester semesterEnum,
                        @RequestParam (required = false, name = "groupAdmin.name") String groupAdminName,
                        @RequestParam (required = false, name = "groupAdmin.passportID") String groupAdminPassportID,
                        @RequestParam (required = false, name = "groupAdmin.eyeColor") Color groupAdminEyeColor,
                        @RequestParam (required = false, name = "groupAdmin.hairColor") Color groupAdminHairColor,
                        @RequestParam (required = false, name = "groupAdmin.nationality") Country groupAdminNationality
                        ) throws Exception {

        if (coordinatesX == null || coordinatesY == null) return FrontendResult.balloon("??????-???? ???? ?????? ?? ?????????????? ??????????");

        Coordinates coordinates = new Coordinates(coordinatesX, coordinatesY);
        Person groupAdmin = new Person(groupAdminName, groupAdminPassportID, groupAdminEyeColor,
                groupAdminHairColor, groupAdminNationality);
        StudyGroup group = new StudyGroup(name, coordinates, studentsCount, formOfEducation, semesterEnum);

        if (!checkService.studyGroup(group)) {
            return FrontendResult.balloon("??????-???? ???? ?????? ?? ?????????????? ??????????");
        }
        if (checkService.person(groupAdmin)) group.setGroupAdmin(groupAdmin);
        else group.setGroupAdmin(null);

        RestTemplate rt = restTemplate();
        final String url = "https://localhost:" + groupPort + "/api/group/add";

        try {
            HttpHeaders headers = new HttpHeaders();
            List<MediaType> mt = new ArrayList<>(1);
            mt.add(MediaType.APPLICATION_XML);
            headers.setAccept(mt);
            headers.setContentType(MediaType.APPLICATION_XML);
            HttpEntity<StudyGroup> entity = new HttpEntity<>(group, headers);
            ResponseEntity<StudyGroup> resp = rt.exchange(url, HttpMethod.POST, entity, StudyGroup.class);

            if (resp.getBody() == null) {
                return FrontendResult.balloon("???????????? ?????????? ???? API");
            }
            return FrontendResult.goHome();
        } catch (HttpClientErrorException ex) {
            return FrontendResult.balloon("?????????????????????? ???????????? API");
        } catch (RestClientException ex) {
            return FrontendResult.balloon("???? ?????????????? ???????????????? ???????????? ?? API");
        }
    }

    @PostMapping("/count_higher_semester")
    public @ResponseBody FrontendResult countHigherSemester(@RequestParam Semester semesterEnum) throws Exception {
        RestTemplate rt = restTemplate();
        final String url = "https://localhost:" + groupPort + "/api/group/count_higher_semester?semester={semester}";
        try {
            ResponseEntity<Integer> resp = rt.getForEntity(url, Integer.class, semesterEnum);
            if (resp.getBody() == null) return FrontendResult.balloon("???????????? ?????????? ???? API");
            return FrontendResult.number(resp.getBody());
        } catch (HttpClientErrorException ex) {
            return FrontendResult.balloon("?????????????????????? ???????????? API");
        } catch (RestClientException ex) {
            return FrontendResult.balloon("???? ?????????????? ???????????????? ???????????? ?? API");
        }
    }

    @PostMapping("/get_starts_from")
    public @ResponseBody FrontendResult getStartsFrom(@RequestParam String prefix) throws Exception {
        RestTemplate rt = restTemplate();
        final String url = "https://localhost:" + groupPort + "/api/group/get_starts_from?prefix={prefix}";
        try {
            ResponseEntity<StudyGroup[]> resp = rt.getForEntity(url, StudyGroup[].class, prefix);
            if (resp.getBody() == null) return FrontendResult.balloon("???????????? ?????????? ???? API");
            return FrontendResult.show(Arrays.stream(resp.getBody()).toList());
        } catch (HttpClientErrorException ex) {
            return FrontendResult.balloon("?????????????????????? ???????????? API");
        } catch (RestClientException ex) {
            return FrontendResult.balloon("???? ?????????????? ???????????????? ???????????? ?? API");
        }
    }

    @PostMapping("/get_unique_forms")
    public @ResponseBody FrontendResult getUniqueForms(ModelMap m) throws Exception {
        RestTemplate rt = restTemplate();
        final String url = "https://localhost:" + groupPort + "/api/group/get_unique_forms";
        try {
            ResponseEntity<FormOfEducation[]> resp = rt.getForEntity(url, FormOfEducation[].class);
            FormOfEducation[] forms = resp.getBody();
            if (forms == null) return FrontendResult.balloon("???????????? ?????????? ???? API");
            List<String> formsList = Arrays.stream(forms).map(Enum::toString).toList();
            return FrontendResult.list(formsList);
        } catch (HttpClientErrorException ex) {
            return FrontendResult.balloon("?????????????????????? ???????????? API");
        } catch (RestClientException ex) {
            return FrontendResult.balloon("???? ?????????????? ???????????????? ???????????? ?? API");
        }
    }
    @PostMapping("/count_expelled")
    public @ResponseBody FrontendResult countExpelled() throws Exception {
        RestTemplate rt = restTemplate();
        final String url = "https://localhost:" + isuPort + "/api/isu/statistics/count_expelled";
        try {
            ResponseEntity<Integer> resp = rt.getForEntity(url, Integer.class);
            if (resp.getBody() == null) return FrontendResult.balloon("???????????? ?????????? ???? API");
            return FrontendResult.number(resp.getBody());
        } catch (HttpClientErrorException ex) {
            return FrontendResult.balloon("?????????????????????? ???????????? API");
        } catch (RestClientException ex) {
            return FrontendResult.balloon("???? ?????????????? ???????????????? ???????????? ?? API");
        }
    }

    @PostMapping("change_edu_form")
    public @ResponseBody FrontendResult changeEduForm(@RequestParam FormOfEducation formOfEducation,
                                @RequestParam(required = false) Integer id) throws Exception {
        if (id == null) {
            return FrontendResult.balloon("???????????????????? ?????????????? ID");
        }
        RestTemplate rt = restTemplate();
        final String url = "https://localhost:" + isuPort + "/api/isu/group/change_edu_form?id={id}&eduForm={formOfEducation}";
        try {
            ResponseEntity<StudyGroup> resp = rt.exchange(url, HttpMethod.PUT, HttpEntity.EMPTY, StudyGroup.class, id, formOfEducation);
            return FrontendResult.goHome();
        } catch (HttpClientErrorException ex) {
            System.out.println(ex.getStatusCode());
            return FrontendResult.balloon("???????????? ?? ?????????????????? ID ???? ????????????????????");
        } catch (RestClientException ex) {
            return FrontendResult.balloon("???? ?????????????? ???????????????? ???????????? ?? API");
        }
    }
}

