import { apiClient } from './ApiClient'

export const retrieveAllBooksApi
    = (username) => apiClient.get(`/api/books/user/${username}`)

export const retrieveAllSubscribedBooksApi
    = (username) => apiClient.get(`/api/subscriptions/user/${username}`)

export const retrieveAllPublishedBooksApi
    = (username) => apiClient.get(`/api/publications/user/${username}`)