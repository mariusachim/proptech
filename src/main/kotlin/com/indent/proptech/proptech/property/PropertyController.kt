package com.indent.proptech.proptech.property

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.OK
import org.springframework.http.ResponseEntity
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

    @PostMapping
    fun newProperty(@RequestBody property: PropertyData): ResponseEntity<UUID> =
        propertyService.saveProperty(property)
            .let { ResponseEntity.status(CREATED).body(it) }

    @GetMapping("/{id}")
    fun getPropertyById(@PathVariable id: UUID): ResponseEntity<PropertyData> =
        propertyService.getPropertyById(id)
            .let { ResponseEntity.status(OK).body(it) }
}
