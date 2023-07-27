import { createContext, useContext, useState } from "react";

import { apiClient } from "../api/ApiClient";
import { executeJwtAuthenticationService, registerNewUserService } from "../api/AuthenticationApiService";

//1: Create a Context
export const AuthContext = createContext()

export const useAuth = () => useContext(AuthContext)

//2: Share the created context with other components
export default function AuthProvider({ children }) {

    //3: Put some state in the context
    const [isAuthenticated, setAuthenticated] = useState(false)

    const [username, setUsername] = useState(null)

    const [token, setToken] = useState(null)


    async function login(username, password) {

        try {

            const response = await executeJwtAuthenticationService(username, password)

            if (response.status === 200) {

                const jwtToken = 'Bearer ' + response.data.accessToken
                // const jwtToken =  response.data.accessToken
                setAuthenticated(true)
                setUsername(username)
                setToken(jwtToken)


                apiClient.interceptors.request.use(
                    (config) => {
                        console.log('intercepting and adding a token')
                        config.headers.Authorization = jwtToken
                        return config
                    }
                )

                return true
            } else {
                logout()
                return false
            }
        } catch (error) {
            logout()
            return false
        }
    }

    async function register({ firstName, lastName, username, email, password, phoneNumber }) {

        try {

            const response = await registerNewUserService({ firstName, lastName, username, email, password, phoneNumber })

            if (response.status === 201) {
                console.log("User created with username" + username)
                return true
            }
            else {
                logout()
                return false
            }
        } catch (error) {
            logout()
            return false
        }
    }


    function logout() {
        setAuthenticated(false)
        setToken(null)
        setUsername(null)
        console.log("logged out")
    }

    return (
        <AuthContext.Provider value={{ isAuthenticated, login, register, logout, username, token }}>
            {children}
        </AuthContext.Provider>
    )
} 