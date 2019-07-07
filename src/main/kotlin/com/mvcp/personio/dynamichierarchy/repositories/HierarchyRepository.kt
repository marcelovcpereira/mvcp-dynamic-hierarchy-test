package com.mvcp.personio.dynamichierarchy.repositories

import com.mvcp.personio.dynamichierarchy.entities.Hierarchy
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface HierarchyRepository: JpaRepository<Hierarchy, Long>