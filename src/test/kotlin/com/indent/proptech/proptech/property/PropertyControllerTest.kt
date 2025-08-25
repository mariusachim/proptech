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
        val req = PropertyData("Casa Nouă", "Bld. Eroilor 2", null)
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
        val propertyData = PropertyData("Casa Nouă", "Bld. Eroilor 2", null)
        given(service.getPropertyById(propertyId)).willReturn(propertyData)

        // then
        mockMvc.perform(
            get("/properties/${propertyId}")
                .contentType(APPLICATION_JSON)
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$.name").value(propertyData.name))
            .andExpect(jsonPath("$.address").value(propertyData.address))
    }

    @Test
    fun `GET all properties`() {
        // given
        val propertyData1 = PropertyData("Casa Nouă 1", "Bld. Eroilor 1", "Nice prop 1")
        val propertyData2 = PropertyData("Casa Nouă 2", "Bld. Eroilor 2", "Nice prop 2")
        val propertyData3 = PropertyData("Casa Nouă 3", "Bld. Eroilor 3", "Nice prop 3")
        given(service.getProperties()).willReturn(
            listOf(propertyData1, propertyData2, propertyData3)
        )

        // then
        mockMvc.perform(
            get("/properties")
                .contentType(APPLICATION_JSON)
        ).andExpect(status().isOk)
            .andExpect(jsonPath("$[0].name").value(propertyData1.name))
            .andExpect(jsonPath("$[0].address").value(propertyData1.address))
            .andExpect(jsonPath("$[0].description").value(propertyData1.description))
            .andExpect(jsonPath("$[1].name").value(propertyData2.name))
            .andExpect(jsonPath("$[1].address").value(propertyData2.address))
            .andExpect(jsonPath("$[1].description").value(propertyData2.description))
            .andExpect(jsonPath("$[2].name").value(propertyData3.name))
            .andExpect(jsonPath("$[2].address").value(propertyData3.address))
            .andExpect(jsonPath("$[2].description").value(propertyData3.description))
    }
}
