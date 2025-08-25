package com.indent.proptech.proptech.property

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.OK
import org.springframework.http.ResponseEntity
import org.springframework.http.ResponseEntity.status
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import java.util.*

@Controller
@ResponseBody
@RequestMapping("/properties")
class PropertyController(@Autowired private val propertyService: PropertyService) {

    private val logger: Logger = LoggerFactory.getLogger(PropertyController::class.java)

    @PostMapping
    fun newProperty(@RequestBody property: PropertyData): ResponseEntity<Void> {
        propertyService.saveProperty(property)
            .also { logger.info("Stored new property with id {}", it) }
        return status(CREATED).build()
    }

    @GetMapping("/{id}")
    fun getPropertyById(@PathVariable id: UUID): ResponseEntity<PropertyData> =
        propertyService.getPropertyById(id)
            .let { status(OK).body(it) }

    @GetMapping("")
    fun getProperties(): ResponseEntity<List<PropertyData>> =
        propertyService.getProperties()
            .let { status(OK).body(it) }

}
