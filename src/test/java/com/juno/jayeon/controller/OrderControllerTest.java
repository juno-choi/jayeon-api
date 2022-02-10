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
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
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
import static org.springframework.restdocs.request.RequestDocumentation.*;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(uriHost = "jayeonapple.com")
@Transactional
class OrderControllerTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private MockMvc mock;

    @Test
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
        ResultActions action = mock.perform(MockMvcRequestBuilders.get("/v1/orders/search")
                .contentType(MediaType.APPLICATION_JSON)
                .params(param));

        action.andExpect(MockMvcResultMatchers.status().isOk());
        action.andExpect(MockMvcResultMatchers.jsonPath("$.code").value("200"));
        action.andExpect(MockMvcResultMatchers.jsonPath("$.data.[0].buyer").value("tester"));
        //docs
        action.andDo(document("order_search",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),

                requestParameters(
                        parameterWithName("buyer").description("구매자"),
                        parameterWithName("sDate").description("검색 시작 날짜"),
                        parameterWithName("eDate").description("검색 종료 날짜"),
                        parameterWithName("orderStatus").description("주문 상태 / "+OrderStatus.BEFORE+", "+OrderStatus.DEPOSIT+", "+OrderStatus.COMPLETE)
                ),
                responseFields(
                        beneathPath("data").withSubsectionId("data"),
                        fieldWithPath("idx").type(JsonFieldType.NUMBER).description("주문 번호"),
                        fieldWithPath("itemList").type(JsonFieldType.ARRAY).description("상품 리스트"),
                        fieldWithPath("buyer").type(JsonFieldType.STRING).description("구매자"),
                        fieldWithPath("price").type(JsonFieldType.NUMBER).description("구매 가격"),
                        fieldWithPath("buyerTel1").type(JsonFieldType.STRING).description("구매자 연락처 1"),
                        fieldWithPath("buyerTel2").type(JsonFieldType.STRING).description("구매자 연락처 2"),
                        fieldWithPath("buyerTel3").type(JsonFieldType.STRING).description("구매자 연락처 3"),
                        fieldWithPath("recipient").type(JsonFieldType.STRING).description("수령자"),
                        fieldWithPath("recipientTel1").type(JsonFieldType.STRING).description("수령자 연락처 1"),
                        fieldWithPath("recipientTel2").type(JsonFieldType.STRING).description("수령자 연락처 2"),
                        fieldWithPath("recipientTel3").type(JsonFieldType.STRING).description("수령자 연락처 3"),
                        fieldWithPath("post1").type(JsonFieldType.STRING).description("수령 주소 우편번호"),
                        fieldWithPath("post2").type(JsonFieldType.STRING).description("수령 주소"),
                        fieldWithPath("post3").type(JsonFieldType.STRING).description("수령 주소 상세 주소"),
                        fieldWithPath("request").type(JsonFieldType.STRING).description("구매자 요청사항"),
                        fieldWithPath("status").type(JsonFieldType.STRING).description("상품 상태"),
                        fieldWithPath("regDate").type(JsonFieldType.STRING).description("구매일")
                )
        ));
    }

    @Test
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
        ResultActions action = mock.perform(MockMvcRequestBuilders.post("/v1/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(body));
        //then
        action.andExpect(MockMvcResultMatchers.status().is2xxSuccessful()); //201 return
        action.andExpect(MockMvcResultMatchers.jsonPath("$.data.orderIdx").isNotEmpty());
        //docs
        action.andDo(document("order_post",
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
                        beneathPath("data").withSubsectionId("data"),
                        fieldWithPath("orderIdx").type(JsonFieldType.NUMBER).description("등록된 주문의 번호")
                )
        ));
    }

    @Test
    void 주문_상태_변경() throws Exception{
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

        long orderIdx = save.getIdx();
        OrderStatus status = save.getStatus();
        Map<String, Object> map = new HashMap<>();
        map.put("idx", orderIdx);
        map.put("orderStatus", String.valueOf(status));

        Gson gson = new Gson();
        String body = gson.toJson(map);

        //when
        ResultActions action = mock.perform(MockMvcRequestBuilders.patch("/v1/orders/status").contentType(MediaType.APPLICATION_JSON).content(body));
        //then
        action.andExpect(MockMvcResultMatchers.status().isOk());
        action.andExpect(MockMvcResultMatchers.jsonPath("$.data.orderIdx").value(orderIdx));

        //docs
        action.andDo(document("order_status",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("idx").type(JsonFieldType.NUMBER).description("주문 번호"),
                    fieldWithPath("orderStatus").type(JsonFieldType.STRING).description("변경될 주문 상태")
                ),
                responseFields(
                    beneathPath("data").withSubsectionId("data"),
                    fieldWithPath("orderIdx").type(JsonFieldType.NUMBER).description("변경된 주문 번호")
                )
        ));
    }

    @Test
    void 주문_삭제() throws Exception{
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

        Long orderIdx = save.getIdx();

        //when
        ResultActions action = mock.perform(RestDocumentationRequestBuilders.delete( "/v1/orders/delete/{idx}", orderIdx).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON));

        //then
        action.andExpect(MockMvcResultMatchers.status().isOk());
        action.andExpect(MockMvcResultMatchers.jsonPath("$.data.orderIdx").value(orderIdx));

        //docs
        action.andDo(document("order_delete",
            preprocessRequest(prettyPrint()),
            preprocessResponse(prettyPrint()),
            pathParameters(
                    parameterWithName("idx").description("주문 번호")
            ),
            responseFields(
                beneathPath("data").withSubsectionId("data"),
                    fieldWithPath("orderIdx").type(JsonFieldType.NUMBER).description("삭제된 주문 번호")
            )
        ));

    }
}