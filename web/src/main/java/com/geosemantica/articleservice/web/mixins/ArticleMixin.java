package com.geosemantica.articleservice.web.mixins;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.geosemantica.articleservice.facades.model.ArticleFacade;

@JsonSerialize(as = ArticleFacade.class)
public interface ArticleMixin {

}
