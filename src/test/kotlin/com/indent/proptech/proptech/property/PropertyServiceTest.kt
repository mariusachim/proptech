package com.indent.proptech.proptech.property

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.ArgumentMatchers.any
import org.mockito.BDDMockito.given
import org.mockito.Mockito.mock
import java.util.Optional.empty
import java.util.Optional.of
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
            address = "address",
            description = "description"
        )
        given(
            propertyRepository.saveAndFlush(
                any(Property::class.java),
            )
        ).willReturn(
            Property(
                id = uuid,
                name = propertyData.name,
                address = propertyData.address,
                description = "description"
            )
        )

        // when
        val propertyDataSaved = propertyService.saveProperty(propertyData)

        // then
        assertThat(propertyDataSaved).isEqualTo(uuid)
    }

    @Test
    fun `gets property by id and returns property data`() {
        // given
        val uuid = randomUUID()
        val property = Property(
            id = uuid,
            name = "test",
            address = "address",
            description = "description"
        )
        given(propertyRepository.findById(uuid)).willReturn(of(property))

        // when
        val propertyData = propertyService.getPropertyById(uuid)

        // then
        assertThat(propertyData.name).isEqualTo(property.name)
        assertThat(propertyData.address).isEqualTo(property.address)
    }

    @Test
    fun `throws exception when property not found`() {
        // given
        val uuid = randomUUID()
        given(propertyRepository.findById(uuid)).willReturn(empty())

        // when/then
        assertThrows<NoSuchElementException> { propertyService.getPropertyById(uuid) }
    }
}
