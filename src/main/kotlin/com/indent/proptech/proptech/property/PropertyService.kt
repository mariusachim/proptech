package com.indent.proptech.proptech.property

import org.springframework.stereotype.Service
import java.util.*

@Service
class PropertyService(val propertyRepository: PropertyRepository) {

    fun saveProperty(propertyData: PropertyData): UUID =
        propertyRepository.saveAndFlush(
            Property(
                name = propertyData.name,
                address = propertyData.address,
                description = propertyData.description,
            )
        ).id ?: throw IllegalArgumentException("Property with name ${propertyData.name} not found")

    fun getPropertyById(id: UUID): PropertyData =
        propertyRepository.findById(id)
            .map { PropertyData(name = it.name, address = it.address, description = it.description) }
            .orElseThrow { NoSuchElementException("Property with id $id not found") }
}
