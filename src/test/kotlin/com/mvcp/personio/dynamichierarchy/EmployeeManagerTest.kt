package com.mvcp.personio.dynamichierarchy


import com.mvcp.personio.dynamichierarchy.entities.Employee
import com.mvcp.personio.dynamichierarchy.managers.EmployeeManager
import com.mvcp.personio.dynamichierarchy.repositories.EmployeeRepository
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
/**
 * Tests Saving/Updating Employees rules
 *
 * @author      Marcelo Pereira
 * @version     1.0.0
 * @since       2019-06-08
 */
class EmployeeManagerTest : BaseTest() {
    @Mock
    lateinit var repository : EmployeeRepository

    @InjectMocks
    lateinit var manager : EmployeeManager

    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
        Mockito.`when`(repository.findByName("TEST_USER")).thenReturn(listOf())
        Mockito.`when`(repository.findByName("andre")).thenReturn(listOf(ANDRE))
        Mockito.`when`(repository.findByName("marcelo")).thenReturn(listOf(MARCELO))
        Mockito.`when`(repository.findByName("aline")).thenReturn(listOf(ALINE))
        Mockito.`when`(repository.findByName("claudia")).thenReturn(listOf(CLAUDIA))
        Mockito.`when`(repository.findByName("joao")).thenReturn(listOf(JOAO))

    }

    @Test
    fun shouldSaveNew() {
        var mockUser = Employee(5, "TEST_USER", ANDRE)
        manager.save(mockUser)
        Mockito.verify(repository, Mockito.times(1)).save(mockUser)
    }

    @Test
    fun shouldNotUpdateRemovingSupervisor() {
        var mockUser = Employee(1, "claudia", null)
        manager.save(mockUser)
        Mockito.verify(repository, Mockito.times(0)).save(mockUser)
    }

    @Test
    fun shouldNotUpdateWithoutSupervisorEvenNotHavingSupervisor() {
        var mockUser = Employee(0, "andre", null)
        manager.save(mockUser)
        Mockito.verify(repository, Mockito.times(0)).save(mockUser)
    }

    @Test
    fun shouldUpdateANonSupervised() {
        var mockUser = Employee(0, "andre", JOAO)
        manager.save(mockUser)
        Mockito.verify(repository, Mockito.times(1)).save(mockUser)
    }
}
