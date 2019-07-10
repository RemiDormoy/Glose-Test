package com.rdo.octo.glose.reducers

import com.rdo.octo.glose.actions.LoadBookshelvesAction
import com.rdo.octo.glose.entities.BookShelve
import com.rdo.octo.glose.repositories.LoadBookshelvesRepository
import com.rdo.octo.glose.state.GloseState
import org.assertj.core.api.Assertions
import org.junit.Test

import org.junit.runner.RunWith
import org.mockito.BDDMockito
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class LoadBookshelvesReducerTest {

    @Mock
    private lateinit var repository: LoadBookshelvesRepository
    @InjectMocks
    private lateinit var reducer: LoadBookshelvesReducer

    @Test
    fun reduce() {
        // Given
        val list = listOf(Mockito.mock(BookShelve::class.java))
        BDDMockito.given(repository.getBookshelves()).willReturn(list)
        val state = GloseState()

        // When
        val result = reducer.reduce(LoadBookshelvesAction, state)

        // Then
        Assertions.assertThat(result).isEqualTo(GloseState(list))
    }
}