package com.indent.proptech.proptech.property

import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface PropertyRepository : JpaRepository<Property, UUID>

