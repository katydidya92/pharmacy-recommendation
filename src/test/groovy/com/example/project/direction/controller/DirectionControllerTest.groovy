package com.example.project.direction.controller

import com.example.project.direction.service.DirectionService
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

class DirectionControllerTest extends Specification {

    private MockMvc mockMvc
    private DirectionService directionService = Mock()

    def setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(new DirectionController(directionService)).build()
    }

    def "GET /dir/{encodedId}"() {
        given:
        String encodedId = "r"

        String redirectUrl = "https://map.kakao.com/link/map/pharmacy,38.11,128.111"

        when:
        directionService.findDirectionUrlById(encodedId) >> redirectUrl
        def result = mockMvc.perform(MockMvcRequestBuilders.get("/dir/{encodedId}", encodedId))

        then:
        result.andExpect(status().is3xxRedirection())   // 리다이렉트 발생 확인
                .andExpect(redirectedUrl(redirectUrl))  // 리다이렉트 경로 검증
                .andDo(print())

    }
}
