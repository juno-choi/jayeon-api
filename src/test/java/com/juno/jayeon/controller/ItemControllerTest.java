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
    @DisplayName("임시 테스트")
    void test() throws Exception{
        //given
        //when
        ResultActions act = mock.perform(MockMvcRequestBuilders.get("/v1/items").contentType(MediaType.APPLICATION_JSON));
        //then
        act.andExpect(MockMvcResultMatchers.jsonPath("$.code").value("200"));

        //docs
        act.andDo(document("test",
                preprocessRequest(prettyPrint()),   //request json 형식으로 이쁘게
                preprocessResponse(prettyPrint()),   //response json 형식으로 이쁘게
                /*requestFields(   //request param

                ),*/
                responseFields(  //response param
                        fieldWithPath("code").type(JsonFieldType.STRING).description("결과 코드"),
                        fieldWithPath("msg").type(JsonFieldType.STRING).description("메세지"),
                        fieldWithPath("data.[].idx").type(JsonFieldType.NUMBER).description("상품 IDX"),
                        fieldWithPath("data.[].name").type(JsonFieldType.STRING).description("상품 이름"),
                        fieldWithPath("data.[].price").type(JsonFieldType.NUMBER).description("상품 가격"),
                        fieldWithPath("data.[].options.[].idx").type(JsonFieldType.NUMBER).description("상품 옵션 IDX"),
                        fieldWithPath("data.[].options.[].kg").type(JsonFieldType.NUMBER).description("상품 무게 KG 값"),
                        fieldWithPath("data.[].options.[].name").type(JsonFieldType.STRING).description("상품 옵션 이름"),
                        fieldWithPath("data.[].options.[].price").type(JsonFieldType.NUMBER).description("상품 옵션 가격")
                )
        ));
    }
}