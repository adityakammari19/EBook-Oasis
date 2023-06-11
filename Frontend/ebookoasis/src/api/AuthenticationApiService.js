import { apiClient } from "./ApiClient";

// export const executeBasicAuthenticationService
//     = (token) => apiClient.get(`/basicauth`
//         , {
//             headers: {
//                 Authorization: token
//             }
//         }
//     )

export const executeJwtAuthenticationService
    = (username, password) =>
        apiClient.post(`/api/auth/login`, { username, password })

export const registerNewUserService
    = ({firstName,lastName,username,email,password,phoneNumber}) =>
        apiClient.post(`api/users/register`, {firstName,lastName,username,email,password,phoneNumber})
