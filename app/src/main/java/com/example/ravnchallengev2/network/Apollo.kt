package com.example.ravnchallengev2.network

import com.apollographql.apollo.ApolloClient

// Apollo Client instance
val apolloClient = ApolloClient.builder()
        .serverUrl("https://swapi-graphql.netlify.app/.netlify/functions/index")
        .build()