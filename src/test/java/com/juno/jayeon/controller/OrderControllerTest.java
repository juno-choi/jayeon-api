package com.juno.jayeon.controller;

import com.google.gson.Gson;
import com.juno.jayeon.domain.entity.Order;
import com.juno.jayeon.domain.entity.OrderStatus;
import com.juno.jayeon.repository.OrderRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriHost = "jayeonapple.com")
@Transactional(readOnly = true)
class OrderControllerTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MockMvc mock;

    private final String API_URL = "http://jayeonapple.com";

    @Test
    @Transactional
    @DisplayName("order 검색")
    void 주문_검색() throws Exception{
        //given
        LocalDateTime day = LocalDateTime.of(2022,1,1,12,0,0);
        Order order = Order.builder()
                .buyer("tester")
                .buyerTel1("010")
                .buyerTel2("1234")
                .buyerTel3("5678")
                .recipient("수령자")
                .recipientTel1("010")
                .recipientTel2("1111")
                .recipientTel3("2222")
                .status(OrderStatus.BEFORE)
                .post1("1234")
                .post2("주소")
                .post3("상세주소")
                .regDate(day.toString())
                .request("테스트입니다.")
                .build();
        Order save = orderRepository.save(order);

        //when
        MultiValueMap<String, String> param = new LinkedMultiValueMap<>();
        param.add("buyer", "tester");
        param.add("sDate", "2022-01-01");
        param.add("eDate", "2022-01-02");
        param.add("orderStatus", "BEFORE");

        //then
        ResultActions action = mock.perform(MockMvcRequestBuilders.get(API_URL + "/v1/orders/search")
                .contentType(MediaType.APPLICATION_JSON)
                .params(param));

        action.andExpect(MockMvcResultMatchers.status().isOk());
        action.andExpect(MockMvcResultMatchers.jsonPath("$.code").value("200"));
        action.andExpect(MockMvcResultMatchers.jsonPath("$.data.[0].buyer").value("tester"));
        //docs
        action.andDo(document("orderSearch",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),

                requestParameters(
                        parameterWithName("buyer").description("구매자"),
                        parameterWithName("sDate").description("검색 시작 날짜"),
                        parameterWithName("eDate").description("검색 종료 날짜"),
                        parameterWithName("orderStatus").description("주문 상태 / "+OrderStatus.BEFORE+", "+OrderStatus.DEPOSIT+", "+OrderStatus.COMPLETE)
                ),
                responseFields(
                        fieldWithPath("code").type(JsonFieldType.STRING).description("결과 코드"),
                        fieldWithPath("msg").type(JsonFieldType.STRING).description("메세지"),
                        fieldWithPath("data.[].idx").type(JsonFieldType.NUMBER).description("주문 번호"),
                        fieldWithPath("data.[].itemList").type(JsonFieldType.ARRAY).description("상품 리스트"),
                        fieldWithPath("data.[].buyer").type(JsonFieldType.STRING).description("구매자"),
                        fieldWithPath("data.[].price").type(JsonFieldType.NUMBER).description("구매 가격"),
                        fieldWithPath("data.[].buyerTel1").type(JsonFieldType.STRING).description("구매자 연락처 1"),
                        fieldWithPath("data.[].buyerTel2").type(JsonFieldType.STRING).description("구매자 연락처 2"),
                        fieldWithPath("data.[].buyerTel3").type(JsonFieldType.STRING).description("구매자 연락처 3"),
                        fieldWithPath("data.[].recipient").type(JsonFieldType.STRING).description("수령자"),
                        fieldWithPath("data.[].recipientTel1").type(JsonFieldType.STRING).description("수령자 연락처 1"),
                        fieldWithPath("data.[].recipientTel2").type(JsonFieldType.STRING).description("수령자 연락처 2"),
                        fieldWithPath("data.[].recipientTel3").type(JsonFieldType.STRING).description("수령자 연락처 3"),
                        fieldWithPath("data.[].post1").type(JsonFieldType.STRING).description("수령 주소 우편번호"),
                        fieldWithPath("data.[].post2").type(JsonFieldType.STRING).description("수령 주소"),
                        fieldWithPath("data.[].post3").type(JsonFieldType.STRING).description("수령 주소 상세 주소"),
                        fieldWithPath("data.[].request").type(JsonFieldType.STRING).description("구매자 요청사항"),
                        fieldWithPath("data.[].status").type(JsonFieldType.STRING).description("상품 상태"),
                        fieldWithPath("data.[].regDate").type(JsonFieldType.STRING).description("구매일")
                )
        ));
    }

    @Test
    @Transactional
    @DisplayName("주문 Test")
    void 주문() throws Exception{
        //given
        Map<String, String> map = new HashMap<>();
        map.put("order", "[{\"item\" : \"1\", \"option\" : \"1\", \"ea\" : \"1\"}, {\"item\" : \"2\", \"option\" : \"2\", \"ea\" : \"2\"}]");
        map.put("buyer", "구매자");
        map.put("orderStatus","BEFORE");
        map.put("buyerTel1","010");
        map.put("buyerTel2","1111");
        map.put("buyerTel3","2222");
        map.put("recipient","수령자");
        map.put("recipientTel1","010");
        map.put("recipientTel2","3333");
        map.put("recipientTel3","4444");
        map.put("post1","1234");
        map.put("post2","주소");
        map.put("post3","상세 주소");
        map.put("request","테스트 입니다.");

        Gson gson = new Gson();
        String body = gson.toJson(map);

        //when
        ResultActions action = mock.perform(MockMvcRequestBuilders.post(API_URL + "/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body));
        //then
        action.andExpect(MockMvcResultMatchers.status().isOk());
        action.andExpect(MockMvcResultMatchers.jsonPath("$.data.orderIdx").isNotEmpty());
        //docs
        action.andDo(document("postOrder",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("order").type(JsonFieldType.STRING).description("주문 상품의 정보"),
                    fieldWithPath("buyer").type(JsonFieldType.STRING).description("구매자"),
                    fieldWithPath("orderStatus").type(JsonFieldType.STRING).description("주문의 결제 상태"),
                    fieldWithPath("buyerTel1").type(JsonFieldType.STRING).description("구매자 연락처 1"),
                    fieldWithPath("buyerTel2").type(JsonFieldType.STRING).description("구매자 연락처 2"),
                    fieldWithPath("buyerTel3").type(JsonFieldType.STRING).description("구매자 연락처 3"),
                    fieldWithPath("recipient").type(JsonFieldType.STRING).description("수령자"),
                    fieldWithPath("recipientTel1").type(JsonFieldType.STRING).description("수령자 연락처 1"),
                    fieldWithPath("recipientTel2").type(JsonFieldType.STRING).description("수령자 연락처 2"),
                    fieldWithPath("recipientTel3").type(JsonFieldType.STRING).description("수령자 연락처 3"),
                    fieldWithPath("post1").type(JsonFieldType.STRING).description("수령지 주소 1"),
                    fieldWithPath("post2").type(JsonFieldType.STRING).description("수령지 주소 2"),
                    fieldWithPath("post3").type(JsonFieldType.STRING).description("수령지 상세 주소"),
                    fieldWithPath("request").type(JsonFieldType.STRING).description("주문 요청 사항")
                ),
                responseFields(
                        fieldWithPath("code").type(JsonFieldType.STRING).description("결과 코드"),
                        fieldWithPath("msg").type(JsonFieldType.STRING).description("메세지"),
                        fieldWithPath("data.orderIdx").type(JsonFieldType.NUMBER).description("등록된 주문의 번호")
                )
        ));
    }
}