package com.juno.jayeon.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;

@SpringBootTest
@AutoConfigureMockMvc   //mock test를 위한 설정
@AutoConfigureRestDocs(uriHost = "jayeonapple.com") //rest docs 설정
@Transactional(readOnly = true)
class ItemControllerTest {
    @Autowired
    private MockMvc mock;

    @Test
    void item_불러오기() throws Exception{
        //given
        //when
        ResultActions act = mock.perform(MockMvcRequestBuilders.get("/v1/items").contentType(MediaType.APPLICATION_JSON));
        //then
        act.andExpect(MockMvcResultMatchers.jsonPath("$.code").value("200"));

        //docs
        act.andDo(document("item_get",
                preprocessRequest(prettyPrint()),   //request json 형식으로 이쁘게
                preprocessResponse(prettyPrint()),   //response json 형식으로 이쁘게
                /*requestFields(   //request param

                ),*/
                responseFields(  //response param
                        beneathPath("data").withSubsectionId("data"),
                        fieldWithPath("idx").type(JsonFieldType.NUMBER).description("상품 IDX"),
                        fieldWithPath("name").type(JsonFieldType.STRING).description("상품 이름"),
                        fieldWithPath("price").type(JsonFieldType.NUMBER).description("상품 가격"),
                        fieldWithPath("options.[].idx").type(JsonFieldType.NUMBER).description("상품 옵션 IDX"),
                        fieldWithPath("options.[].kg").type(JsonFieldType.NUMBER).description("상품 무게 KG 값"),
                        fieldWithPath("options.[].name").type(JsonFieldType.STRING).description("상품 옵션 이름"),
                        fieldWithPath("options.[].price").type(JsonFieldType.NUMBER).description("상품 옵션 가격")
                )
        ));
    }
}