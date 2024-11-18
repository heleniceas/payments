package br.com.caju.payments.repository

import br.com.caju.payments.entity.EstablishmentEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface EstablishmentRepository : JpaRepository<EstablishmentEntity, UUID> {

    fun findByName(name: String): EstablishmentEntity?

    //fun save(establishmentEntity: EstablishmentEntity): EstablishmentEntity


}