package fr.eseo.ld.ts.notetaker.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import fr.eseo.ld.ts.notetaker.repositories.NoteTakerRepository
import fr.eseo.ld.ts.notetaker.repositories.NoteTakerRepositoryFirestoreImpl

class NoteTakerViewModelFactory (
    private val repository : NoteTakerRepositoryFirestoreImpl
) : ViewModelProvider.Factory
{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(NoteTakerViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NoteTakerViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }



}