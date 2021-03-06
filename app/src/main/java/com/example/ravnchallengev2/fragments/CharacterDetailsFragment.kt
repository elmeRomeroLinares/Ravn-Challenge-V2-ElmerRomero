package com.example.ravnchallengev2.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toolbar
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import com.example.ravnchallengev2.R
import com.example.ravnchallengev2.adapters.AttributesRecyclerViewAdapter
import com.example.ravnchallengev2.adapters.VehiclesRecyclerViewAdapter
import com.example.ravnchallengev2.databinding.FragmentCharacterDetailsBinding
import com.example.ravnchallengev2.network.apolloClient
import com.example.ravnchallengev2.repository.CharacterAttributesRepository
import com.example.starwarsserver.CharacterDetailsQuery

class CharacterDetailsFragment : Fragment() {

    private lateinit var binding: FragmentCharacterDetailsBinding
    val args: CharacterDetailsFragmentArgs by navArgs()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = FragmentCharacterDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(args.characterName != null){
            binding.ravnChallengeToolbarDetailsFragment.toolbarTextView.text = args.characterName
        } else {
            binding.ravnChallengeToolbarDetailsFragment.toolbarTextView.text = getString(R.string.unknown)
        }

        val toolbar: androidx.appcompat.widget.Toolbar =
                binding.ravnChallengeToolbarDetailsFragment.ravnChallengeToolbar
        toolbar.setNavigationIcon(R.drawable.ic_baseline_arrow_back_24)

        toolbar.setNavigationOnClickListener{
            findNavController().navigateUp()
        }

        lifecycleScope.launchWhenResumed {
            val characterDetailContainer = binding.characterDetailContainer
            characterDetailContainer.visibility = View.GONE
            val characterVehicles = mutableListOf<CharacterDetailsQuery.Vehicle>()

            val response = try{
                apolloClient.query(
                        CharacterDetailsQuery(id = Input.fromNullable(args.characterId))
                ).await()
            } catch (e:ApolloException) {
                binding.loadingLayout.root.visibility = View.GONE
                binding.noticeCellDetailsFragment.root.visibility = View.VISIBLE
                return@launchWhenResumed
            }

            val characterAttributes = response.data?.person
            if (characterAttributes == null || response.hasErrors()){
                binding.loadingLayout.root.visibility = View.GONE
                binding.noticeCellDetailsFragment.root.visibility = View.VISIBLE
                return@launchWhenResumed
            }

            val attributes = CharacterAttributesRepository()
                    .getCharacterFixedAttributes(characterAttributes, requireContext())
            val attributesAdapter = AttributesRecyclerViewAdapter(attributes)
            binding.characterAttributesRecyclerView.layoutManager =
                    LinearLayoutManager(requireContext())
            binding.characterAttributesRecyclerView.adapter = attributesAdapter

            val responseCharacterVehicles = characterAttributes.vehicleConnection?.vehicles?.filterNotNull()
            if(responseCharacterVehicles != null) {
                characterVehicles.addAll(responseCharacterVehicles)
            }
            val vehicleAdapter = VehiclesRecyclerViewAdapter(vehiclesList = characterVehicles)
            binding.characterVehiclesRecyclerView.layoutManager =
                    LinearLayoutManager(requireContext())
            binding.characterVehiclesRecyclerView.adapter = vehicleAdapter

            binding.loadingLayout.root.visibility = View.GONE

            characterDetailContainer.visibility = View.VISIBLE

        }

    }
}