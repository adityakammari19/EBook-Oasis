import { apiClient } from './ApiClient'

export const retrieveAllBooksApi
    = (username) => apiClient.get(`/api/books/user/${username}`)

export const retrieveAllSubscribedBooksApi
    = (username) => apiClient.get(`/api/subscriptions/user/${username}`)

export const retrieveAllPublishedBooksApi
    = (username) => apiClient.get(`/api/publications/user/${username}`)

export const retriveBookApi
    = (bookId) => apiClient.get(`/api/books/${bookId}`)

export const subscribeToABookApi
    = (username,bookId) => apiClient.post(`/api/subscriptions/users/${username}/books/${bookId}`)

export const unSubscribeToABookApi
    = (username,bookId) => apiClient.delete(`/api/subscriptions/users/${username}/books/${bookId}`)