package com.indent.proptech.proptech.property

import org.springframework.stereotype.Service

@Service
class PropertyService(val propertyRepository: PropertyRepository) {

    fun saveProperty(propertyData: PropertyData): PropertyData {
        val stored = propertyRepository.saveAndFlush(
            Property(
                name = propertyData.name,
                address = propertyData.address,
            )
        )
        return stored.asPropertyData()
    }

    private fun Property.asPropertyData(): PropertyData = PropertyData(
        name = name,
        address = address
    )
}