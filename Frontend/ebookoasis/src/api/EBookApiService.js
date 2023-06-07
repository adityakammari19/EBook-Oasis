import { apiClient } from './ApiClient'

export const retrieveAllBooksApi
    = () => apiClient.get(`/api/books`)