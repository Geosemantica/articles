package com.geosemantica.articleservice.web.mixins;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.geosemantica.articleservice.facades.model.CommentFacade;

@JsonSerialize(as = CommentFacade.class)
public interface CommentMixin {
}
