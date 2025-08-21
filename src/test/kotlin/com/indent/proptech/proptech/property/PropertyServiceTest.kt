package com.indent.proptech.proptech.property

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock
import java.util.UUID.randomUUID

class PropertyServiceTest {
    val propertyRepository: PropertyRepository = mock()
    val propertyService = PropertyService(propertyRepository)

    @Test
    fun `saves entity as returns value object`() {
        // given
        val uuid = randomUUID()
        val propertyData = PropertyData(
            name = "test",
            address = "address"
        )
        given(
            propertyRepository.saveAndFlush(
                any(Property::class.java),
            )
        ).willReturn(
            Property(
                id = uuid,
                name = propertyData.name,
                address = propertyData.address
            )
        )

        // when
        val propertyDataSaved = propertyService.saveProperty(propertyData)

        // then
        assertThat(propertyDataSaved).isEqualTo(propertyData)
    }
}