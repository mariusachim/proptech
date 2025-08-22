package com.indent.proptech.proptech.property

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
class PropertyRepositoryTest {

    @Autowired
    lateinit var propertyRepository: PropertyRepository

    @Test
    fun `writes entity correctly generating the id`() {
        // given
        val property =
            Property(
                name = "aa",
                address = "center street"
            )

        // when
        val persisted = propertyRepository.saveAndFlush(property)

        // then
        assertThat(persisted.id).isNotNull
        assertThat(persisted.name).isEqualTo("aa")
        assertThat(persisted.address).isEqualTo("center street")
    }
}