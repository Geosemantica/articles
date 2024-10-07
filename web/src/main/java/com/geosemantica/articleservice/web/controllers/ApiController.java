package com.geosemantica.articleservice.web.controllers;

import com.geosemantica.articleservice.web.config.annotations.AllowForAll;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@AllowForAll
@Controller("/")
public class ApiController {
    @Value("classpath:/openapi/swagger.yaml")
    private Resource api;

    @Value("classpath:/openapi/index.html")
    private Resource index;

    @Value("classpath:/openapi/rapidoc.html")
    private Resource rapidoc;

    @ResponseBody
    @GetMapping(value = {"swagger.yaml"}, produces = "text/yaml")
    public Resource getAccountApi() {
        return api;
    }

    @ResponseBody
    @GetMapping(value = {"/", "index.html", "index.htm"}, produces = MediaType.TEXT_HTML_VALUE)
    public Resource getIndex() {
        return index;
    }

    @ResponseBody
    @GetMapping(value = {"/rapidoc/", "rapidoc.html"}, produces = MediaType.TEXT_HTML_VALUE)
    public Resource getRapidoc() {
        return rapidoc;
    }
}
