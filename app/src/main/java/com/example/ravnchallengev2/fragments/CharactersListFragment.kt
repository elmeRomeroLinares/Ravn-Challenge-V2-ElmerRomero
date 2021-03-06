package com.example.ravnchallengev2.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloException
import com.example.ravnchallengev2.R
import com.example.ravnchallengev2.adapters.CharactersRecyclerViewAdapter
import com.example.ravnchallengev2.databinding.FragmentCharactersListBinding
import com.example.ravnchallengev2.network.apolloClient
import com.example.starwarsserver.StarWarsCharactersQuery
import kotlinx.coroutines.channels.Channel

class CharactersListFragment : Fragment() {
    companion object {
        private const val CHARACTERS_PER_LOAD = 5
    }

    private lateinit var binding: FragmentCharactersListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCharactersListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val characters = mutableListOf<StarWarsCharactersQuery.Person>()
        val adapter = CharactersRecyclerViewAdapter(characters)
        binding.charactersRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.charactersRecyclerView.adapter = adapter

        //init first load
        val channel = Channel<Unit>(Channel.CONFLATED)
        channel.offer(Unit)
        adapter.onEndOfListReached = {
            channel.offer(Unit)
        }

        lifecycleScope.launchWhenResumed {
            var afterCursor: String? = null
            for (item in channel) {
                val serverResponse = try {
                    apolloClient.query(StarWarsCharactersQuery(
                            after = Input.fromNullable(afterCursor),
                            first = Input.fromNullable(CHARACTERS_PER_LOAD)
                    )).await()
                } catch (e: ApolloException) {
                    // TODO implement error handling
                    return@launchWhenResumed
                }

                val newCharacters = serverResponse.data?.allPeople?.people?.filterNotNull()
                if (newCharacters != null) {
                    characters.addAll(newCharacters)
                    adapter.notifyDataSetChanged()
                }

                afterCursor = serverResponse.data?.allPeople?.pageInfo?.endCursor

                if (characters.size == serverResponse.data?.allPeople?.totalCount) {
                    adapter.setIsLoaderVisible(false)
                    break
                }
            }

            adapter.onEndOfListReached = null
            channel.close()
        }
    }
}