package com.dsvag.androidacademyproject.ui.person

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.BlurTransformation
import coil.transform.RoundedCornersTransformation
import com.dsvag.androidacademyproject.R
import com.dsvag.androidacademyproject.databinding.FragmentPersonBinding
import com.dsvag.androidacademyproject.models.person.Person
import com.dsvag.androidacademyproject.ui.MainActivity
import com.dsvag.androidacademyproject.ui.viewBinding
import com.dsvag.androidacademyproject.utils.ItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PersonFragment : Fragment(R.layout.fragment_person) {
    private val binding by viewBinding(FragmentPersonBinding::bind)

    private val personViewModel: PersonViewModel by viewModels()

    private val moviesAdapter by lazy { MoviesAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as MainActivity?)?.setBottomViewVisibility(false)

        binding.filmList.addItemDecoration(ItemDecoration(16f))
        binding.filmList.adapter = moviesAdapter

        personViewModel.state.observe(viewLifecycleOwner, ::stateObserver)

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onStart() {
        super.onStart()
        val creditId = arguments?.getLong("castId") ?: 0

        personViewModel.fetchPerson(creditId)
    }

    private fun stateObserver(state: PersonViewModel.State) {
        when (state) {
            PersonViewModel.State.Loading -> binding.container.isVisible = false
            is PersonViewModel.State.Error -> {
                Toast.makeText(requireContext(), state.msg, Toast.LENGTH_LONG).show()
                findNavController().popBackStack()
            }
            is PersonViewModel.State.Success -> {
                setPersonData(state.person)
                moviesAdapter.setData(state.personMovie)
            }
        }
    }

    private fun setPersonData(person: Person) {
        val backdropUrl = "https://image.tmdb.org/t/p/h632" + person.profilePath
        val photoUrl = "https://image.tmdb.org/t/p/h632" + person.profilePath

        binding.backdrop.load(backdropUrl) {
            crossfade(true)
            transformations(BlurTransformation(requireContext()))
        }

        binding.photo.load(photoUrl) {
            crossfade(true)
            transformations(RoundedCornersTransformation(16f))
            error(R.drawable.ic_launcher_foreground)
        }

        binding.name.text = person.name
        binding.dateOfBirth.text = person.birthday?.split('-')?.asReversed()?.joinToString(".")
        binding.placeOfBirth.text = person.placeOfBirth
        binding.knownForDepartment.text = person.knownForDepartment
        binding.biography.text = person.biography

        binding.container.isVisible = true
    }
}