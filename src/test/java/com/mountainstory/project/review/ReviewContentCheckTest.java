package com.mountainstory.project.review;


import com.mountainstory.project.controller.ReviewController;
import com.mountainstory.project.service.review.ReviewService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@WebMvcTest(ReviewController.class)
public class ReviewContentCheckTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ReviewService reviewService;


    @Test
    @DisplayName("리뷰 작성 규칙 검증 테스트")
    @WithMockUser(username = "테스트용 계정")
    void reviewContentCheckTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/mountain/info/review/check/content")
                .param("reviewTitle","aaaaaaaaaaaa")
                .param("reviewContent","aaaaaaaaaaaaaaaaaaaaa").with(csrf()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
