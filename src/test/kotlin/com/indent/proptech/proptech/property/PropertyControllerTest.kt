package com.indent.proptech.proptech.property

import com.fasterxml.jackson.databind.ObjectMapper
import org.mockito.BDDMockito.given
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.context.bean.override.mockito.MockitoBean
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.util.UUID.randomUUID
import kotlin.test.Test

@WebMvcTest(PropertyController::class)
@AutoConfigureMockMvc
class PropertyControllerTest(
    @Autowired val mockMvc: MockMvc,
    @Autowired val objectMapper: ObjectMapper
) {

    @MockitoBean
    lateinit var service: PropertyService

    @Test
    fun `POST validates input and returns 201`() {
        // given
        val req = PropertyData("Casa Nouă", "Bld. Eroilor 2")
        val givenId = randomUUID()
        given(service.saveProperty(req)).willReturn(givenId)

        // then
        mockMvc.perform(
            post("/properties")
                .contentType(APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(req))
        ).andExpect(status().isCreated)
    }

    @Test
    fun `GET property by ID returns 200 and property data`() {
        // given
        val propertyId = randomUUID()
        val propertyData = PropertyData("Casa Nouă", "Bld. Eroilor 2")
        given(service.getPropertyById(propertyId)).willReturn(propertyData)

        // then
        mockMvc.perform(
            get("/properties/${propertyId}")
                .contentType(APPLICATION_JSON)
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$.name").value(propertyData.name))
            .andExpect(jsonPath("$.address").value(propertyData.address))
    }
}
