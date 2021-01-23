package com.dsvag.androidacademyproject.ui.credits

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import coil.load
import coil.transform.BlurTransformation
import coil.transform.RoundedCornersTransformation
import com.dsvag.androidacademyproject.R
import com.dsvag.androidacademyproject.databinding.FragmentCreditBinding
import com.dsvag.androidacademyproject.models.person.Person
import com.dsvag.androidacademyproject.utils.ItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CreditFragment : Fragment() {
    private var _binding: FragmentCreditBinding? = null
    private val binding get() = _binding!!

    private val personViewModel: PersonViewModel by viewModels()

    private val castAdapter by lazy { MoviesAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreditBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.filmList.addItemDecoration(ItemDecoration(16f))
        binding.filmList.adapter = castAdapter

        personViewModel.personData.observe(viewLifecycleOwner) { person ->
            person?.let { setPersonData(it) }
        }

        personViewModel.personMovieData.observe(viewLifecycleOwner) { movieCredits ->
            movieCredits?.let { castAdapter.setData(it) }
        }

        binding.back.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onStart() {
        super.onStart()
        val creditId = arguments?.getInt("castId") ?: 0

        personViewModel.fetchPerson(creditId)
        personViewModel.fetchPersonMovie(creditId)
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
    }
}